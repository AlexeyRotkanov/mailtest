package Test;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class MailTest {

    private WebDriver driver;
    private final int WAIT_TIMEOUT_SECONDS = 10;

    private static final String HOMEPAGE_URL = "https://mail.ru/";
    private static final String LOGIN = "distest";
    private static final String PASSWORD = "Cdznjq25";
    private static String mailAddress = "alex.r.epm@gmail.com";
    private static String mailSubject = "test subject";
    private static String mailBody = "test body";

    private static By loginInputFieldLocator =
            new By.ByName("login");
    private static By passwordButtonLocator =
            new By.ByXPath("//input[@name='login']/ancestor::form/button[not(contains(@class, 'second'))]");
    private static By passwordInputFieldLocator =
            new By.ByName("password");
    private static By enterButtonLocator =
            new By.ByXPath("//input[@name='login']/ancestor::form/button[contains(@class, 'second')]");

    private static By menuButtonLocator = new By.ByXPath("//i[contains(@id, 'user-email')]");
    private static By logoutLinkLocator = new By.ByXPath("//*[contains(@id, 'logoutLink')]");

    private static By createNewLetterButtonLocator =
            new By.ByXPath("//div[@class='b-sticky']//*[@data-name='compose']");
    private static By saveDraftButtonLocator =
            new By.ByXPath("//div[@class='b-sticky js-not-sticky']//*[@data-name='saveDraft']");
    private static By sendDraftButtonLocator =
            new By.ByXPath("//div[@class='b-sticky js-not-sticky']//*[@data-name='send']");

    private static By saveStatusLocator =
            new By.ByXPath("//div[@class='b-sticky']//div[@data-mnemo='saveStatus']/span");

    private static By mailAddressFieldLocator =
            new By.ByXPath("//textarea[@data-original-name='To']");
    private static By mailSubjectFieldLocator =
            new By.ByName("Subject");
    private static By mailBodyFieldLocator =
            new By.ByXPath("//*[@id='tinymce']/br[1]");

    private static By draftsFolderLocator =
            new By.ByXPath("//*[contains(@class, 'draft')]/following::span[1]");
    private static By sendFolderLocator =
            new By.ByXPath("//*[contains(@class, 'folder_send')]/following::span[1]");

    private static By draftMailEntryLocator =
            new By.ByXPath("//*[@id='b-letters']//div[contains(@data-cache-key, '500001')]" +
                    "//div[contains(@class, 'b-datalist__body')]//div[contains(@class, 'item__info')]");


    @BeforeClass(alwaysRun = true)
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void loginToMailBoxTest() throws InterruptedException {

        driver.get(HOMEPAGE_URL);

        loginToMailbox(LOGIN, PASSWORD);

        Boolean isMenuLinkContainsLogin = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.textToBe(menuButtonLocator, LOGIN + "@mail.ru"));

        Assert.assertTrue(isMenuLinkContainsLogin, "Menu link is not found, check that user is logged in");
    }

    @Test(dependsOnMethods = "loginToMailBoxTest")
    public void saveMailAsDraftTest() throws InterruptedException {

        startToCreateNewMail();

        fillInMailAddress(mailAddress);

        fillInMailSubject(mailSubject);

        fillInMailBody(mailBody);

        saveMailAsDraft();

        openDraftsFolder();

        String draftMailData = getLastMailOnPage().getText();

        Assert.assertTrue(draftMailData.contains(mailSubject), "Subject of mail does not contains test text, " +
                "probably mail was not saved");
    }

    @Test(dependsOnMethods = "saveMailAsDraftTest")
    public void checkDraftMailData() {
        String draftMailData = getLastMailOnPage().getText();

        Boolean isDraftMailDataCorrect = draftMailData.contains(mailSubject) && draftMailData.contains(mailBody)
                && draftMailData.contains(mailAddress);

        Assert.assertTrue(isDraftMailDataCorrect, "Draft mail data do not match data entered during creation");
    }

    @Test(dependsOnMethods = "checkDraftMailData")
    public void sendDraftMail() {
        getLastMailOnPage().click();

        WebElement sendButton = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(sendDraftButtonLocator));
        sendButton.click();

        openDraftsFolder();
        Boolean isDraftMailWasNotSent = false;
        if (getAllMailsOnPage().size() != 0) {
            String lastMailData = getLastMailOnPage().getText();
            isDraftMailWasNotSent = lastMailData.contains(mailSubject);
        }

        Assert.assertFalse(isDraftMailWasNotSent, "The draft mail was not send");
    }

    public WebElement getLastMailOnPage() {
        return getAllMailsOnPage().get(0);
    }

    public void loginToMailbox(String login, String password) {
        fillInLogin(login);
        fillInPassword(password);

        WebElement enterButton = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(enterButtonLocator));
        enterButton.click();
    }

    public void fillInPassword(String password) {
        WebElement passwordButton = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(passwordButtonLocator));
        passwordButton.click();

        WebElement passwordField = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOfElementLocated(passwordInputFieldLocator));
        passwordField.sendKeys(password);
    }

    public void fillInLogin(String login) {
        WebElement loginField = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(loginInputFieldLocator));
        loginField.sendKeys(login);
    }

    public void openDraftsFolder() {
        WebElement draftsFolder = driver.findElement(draftsFolderLocator);
        draftsFolder.click(); // Sometimes StaleElementReferenceException: stale element reference: element is not attached to the page document
    }

    public void openSendFolder() {
        WebElement draftsFolder = driver.findElement(sendFolderLocator);
        draftsFolder.click();
    }

    public void saveMailAsDraft() {
        WebElement saveDraftButton = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(saveDraftButtonLocator)); // Often TimeoutException: Expected condition failed: waiting for element to be clickable: By.xpath
        saveDraftButton.click();

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(saveStatusLocator));
    }

    public void fillInMailBody(String mailBody) {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));

        WebElement mailBodyField = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(mailBodyFieldLocator));  // Often TimeoutException: Expected condition failed: waiting for presence of element located by: By.xpath
        mailBodyField.sendKeys(mailBody);

        driver.switchTo().defaultContent();
    }

    public void fillInMailSubject(String mailSubject) {
        WebElement mailSubjectField = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(mailSubjectFieldLocator));
        mailSubjectField.sendKeys(mailSubject);
    }

    public void fillInMailAddress(String mailAddress) {
        WebElement mailAddressField = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(mailAddressFieldLocator));
        mailAddressField.sendKeys(mailAddress);
    }

    public void startToCreateNewMail() {
        WebElement createNewMailButton = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(createNewLetterButtonLocator));
        createNewMailButton.click();
    }

    public List<WebElement> getAllMailsOnPage() {
        List<WebElement> allDraftMails = new ArrayList<WebElement>();

        allDraftMails = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(draftMailEntryLocator));

        return allDraftMails;
    }
}
