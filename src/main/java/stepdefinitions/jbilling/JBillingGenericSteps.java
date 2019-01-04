package stepdefinitions.jbilling;

import com.essent.roles.UserRoles;
import com.essent.testing.jbilling.scenario.JBillingScenario;
import com.essent.testing.jbilling.pageobject.Window;
import com.essent.testing.jbilling.pageobject.impl.modal.login.JBillingLogin;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

import static org.junit.Assert.assertNotNull;

public class JBillingGenericSteps extends JBillingScenario {
    @Before("@JBILLING, @B2B, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Given("^I logged in to JBilling as \"([^\"]*)\"$")
    public void loginAs(String username) throws Throwable {
        isJBillingRunning();
        UserRoles jBillingUser = UserRoles.get(username);
        Window application = new JBillingLogin(webDriver).login(jBillingUser.getUsername(), jBillingUser.getPassword());
        assertNotNull("DWP application did not appear after a login", application);
    }

    @After("@JBILLING, @B2B, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
