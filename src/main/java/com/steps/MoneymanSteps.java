package com.steps;

import com.pageobject.MoneymanPage;
import com.webdriver.WebDriverFactory;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Created by Artem on 10.05.2017.
 */
public class MoneymanSteps extends WebDriverFactory{
    private Logger logger = Logger.getLogger(MoneymanSteps.class);

    @Test(alwaysRun = true, groups = {"moneyman"}, priority = 1)
    public void moneymanDataCredit(){

        MoneymanPage moneymanPage = new MoneymanPage(driver);
        logger.info("https://moneyman.ru/");
//        driver.get("https://moneyman.ru/");
        moneymanPage.saveSum();


    }
}
