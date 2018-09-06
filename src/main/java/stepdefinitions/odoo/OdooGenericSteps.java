package stepdefinitions.odoo;

import com.essent.roles.UserRoles;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.selenium.SeleniumScenario;
import cucumber.api.java.en.Given;
import stepdefinitions.dwp.login.LoginAction;

import static org.testng.AssertJUnit.assertNotNull;

public class OdooGenericSteps extends SeleniumScenario {

    @Given("^I logged in to Odoo as ([^\"]*)$")
    public void loginAs(String username) throws Throwable {
        UserRoles dwpUser = UserRoles.get(username);
        Window application = new LoginAction(webDriver).doLogin(dwpUser.getUsername(), dwpUser.getPassword());
        assertNotNull("DWP application did not appear after a login", application);
    }
}
