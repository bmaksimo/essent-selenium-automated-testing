package stepdefinitions.dwp.top_actions;

import com.essent.automation.util.Sleeper;
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

    @Before("@DWP, @CORE, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Top action is \"([^\"]*)\"$")
    public void checkTopAction(String action) throws Throwable {
        clickTopAction(action);
    }

    @When("^Top action is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void checkTopAction(String action, int waitingTime) throws Throwable {
        clickTopAction(action, waitingTime);
    }

    @And("^Top arrow button is \"([^\"]*)\"$")
    public void clickTopArrowButton(String arrow) throws Throwable {
        super.clickTopArrow(arrow);
    }

    @And("^Top arrow button is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void clickTopArrowButton(String arrow, int waitingTime) throws Throwable {
        super.clickTopArrow(arrow.toLowerCase(), waitingTime);
    }

    @When("^Cockpit item is \"([^\"]*)\"$")
    public void checkCockpitItem(String item) throws Throwable {
        clickCockpitItem(item);
    }

    @And("^Changes are confirmed$")
    public void confirmChange() {
        seleniumDriver.waitForRequestsToFinish();
        boolean success = new ClickConfirm().test("");
        assertThat(String.format("Button %s was not available.", ""), success, is(true));
    }

    @And("^Changes are confirmed waiting for (\\d+) seconds$")
    public void confirmChange(int waitingTime) {
        Sleeper.sleepTightInSeconds(waitingTime);
        boolean success = new ClickConfirm().testNow("");
        assertThat(String.format("Button %s was not available.", ""), success, is(true));
    }

    @And("^Search input is \"([^\"]*)\"$")
    public void input(String inputName) throws Throwable {
        String name = parameterProvider.getValueOrParameterAsString(inputName);
        Map<String, String> customerName = new HashMap<>();
        customerName.put("name", name);
        boolean success = new SearchCustomer().test(name);
        assertThat(String.format("Customer %s was not found.", name),
            success, is(true));
    }

    @And("^Customer \"([^\"]*)\" is found$")
    public void customerFind(String inputName) throws Throwable {
        String name = parameterProvider.getValueOrParameterAsString(inputName);
        Map<String, String> customerName = new HashMap<>();
        String Inputname = parameterProvider.getValueOrParameterAsString(name);
        customerName.put("name", Inputname);
        boolean success = new ValidateCustomer().test(customerName);
        assertThat(String.format("View list did not contain customer '%s'", inputName),
            success, is(true));
    }

    @Override
    @After("@DWP, @CORE, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
