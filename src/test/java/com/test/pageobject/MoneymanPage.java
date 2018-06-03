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

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class MoneymanPage implements CreditDataPage, WaitUtils {

    @Autowired
    private WebDriverFactory webDriverFactory;

    @Qualifier("bl")
    @Autowired
    private CreditInfoDao dataBaseBL;

    @Override
    public void saveSum() {
        String moneyMan = "moneyman";
        String sumWithPercentPath = "//div[@class='mainCalculator__info__col mainCalculatorInfo__col_3 new']//div[@class='mainCalculator__info__data_default']//span[@class='mainCalculator__info__value']";
        WebElement sum = webDriverFactory.getDriver().findElement(By.xpath("//input[@id='money']"));
        WebElement period = webDriverFactory.getDriver().findElement(By.id("days"));
        List<WebElement> minSumAndPeriod = webDriverFactory.getDriver().findElements(By.xpath(".//span[@class='mainCalculatorLabel__min mainCalculator__label__value']"));
        List<WebElement> infoAboutCredit = webDriverFactory.getDriver().findElements(By.xpath(".//span[@class='mainCalculator__info__value']"));
        WebElement sumWithPercent = webDriverFactory.getDriver().findElement(By.xpath(sumWithPercentPath));
        int maxSum = 10000;
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

                waitFor(By.xpath(sumWithPercentPath), ExpectedConditions::presenceOfElementLocated);
                int percent = (((Integer.parseInt(sumWithPercent.getAttribute("innerText").replaceAll(" ", ""))+104) * 100) / Integer.parseInt(sum.getAttribute("value").replaceAll(" ", ""))) - 100;
                System.out.println(sum.getAttribute("value") + "-" + period.getAttribute("value") + "дней : " + percent);

                Data data = new Data(d -> {
                    d.setNameCompany(moneyMan);
                    d.setSumCredit(sum.getAttribute("value").replaceAll(" ", ""));
                    d.setPeriodCredit(period.getAttribute("value").replaceAll(" ", ""));
                    d.setOldPercentSum(String.valueOf(percent).replaceAll(" ", ""));
                    d.setNewPercentSum("0");
                    d.setDifferencePercentSum("0");
                });

                writeDataToDB(data);
            }
        }

//        for (int minSum = 30500; minSum < 70000; minSum += 500) {
//            waitFor(sum, ExpectedConditions::visibilityOf);
//            sum.clear();
//            sum.sendKeys(String.valueOf(minSum));
//
//            for (int minPeriod = 11; minPeriod <= 19; minPeriod += 2) {
//                waitFor(period, ExpectedConditions::visibilityOf);
//                period.clear();
//                period.clear();
//                period.sendKeys(String.valueOf(minPeriod));
//                infoAboutCredit.get(1).click();
//
//                waitFor(By.xpath(sumWithPercentPath), ExpectedConditions::presenceOfElementLocated);
//                int res = (minPeriod / 2) * Integer.parseInt(sumWithPercent.getAttribute("innerText").replaceAll(" ", "")) - Integer.parseInt(infoAboutCredit.get(0).getText().replace(" ", ""));
//
//                Data data = new Data(d -> {
//                    d.setNameCompany(moneyMan);
//                    d.setSumCredit(sum.getAttribute("value").replaceAll(" ", ""));
//                    d.setPeriodCredit(period.getAttribute("value").replaceAll(" ", ""));
//                    d.setOldPercentSum(String.valueOf(res).replaceAll(" ", ""));
//                    d.setNewPercentSum("0");
//                    d.setDifferencePercentSum("0");
//                });
//
//                writeDataToDB(data);
//            }
//        }

    }

    private void writeDataToDB(Data data) {
        List<Data> dataList = dataBaseBL.findAll();

        Optional<Data> dataRes = dataList.stream().filter(d ->
                d.getNameCompany().equals(data.getNameCompany()) &&
                        d.getSumCredit().equals(data.getSumCredit()) &&
                        d.getPeriodCredit().equals(data.getPeriodCredit())).findFirst();

        if (dataList.isEmpty() || !dataRes.isPresent()) {
            dataBaseBL.addNewData(data);
        } else {
            Data dataResult = dataList.stream().filter(d ->
                    !d.getOldPercentSum().equals(data.getOldPercentSum()) &&
                            d.getNameCompany().equals(data.getNameCompany()) &&
                            d.getSumCredit().equals(data.getSumCredit()) &&
                            d.getPeriodCredit().equals(data.getPeriodCredit())
            ).findFirst().orElse(null);
            if (dataResult != null) {
                dataBaseBL.updateDataCredit(new Data(d -> {
                    d.setDifferencePercentSum(differencePercentSum(dataResult.getOldPercentSum(), data.getOldPercentSum()));
                    d.setNameCompany(data.getNameCompany());
                    d.setSumCredit(data.getSumCredit());
                    d.setPeriodCredit(data.getPeriodCredit());
                    d.setNewPercentSum(data.getOldPercentSum());
                    d.setOldPercentSum(dataResult.getOldPercentSum());
                }));
            } else {
                dataBaseBL.updateDataNewPercentSum(
                        new Data(d -> {
                            d.setNewPercentSum(data.getOldPercentSum());
                            d.setNameCompany(data.getNameCompany());
                            d.setSumCredit(data.getSumCredit());
                            d.setPeriodCredit(data.getPeriodCredit());
                        })
                );
            }
        }
    }

    private String differencePercentSum(String oldPercentSumExpected, String oldPercentSumActual) {
        int differencePercentsum = 0;
        if (Integer.parseInt(oldPercentSumExpected)
                > Integer.parseInt(oldPercentSumActual)) {
            differencePercentsum =
                    Integer.parseInt(oldPercentSumExpected)
                            - Integer.parseInt(oldPercentSumActual);
        } else if (Integer.parseInt(oldPercentSumExpected)
                < Integer.parseInt(oldPercentSumActual)) {
            differencePercentsum =
                    Integer.parseInt(oldPercentSumActual)
                            - Integer.parseInt(oldPercentSumExpected);
        }
        return String.valueOf(differencePercentsum);
    }

    private Integer value(List<WebElement> elements, Integer position) {
        waitFor(elements.get(position), ExpectedConditions::visibilityOf);
        return Integer.parseInt(elements.get(position).getText().replace(" ", ""));
    }

    @Override
    public WebDriver getDriver() {
        return webDriverFactory.getDriver();
    }
}
