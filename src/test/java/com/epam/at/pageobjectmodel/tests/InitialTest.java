package com.epam.at.pageobjectmodel.tests;

import com.epam.at.pageobjectmodel.decorators.CustomDriverDecorator;
import com.epam.at.pageobjectmodel.drivermanagers.WebDriverSingleton;
import com.epam.at.pageobjectmodel.pages.HomePage;
import com.epam.at.pageobjectmodel.tools.Screenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class InitialTest {

    @BeforeSuite(alwaysRun = true)
    protected void setUp() {

        new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance())
                .manage()
                .window()
                .maximize();
    }

    @AfterClass(alwaysRun = true)
    public void logout() {
        if (!WebDriverSingleton.getWebDriverInstance().getCurrentUrl().contains("logout"))
            new HomePage(WebDriverSingleton.getWebDriverInstance()).logout();
    }

    @AfterMethod(alwaysRun = true)
    public void makeScreenshotIfTestFailed(ITestResult iTestResult) {
        if (!iTestResult.isSuccess())
            Screenshot.takeScreenshot(WebDriverSingleton.getWebDriverInstance());
    }

    @AfterSuite(alwaysRun = true)
    protected void tearDown() {
        if (WebDriverSingleton.getWebDriverInstance() != null) {
            new CustomDriverDecorator(WebDriverSingleton
                    .getWebDriverInstance()).quit();
        }
    }
}
