package com.epam.at.pageobjectmodel.tools;

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

        return mail;
    }

}
