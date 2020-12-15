package Test;

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

import java.util.List;

public class MailTest {

    private WebDriver driver;
    private final int WAIT_TIMEOUT_SECONDS = 10;

    // Credentials
    private static final String HOMEPAGE_URL = "https://mail.ru/";
    private static final String LOGIN = "distest";
    private static final String PASSWORD = "P@ssword!23";

    // Data for creating a draft mail
    private String mailAddress = "alex.r.epm@gmail.com";
    private String mailSubject = System.currentTimeMillis() + " test subject";
    private String mailBody = System.currentTimeMillis() + " test body";

    // Sign in fields
    private By loginInputFieldLocator =
            new By.ByName("login");
    private By passwordButtonLocator =
            new By.ByXPath("//input[@name='login']/ancestor::form/button[not(contains(@class, 'second'))]");
    private By passwordInputFieldLocator =
            new By.ByName("password");
    private By enterButtonLocator =
            new By.ByXPath("//input[@name='login']/ancestor::form/button[contains(@class, 'second')]");

    // Top menu
    private By menuButtonLocator = new By.ByXPath("//i[contains(@id, 'user-email')]");
    private By logoutLinkLocator = new By.ByXPath("//*[contains(@id, 'logoutLink')]");

    // Buttons for creating, saving, sending a mail
    private By createNewLetterButtonLocator =
            new By.ByXPath("//a[contains(@title, 'Написать письмо')]");
    private By saveDraftButtonLocator =
            new By.ByXPath("//span[contains(@data-title-shortcut, 'Ctrl+S')]");
    private By sendDraftButtonLocator =
            new By.ByXPath("//span[contains(@data-title-shortcut, 'Ctrl+Enter')]");

    // Button for closing 'A mail' popup
    private By closeMailButtonLocator =
            new By.ByXPath("//button[contains(@title, 'Закрыть')]");

    // Button for closing 'Mail sent' popup
    private By closeSendPopupLocator =
            new By.ByXPath("//*[contains(@class, 'button2_close')]");

    // Mail fields - address, subject, body
    private By mailAddressFieldLocator =
            new By.ByXPath("//div[contains(@class, 'contactsContainer')]//input");
    private By mailSubjectFieldLocator =
            new By.ByName("Subject");
    private By mailBodyFieldLocator =
            new By.ByXPath("//*[@role='textbox']/div[1]/br[1]");

    // Folders
    private By draftsFolderLocator =
            new By.ByXPath("//*[contains(@class, 'drafts')]/following::div[1]");
    private By sendFolderLocator =
            new By.ByXPath("//*[contains(@class, 'reply')]/following::div[1]");

    // List of letters
    private By draftMailEntryLocator =
            new By.ByXPath("//a[contains(@class, 'letter-list-item')]");

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @Test
    public void loginToMailBoxTest() throws InterruptedException {

        driver.get(HOMEPAGE_URL);

        loginToMailbox(LOGIN, PASSWORD);

        boolean isMenuLinkContainsLogin = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
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
        closeMailPopup();

        openDraftsFolder();

        Assert.assertTrue(getLastMailOnPage().getText().contains(mailSubject), "Subject of mail does not contains test text, " +
                "probably mail was not saved");
    }

    @Test(dependsOnMethods = "saveMailAsDraftTest")
    public void checkDraftMailDataTest() {
        String draftMailData = getLastMailOnPage().getText();

        boolean isDraftMailDataCorrect = draftMailData.contains(mailSubject) && draftMailData.contains(mailBody)
                && draftMailData.contains(mailAddress);

        Assert.assertTrue(isDraftMailDataCorrect, "Draft mail data do not match data entered during creation");
    }

    @Test(dependsOnMethods = "checkDraftMailDataTest")
    public void sendDraftMailTest() throws InterruptedException {
        getLastMailOnPage().click();

        sendMail();
        closeSentPopup();
        openDraftsFolder();
        ;

        // Sleep added because list of mails is updated slowly and previous state of Draft and Send folders may be captured
        // Don't know how it can be processed in proper way
        Thread.sleep(2000);

        boolean isDraftMailWasNotSent = getLastMailOnPage().getText().contains(mailSubject);
        Assert.assertFalse(isDraftMailWasNotSent, "The draft mail was not send, it was found in Draft folder");
    }

    @Test(dependsOnMethods = "sendDraftMailTest")
    public void sentMailIsInSendFolderTest() throws InterruptedException {
        openSendFolder();

        // Add because Send folder is opened quite slowly and draft list can be captured
        Thread.sleep(2000);

//        Just for check current mail data
//        String lastMailData = getLastMailOnPage().getText();
//        System.out.println("Last mail in send folder: " + getLastMailOnPage().getText());
//        System.out.println("Current subject: " + mailSubject);
//        System.out.println("Contains flag: " + lastMailData.contains(mailSubject));

        Assert.assertTrue(getLastMailOnPage().getText().contains(mailSubject), "Subject of mail does not contains test text, " +
                "probably mail was not sent");
    }

    @Test(dependsOnMethods = "sentMailIsInSendFolderTest")
    public void logoutTest() {
        logout();
        boolean isUserLoggedOut = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.urlContains("logout"));
        Assert.assertTrue(isUserLoggedOut, "User is not logged out");
    }

    private void logout() {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(logoutLinkLocator)).click();
    }

    private void closeSentPopup() {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(closeSendPopupLocator)).click();
    }

    private void sendMail() {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(sendDraftButtonLocator)).click();
    }

    public WebElement getLastMailOnPage() {
        return getAllMailsOnPage().get(0);
    }

    private void closeMailPopup() {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(closeMailButtonLocator)).click();
    }

    public void loginToMailbox(String login, String password) {
        fillInLogin(login);
        fillInPassword(password);

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(enterButtonLocator)).click();
    }

    public void fillInPassword(String password) {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(passwordButtonLocator)).click();

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOfElementLocated(passwordInputFieldLocator)).sendKeys(password);
    }

    public void fillInLogin(String login) {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(loginInputFieldLocator)).sendKeys(login);
    }

    public void openDraftsFolder() {
        driver.findElement(draftsFolderLocator).click();
    }

    public void openSendFolder() {
        driver.findElement(sendFolderLocator).click();
    }

    public void saveMailAsDraft() {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(saveDraftButtonLocator)).click();
    }

    public void fillInMailBody(String mailBody) {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(mailBodyFieldLocator)).sendKeys(mailBody);
    }

    public void fillInMailSubject(String mailSubject) {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(mailSubjectFieldLocator)).sendKeys(mailSubject);
    }

    public void fillInMailAddress(String mailAddress) {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(mailAddressFieldLocator)).sendKeys(mailAddress);
    }

    public void startToCreateNewMail() {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(createNewLetterButtonLocator)).click();
    }

    public List<WebElement> getAllMailsOnPage() {
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(draftMailEntryLocator));
    }
}
