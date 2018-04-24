package stepdefinitions.dwp;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Autocrat;
import com.essent.automation.autocrat.Model;
import com.essent.automation.autocrat.Model.Flow;
import com.billinghouse.javascript.model.options.TrGetUserLanguageOptions;
import com.essent.testing.dwp.DwpScenario;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.impl.LoginDialog;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

import static com.essent.testing.dwp.DwpConstant.*;
import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")
public class DWPGeneralScenario extends DwpScenario {

    private class GetPreferredLanguage implements Model.Callback {

        @Override
        public void onAccess(Autocrat.ExecutionContext context, Model.Step step, WebElement value) {
            JavascriptExecutor jsExec = (JavascriptExecutor) context.driver;
            preferredLanguage = (String)jsExec.executeScript(String.format(
                "return window.localStorage.%s;", "NG_TRANSLATE_LANG_KEY"));
            logger().info("NG_TRANSLATE_LANG_KEY=" + preferredLanguage);
        }
    }

    @Before("@QUOTE, @MENU, @DWP_SETUP, @CORE_SUPERNOVA, @ASSIGNMENT, @FILTER, @SMOKE")
    public void SetupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
        setUpWebDriver();
        dwp_is_running();
    }

    @After({"@QUOTE, @MENU, @DWP_TEARDOWN, @CORE_SUPERNOVA, @ASSIGNMENT, @FILTER, @SMOKE"})
    public void tearDown() throws Exception {
        tidyUp();
    }

    @After({"@QUOTE, @SMOKE"})
    public void failedScenario(Scenario scenario) throws Exception {
        if (scenario.isFailed()) {
            logger().error("The scenario '" + scenario.getName() + "' failed");
        }
    }

    public void dwp_is_running() throws Exception {
        verifyDwpIsRunning(BASE_URL);
    }


    public void go_to_dwp() throws Throwable {
        webDriver.getDriver().get(BASE_URL);
    }

    @Given("^I logged in on the DWP Main Page$")
    public void dwpPageLogin() throws Throwable {
        mainDwpPageLogin();
    }

    @Given("^I logged in as admin on the DWP Main Page - Obsolete$")
    public void mainDwpPageLogin() throws Throwable {

        Autocrat.ExecutionContext getLanguageExecutionContext = new Autocrat.ExecutionContext(webDriver.getDriver());
        Flow getDefaultlanguageFlow = getLanguageExecutionContext.getExecution()
            .variable("DWP_USER", DWP_USER)
            .element("DWP_USER", new Model.Element().search("ID").query("username"))
            .flow()
            .steps(
                new Model.Step().action(Action.GOTO).value(BASE_URL),
                new Model.Step().action(Action.ACCESS).element("DWP_USER")
                    .timeoutInSeconds(5)
                    .callback(new GetPreferredLanguage()));
        Autocrat.executeFlow(getLanguageExecutionContext, getDefaultlanguageFlow);

        Map<String, String> loginButtonLabel = new HashMap<>();
        loginButtonLabel.put("nl_BE", "Log in");
        loginButtonLabel.put("en_BE", "Login");

        String loginButtonQuery = String.format(".form__footer > .button[value='%s']", loginButtonLabel.get(preferredLanguage));

        Autocrat.ExecutionContext executionContext = new Autocrat.ExecutionContext(webDriver.getDriver());

        Flow dwpLoginFlow = executionContext.getExecution()
            .variable("DWP_USER", DWP_USER)
            .variable("DWP_PASS", DWP_PASSWORD)
            .element("DWP_USER", new Model.Element().search("ID").query("username"))
            .element("DWP_PASS", new Model.Element().search("ID").query("password"))
            .element("DWP_LOGIN", new Model.Element().search("SELECTOR").query(loginButtonQuery))
            .element("ICON_NOVA", new Model.Element().search("SELECTOR").query(".icon-nova"))
            .flow()
            .steps(
                //new Model.Step().action(Action.GOTO).value(BASE_URL),
                new Model.Step().action(Action.TYPING).element("DWP_USER").value("{{VAR:DWP_USER}}"),
                new Model.Step().action(Action.TYPING).element("DWP_PASS").value("{{VAR:DWP_PASS}}"),
                new Model.Step().action(Action.SLEEP).sleepInMillis(2000),
                new Model.Step().action(Action.CLICK).element("DWP_LOGIN"),
                new Model.Step().action(Action.SLEEP).sleepInMillis(3000),
                new Model.Step().action(Action.REQUIRE_ABSENT).element("DWP_LOGIN").breakFlowOnFailure(false).timeoutInSeconds(5).sleepInMillis(1000),
                new Model.Step().action(Action.ACCESS).element("ICON_NOVA")
                    .timeoutInSeconds(5)
            .callback(new GetPreferredLanguage()));
        assertTrue(Autocrat.executeFlow(executionContext, dwpLoginFlow), "Main DWP Page is expected to be active, but this did not happen");
        injectJavaScriptTestRunner();
    }
    @Given("^I logged in as admin on the DWP Main Page$")
    public void loginUsingPageObjects() throws Throwable {
        LoginDialog login = new LoginDialog(webDriver);
        injectJavaScriptTestRunner();
        retrieveAndInitUserLanguage();
        Window application = login.login(DWP_USER, DWP_PASSWORD);
        retrieveAndInitUserLanguage();
        System.out.println("=================================test");
        assertNotNull("DWP application did not appear after a login", application);
    }

    private void retrieveAndInitUserLanguage() {
        TrGetUserLanguageOptions options = new TrGetUserLanguageOptions();
        options.setLanguageKey("NG_TRANSLATE_LANG_KEY");
        Map result = executeJavascriptMethod("TrGetUserLanguage", options);
        String userLanguage = (String) result.get("userLanguage");
        if(StringUtils.isEmpty(userLanguage)) {
            logger().warn(" - WARNING: Application did not contain " + options.getLanguageKey() + " key value, default: " + preferredLanguage + " will be used.");
        } else {
            logger().info(" - RESULT: setting preferred language: " + userLanguage);
            preferredLanguage = userLanguage;
        }
    }

    @Given("I logged in as admin on the DWP Sales")
    public void I_logged_in_as_admin_on_the_DWP() throws Throwable {

        Model.Execution execution = new Model.Execution();
        execution.variable("DWP_USER", DWP_USER);
        execution.variable("DWP_PASS", DWP_PASSWORD);
        execution.element("DWP_USER", new Model.Element().search("ID").query("username"));
        execution.element("DWP_PASS", new Model.Element().search("ID").query("password"));
        execution.element("DWP_LOGIN",
            new Model.Element().search("SELECTOR").query(".form__footer > .button"));
        execution.element("ACCOUNTS_LIST",
            new Model.Element().search("SELECTOR").query(".main-content list[list-key='Accounts']"));
        Autocrat.ExecutionContext context =
            new Autocrat.ExecutionContext(webDriver.getDriver(), execution);
        Flow flow = new Flow();
        flow.steps(
            new Model.Step().action(Action.GOTO)
                .value(BASE_URL + "sales-marketing/dashboard/accounts_list/"),
            new Model.Step().action(Action.TYPING).element("DWP_USER").value("{{VAR:DWP_USER}}"),
            new Model.Step().action(Action.TYPING).element("DWP_PASS").value("{{VAR:DWP_PASS}}"),
            new Model.Step().action(Action.CLICK).element("DWP_LOGIN"),
            new Model.Step().action(Action.REQUIRE_ABSENT).element("DWP_LOGIN"));
        // ,new Model.Step().action(Action.REQUIRE).element("ACCOUNTS_LIST").timeoutInSeconds(3.5));
        Autocrat.executeFlow(context, flow);

    }

    @Given("^I optionally discard a previous flow:$")
    public void i_optionally_discard_a_previous_flow() throws Throwable {
        Model.Execution execution = new Model.Execution();
        execution.element("DWP_MODAL_CANCEL",
            new Model.Element().search("SELECTOR").query("#cancel-button"));
        Autocrat.ExecutionContext context =
            new Autocrat.ExecutionContext(webDriver.getDriver(), execution);
        Model.Step s =
            new Model.Step().action(Action.REQUIRE).element("DWP_MODAL_CANCEL").timeoutInSeconds(2).breakFlowOnFailure(false);
        // found element, get rid of it
        Flow flow = s.flow();
        flow.steps(new Model.Step().action(Action.CLICK).element("DWP_MODAL_CANCEL").breakFlowOnFailure(false),
            new Model.Step().action(Action.REQUIRE_ABSENT).element("DWP_MODAL_CANCEL"));
        Autocrat.executeFlow(context, flow);
    }
}
