package com.epam.at.pageobjectmodel.drivermanagers;

import com.epam.at.pageobjectmodel.reporting.MailLogger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class ChromeDriverCreator implements WebDriverCreator {

    public WebDriver createRemoteWebDriver(String webDriverHubUrl) throws MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions();
        MailLogger.debug("Chrome remote webdriver created");
        return new RemoteWebDriver(new URL(webDriverHubUrl), chromeOptions);
    }
}
