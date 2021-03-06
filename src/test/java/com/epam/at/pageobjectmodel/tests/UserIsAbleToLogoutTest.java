package com.epam.at.pageobjectmodel.tests;

import com.epam.at.pageobjectmodel.decorators.CustomDriverDecorator;
import com.epam.at.pageobjectmodel.drivermanagers.WebDriverSingleton;
import com.epam.at.pageobjectmodel.objects.User;
import com.epam.at.pageobjectmodel.reporting.MailLogger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.epam.at.pageobjectmodel.pages.SignInPage;
import com.epam.at.pageobjectmodel.dataproviders.AccountCredentials;

public class UserIsAbleToLogoutTest extends InitialTest {

    @Test(dataProviderClass = AccountCredentials.class, dataProvider = "accountCredentials")
    public void logoutTest(String login, String password) {

        User user = new User(login, password);

        new SignInPage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()))
                .openPage()
                .signInToMailbox(user.getUsername(), user.getPassword())
                .logout();

        String currentUrl = WebDriverSingleton.getWebDriverInstance().getCurrentUrl();
        MailLogger.debug("URL after logout is: " + currentUrl);
        boolean isUserLoggedOut = currentUrl.contains("logout");
        Assert.assertTrue(isUserLoggedOut, "User is not logged out");
    }
}
