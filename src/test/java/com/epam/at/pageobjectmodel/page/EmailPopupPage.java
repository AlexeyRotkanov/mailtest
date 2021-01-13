package com.epam.at.pageobjectmodel.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmailPopupPage extends HomePage {

    @FindBy(xpath = "//span[contains(@data-title-shortcut, 'Ctrl+S')]")
    private WebElement saveDraftButton;

    @FindBy(xpath = "//span[contains(@data-title-shortcut, 'Ctrl+Enter')]")
    private WebElement sendDraftButton;

    @FindBy(xpath = "//button[contains(@title, 'Закрыть')]")
    private WebElement closeMailButton;

    @FindBy(xpath = "//div[contains(@class, 'contactsContainer')]//input")
    private WebElement mailAddressField;

    @FindBy(name = "Subject")
    private WebElement mailSubjectField;

    @FindBy(xpath = "//*[@role='textbox']/div[1]/br[1]")
    private WebElement mailBodyField;

    protected EmailPopupPage(WebDriver driver) {
        super(driver);
    }

    public EmailPopupPage fillInMailAddress(String mailAddress) {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(mailAddressField)).sendKeys(mailAddress);
        return this;
    }

    public EmailPopupPage fillInMailSubject(String mailSubject) {
        mailSubjectField.sendKeys(mailSubject);
        return this;
    }

    public EmailPopupPage fillInMailBody(String mailBody) {
        mailBodyField.sendKeys(mailBody);
        return this;
    }

    public EmailPopupPage saveMailAsDraft() {
        saveDraftButton.click();
        return this;
    }

    public HomePage closeMailPopup() {
        closeMailButton.click();
        return new HomePage(driver);
    }

    public SentPopupPage sendDraftMail() {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(sendDraftButton)).click();
        return new SentPopupPage(driver);
    }
}
