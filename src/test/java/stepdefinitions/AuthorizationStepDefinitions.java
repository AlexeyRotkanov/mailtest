package stepdefinitions;

import com.epam.at.pageobjectmodel.decorators.CustomDriverDecorator;
import com.epam.at.pageobjectmodel.drivermanagers.WebDriverSingleton;
import com.epam.at.pageobjectmodel.pages.HomePage;
import com.epam.at.pageobjectmodel.pages.SignInPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class AuthorizationStepDefinitions {

    @Given("^I opened the Mail site$")
    public void iOpenedMailSite() {
        new SignInPage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()))
                .openPage();
    }

    @And("^I logged in with login ([^\"]*) and password ([^\"]*)$")
    public void iLoggedInWithLoginAndPassword(String login, String password) {
        new SignInPage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()))
                .signInToMailbox(login, password);
    }

    @Then("^I am logged in$")
    public void iAmLoggedIn() {
        Assert.assertTrue(new HomePage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance())).isUserLoggedIn(), "User is not in inbox, check that user is signed in");
    }

    @When("^I logged out$")
    public void iLoggedOut() {
        new HomePage(new CustomDriverDecorator(WebDriverSingleton
                .getWebDriverInstance()))
                .logout();
    }

    @Then("^I am logged out$")
    public void iAmLoggedOut() {
        boolean isUserLoggedOut = WebDriverSingleton.getWebDriverInstance().getCurrentUrl().contains("logout");
        Assert.assertTrue(isUserLoggedOut, "User is not logged out");
    }
}
