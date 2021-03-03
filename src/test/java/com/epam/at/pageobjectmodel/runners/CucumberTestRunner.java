package com.epam.at.pageobjectmodel.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;


@CucumberOptions(
        features = "src/test/resources/features",
        glue="src/test/java/stepdefinitions",
        plugin = {
                "pretty",
                "html:target/cucumber-reports"
        }
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
}
