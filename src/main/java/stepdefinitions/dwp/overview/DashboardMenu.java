package stepdefinitions.dwp.overview;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import stepdefinitions.dwp.navigation.NavigationElements;

public class DashboardMenu extends NavigationElements {

    @Before("@DWP, @CORE, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Dashboard menu is \"([^\"]*)\"")
    public void checkDashboardMenuItem(String menuItem) throws Throwable {
        clickDashboardMenu(menuItem);
        parameterProvider.put("navigation", "back");
        parameterProvider.put("dashboard-menu", menuItem);
    }

    @Override
    @After("@DWP, @CORE, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
