package backup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import com.epam.at.pageobjectmodel.page.SignInPage;

public class MailTestWithPO {

    public WebDriver driver;

    // Credentials
    private static final String LOGIN = "distest";
    private static final String PASSWORD = "P@ssword!23";

    // Data for creating a draft mail
    private String mailAddress = "alex.r.epm@gmail.com";
    private String mailSubject = System.currentTimeMillis() + " com.epam.at.pageobjectmodel.test subject";
    private String mailBody = System.currentTimeMillis() + " com.epam.at.pageobjectmodel.test body";

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @Test
    public void loginToMailBoxTest() {

        new SignInPage(driver)
                .openPage()
                .signInToMailbox(LOGIN, PASSWORD);

        boolean isUserSignedIn = driver.getCurrentUrl().contains("inbox");


        Assert.assertTrue(isUserSignedIn, "User is not in inbox, check that user is signed in");
    }

    @Test
    public void saveMailAsDraftTest() {

        WebElement lastMail = new SignInPage(driver)
                .openPage()
                .signInToMailbox(LOGIN, PASSWORD)
                .startToCreateNewMail()
                .fillInMailAddress(mailAddress)
                .fillInMailSubject(mailSubject)
                .fillInMailBody(mailBody)
                .saveMailAsDraft()
                .closeMailPopup()
                .openDraftsFolder()
                .getLastMailOnPage();

        Assert.assertTrue(lastMail.getText().contains(mailSubject), "Subject of mail does not contains com.epam.at.pageobjectmodel.test text, " +
                "probably mail was not saved");
    }

    @Test
    public void checkDraftMailDataTest() {

        String lastDraftMailText = new SignInPage(driver)
                .openPage()
                .signInToMailbox(LOGIN, PASSWORD)
                .startToCreateNewMail()
                .fillInMailAddress(mailAddress)
                .fillInMailSubject(mailSubject)
                .fillInMailBody(mailBody)
                .saveMailAsDraft()
                .closeMailPopup()
                .openDraftsFolder()
                .getLastMailOnPage()
                .getText();

        boolean isDraftMailDataCorrect = lastDraftMailText.contains(mailSubject) && lastDraftMailText.contains(mailBody)
                && lastDraftMailText.contains(mailAddress);

        Assert.assertTrue(isDraftMailDataCorrect, "Draft mail data do not match data entered during creation");
    }

    @Test
    public void sendDraftMailTest() {

        String lastDraftMailText = new SignInPage(driver)
                .openPage()
                .signInToMailbox(LOGIN, PASSWORD)
                .startToCreateNewMail()
                .fillInMailAddress(mailAddress)
                .fillInMailSubject(mailSubject)
                .fillInMailBody(mailBody)
                .saveMailAsDraft()
                .closeMailPopup()
                .openDraftsFolder()
                .openLastMailOnPage()
                .sendDraftMail()
                .closeSentMailPopup()
                .openDraftsFolder()
                .getLastMailOnPage()
                .getText();

        boolean isDraftMailWasNotSent = lastDraftMailText.contains(mailSubject);
        Assert.assertFalse(isDraftMailWasNotSent, "The draft mail was not send, it was found in Draft folder");
    }

    @Test
    public void sentMailIsInSentFolderTest() {

        String lastSentMailText = new SignInPage(driver)
                .openPage()
                .signInToMailbox(LOGIN, PASSWORD)
                .startToCreateNewMail()
                .fillInMailAddress(mailAddress)
                .fillInMailSubject(mailSubject)
                .fillInMailBody(mailBody)
                .saveMailAsDraft()
                .closeMailPopup()
                .openDraftsFolder()
                .openLastMailOnPage()
                .sendDraftMail()
                .closeSentMailPopup()
                .openSentFolder()
                .getLastMailOnPage()
                .getText();

        Assert.assertTrue(lastSentMailText.contains(mailSubject), "Subject of mail does not contains com.epam.at.pageobjectmodel.test text, " +
                "probably mail was not sent");
    }

    @Test
    public void logoutTest() {

        new SignInPage(driver)
                .openPage()
                .signInToMailbox(LOGIN, PASSWORD)
                .logout();


        boolean isUserLoggedOut = driver.getCurrentUrl().contains("logout");
        Assert.assertTrue(isUserLoggedOut, "User is not logged out");
    }
}
