package stepdefinitions.odoo.navigation.menu;

import com.essent.testing.odoo.navigation.menu.MenuNavigation;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import cucumber.runtime.CucumberException;

public class OdooMenu extends OdooScenario {

    @Before("@SMOKE, @ODOO, @CODA")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Odoo top menu is ([^\"]*)$")
    public void clickTopMenu(String menu) throws Throwable {
        MenuNavigation menuuNavigation = new MenuNavigation(webDriver);
        boolean success = menuuNavigation.findAndClickMainMenuItem(menu);
        if(! success) {
            throw new CucumberException(menuuNavigation.getReason());
        }
    }

    @When("^Odoo left menu is ([^\"]*)$")
    public void OdooCheckTopMenuAction(String menuPath) throws Throwable {
        MenuNavigation odooMenuNavigation = new MenuNavigation(webDriver);
        odooMenuNavigation.executeAction(menuPath);
    }

    @Override
    @After("@SMOKE, @ODOO, @CODA")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
