package stepdefinitions;

import com.epam.at.pageobjectmodel.drivermanagers.WebDriverSingleton;
import com.epam.at.pageobjectmodel.pages.HomePage;
import cucumber.api.java.After;


public class ScenarioHooks {

    @After
    public void afterScenario(){
        if (!WebDriverSingleton.getWebDriverInstance().getCurrentUrl().contains("logout"))
            new HomePage(WebDriverSingleton.getWebDriverInstance()).logout();
    }
}
