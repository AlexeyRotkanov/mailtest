package com.epam.at.pageobjectmodel.pages;

import com.epam.at.pageobjectmodel.reporting.MailLogger;
import com.epam.at.pageobjectmodel.tools.HighlightElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

    @FindBy(xpath = "//*[@role='textbox']")
    private WebElement mailBodyField;

    public EmailPopupPage(WebDriver driver) {
        super(driver);
    }

    public EmailPopupPage fillInMailAddress(String mailAddress) {
        MailLogger.info("Filling mail data: mail address = " + mailAddress);
        HighlightElement.highlightElement(driver, new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(mailAddressField)));
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(mailAddressField)).sendKeys(mailAddress);
        return this;
    }

    public EmailPopupPage fillInMailSubject(String mailSubject) {
        MailLogger.info("Filling mail data: mail address = " + mailSubject);
        HighlightElement.highlightElement(driver, mailSubjectField);
        mailSubjectField.sendKeys(mailSubject);
        return this;
    }

    public EmailPopupPage fillInMailBody(String mailBody) {
        MailLogger.info("Filling mail data: mail address = " + mailBody);
        HighlightElement.highlightElement(driver, mailBodyField);
        mailBodyField.sendKeys(mailBody);
        return this;
    }

    public EmailPopupPage saveMailAsDraft() {
        MailLogger.debug("Saving mail as draft");
        HighlightElement.highlightElement(driver, saveDraftButton);
        saveDraftButton.click();
        return this;
    }

    public EmailPopupPage saveMailAsDraftUsingHotKeys() {
        MailLogger.debug("Saving mail as draft using hotkeys");
        Actions action = new Actions(driver);
        action.keyDown(Keys.CONTROL).sendKeys("s").build().perform();
        action.keyUp(Keys.CONTROL).build().perform();
        return this;
    }

    public HomePage closeMailPopup() {
        MailLogger.debug("Closing draft mail pop-up");
        HighlightElement.highlightElement(driver, closeMailButton);
        closeMailButton.click();
        return new HomePage(driver);
    }

    public SentPopupPage sendDraftMail() {
        MailLogger.debug("Sending draft mail");
        HighlightElement.highlightElement(driver, new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(sendDraftButton)));
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(sendDraftButton)).click();
        return new SentPopupPage(driver);
    }
}
