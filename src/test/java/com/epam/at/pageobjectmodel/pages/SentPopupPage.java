package com.epam.at.pageobjectmodel.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SentPopupPage extends HomePage {

    @FindBy(xpath = "//*[contains(@class, 'button2_close')]")
    private WebElement closeSendPopupButton;

    protected SentPopupPage(WebDriver driver) {
        super(driver);
    }

    public HomePage closeSentMailPopup() {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(closeSendPopupButton)).click();
        return new HomePage(driver);
    }
}
