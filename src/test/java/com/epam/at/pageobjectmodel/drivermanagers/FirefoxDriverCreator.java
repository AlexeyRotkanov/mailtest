package com.epam.at.pageobjectmodel.drivermanagers;

import com.epam.at.pageobjectmodel.reporting.MailLogger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class FirefoxDriverCreator implements WebDriverCreator {

    public WebDriver createRemoteWebDriver(String webDriverHubUrl) throws MalformedURLException {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        MailLogger.debug("Firefox remote webdriver created");
        return new RemoteWebDriver(new URL(webDriverHubUrl), firefoxOptions);
    }
}
