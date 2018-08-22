package stepdefinitions.dwp;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Autocrat;
import com.essent.automation.autocrat.Model;
import com.essent.automation.autocrat.Model.Flow;
import com.essent.roles.UserRoles;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.springframework.test.context.ContextConfiguration;
import stepdefinitions.dwp.login.LoginAction;

import static com.essent.testing.dwp.DwpConstant.BASE_URL;
import static org.junit.Assert.assertNotNull;
@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")
public class GenericSteps extends DwpScenario {

    @Before("@QUOTE, @MENU, @FILTER, @SMOKE")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
        setUpWebDriver();
        isDwpRunning(BASE_URL);
    }

    @Given("^I logged in to DWP as ([^\"]*)$")
    public void loginAs(String username) throws Throwable {
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


    @After({"@QUOTE, @MENU, @FILTER, @SMOKE"})
    public void tearDown() throws Exception {
        tidyUp();
    }

    @After({"@QUOTE, @SMOKE"})
    public void failedScenario(Scenario scenario) throws Exception {
        if (scenario.isFailed()) {
            logger().error("The scenario '" + scenario.getName() + "' failed");
        }
    }

}
