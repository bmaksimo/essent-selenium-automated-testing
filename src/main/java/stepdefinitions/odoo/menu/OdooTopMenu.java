package stepdefinitions.odoo.menu;

import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import stepdefinitions.odoo.navigation.OdooNavigationElements;

public class OdooTopMenu extends OdooNavigationElements {

    @Before("@CODA")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Odoo top menu is ([^\"]*)$")
    public void OdooCheckTopMenuAction(String menu) throws Throwable {
        OdooClickTopMenuAction(menu);
    }
}
