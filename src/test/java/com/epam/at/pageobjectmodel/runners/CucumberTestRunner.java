package com.epam.at.pageobjectmodel.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        plugin = {
                "pretty",
                "html:target/cucumber-reports"
        }
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
}
