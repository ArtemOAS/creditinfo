package com.pageobject;

import com.entity.Data;
import com.utils.waitutils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artem on 12.05.2017.
 */
public class MoneymanPage implements CreditDataPage, WaitUtils {
    private WebDriver driver;

    public MoneymanPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "money")
    private WebElement sum;

    @FindBy(id = "days")
    private WebElement period;

    @FindBy(xpath = "//span[@class='mainCalculatorLabel__min mainCalculator__label__value']")
    private List<WebElement> minSumAndPeriod;

    @FindBy(xpath = "//span[@class='mainCalculatorLabel__max mainCalculator__label__value']")
    private List<WebElement> maxSumAndPeriod;

    @FindBy(xpath = "//span[@class='mainCalculator__info__value']")
    private List<WebElement> infoAboutCredit;

    @FindBy(xpath = "//span[@class='mainCalculator__info__label']")
    private List<WebElement> dataSign;

    @Override
    public void saveSum() {

        int maxSum = 30000;
        int maxPeriod = 30;
        List<Data> list = new ArrayList<>();

//        for (int minSum = value(minSumAndPeriod,0); minSum < maxSum; minSum += 500){
//            waitFor(sum, ExpectedConditions::visibilityOf);
//            sum.clear();
//            sum.sendKeys(String.valueOf(minSum));
//            for (int minPeriod = 5; minPeriod <= maxPeriod; minPeriod++){
//                waitFor(period, ExpectedConditions::visibilityOf);
//                period.clear();
//                period.clear();
//                period.sendKeys(String.valueOf(minPeriod));
//                infoAboutCredit.get(0).click();
//
//                waitFor(infoAboutCredit.get(2), ExpectedConditions::visibilityOf);
//                System.out.println(sum.getAttribute("value")+"-"+period.getAttribute("value")+"дней : "+infoAboutCredit.get(2).getText());
//                list.add(sum.getAttribute("value")+"-"+period.getAttribute("value")+"дней : "+infoAboutCredit.get(2).getText());
//
//            }
//        }

//        for (int minSum = 30500; minSum < 70000; minSum += 500){
//            waitFor(sum, ExpectedConditions::visibilityOf);
//            sum.clear();
//            sum.sendKeys(String.valueOf(minSum));
//
//            for (int minPeriod = 11; minPeriod <= 19; minPeriod+=2){
//                waitFor(period, ExpectedConditions::visibilityOf);
//                period.clear();
//                period.clear();
//                period.sendKeys(String.valueOf(minPeriod));
//                infoAboutCredit.get(1).click();
//
//                waitFor(infoAboutCredit.get(3), ExpectedConditions::visibilityOf);
//                int res = (minPeriod/2)*Integer.parseInt(infoAboutCredit.get(3).getText().replace(" ",""))-Integer.parseInt(infoAboutCredit.get(0).getText().replace(" ", ""));
//                System.out.println(sum.getAttribute("value")+"-"+period.getAttribute("value")+"недель : "+res);
//                list.add(sum.getAttribute("value")+"-"+period.getAttribute("value")+"недель : "+res);
//            }
//        }

        list.add(new Data(data -> {
            data.setSumCredit("100");
            data.setPeriodCredit("121212");
            data.setPercentSum("2323");
        }));

    }

    private Integer value(List<WebElement> elements, int position){
        waitFor(elements.get(position), ExpectedConditions::visibilityOf);
        return Integer.parseInt(elements.get(position).getText().replace(" ",""));
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }
}
