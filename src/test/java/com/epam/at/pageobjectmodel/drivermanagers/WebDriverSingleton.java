package com.epam.at.pageobjectmodel.drivermanagers;

import com.epam.at.pageobjectmodel.tools.GetProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.net.MalformedURLException;


public class WebDriverSingleton {

    private static WebDriver driver;

    private WebDriverSingleton() {

    }

    public static WebDriver getWebDriverInstance() {

        if (driver == null) {

            try {

                String browserType = GetProperties.getPropertyValueByName("browserType");
                String webDriverHubUrl = GetProperties.getPropertyValueByName("webDriverHubUrl");

                if (browserType.equals("chrome")) {
                    driver = new ChromeDriverCreator().createRemoteWebDriver(webDriverHubUrl);
                    driver.manage().window().maximize();
                } else if (browserType.equals("firefox")) {
                    driver = new FirefoxDriverCreator().createRemoteWebDriver(webDriverHubUrl);
                    driver.manage().window().maximize();
                }

            } catch (MalformedURLException e) {
                System.out.println("Malformed URL has occurred or could not be parsed. " +
                        "Current webDriverHubUrl in config.properties = " +
                        GetProperties.getPropertyValueByName("webDriverHubUrl"));
                e.printStackTrace();
            } catch (WebDriverException e) {
                System.out.println("Probably incorrect webDriverHubUrl is configured. " +
                        "Current webDriverHubUrl in config.properties = " +
                        GetProperties.getPropertyValueByName("webDriverHubUrl"));
                e.printStackTrace();
            }
        }
        return driver;
    }
}
