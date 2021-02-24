package com.epam.at.pageobjectmodel.tests;

import com.epam.at.pageobjectmodel.decorators.CustomDriverDecorator;
import com.epam.at.pageobjectmodel.drivermanagers.WebDriverSingleton;
import com.epam.at.pageobjectmodel.objects.Mail;
import com.epam.at.pageobjectmodel.objects.User;
import com.epam.at.pageobjectmodel.tools.ParseMailDataFromWebElement;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.epam.at.pageobjectmodel.pages.SignInPage;
import com.epam.at.pageobjectmodel.dataproviders.AccountCredentials;
import org.testng.asserts.SoftAssert;

public class DraftMailIsSavedWithCorrectDataTest extends InitialTest {

    private Mail mail = new Mail();

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void checkDraftMailDataTest(String login, String password) {

        User user = new User(login, password);

        String lastDraftMailData = new SignInPage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()))
                .openPage()
                .signInToMailbox(user.getUsername(), user.getPassword())
                .startToCreateNewMail()
                .fillInMailAddress(mail.getAddressTo())
                .fillInMailSubject(mail.getSubject())
                .fillInMailBody(mail.getBody())
                .saveMailAsDraft()
                .closeMailPopup()
                .openDraftsFolder()
                .getMailFromListOnPage(mail.getSubject())
                .getText();

        Mail draftMail = new Mail.MailBuilder(ParseMailDataFromWebElement.getMailAddressTo(lastDraftMailData))
                .withSubject(ParseMailDataFromWebElement.getMailSubject(lastDraftMailData))
                .withBody(ParseMailDataFromWebElement.getMailBody(lastDraftMailData))
                .build();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(draftMail.getAddressTo(), mail.getAddressTo(), "Draft mail address is incorrect: ");
        softAssert.assertEquals(draftMail.getSubject(), mail.getSubject(), "Draft mail subject is incorrect: ");
        softAssert.assertEquals(draftMail.getBody(), mail.getBody(), "Draft mail body is incorrect: ");
        softAssert.assertAll();
    }
}
