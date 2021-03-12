package com.epam.at.pageobjectmodel.tools;

import com.epam.at.pageobjectmodel.reporting.MailLogger;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SelectMail {

    public static WebElement selectMailBySubject(String mailSubject, List<WebElement> listOfMails) {
        Delay.makeDelay(1000);

        WebElement mail = null;
        for (WebElement element : listOfMails) {
            if (element.getText().contains(mailSubject))
                mail = element;
        }

        if (mail==null)
            MailLogger.warn("No mail selected! The mail list has not yet been loaded or the mail does not exist.");

        return mail;
    }

}
