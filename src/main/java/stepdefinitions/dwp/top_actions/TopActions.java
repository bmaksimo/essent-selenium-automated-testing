package stepdefinitions.dwp.top_actions;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import stepdefinitions.dwp.navigation.NavigationElements;

public class TopActions extends NavigationElements {

    @Before("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Top Action is ([^\"]*)$")
    public void checkTopAction(String action) throws Throwable {
        clickTopAction(action);
    }

    @And("^Top Arrow button is ([^\"]*)$")
    public void clickTopArrow(String arrow) throws Throwable {
        super.clickTopArrow(arrow.toLowerCase());
    }

    @When("^Cockpit item is ([^\"]*)$")
    public void checkCockpitItem(String item) throws Throwable {
        clickCockpitItem(item);
    }

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
