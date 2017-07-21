package com.test.webdriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import ru.stqa.selenium.factory.WebDriverPool;

import java.util.concurrent.TimeUnit;

public class WebDriverFactory extends WebDriverCapabilities {

    @BeforeTest
    public void setUp(){
        System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");

        webDriverMap().get(WebDriverName.CHROME.name()).setupDriver(WebDriverName.CHROME.name());
        driver = WebDriverPool.DEFAULT.getDriver(capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @AfterTest
    public void closeDriver(){
        if (driver != null && !driver.getWindowHandles().isEmpty()){
            WebDriverPool.DEFAULT.dismissAll();
        }
    }

}
