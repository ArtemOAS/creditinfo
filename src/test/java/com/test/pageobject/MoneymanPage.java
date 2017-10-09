package com.test.pageobject;

import com.dao.CreditInfoDao;
import com.entity.Data;
import com.test.waitutils.WaitUtils;
import com.test.webdriver.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Artem on 12.05.2017.
 */

@Component
public class MoneymanPage implements CreditDataPage, WaitUtils {

    @Autowired
    private WebDriverFactory webDriverFactory;

    @Qualifier("test_bl")
    @Autowired
    private CreditInfoDao dataBaseBL;

    @Override
    public void saveSum() {
        WebElement sum = webDriverFactory.getDriver().findElement(By.id("money"));
        WebElement period = webDriverFactory.getDriver().findElement(By.id("days"));
        List<WebElement> minSumAndPeriod = webDriverFactory.getDriver().findElements(By.xpath("//span[@class='mainCalculatorLabel__min mainCalculator__label__value']"));
        List<WebElement> maxSumAndPeriod = webDriverFactory.getDriver().findElements(By.xpath("//span[@class='mainCalculatorLabel__max mainCalculator__label__value']"));
        List<WebElement> infoAboutCredit = webDriverFactory.getDriver().findElements(By.xpath("//span[@class='mainCalculator__info__value']"));
        List<WebElement> dataSign = webDriverFactory.getDriver().findElements(By.xpath("//span[@class='mainCalculator__info__label']"));

        int maxSum = 30000;
        int maxPeriod = 30;

        for (int minSum = value(minSumAndPeriod, 0); minSum < maxSum; minSum += 500) {
            waitFor(sum, ExpectedConditions::visibilityOf);
            sum.clear();
            sum.sendKeys(String.valueOf(minSum));
            for (int minPeriod = 5; minPeriod <= maxPeriod; minPeriod++) {
                waitFor(period, ExpectedConditions::visibilityOf);
                period.clear();
                period.clear();
                period.sendKeys(String.valueOf(minPeriod));
                infoAboutCredit.get(0).click();

                waitFor(infoAboutCredit.get(2), ExpectedConditions::visibilityOf);
                System.out.println(sum.getAttribute("value") + "-" + period.getAttribute("value") + "дней : " + infoAboutCredit.get(2).getText());

                Data data = new Data(d -> {
                    d.setNameCompany(webDriverFactory.getDriver().getCurrentUrl().replace("https:", "").replace(".ru", "").replaceAll("/", ""));
                    d.setSumCredit(sum.getAttribute("value"));
                    d.setPeriodCredit(period.getAttribute("value"));
                    d.setOldPercentSum(infoAboutCredit.get(2).getText().replace(" ", ""));
                });

                Data dataCompany = new Data(d -> {
                    d.setNameCompany(webDriverFactory.getDriver().getCurrentUrl().replace("https:", "").replace(".ru", "").replaceAll("/", ""));
                    d.setSumCredit(sum.getAttribute("value"));
                    d.setPeriodCredit(period.getAttribute("value"));
                });

                writeDataToDB(data, dataCompany);
            }
        }

        for (int minSum = 30500; minSum < 70000; minSum += 500) {
            waitFor(sum, ExpectedConditions::visibilityOf);
            sum.clear();
            sum.sendKeys(String.valueOf(minSum));

            for (int minPeriod = 11; minPeriod <= 19; minPeriod += 2) {
                waitFor(period, ExpectedConditions::visibilityOf);
                period.clear();
                period.clear();
                period.sendKeys(String.valueOf(minPeriod));
                infoAboutCredit.get(1).click();

                waitFor(infoAboutCredit.get(3), ExpectedConditions::visibilityOf);
                int res = (minPeriod / 2) * Integer.parseInt(infoAboutCredit.get(3).getText().replace(" ", "")) - Integer.parseInt(infoAboutCredit.get(0).getText().replace(" ", ""));

                Data data = new Data(d -> {
                    d.setNameCompany(webDriverFactory.getDriver().getCurrentUrl().replace("https:", "").replace(".ru", "").replaceAll("/", ""));
                    d.setSumCredit(sum.getAttribute("value"));
                    d.setPeriodCredit(period.getAttribute("value"));
                    d.setOldPercentSum(String.valueOf(res));
                });

                Data dataCompany = new Data(d -> {
                    d.setNameCompany(webDriverFactory.getDriver().getCurrentUrl().replace("https:", "").replace(".ru", "").replaceAll("/", ""));
                    d.setSumCredit(sum.getAttribute("value"));
                    d.setPeriodCredit(period.getAttribute("value"));
                });

                writeDataToDB(data, dataCompany);
            }
        }

    }

    private void writeDataToDB(Data data, Data dataCompany){
        if (!dataBaseBL.findAll().contains(data)){
            dataBaseBL.addNewData(data);
        }
        if (!dataBaseBL.findAll().isEmpty() && dataBaseBL.findDataCredit(dataCompany).contains(dataCompany)
                &&!dataBaseBL.findAll().contains(data)) {

            for (Data dataRes : dataBaseBL.findAll()) {
                String res = dataRes.getOldPercentSum();
                if (dataRes.getOldPercentSum() != null && !res.equals(data.getOldPercentSum())) {
                    dataBaseBL.updateDataCredit(data);

                    int differencePercentsum=0;
                    if (Integer.parseInt(dataRes.getOldPercentSum())
                            > Integer.parseInt(data.getOldPercentSum())) {
                        differencePercentsum =
                                Integer.parseInt(dataRes.getOldPercentSum())
                                        - Integer.parseInt(data.getOldPercentSum());
                    }else {
                        differencePercentsum =
                                Integer.parseInt(data.getOldPercentSum())
                                        - Integer.parseInt(dataRes.getOldPercentSum());
                    }

                    int finalDifferencePercentsum = differencePercentsum;
                    dataBaseBL.updateDataCredit(new Data(d -> {
                        d.setDifferencePercentSum(String.valueOf(finalDifferencePercentsum));
                        d.setNameCompany(data.getNameCompany());
                        d.setSumCredit(data.getSumCredit());
                        d.setPeriodCredit(data.getPeriodCredit());
                    }));
                }
            }
        } else {
            for (Data dataCompanyRes : dataBaseBL.findDataCredit(dataCompany)) {
                if (dataCompanyRes != null && dataCompanyRes.equals(dataCompany)) {
                    dataBaseBL.updateDataNewPercentSum(
                            new Data(d -> {
                                d.setOldPercentSum(data.getOldPercentSum());
                                d.setNameCompany(dataCompany.getNameCompany());
                                d.setSumCredit(dataCompany.getSumCredit());
                                d.setPeriodCredit(dataCompany.getPeriodCredit());
                            })
                    );

                    dataBaseBL.updateDataDiffSum(
                            new Data(d -> {
                                d.setDifferencePercentSum(String.valueOf(0));
                                d.setNameCompany(dataCompany.getNameCompany());
                                d.setSumCredit(dataCompany.getSumCredit());
                                d.setPeriodCredit(dataCompany.getPeriodCredit());
                            })
                    );
                }
            }
        }
    }

    private Integer value(List<WebElement> elements, int position) {
        waitFor(elements.get(position), ExpectedConditions::visibilityOf);
        return Integer.parseInt(elements.get(position).getText().replace(" ", ""));
    }

    @Override
    public WebDriver getDriver() {
        return webDriverFactory.getDriver();
    }
}
