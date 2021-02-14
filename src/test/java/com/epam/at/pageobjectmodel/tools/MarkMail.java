package com.epam.at.pageobjectmodel.tools;

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
                break;
            }
        }

        return mail;
    }
}
