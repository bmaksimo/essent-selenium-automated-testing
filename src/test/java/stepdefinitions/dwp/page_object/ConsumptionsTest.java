package stepdefinitions.dwp.page_object;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import stepdefinitions.dwp.navigation.NavigationElements;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConsumptionsTest extends NavigationElements {

    @Before("@DWP, @E2E")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Then("^View list is not empty$")
    public void viewListIsNotEmpty() throws Throwable {
        boolean success = new CheckViewListIsNotEmpty().test("");
        assertThat(String.format("View list did not contain header '%s'", ""),
            success, is(true));
    }

    @Override
    @After("@DWP, @E2E")
    public void tearDown() {
        super.tearDown();
    }
}
