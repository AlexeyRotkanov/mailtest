package com.epam.at.pageobjectmodel.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;

public class InitialTest {

    protected WebDriver driver;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browserType", "webdriverHubUrl"})
    protected void setUp(String browserType, String webdriverHubUrl) {

        try {
            if (browserType.equals("chrome")) {
                ChromeOptions chromeOptions = new ChromeOptions();
                driver = new RemoteWebDriver(new URL(webdriverHubUrl), chromeOptions);

            } else if (browserType.equals("firefox")) {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new RemoteWebDriver(new URL(webdriverHubUrl), firefoxOptions);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().window().maximize();
    }

    @AfterClass(alwaysRun = true)
    protected void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
