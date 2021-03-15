package com.epam.at.pageobjectmodel.runners;

import com.epam.at.pageobjectmodel.drivermanagers.WebDriverSingleton;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        plugin = {
                "pretty",
                "html:target/cucumber-reports"
        }
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {

    @AfterClass(alwaysRun = true)
    public void tearDown() {
            if (WebDriverSingleton.getWebDriverInstance() != null) {
                    WebDriverSingleton.getWebDriverInstance().quit();
            }
    }
}
