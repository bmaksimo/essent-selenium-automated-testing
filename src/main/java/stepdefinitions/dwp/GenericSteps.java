package stepdefinitions.dwp;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.roles.UserRoles;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.impl.modal.login.LoginAction;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertNotNull;
@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")
public class GenericSteps extends DwpScenario {

    @Before("@DWP, @ODOO, @CORE, @E2E, @REGRESSION, @SALES-MARKETING, @CONTRACTING-SWITCHING, @BUSINESS-DESK, @BILLING")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Given("^I logged in to DWP as ([^\"]*)$")
    public void loginAs(String username) throws Throwable {
        isDwpRunning();
        UserRoles dwpUser = UserRoles.get(username);
        Window application = new LoginAction(webDriver).doLogin(dwpUser.getUsername(), dwpUser.getPassword());
        assertNotNull("DWP application did not appear after a login", application);
        injectJavaScriptTestRunner();
        discardPreviousFlow();
    }

    private void discardPreviousFlow() throws Throwable {
        Model.Execution execution = createExecution();
        execution
            .element("DWP_MODAL_CANCEL", new Model.Element().search("SELECTOR").query("#cancel-button"))
            .step(createStep(Action.CLICK).element("DWP_MODAL_CANCEL").timeoutInSeconds(3).sleepInMillis(100));
        execute(execution);
    }


    @After("@DWP, @ODOO, @CORE, @E2E, @REGRESSION, @SALES-MARKETING, @CONTRACTING-SWITCHING, @BUSINESS-DESK, @BILLING")
    public void tearDown() throws Exception {
        tidyUp();
    }

}
