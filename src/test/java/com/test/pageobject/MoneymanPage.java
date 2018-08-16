package com.test.pageobject;

import com.entity.Data;
import com.test.utils.dbutils.WriteToDB;
import com.test.utils.waitutils.WaitUtils;
import com.test.webdriver.WebDriverFactory;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MoneymanPage implements CreditDataPage, WaitUtils {

    private static final String ZERO = "0";
    private static final int oneHundredPercent = 100;

    @Autowired
    private WebDriverFactory webDriverFactory;

    @Autowired
    private WriteToDB writeToDB;

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

                Data data = new Data(d -> {
                    d.setNameCompany(moneyMan);
                    d.setSumCredit(getTextWS(sum.getAttribute(Attribute.VALUE.toString())));
                    d.setPeriodCredit(getTextWS(period.getAttribute(Attribute.VALUE.toString())));
                    d.setOldPercentSum(getTextWS(String.valueOf(
                            getPercent(getTextWS(sumWithPercent.getAttribute(Attribute.INNER_TEXT.toString())),
                            getTextWS(sum.getAttribute(Attribute.VALUE.toString()))))));
                    d.setNewPercentSum(ZERO);
                    d.setDifferencePercentSum(ZERO);
                });

                writeToDB.writeDataToDB(data);
            }
        }
    }

    private int getPercent(String sumWithPercent, String sum){
        return (((Integer.parseInt(sumWithPercent)) * oneHundredPercent) / Integer.parseInt(sum)) - oneHundredPercent;
    }

    private String getTextWS(String text){
        return text.replaceAll(StringUtils.SPACE, StringUtils.EMPTY);
    }

    private int value(List<WebElement> elements, Integer position) {
        waitFor(elements.get(position), ExpectedConditions::visibilityOf);
        return Integer.parseInt(elements.get(position).getText().replace(" ", ""));
    }

    @Override
    public WebDriver getDriver() {
        return webDriverFactory.getDriver();
    }
}

enum Attribute {
    VALUE("value"), INNER_TEXT("innerText");

    private String attribute;

    Attribute(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return attribute;
    }
}
