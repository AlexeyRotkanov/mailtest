package com.epam.at.pageobjectmodel.tests;

import com.epam.at.pageobjectmodel.dataproviders.AccountCredentials;
import com.epam.at.pageobjectmodel.objects.Mail;
import com.epam.at.pageobjectmodel.objects.User;
import com.epam.at.pageobjectmodel.pages.SignInPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteDraftMailUsingContextMenuTest extends InitialTest {

    private Mail mail = new Mail();

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void checkDraftMailIsDeleted(String login, String password) {

        User user = new User(login, password);

        WebElement lastDeletedMail = new SignInPage(driver)
                .openPage()
                .signInToMailbox(user.getUsername(), user.getPassword())
                .startToCreateNewMailUsingHotKeys()
                .fillInMailAddress(mail.getAddressTo())
                .fillInMailSubject(mail.getSubject())
                .fillInMailBody(mail.getBody())
                .saveMailAsDraftUsingHotKeys()
                .closeMailPopup()
                .openDraftsFolder()
                .deleteMailUsingContextMenu(mail.getSubject())
                .openTrashFolder()
                .getMailOnPageUsingJs(mail.getSubject());

        Assert.assertFalse(lastDeletedMail == null, "Mail with given subject is not found in Trash folder " +
                "probably mail was not deleted");
    }
}
