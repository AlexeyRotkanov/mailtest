package com.epam.at.pageobjectmodel.drivermanagers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class FirefoxDriverCreator implements WebDriverCreator {

    public WebDriver createRemoteWebDriver(String webDriverHubUrl) throws MalformedURLException {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        return new RemoteWebDriver(new URL(webDriverHubUrl), firefoxOptions);
    }
}
