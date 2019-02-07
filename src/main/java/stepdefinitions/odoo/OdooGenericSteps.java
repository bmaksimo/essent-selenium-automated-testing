package stepdefinitions.odoo;

import com.essent.roles.UserRoles;
import com.essent.testing.odoo.pageobject.Window;
import com.essent.testing.odoo.pageobject.impl.modal.login.OdooLogin;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

import static org.junit.Assert.assertNotNull;

public class OdooGenericSteps extends OdooScenario {
    @Before("@ODOO, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
        setUpWebDriver();
    }

    @Given("^I logged in to Odoo as \"([^\"]*)\"$")
    public void login(String username) throws Throwable {
        isOdooRunning();
        UserRoles odooUser = UserRoles.get(username);
        Window application = new OdooLogin().login(odooUser.getUsername(), odooUser.getPassword());
        assertNotNull("DWP application did not appear after a login", application);
    }

    @Given("^I renew login to Odoo as \"([^\"]*)\"$")
    public void renewLoginAs(String username) throws Throwable {
        setUpWebDriver();
        login(username);
    }

    @After("@ODOO, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
