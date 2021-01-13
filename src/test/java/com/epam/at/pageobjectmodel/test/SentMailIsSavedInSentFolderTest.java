package com.epam.at.pageobjectmodel.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.epam.at.pageobjectmodel.page.SignInPage;
import com.epam.at.pageobjectmodel.dataprovider.AccountCredentials;

public class SentMailIsSavedInSentFolderTest extends InitialTest {

    private String mailSubject = System.currentTimeMillis() + " test subject";
    private String mailBody = System.currentTimeMillis() + " test body";

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void sentMailIsInSentFolderTest(String login, String password, String mailAddress) {

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
                .openLastMailOnPage()
                .sendDraftMail()
                .closeSentMailPopup()
                .openSentFolder()
                .getLastMailOnPage()
                .getText();

        Assert.assertTrue(lastSentMailText.contains(mailSubject), "Subject of mail does not contains com.epam.at.pageobjectmodel.test text, " +
                "probably mail was not sent");
    }
}
