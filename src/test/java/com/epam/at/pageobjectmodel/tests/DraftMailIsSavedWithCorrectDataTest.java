package com.epam.at.pageobjectmodel.tests;

import com.epam.at.pageobjectmodel.objects.Mail;
import com.epam.at.pageobjectmodel.objects.User;
import org.testng.annotations.Test;
import com.epam.at.pageobjectmodel.pages.SignInPage;
import com.epam.at.pageobjectmodel.dataproviders.AccountCredentials;
import org.testng.asserts.SoftAssert;

public class DraftMailIsSavedWithCorrectDataTest extends InitialTest {

    private Mail mail = new Mail();

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void checkDraftMailDataTest(String login, String password) {

        User user = new User(login, password);

        String lastDraftMailText = new SignInPage(driver)
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

        String[] mailData = lastDraftMailText.split(" ");
        Mail draftMail = new Mail(mailData[0].split("\\R")[0], mailData[0].split("\\R")[1], mailData[1]);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(draftMail.getAddressTo(), mail.getAddressTo(), "Draft mail address is incorrect: ");
        softAssert.assertEquals(draftMail.getSubject(), mail.getSubject(), "Draft mail subject is incorrect: ");
        softAssert.assertEquals(draftMail.getBody(), mail.getBody(), "Draft mail body is incorrect: ");
        softAssert.assertAll();
    }
}
