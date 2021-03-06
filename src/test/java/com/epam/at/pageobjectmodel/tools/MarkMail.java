package com.epam.at.pageobjectmodel.tools;

import com.epam.at.pageobjectmodel.reporting.MailLogger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MarkMail {

    public static WebElement markMailBySubject(String mailSubject,
                                        List<WebElement> listOfMails,
                                        List<WebElement> listOfCheckboxes,
                                        WebDriver driver,
                                        int waitTiomeoutSeconds) {
        Delay.makeDelay(1000);

        WebElement mail = null;
        for (int i = 0; i < listOfMails.size(); i++) {
            if (listOfMails.get(i).getText().contains(mailSubject)) {
                mail = listOfMails.get(i);
                new Actions(driver).moveToElement(listOfMails.get(i)).build().perform();
                new WebDriverWait(driver, waitTiomeoutSeconds)
                        .until(ExpectedConditions.elementToBeClickable(listOfCheckboxes.get(i))).click();
                MailLogger.info(String.format("Mail with index #%s was marked", i));
                break;
            } else {
                MailLogger.warn("No mail found for marking. It affects drag and drop function!");
            }
        }

        return mail;
    }
}
