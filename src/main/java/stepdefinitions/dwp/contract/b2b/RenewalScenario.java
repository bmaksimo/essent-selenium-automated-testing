package stepdefinitions.dwp.contract.b2b;

import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class RenewalScenario extends DwpScenario {
    @Before("@RENEWAL")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@RENEWAL")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
