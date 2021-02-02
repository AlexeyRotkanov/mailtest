package com.epam.at.pageobjectmodel.test;

import org.testng.annotations.Test;
import com.epam.at.pageobjectmodel.page.SignInPage;
import com.epam.at.pageobjectmodel.dataprovider.AccountCredentials;
import org.testng.asserts.SoftAssert;

public class DraftMailIsSavedWithCorrectDataTest extends InitialTest {

    private String mailSubject = System.currentTimeMillis() + "_test_subject";
    private String mailBody = System.currentTimeMillis() + "_test_body";
    private String mailAddress = "alex.r.epm@gmail.com";

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void checkDraftMailDataTest(String login, String password) {

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
                .getMailFromListOnPage(mailSubject)
                .getText();

        String[] mailData = lastDraftMailText.split(" ");
        String draftMailAddress = mailData[0].split("\\R")[0];
        String draftMailSubject = mailData[0].split("\\R")[1];
        String draftMailBody = mailData[1];

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(draftMailAddress, mailAddress, "Draft mail address is incorrect: ");
        softAssert.assertEquals(draftMailSubject, mailSubject, "Draft mail subject is incorrect: ");
        softAssert.assertEquals(draftMailBody, mailBody, "Draft mail body is incorrect: ");
        softAssert.assertAll();
    }
}
