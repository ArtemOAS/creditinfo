package com.test.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artem on 10.05.2017.
 */
@Service
public class WebDriverCapabilities {

    public DesiredCapabilities capabilities;

    public Map<String, WebDriverProcess> webDriverMap() {
        return new HashMap<String, WebDriverProcess>() {{

            put(WebDriverName.FIREFOX.name(), nameDriver -> {
                capabilities = DesiredCapabilities.firefox();
                capabilities.setCapability("marionette", true);
            });

            put(WebDriverName.CHROME.name(), nameDriver -> {
                capabilities = DesiredCapabilities.chrome();
            });
        }};
    }


}
