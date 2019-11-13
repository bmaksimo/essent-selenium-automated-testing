package stepdefinitions.dwp.overview;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.navigation.DashboardMenuPage;
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
    public void goToDashboardMenuItem(String menuItem) {
        seleniumDriver.waitForRequestsToFinish();
        new DashboardMenuPage().clickOnDashboardElement(menuItem);
        addParametersToContext(menuItem);
    }

    @When("^Dashboard menu is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void goToDashboardMenuItem(String menuItem, int waitingTime){
        Sleeper.sleepTightInSeconds(waitingTime);
        new DashboardMenuPage().clickOnDashboardElementNow(menuItem);
        addParametersToContext(menuItem);
    }

    private void addParametersToContext(String menuItem) {
        parameterProvider.put("navigation", "back");
        parameterProvider.put("dashboard-menu", menuItem);
    }

    @Override
    @After("@DWP or @CORE or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
