package com.epam.at.pageobjectmodel.pages;

import com.epam.at.pageobjectmodel.conditions.CustomConditions;

import com.epam.at.pageobjectmodel.tools.Delay;
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
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(createNewLetterButton)).click();
        return new EmailPopupPage(driver);
    }

    public EmailPopupPage startToCreateNewMailUsingHotKeys(WebDriver driver) {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(createNewLetterButton));
        new Actions(driver).sendKeys("n").build().perform();
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

    public HomePage openTrashFolder() {

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(trashFolder)).click();
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(CustomConditions.jQueryAjaxCompleted());

        return this;
    }

    public WebElement getMailFromListOnPage(String mailSubject) {
        return SelectMail.selectMailBySubject(mailSubject, draftMailEntries);
    }

    public EmailPopupPage openMailFromListOnPage(String mailSubject) {
        SelectMail.selectMailBySubject(mailSubject, draftMailEntries).click();
        return new EmailPopupPage(driver);
    }

    public HomePage deleteDraftMailUsingDragNDrop(String mailSubject) {

        WebElement draftMail = markMailBySubjectInList(mailSubject, draftMailEntries);

        new Actions(driver).dragAndDrop(draftMail, trashFolder).build().perform();

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(CustomConditions.jQueryAjaxCompleted());

        return this;
    }

    public HomePage deleteMailUsingContextMenu(String mailSubject) {

        WebElement mail = SelectMail.selectMailBySubject(mailSubject, draftMailEntries);
        new Actions(driver).contextClick(mail).build().perform();

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("$('span:contains(\"Удалить\")').click()");

        return this;
    }

    public WebElement getMailOnPageUsingJs(String mailSubject) {
        Delay.makeDelay(1000);

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        List<WebElement> listOfMails =
                (List<WebElement>) jsExecutor
                        .executeScript("var elements = document.querySelectorAll('a.js-letter-list-item'); return elements;");

        return SelectMail.selectMailBySubject(mailSubject, listOfMails);
    }

    public WebElement markMailBySubjectInList(String mailSubject, List<WebElement> listOfMails) {

        return MarkMail.markMailBySubject(mailSubject, listOfMails, mailEntriesCheckboxes,
                driver, WAIT_TIMEOUT_SECONDS);
    }

    public SignInPage logout() {
        logoutLink.click();
        return new SignInPage(driver);
    }
}