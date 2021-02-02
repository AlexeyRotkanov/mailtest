package com.epam.at.pageobjectmodel.test;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.epam.at.pageobjectmodel.page.SignInPage;
import com.epam.at.pageobjectmodel.dataprovider.AccountCredentials;

public class UserIsAbleToSendDraftMailTest extends InitialTest {

    private String mailSubject = System.currentTimeMillis() + "_test_subject";
    private String mailBody = System.currentTimeMillis() + "_test_body";
    private String mailAddress = "alex.r.epm@gmail.com";

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void sendDraftMailTest(String login, String password) {

        WebElement lastDraftMail = new SignInPage(driver)
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
                .openDraftsFolder()
                .getMailFromListOnPage(mailSubject);

        Assert.assertTrue(lastDraftMail == null, "The draft mail was not send, it was found in Draft folder");
    }
}
