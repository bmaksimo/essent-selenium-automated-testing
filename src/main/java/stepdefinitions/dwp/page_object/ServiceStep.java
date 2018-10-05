package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.impl.service_contracting.ServicePage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;

public class ServiceStep extends DwpScenario {

    @Before("@SMOKE, @E2E, @QUOTE, @RENEWAL, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@SMOKE, @E2E, @QUOTE, @RENEWAL, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Then("^\"([^\"]*)\" is created$")
    public void isCreated(String input) throws Throwable {
        ServicePage servicePage = new ServicePage(webDriver);
        servicePage.validateCreatedTask(input);
    }

}
