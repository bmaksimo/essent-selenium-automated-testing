package stepdefinitions.dwp.overview;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import stepdefinitions.dwp.navigation.NavigationElements;

public class DashboardMenu extends NavigationElements {

    @Before("@DWP or @CORE or @E2E or @REGRESSION or @API")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @When("^Dashboard menu is \"([^\"]*)\"$")
    public void checkDashboardMenuItem(String menuItem){
        clickDashboardMenu(menuItem);
        parameterProvider.put("navigation", "back");
        parameterProvider.put("dashboard-menu", menuItem);
    }

    @When("^Dashboard menu is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void checkDashboardMenuItem(String menuItem, int waitingTime){
        clickDashboardMenu(menuItem, waitingTime);
        parameterProvider.put("navigation", "back");
        parameterProvider.put("dashboard-menu", menuItem);
    }

    @Override
    @After("@DWP or @CORE or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
