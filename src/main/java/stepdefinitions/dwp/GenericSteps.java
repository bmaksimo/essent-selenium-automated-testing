package stepdefinitions.dwp;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Autocrat;
import com.essent.automation.autocrat.Model;
import com.essent.automation.autocrat.Model.Flow;
import com.essent.roles.UserRoles;
import com.essent.testing.dwp.DwpScenario;
import com.essent.testing.dwp.pageobject.Window;
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

    @Before("@QUOTE, @MENU, @RENEWAL, @FILTER, @SMOKE")
    public void SetupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
        setUpWebDriver();
        isDwpRunning(BASE_URL);
    }

    @Given("^I logged in to DWP as ([^\"]*)$")
    public void loginAs(String username) throws Throwable {
        retrieveUserLanguage();
        UserRoles dwpUser = UserRoles.get(username);
        Window application = new LoginAction(webDriver).doLogin(dwpUser.getUsername(), dwpUser.getPassword());
        assertNotNull("DWP application did not appear after a login", application);
        injectJavaScriptTestRunner();
        discardPreviousFlow();
    }

    private void discardPreviousFlow() throws Throwable {
        Model.Execution execution = new Model.Execution();
        execution.element("DWP_MODAL_CANCEL",
            new Model.Element().search("SELECTOR").query("#cancel-button"));
        Autocrat.ExecutionContext context =
            new Autocrat.ExecutionContext(webDriver.getDriver(), execution);
        Model.Step s =
            new Model.Step().action(Action.REQUIRE).element("DWP_MODAL_CANCEL").timeoutInSeconds(2).breakFlowOnFailure(false);
        Flow flow = s.flow();
        flow.steps(new Model.Step().action(Action.CLICK).element("DWP_MODAL_CANCEL").breakFlowOnFailure(false),
            new Model.Step().action(Action.REQUIRE_ABSENT).element("DWP_MODAL_CANCEL"));
        Autocrat.executeFlow(context, flow);
    }

    private void retrieveUserLanguage() {
        WebStorage webStorage = (WebStorage)webDriver.getDriver();
        LocalStorage localStorage = webStorage.getLocalStorage();
        String userLanguage = localStorage.getItem("NG_TRANSLATE_LANG_KEY");
        if(StringUtils.isEmpty(userLanguage)) {
            logger().warn(" - WARNING: Application did not contain user language value. Default: " + preferredLanguage + " will be used.");
        } else {
            logger().info(" - RESULT: setting preferred language: " + userLanguage);
            preferredLanguage = userLanguage;
        }
    }

    @After({"@QUOTE, @MENU, @RENEWAL, @FILTER, @SMOKE"})
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
