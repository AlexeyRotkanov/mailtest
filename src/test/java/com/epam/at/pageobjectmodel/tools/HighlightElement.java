package com.epam.at.pageobjectmodel.tools;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HighlightElement {

    public static void highlightElement(WebDriver driver, WebElement element) {
        String defaultBackground = element.getCssValue("backgroundColor");
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].style.backgroundColor = '" + "yellow" + "'", element);
        Screenshot.takeScreenshot(driver);
        js.executeScript("arguments[0].style.backgroundColor = '" + defaultBackground + "'", element);
    }
}