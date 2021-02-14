package com.epam.at.pageobjectmodel.drivermanagers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class ChromeDriverCreator implements WebDriverCreator {

    public WebDriver createRemoteWebDriver(String webDriverHubUrl) throws MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions();
        return new RemoteWebDriver(new URL(webDriverHubUrl), chromeOptions);
    }
}
