package com.epam.at.pageobjectmodel.page;
import com.epam.at.pageobjectmodel.condition.CustomConditions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
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

    @FindBy(xpath = "//*[contains(@class, 'actions:delete')]/following::div[1]")
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

    public EmailPopupPage startToCreateNewMailUsingHotKeys() {
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
        trashFolder.click();

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(CustomConditions.jQueryAjaxCompleted());

        return this;
    }

    public WebElement getMailFromListOnPage(String mailSubject) {
        return selectMailBySubjectFromList(mailSubject, draftMailEntries);
    }

    public EmailPopupPage openMailFromListOnPage(String mailSubject) {
        selectMailBySubjectFromList(mailSubject, draftMailEntries).click();
        return new EmailPopupPage(driver);
    }

    public HomePage deleteDraftMailUsingDragNDrop(String mailSubject) {

        WebElement draftMail = selectAndCheckMailBySubjectFromList(mailSubject, draftMailEntries);
        Point trashPoint = trashFolder.getLocation();
        System.out.println("Coordinates x: " + trashPoint.getX() + ", y: " + trashPoint.getY());

            new Actions(driver).moveToElement(draftMail).build().perform();
            new Actions(driver).clickAndHold().build().perform();
            new Actions(driver).moveByOffset(trashPoint.getX(), trashPoint.getY()).build().perform();
            new Actions(driver).release().build().perform();

//        new Actions(driver).dragAndDrop(draftMail, trashFolder).build().perform();

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(CustomConditions.jQueryAjaxCompleted());

        return this;
    }

    public HomePage deleteMailUsingContextMenu(String mailSubject) {

        WebElement mail = selectMailBySubjectFromList(mailSubject, draftMailEntries);
        new Actions(driver).contextClick(mail).build().perform();

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("$('span:contains(\"Удалить\")').click()");

        return this;
    }

    public WebElement getMailOnPageUsingJs(String mailSubject) {

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        List<WebElement> listOfMails =
                (List<WebElement>) jsExecutor
                        .executeScript("var elements = document.querySelectorAll('a.js-letter-list-item'); return elements;");

        return selectMailBySubjectFromList(mailSubject, listOfMails);
    }

    public WebElement selectMailBySubjectFromList(String mailSubject, List<WebElement> listOfMails) {
        WebElement mail = null;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (WebElement element: listOfMails) {
            if (element.getText().contains(mailSubject))
                mail = element;
        }

        return mail;
    }

    public WebElement selectAndCheckMailBySubjectFromList(String mailSubject, List<WebElement> listOfMails) {
        WebElement mail = null;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < listOfMails.size(); i++) {
            if (listOfMails.get(i).getText().contains(mailSubject)) {
                mail = listOfMails.get(i);
                new Actions(driver).moveToElement(listOfMails.get(i)).build().perform();
                new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                        .until(ExpectedConditions.elementToBeClickable(mailEntriesCheckboxes.get(i))).click();
                break;
            }
        }

        return mail;
    }

    public SignInPage logout() {
        logoutLink.click();
        return new SignInPage(driver);
    }
}