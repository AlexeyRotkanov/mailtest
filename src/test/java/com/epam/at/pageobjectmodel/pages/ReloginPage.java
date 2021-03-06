package com.epam.at.pageobjectmodel.pages;

import org.openqa.selenium.WebDriver;

public class ReloginPage extends AbstractPage {

    protected ReloginPage(WebDriver driver) {
        super(driver);
    }

    protected AbstractPage openPage() {
        throw new RuntimeException("ReloginPage cannot be opened directly. " +
                "Please open HomePage and pass the logout process");
    }
}
