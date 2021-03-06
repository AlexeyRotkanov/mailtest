package com.epam.at.pageobjectmodel.tools;

import com.epam.at.pageobjectmodel.reporting.MailLogger;

public class ParseMailDataFromWebElement {

    public static String getMailAddressTo(String mailData) {
        String parsedMailAddressTo = splitMailData(mailData)[0].split("\\R")[0];
        MailLogger.info("Parsing mail data: Address To = " + parsedMailAddressTo);
        return parsedMailAddressTo;
    }

    public static String getMailSubject(String mailData) {
        String parsedMailSubject = splitMailData(mailData)[0].split("\\R")[1];
        MailLogger.info("Parsing mail data: Subject = " + parsedMailSubject);
        return parsedMailSubject;
    }

    public static String getMailBody(String mailData) {
        String parsedMailBody = splitMailData(mailData)[1];
        MailLogger.info("Parsing mail data: Body = " + parsedMailBody);
        return parsedMailBody;
    }

    private static String[] splitMailData(String mailData) {
        return mailData.split(" ");
    }
}
