package com.epam.at.pageobjectmodel.dataproviders;

import org.testng.annotations.DataProvider;

public class AccountCredentials {

    @DataProvider(name = "accountCredentials")
    public Object[][] accountCredentials() {
        return new Object[][] {
                {"distest", "P@ssword!23"}
        };
    }
}
