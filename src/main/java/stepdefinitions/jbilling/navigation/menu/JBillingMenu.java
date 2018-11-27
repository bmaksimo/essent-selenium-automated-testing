package stepdefinitions.jbilling.navigation.menu;

import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class JBillingMenu extends OdooScenario {

    @Before("@JBILLING, @B2B, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@JBILLING, @B2B, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
