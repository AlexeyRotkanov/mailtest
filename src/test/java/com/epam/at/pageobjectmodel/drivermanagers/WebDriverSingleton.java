package com.epam.at.pageobjectmodel.drivermanagers;

import com.epam.at.pageobjectmodel.reporting.MailLogger;
import com.epam.at.pageobjectmodel.tools.GetProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.net.MalformedURLException;


public class WebDriverSingleton {

    private static WebDriver driver;

    private WebDriverSingleton() {

    }

    public static WebDriver getWebDriverInstance() {

        WebDriverCreator driverCreator = null;

        if (driver == null) {

            try {

                String browserType = GetProperties.getPropertyValueByName("browserType");
                String webDriverHubUrl = GetProperties.getPropertyValueByName("webDriverHubUrl");

                if (browserType.equals("chrome")) {
                    driverCreator = new ChromeDriverCreator();
                } else if (browserType.equals("firefox")) {
                    driverCreator = new FirefoxDriverCreator();
                }

                if (driverCreator != null) {
                    driver = driverCreator.createRemoteWebDriver(webDriverHubUrl);
                    driver.manage().window().maximize();
                }

            } catch (MalformedURLException e) {
                MailLogger.fatal("Malformed URL has occurred or could not be parsed. " +
                        "Current webDriverHubUrl in config.properties = " +
                        GetProperties.getPropertyValueByName("webDriverHubUrl"), e);
            } catch (WebDriverException e) {
                MailLogger.fatal("Probably incorrect webDriverHubUrl is configured. " +
                        "Current webDriverHubUrl in config.properties = " +
                        GetProperties.getPropertyValueByName("webDriverHubUrl"), e);
            }
        }
        return driver;
    }
}
