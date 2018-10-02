package stepdefinitions.odoo.menu;

import com.essent.testing.odoo.navigation.menu.LeftMenuNavigation;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import stepdefinitions.odoo.navigation.OdooNavigationElements;

public class OdooLeftMenu extends OdooNavigationElements {

    @Before("@CODA")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Odoo left menu is ([^\"]*)$")
    public void OdooCheckTopMenuAction(String menuPath) throws Throwable {
        LeftMenuNavigation odooLeftMenuNavigation = new LeftMenuNavigation(webDriver);
        odooLeftMenuNavigation.executeAction(menuPath);
        logger().info("Success");
    }
}
