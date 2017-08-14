package com.test.steps;

import com.test.pageobject.MoneymanPage;
import com.test.webdriver.WebDriverFactory;
import org.apache.log4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

/**
 * Created by Artem on 10.05.2017.
 */
@SpringBootTest
public class MoneymanSteps extends WebDriverFactory{
    private Logger logger = Logger.getLogger(MoneymanSteps.class);

    @Test
    public void moneymanDataCredit(){

        MoneymanPage moneymanPage = new MoneymanPage(driver);
        logger.info("https://moneyman.ru/");
        driver.get("https://moneyman.ru/");
        moneymanPage.saveSum();


    }
}
