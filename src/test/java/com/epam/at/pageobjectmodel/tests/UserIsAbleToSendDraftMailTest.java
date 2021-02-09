package com.epam.at.pageobjectmodel.tests;

import com.epam.at.pageobjectmodel.objects.Mail;
import com.epam.at.pageobjectmodel.objects.User;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.epam.at.pageobjectmodel.pages.SignInPage;
import com.epam.at.pageobjectmodel.dataproviders.AccountCredentials;

public class UserIsAbleToSendDraftMailTest extends InitialTest {

    private Mail mail = new Mail();

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void sendDraftMailTest(String login, String password) {

        User user = new User(login, password);

        WebElement lastDraftMail = new SignInPage(driver)
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
                .openDraftsFolder()
                .getMailFromListOnPage(mail.getSubject());

        Assert.assertTrue(lastDraftMail == null, "The draft mail was not send, it was found in Draft folder");
    }
}
