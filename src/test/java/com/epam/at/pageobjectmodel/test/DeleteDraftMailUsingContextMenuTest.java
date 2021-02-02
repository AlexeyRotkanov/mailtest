package com.epam.at.pageobjectmodel.test;

import com.epam.at.pageobjectmodel.dataprovider.AccountCredentials;
import com.epam.at.pageobjectmodel.page.SignInPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteDraftMailUsingContextMenuTest extends InitialTest {

    private String mailSubject = System.currentTimeMillis() + "_test_subject";
    private String mailBody = System.currentTimeMillis() + "_test_body";
    private String mailAddress = "alex.r.epm@gmail.com";

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void checkDraftMailIsDeleted(String login, String password) {

        WebElement lastDeletedMail = new SignInPage(driver)
                .openPage()
                .signInToMailbox(login, password)
                .startToCreateNewMailUsingHotKeys()
                .fillInMailAddress(mailAddress)
                .fillInMailSubject(mailSubject)
                .fillInMailBody(mailBody)
                .saveMailAsDraftUsingHotKeys()
                .closeMailPopup()
                .openDraftsFolder()
                .deleteMailUsingContextMenu(mailSubject)
                .openTrashFolder()
                .getMailOnPageUsingJs(mailSubject);

        Assert.assertFalse(lastDeletedMail == null, "Mail with given subject is not found in Trash folder " +
                "probably mail was not deleted");
    }
}
