package stepdefinitions.dwp.top_actions;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import stepdefinitions.dwp.navigation.NavigationElements;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class TopActions extends NavigationElements {

    @Before("@DWP, CORE, @E2E, @REGRESSION, @SALES-MARKETING, @CONTRACTING-SWITCHING, @BUSINESS-DESK, @BILLING")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Top action is ([^\"]*)$")
    public void checkTopAction(String action) throws Throwable {
        clickTopAction(action);
    }

    @And("^Top arrow button is ([^\"]*)$")
    public void clickTopArrow(String arrow) throws Throwable {
        super.clickTopArrow(arrow.toLowerCase());
    }

    @When("^Cockpit item is ([^\"]*)$")
    public void checkCockpitItem(String item) throws Throwable {
        clickCockpitItem(item);
    }

    @And("Changes are confirmed")
    public void confirmChange() {
        webDriver.waitForRequestsToFinish();
        boolean success = new ClickConfirm().test("");
        assertThat(String.format("Button %s was not available.", ""),
            success, is(true));
    }

    @And("Search input is ([^\"]*)$")
    public void input(String name) throws Throwable {
        Map<String, String> customerName = new HashMap<>();
        customerName.put("name", name);
        boolean success = new SearchCustomer().test(name);
        assertThat(String.format("Customer %s was not found.", name),
            success, is(true));
    }

    @And("^Customer \"([^\"]*)\" is found$")
    public void customerFind(String name) throws Throwable {
        Map<String, String> customerName = new HashMap<>();
        customerName.put("name", name);
        boolean success = new ValidateCustomer().test(customerName);
        assertThat(String.format("View list did not contain customer '%s'", name),
            success, is(true));
    }

    @Override
    @After("@DWP, CORE, @E2E, @REGRESSION, @SALES-MARKETING, @CONTRACTING-SWITCHING, @BUSINESS-DESK, @BILLING")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
