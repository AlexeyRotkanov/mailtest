package com.epam.at.pageobjectmodel.tests;

import com.epam.at.pageobjectmodel.dataproviders.AccountCredentials;
import com.epam.at.pageobjectmodel.decorators.CustomDriverDecorator;
import com.epam.at.pageobjectmodel.drivermanagers.WebDriverSingleton;
import com.epam.at.pageobjectmodel.objects.Mail;
import com.epam.at.pageobjectmodel.objects.User;
import com.epam.at.pageobjectmodel.pages.SignInPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteDraftMailUsingDragNDropTest extends InitialTest {

    private Mail mail = new Mail();

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void checkDraftMailIsDeleted(String login, String password) {

        User user = new User(login, password);

        WebElement lastDraftMail = new SignInPage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()))
                .openPage()
                .signInToMailbox(user.getUsername(), user.getPassword())
                .startToCreateNewMailUsingHotKeys(WebDriverSingleton.getWebDriverInstance())
                .fillInMailAddress(mail.getAddressTo())
                .fillInMailSubject(mail.getSubject())
                .fillInMailBody(mail.getBody())
                .saveMailAsDraftUsingHotKeys()
                .closeMailPopup()
                .openDraftsFolder()
                .deleteDraftMailUsingDragNDrop(mail.getSubject())
                .openTrashFolder()
                .getMailFromListOnPage(mail.getSubject());

        Assert.assertFalse(lastDraftMail == null, "Mail with given subject is not found in Trash folder, " +
                "probably mail was not deleted");
    }
}
