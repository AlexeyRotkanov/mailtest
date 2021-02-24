package com.epam.at.pageobjectmodel.tools;

import org.openqa.selenium.WebElement;

public class ParseMailDataFromWebElement {

    public static String getMailAddressTo(String mailData) {
        return splitMailData(mailData)[0].split("\\R")[0];
    }

    public static String getMailSubject(String mailData) {
        return splitMailData(mailData)[0].split("\\R")[1];
    }

    public static String getMailBody(String mailData) {
        return splitMailData(mailData)[1];
    }

    private static String[] splitMailData(String mailData) {
        return mailData.split(" ");
    }
}
