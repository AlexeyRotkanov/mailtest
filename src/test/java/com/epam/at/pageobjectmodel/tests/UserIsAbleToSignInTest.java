package com.epam.at.pageobjectmodel.tests;

import com.epam.at.pageobjectmodel.decorators.CustomDriverDecorator;
import com.epam.at.pageobjectmodel.drivermanagers.WebDriverSingleton;
import com.epam.at.pageobjectmodel.objects.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.epam.at.pageobjectmodel.pages.SignInPage;
import com.epam.at.pageobjectmodel.dataproviders.AccountCredentials;

public class UserIsAbleToSignInTest extends InitialTest {

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void loginToMailBoxTest(String login, String password) {

        User user = new User(login, password);

        boolean isUserLoggedIn = new SignInPage(WebDriverSingleton
                .getWebDriverInstance())
                .openPage()
                .signInToMailbox(user.getUsername(), user.getPassword())
                .isUserLoggedIn();

        Assert.assertTrue(isUserLoggedIn, "User is not in inbox, check that user is signed in");
    }
}
