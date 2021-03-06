package com.epam.at.pageobjectmodel.pages;

import com.epam.at.pageobjectmodel.conditions.CustomConditions;

import com.epam.at.pageobjectmodel.reporting.MailLogger;
import com.epam.at.pageobjectmodel.tools.Delay;
import com.epam.at.pageobjectmodel.tools.HighlightElement;
import com.epam.at.pageobjectmodel.tools.MarkMail;
import com.epam.at.pageobjectmodel.tools.SelectMail;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage extends AbstractPage {

    @FindBy(id = "PH_user-email")
    private WebElement menuButton;

    @FindBy(id = "PH_logoutLink")
    private WebElement logoutLink;

    @FindBy(xpath = "//*[contains(@class, 'ph-item') and @tabindex='0']")
    private WebElement logoutLinkInUserMenu;

    @FindBy(xpath = "//*[contains(@class, 'ph-project__account')]")
    private WebElement loggedUserMenu;

    @FindBy(xpath = "//a[@href = '/compose/']")
    private WebElement createNewLetterButton;

    @FindBy(xpath = "//*[contains(@class, 'drafts')]/following::div[1]")
    private WebElement draftsFolder;

    @FindBy(xpath = "//*[contains(@class, 'reply')]/following::div[1]")
    private WebElement sendFolder;

    @FindBy(xpath = "//a[@href='/trash/']")
    private WebElement trashFolder;

    @FindBy(xpath = "//span[text()='Удалить']")
    private WebElement contextMenuDelete;

    @FindBy(xpath = "//a[contains(@class, 'letter-list-item')]")
    private List<WebElement> draftMailEntries;

    @FindBy(xpath = "//a[contains(@class, 'letter-list-item')]//div[@class='checkbox']")
    private List<WebElement> mailEntriesCheckboxes;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected HomePage openPage() {
        throw new RuntimeException("HomePage cannot be opened directly. " +
                "Please open SginInPage and pass the authorization process");
    }

    public EmailPopupPage startToCreateNewMail() {
        MailLogger.debug("Starting to create new mail");
        HighlightElement.highlightElement(driver, new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(createNewLetterButton)));
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(createNewLetterButton)).click();
        return new EmailPopupPage(driver);
    }

    public EmailPopupPage startToCreateNewMailUsingHotKeys(WebDriver driver) {
        MailLogger.debug("Starting to create new mail using hotkeys");
        HighlightElement.highlightElement(driver, new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(createNewLetterButton)));
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(createNewLetterButton));
        new Actions(driver).sendKeys("n").build().perform();
        return new EmailPopupPage(driver);
    }

    public HomePage openDraftsFolder() {
        MailLogger.debug("Opening 'Draft' folder");
        HighlightElement.highlightElement(driver, draftsFolder);
        draftsFolder.click();

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(CustomConditions.jQueryAjaxCompleted());

        return this;
    }

    public HomePage openSentFolder() {
        MailLogger.debug("Opening 'Sent' folder");
        HighlightElement.highlightElement(driver, sendFolder);
        sendFolder.click();

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(CustomConditions.jQueryAjaxCompleted());

        return this;
    }

    public HomePage openTrashFolder() {
        MailLogger.debug("Opening 'Trash' folder");
        HighlightElement.highlightElement(driver, new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(trashFolder)));
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(trashFolder)).click();
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(CustomConditions.jQueryAjaxCompleted());

        return this;
    }

    public WebElement getMailFromListOnPage(String mailSubject) {
        MailLogger.info("Getting the mail with subject [" + mailSubject + "] from the list");
        return SelectMail.selectMailBySubject(mailSubject, draftMailEntries);
    }

    public EmailPopupPage openMailFromListOnPage(String mailSubject) {
        MailLogger.info("Opening the mail with subject [" + mailSubject + "] from the list");
        HighlightElement.highlightElement(driver, SelectMail.selectMailBySubject(mailSubject, draftMailEntries));
        SelectMail.selectMailBySubject(mailSubject, draftMailEntries).click();
        return new EmailPopupPage(driver);
    }

    public HomePage deleteDraftMailUsingDragNDrop(String mailSubject) {
        MailLogger.info("Deleting the mail with subject [" + mailSubject + "] using Drag And Drop");

        WebElement draftMail = markMailBySubjectInList(mailSubject, draftMailEntries);
        HighlightElement.highlightElement(driver, draftMail);
        HighlightElement.highlightElement(driver, trashFolder);

        new Actions(driver).dragAndDrop(draftMail, trashFolder).build().perform();

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(CustomConditions.jQueryAjaxCompleted());

        return this;
    }

    public HomePage deleteMailUsingContextMenu(String mailSubject) {
        MailLogger.info("Deleting the mail with subject [" + mailSubject + "] using context menu");

        WebElement mail = SelectMail.selectMailBySubject(mailSubject, draftMailEntries);
        HighlightElement.highlightElement(driver, mail);
        new Actions(driver).contextClick(mail).build().perform();

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("$('span:contains(\"Удалить\")').click()");

        return this;
    }

    public WebElement getMailOnPageUsingJs(String mailSubject) {
        Delay.makeDelay(1000);
        MailLogger.info("Getting the mail with subject [" + mailSubject + "] from the list using JS");
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        List<WebElement> listOfMails =
                (List<WebElement>) jsExecutor
                        .executeScript("var elements = document.querySelectorAll('a.js-letter-list-item'); return elements;");

        return SelectMail.selectMailBySubject(mailSubject, listOfMails);
    }

    public WebElement markMailBySubjectInList(String mailSubject, List<WebElement> listOfMails) {
        MailLogger.info("Marking the mail with subject [" + mailSubject + "] in the list");
        return MarkMail.markMailBySubject(mailSubject, listOfMails, mailEntriesCheckboxes,
                driver, WAIT_TIMEOUT_SECONDS);
    }

    public SignInPage logout() {
        HighlightElement.highlightElement(driver, logoutLink);
        logoutLink.click();
        return new SignInPage(driver);
    }

    public ReloginPage newLogout() {
        MailLogger.debug("Logging out");
        HighlightElement.highlightElement(driver, new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(loggedUserMenu)));
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(loggedUserMenu)).click();
        HighlightElement.highlightElement(driver, new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(logoutLinkInUserMenu)));
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(logoutLinkInUserMenu)).click();
        Delay.makeDelay(1000);
        return new ReloginPage(driver);
    }
}