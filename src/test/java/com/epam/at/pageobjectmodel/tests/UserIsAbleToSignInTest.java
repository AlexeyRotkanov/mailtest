package com.epam.at.pageobjectmodel.tests;

import com.epam.at.pageobjectmodel.objects.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.epam.at.pageobjectmodel.pages.SignInPage;
import com.epam.at.pageobjectmodel.dataproviders.AccountCredentials;

public class UserIsAbleToSignInTest extends InitialTest {

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void loginToMailBoxTest(String login, String password) {

        User user = new User(login, password);

        new SignInPage(driver)
                .openPage()
                .signInToMailbox(user.getUsername(), user.getPassword());

        boolean isUserSignedIn = driver.getCurrentUrl().contains("inbox");
        Assert.assertTrue(isUserSignedIn, "User is not in inbox, check that user is signed in");
    }
}
