package com.epam.at.pageobjectmodel.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.epam.at.pageobjectmodel.page.SignInPage;
import com.epam.at.pageobjectmodel.dataprovider.AccountCredentials;

public class SentMailIsSavedInSentFolderTest extends InitialTest {

    private String mailSubject = System.currentTimeMillis() + "_test_subject";
    private String mailBody = System.currentTimeMillis() + "_test_body";
    private String mailAddress = "alex.r.epm@gmail.com";

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void sentMailIsInSentFolderTest(String login, String password) {

        String lastSentMailText = new SignInPage(driver)
                .openPage()
                .signInToMailbox(login, password)
                .startToCreateNewMail()
                .fillInMailAddress(mailAddress)
                .fillInMailSubject(mailSubject)
                .fillInMailBody(mailBody)
                .saveMailAsDraft()
                .closeMailPopup()
                .openDraftsFolder()
                .openMailFromListOnPage(mailSubject)
                .sendDraftMail()
                .closeSentMailPopup()
                .openSentFolder()
                .getMailFromListOnPage(mailSubject)
                .getText();

        Assert.assertTrue(lastSentMailText.contains(mailSubject), "Subject of mail does not contains test text, " +
                "probably mail was not sent");
    }
}
