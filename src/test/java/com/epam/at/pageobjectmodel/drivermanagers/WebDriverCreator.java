package com.epam.at.pageobjectmodel.drivermanagers;

import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

public interface WebDriverCreator {
    WebDriver createRemoteWebDriver(String webDriverHubUrl) throws MalformedURLException;
}
