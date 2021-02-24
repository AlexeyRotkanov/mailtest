package com.epam.at.pageobjectmodel.tests;

import com.epam.at.pageobjectmodel.decorators.CustomDriverDecorator;
import com.epam.at.pageobjectmodel.decorators.MailData;
import com.epam.at.pageobjectmodel.decorators.MailDataWithCopy;
import com.epam.at.pageobjectmodel.drivermanagers.WebDriverSingleton;
import com.epam.at.pageobjectmodel.objects.Mail;
import com.epam.at.pageobjectmodel.objects.User;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.epam.at.pageobjectmodel.pages.SignInPage;
import com.epam.at.pageobjectmodel.dataproviders.AccountCredentials;

public class UserIsAbleToSaveMailAsDraftTest extends InitialTest {

    private MailData mail = new Mail();

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void saveMailAsDraftTest(String login, String password) {

        User user = new User(login, password);
        mail = new MailDataWithCopy(mail, "distest@mail.ru");
        mail.writeMail();

        WebElement lastMail = new SignInPage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()))
                .openPage()
                .signInToMailbox(user.getUsername(), user.getPassword())
                .startToCreateNewMail()
                .fillInMailAddress(mail.getMailAddress())
                .fillInMailSubject(mail.getMailSubject())
                .fillInMailBody(mail.getMailBody())
                .saveMailAsDraft()
                .closeMailPopup()
                .openDraftsFolder()
                .getMailFromListOnPage(mail.getMailSubject());

        Assert.assertTrue(lastMail.getText().contains(mail.getMailSubject()), "Subject of mail does not contains test text, " +
                "probably mail was not saved");
    }
}
