package com.epam.at.pageobjectmodel.decorators;

import com.epam.at.pageobjectmodel.reporting.MailLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

public class CustomDriverDecorator implements WebDriver, JavascriptExecutor {

    protected WebDriver driver;

    public CustomDriverDecorator(WebDriver driver) {
        this.driver = driver;
    }

    public void get(String url) {
        MailLogger.debug(String.format("Opening URL: %s", url));
        driver.get(url);
    }

    public String getCurrentUrl() {
        MailLogger.debug(String.format("Getting current URL: %s", driver.getCurrentUrl()));
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public List<WebElement> findElements(By by) {
        MailLogger.debug(String.format("Finding elements: %s; page URL: %s", by, driver.getCurrentUrl()));
        return driver.findElements(by);
    }

    public WebElement findElement(By by) {
        MailLogger.debug(String.format("Finding an element: %s; page URL: %s", by, driver.getCurrentUrl()));
        return driver.findElement(by);
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    public void close() {
        driver.close();
    }

    public void quit() {
        MailLogger.debug("Closing the browser");
        driver.quit();
    }

    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    public Navigation navigate() {
        return driver.navigate();
    }

    public Options manage() {
        return driver.manage();
    }

    public Object executeScript(String s, Object... objects) {
        MailLogger.debug(String.format("Execute script: %s", s));
        return ((JavascriptExecutor) driver).executeScript(s, objects);
    }

    public Object executeAsyncScript(String s, Object... objects) {
        MailLogger.debug(String.format("Execute script: %s", s));
        return ((JavascriptExecutor) driver).executeAsyncScript(s, objects);
    }
}
