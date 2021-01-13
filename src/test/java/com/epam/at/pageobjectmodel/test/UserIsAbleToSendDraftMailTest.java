package com.epam.at.pageobjectmodel.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.epam.at.pageobjectmodel.page.SignInPage;
import com.epam.at.pageobjectmodel.dataprovider.AccountCredentials;

public class UserIsAbleToSendDraftMailTest extends InitialTest {

    private String mailSubject = System.currentTimeMillis() + " test subject";
    private String mailBody = System.currentTimeMillis() + " test body";

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void sendDraftMailTest(String login, String password, String mailAddress) {

        String lastDraftMailText = new SignInPage(driver)
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
                .openDraftsFolder()
                .getLastMailOnPage()
                .getText();

        boolean isDraftMailWasNotSent = lastDraftMailText.contains(mailSubject);
        Assert.assertFalse(isDraftMailWasNotSent, "The draft mail was not send, it was found in Draft folder");
    }
}
