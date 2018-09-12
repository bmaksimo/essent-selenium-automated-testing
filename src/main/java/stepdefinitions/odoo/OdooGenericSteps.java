package stepdefinitions.odoo;

import com.essent.roles.UserRoles;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.impl.modal.login.LoginAction;
import com.essent.testing.odoo.pageobject.impl.modal.login.OdooLogin;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

import static org.junit.Assert.assertNotNull;

public class OdooGenericSteps extends OdooScenario {
    @Before("@SMOKE, @CODA, @ODOO")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
        setUpWebDriver();
        isOdooRunning();
    }

    @Given("^I logged in to Odoo as ([^\"]*)$")
    public void loginAs(String username) throws Throwable {
        UserRoles odooUser = UserRoles.get(username);
        Window application = new OdooLogin(webDriver).login(odooUser.getUsername(), odooUser.getPassword());
        assertNotNull("DWP application did not appear after a login", application);
    }

    @After("@SMOKE, @CODA, @ODOO")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
