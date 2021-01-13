package com.epam.at.pageobjectmodel.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.epam.at.pageobjectmodel.page.SignInPage;
import com.epam.at.pageobjectmodel.dataprovider.AccountCredentials;

public class DraftMailIsSavedWithCorrectDataTest extends InitialTest {

    private String mailSubject = System.currentTimeMillis() + " test subject";
    private String mailBody = System.currentTimeMillis() + " test body";

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void checkDraftMailDataTest(String login, String password, String mailAddress) {

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
                .getLastMailOnPage()
                .getText();

        System.out.println("Mail data is: " + lastDraftMailText);

        boolean isDraftMailDataCorrect = lastDraftMailText.contains(mailSubject) && lastDraftMailText.contains(mailBody)
                && lastDraftMailText.contains(mailAddress);

        Assert.assertTrue(isDraftMailDataCorrect, "Draft mail data do not match data entered during creation");
    }
}
