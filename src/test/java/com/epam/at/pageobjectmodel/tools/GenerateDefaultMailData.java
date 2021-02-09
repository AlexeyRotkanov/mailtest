package com.epam.at.pageobjectmodel.tools;

public class GenerateDefaultMailData {

    public static String generateMailSubject() {
        return System.currentTimeMillis() + "_test_subject";
    }

    public static String generateMailBody() {
        return System.currentTimeMillis() + "_test_body";
    }
}
