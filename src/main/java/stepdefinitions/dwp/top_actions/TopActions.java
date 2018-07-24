package stepdefinitions.dwp.top_actions;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import stepdefinitions.dwp.NavigationElements;

public class TopActions extends NavigationElements {

    @Before("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL @B2B_REGRESSION")
    public void SetupTest(Scenario scenario) throws Throwable {
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

    @And("^([^\"]*) Change$")
    public void confirmChange(String action) {
        clickConfirm(action);
    }
}
