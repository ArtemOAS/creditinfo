package com.test.waitutils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Function;

/**
 * Created by Artem on 15.05.2017.
 */
public interface WaitUtils {
    int DEFAULT_WAIT = 30;

    WebDriver getDriver();

    default void waitFor(WebElement element, Function<WebElement, ExpectedCondition<?>> con){
        new WebDriverWait(getDriver(),DEFAULT_WAIT).until(con.apply(element));
    }

    default void waitFor(By by, Function<By, ExpectedCondition<?>> con){
        new WebDriverWait(getDriver(),DEFAULT_WAIT).until(con.apply(by));
    }

    default void waitFor(List<WebElement> elements, Function<List<WebElement>, ExpectedCondition<?>> con){
        new WebDriverWait(getDriver(),DEFAULT_WAIT).until(con.apply(elements));
    }

}
