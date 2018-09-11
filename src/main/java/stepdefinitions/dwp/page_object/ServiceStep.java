package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.service_contracting.ServicePage;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;

public class ServiceStep extends ServicePage {

    @Before("@SMOKE, @QUOTE, @RENEWAL, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@SMOKE, @QUOTE, @RENEWAL, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Then("^\"([^\"]*)\" is created$")
    public void isCreated(String input) throws Throwable {
        validateCreatedTask(input);
    }

}
