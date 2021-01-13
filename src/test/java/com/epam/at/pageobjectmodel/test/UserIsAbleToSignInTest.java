package com.epam.at.pageobjectmodel.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.epam.at.pageobjectmodel.page.SignInPage;
import com.epam.at.pageobjectmodel.dataprovider.AccountCredentials;

public class UserIsAbleToSignInTest extends InitialTest {

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void loginToMailBoxTest(String login, String password, String mailAddress) {

        new SignInPage(driver)
                .openPage()
                .signInToMailbox(login, password);

        boolean isUserSignedIn = driver.getCurrentUrl().contains("inbox");
        Assert.assertTrue(isUserSignedIn, "User is not in inbox, check that user is signed in");
    }
}
