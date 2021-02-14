package com.epam.at.pageobjectmodel.tests;

import com.epam.at.pageobjectmodel.decorators.CustomDriverDecorator;
import com.epam.at.pageobjectmodel.drivermanagers.WebDriverSingleton;
import com.epam.at.pageobjectmodel.pages.HomePage;
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

    @AfterSuite(alwaysRun = true)
    protected void tearDown() {
        if (WebDriverSingleton.getWebDriverInstance() != null) {
            new CustomDriverDecorator(WebDriverSingleton
                    .getWebDriverInstance()).quit();
        }
    }
}
