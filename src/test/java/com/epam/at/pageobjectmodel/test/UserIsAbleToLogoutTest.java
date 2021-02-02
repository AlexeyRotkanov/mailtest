package com.epam.at.pageobjectmodel.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.epam.at.pageobjectmodel.page.SignInPage;
import com.epam.at.pageobjectmodel.dataprovider.AccountCredentials;

public class UserIsAbleToLogoutTest extends InitialTest {

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void logoutTest(String login, String password) {

        new SignInPage(driver)
                .openPage()
                .signInToMailbox(login, password)
                .logout();

        boolean isUserLoggedOut = driver.getCurrentUrl().contains("logout");
        Assert.assertTrue(isUserLoggedOut, "User is not logged out");
    }
}
