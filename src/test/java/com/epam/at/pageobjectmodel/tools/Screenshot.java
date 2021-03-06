package com.epam.at.pageobjectmodel.tools;

import com.epam.at.pageobjectmodel.reporting.MailLogger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Screenshot {

    private static final String SCREENSHOTS_NAME_TPL = "screenshots/scr";

    public static void takeScreenshot(WebDriver driver) {
        TakesScreenshot tk = (TakesScreenshot) driver;
        File screenshot = tk.getScreenshotAs(OutputType.FILE);
        try {
            String screenshotName = SCREENSHOTS_NAME_TPL + System.nanoTime();
            String scrPath = screenshotName + ".jpg";
            File copy = new File(scrPath);
            FileUtils.copyFile(screenshot, copy);
        } catch (IOException e) {
            MailLogger.error("Failed to make screenshot");
        }
    }
}
