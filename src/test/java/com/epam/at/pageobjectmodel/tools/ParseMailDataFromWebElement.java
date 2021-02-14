package com.epam.at.pageobjectmodel.tools;

import org.openqa.selenium.WebElement;

public class ParseMailDataFromWebElement {

    public static String getMailAddressTo(WebElement mail) {
        String[] mailData = mail.getText().split(" ");

        return mailData[0].split("\\R")[0];
    }

    public static String getMailSubject(WebElement mail) {

        String[] mailData = mail.getText().split(" ");

        return mailData[0].split("\\R")[1];
    }

    public static String getMailBody(WebElement mail) {

        String[] mailData = mail.getText().split(" ");

        return mailData[1];
    }
}
