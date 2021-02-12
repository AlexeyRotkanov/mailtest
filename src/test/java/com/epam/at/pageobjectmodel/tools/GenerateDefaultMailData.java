package com.epam.at.pageobjectmodel.tools;

public class GenerateDefaultMailData {

    private static final String MAIL_SUBJECT_DEFAULT_TEXT = "_test_subject";
    private static final String MAIL_BODY_DEFAULT_TEXT = "_test_body";

    public static String generateMailSubject() {
        return System.currentTimeMillis() + MAIL_SUBJECT_DEFAULT_TEXT;
    }

    public static String generateMailBody() {
        return System.currentTimeMillis() + MAIL_BODY_DEFAULT_TEXT;
    }
}
