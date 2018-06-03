package com.test.steps;

import com.test.utils.fileutils.FileData;
import com.test.utils.fileutils.TestData;
import com.test.pageobject.MoneymanPage;
import com.test.webdriver.WebDriverFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Artem on 10.05.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MoneymanSteps {
    private Logger logger = Logger.getLogger(MoneymanSteps.class);

    @Autowired
    private WebDriverFactory webDriverFactory;

    @Autowired
    private  MoneymanPage moneymanPage;

    @Autowired
    private TestData testData;

    @Test
    public void moneymanDataCredit(){
        String moneyman = testData.getFileData(FileData.MOMEYMAN_URL.getValue());
        logger.info(moneyman);
        webDriverFactory.getDriver().get(moneyman);
        moneymanPage.saveSum();
    }

}
