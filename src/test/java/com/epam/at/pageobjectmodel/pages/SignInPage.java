package com.epam.at.pageobjectmodel.pages;

import com.epam.at.pageobjectmodel.conditions.CustomConditions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignInPage extends AbstractPage {

    private static final String HOMEPAGE_URL = "https://mail.ru/";

    @FindBy(name = "login")
    private WebElement loginInputField;

    @FindBy(xpath = "//input[@name='login']/ancestor::form/button[not(contains(@class, 'second'))]")
    private WebElement passwordButton;

    @FindBy(name = "password")
    private WebElement passwordInputField;

    @FindBy(xpath = "//input[@name='login']/ancestor::form/button[contains(@class, 'second')]")
    private WebElement signInButton;


    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public SignInPage openPage() {
        driver.get(HOMEPAGE_URL);
        return this;
    }

    public HomePage signInToMailbox(String login, String password) {
        fillInLogin(login);
        fillInPassword(password);
        signInButton.click();

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(CustomConditions.jQueryAjaxCompleted());

        return new HomePage(driver);
    }

    private SignInPage fillInPassword(String password) {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(passwordInputField)).sendKeys(password);
        return this;
    }

    private SignInPage fillInLogin(String login) {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(loginInputField)).sendKeys(login);
        passwordButton.click();
        return this;
    }
}
