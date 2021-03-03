package stepdefinitions;

import com.epam.at.pageobjectmodel.decorators.CustomDriverDecorator;
import com.epam.at.pageobjectmodel.decorators.MailData;
import com.epam.at.pageobjectmodel.drivermanagers.WebDriverSingleton;
import com.epam.at.pageobjectmodel.objects.Mail;
import com.epam.at.pageobjectmodel.pages.EmailPopupPage;
import com.epam.at.pageobjectmodel.pages.HomePage;
import com.epam.at.pageobjectmodel.pages.SignInPage;
import com.epam.at.pageobjectmodel.tools.ParseMailDataFromWebElement;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class MailStepDefinitions {

    private MailData mail = new Mail();

    @Given("^I started to create a new mail$")
    public void iStartedToCreateANewMail() {
        new HomePage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()))
                .startToCreateNewMail();
    }

    @And("^I filled in mail fields with default data$")
    public void iFilledInMailFieldsWithDefaultData() {
        mail = new Mail();

        new EmailPopupPage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()))
                .fillInMailAddress(mail.getMailAddress())
                .fillInMailSubject(mail.getMailSubject())
                .fillInMailBody(mail.getMailBody());
    }

    @When("^I saved a mail as draft$")
    public void iSavedMailAsDraft() {
        new EmailPopupPage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()))
                .saveMailAsDraft()
                .closeMailPopup();
    }

    @And("^I opened \"([^\"]*)\" folder$")
    public void iOpenedFolder(String folderName) {

        HomePage homePage = new HomePage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()));

        if (folderName.toLowerCase().contains("draft")) {
            homePage.openDraftsFolder();
        } else if (folderName.toLowerCase().contains("sent")) {
            homePage.openSentFolder();
        } else if (folderName.toLowerCase().contains("trash")) {
            homePage.openTrashFolder();
        }
    }

    @Then("^Mail is in folder$")
    public void mailIsInFolder() {
        String mailData = new HomePage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()))
                .getMailFromListOnPage(mail.getMailSubject())
                .getText();

        Assert.assertTrue(mailData != null, "Subject of mail does not contains test text, " +
                "probably mail was not saved");
    }

    @Then("^Saved draft mail contains correct data$")
    public void savedDraftMailContainsCorrectData() {
        String mailData = new HomePage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()))
                .getMailFromListOnPage(mail.getMailSubject())
                .getText();

        Mail draftMail = new Mail.MailBuilder(ParseMailDataFromWebElement.getMailAddressTo(mailData))
                .withSubject(ParseMailDataFromWebElement.getMailSubject(mailData))
                .withBody(ParseMailDataFromWebElement.getMailBody(mailData))
                .build();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(draftMail.getAddressTo(), mail.getMailAddress(), "Draft mail address is incorrect: ");
        softAssert.assertEquals(draftMail.getSubject(), mail.getMailSubject(), "Draft mail subject is incorrect: ");
        softAssert.assertEquals(draftMail.getBody(), mail.getMailBody(), "Draft mail body is incorrect: ");
        softAssert.assertAll();
    }

    @When("I delete draft mail using (?:[dD]rag.*[dD]rop)$")
    public void iDeleteDraftMailUsingDragNDrop() {
        System.out.println("START TO DRAG N DROP");
        new HomePage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()))
                .deleteDraftMailUsingDragNDrop(mail.getMailSubject());
    }

    @When("I delete draft mail using (?:[cC]ontext.*[mM]enu)$")
    public void iDeleteDraftMailUsingContextMenu() {
        System.out.println("START TO DELETE USING CONTEXT MENU");
        new HomePage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()))
                .deleteMailUsingContextMenu(mail.getMailSubject());
    }

    @When("^I send draft mail$")
    public void iSendDraftMail() {
        new HomePage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()))
                .openMailFromListOnPage(mail.getMailSubject())
                .sendDraftMail()
                .closeSentMailPopup();
    }

    @Then("^Mail was deleted from folder$")
    public void mailIsDeletedFromFolder() {
        Assert.assertTrue(new HomePage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()))
                .getMailFromListOnPage(mail.getMailSubject()) == null, "The draft mail was not send, it was found in Draft folder");
    }
}
