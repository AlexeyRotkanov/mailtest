package com.epam.at.pageobjectmodel.tests;

import com.epam.at.pageobjectmodel.objects.Mail;
import com.epam.at.pageobjectmodel.objects.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.epam.at.pageobjectmodel.pages.SignInPage;
import com.epam.at.pageobjectmodel.dataproviders.AccountCredentials;

public class SentMailIsSavedInSentFolderTest extends InitialTest {

    private Mail mail = new Mail();

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void sentMailIsInSentFolderTest(String login, String password) {

        User user = new User(login, password);

        String lastSentMailText = new SignInPage(driver)
                .openPage()
                .signInToMailbox(user.getUsername(), user.getPassword())
                .startToCreateNewMail()
                .fillInMailAddress(mail.getAddressTo())
                .fillInMailSubject(mail.getSubject())
                .fillInMailBody(mail.getBody())
                .saveMailAsDraft()
                .closeMailPopup()
                .openDraftsFolder()
                .openMailFromListOnPage(mail.getSubject())
                .sendDraftMail()
                .closeSentMailPopup()
                .openSentFolder()
                .getMailFromListOnPage(mail.getSubject())
                .getText();

        Assert.assertTrue(lastSentMailText.contains(mail.getSubject()), "Subject of mail does not contains test text, " +
                "probably mail was not sent");
    }
}
