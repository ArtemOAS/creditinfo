package com.test.webdriver;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import ru.stqa.selenium.factory.WebDriverPool;

import java.util.concurrent.TimeUnit;

@Service
public class WebDriverFactory {

    @Autowired
    private WebDriverCapabilities webDriverCapabilities;

    @BeforeTest
    public WebDriver getDriver(){
        WebDriver driver;

        System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");

        webDriverCapabilities.webDriverMap().get(WebDriverName.CHROME.name()).setupDriver(WebDriverName.CHROME.name());
        driver = WebDriverPool.DEFAULT.getDriver(webDriverCapabilities.capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        return driver;
    }

    @AfterTest
    public void closeDriver(){
        if (getDriver() != null && !getDriver().getWindowHandles().isEmpty()){
            WebDriverPool.DEFAULT.dismissAll();
        }
    }

}
