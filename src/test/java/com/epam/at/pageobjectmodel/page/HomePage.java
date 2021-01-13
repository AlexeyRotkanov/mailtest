package com.epam.at.pageobjectmodel.page;

import com.epam.at.pageobjectmodel.condition.CustomConditions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage extends AbstractPage {

    @FindBy(id = "PH_user-email")
    private WebElement menuButton;

    @FindBy(id = "PH_logoutLink")
    private WebElement logoutLink;

    @FindBy(xpath = "//a[@href = '/compose/']")
    private WebElement createNewLetterButton;

    @FindBy(xpath = "//*[contains(@class, 'drafts')]/following::div[1]")
    private WebElement draftsFolder;

    @FindBy(xpath = "//*[contains(@class, 'reply')]/following::div[1]")
    private WebElement sendFolder;

    @FindBy(xpath = "//a[contains(@class, 'letter-list-item')]")
    private List<WebElement> draftMailEntries;

    protected HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected HomePage openPage() {
        throw new RuntimeException("HomePage cannot be opened directly. " +
                "Please open SginInPage and pass the authorization process");
    }

    public EmailPopupPage startToCreateNewMail() {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(createNewLetterButton)).click();
        return new EmailPopupPage(driver);
    }

    public HomePage openDraftsFolder() {
        draftsFolder.click();

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(CustomConditions.jQueryAjaxCompleted());

        return this;
    }

    public HomePage openSentFolder() {
        sendFolder.click();

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(CustomConditions.jQueryAjaxCompleted());

        return this;
    }

    public WebElement getLastMailOnPage() {
        return draftMailEntries.get(0);
    }

    public EmailPopupPage openLastMailOnPage() {
        getLastMailOnPage().click();
        return new EmailPopupPage(driver);
    }

    public SignInPage logout() {
        logoutLink.click();
        return new SignInPage(driver);
    }
}
