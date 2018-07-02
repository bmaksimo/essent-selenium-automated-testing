package stepdefinitions.dwp.javascript;

import com.essent.testing.dwp.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class JavascriptTestRunnerTest extends DwpScenario {

    @Before("@SMOKE")
    public void SetupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^I smoke test all Javascript functions$")
    public void iRunAllJavascriptFunctions() throws Throwable {
        testTrGetApplicationState();
    }

    @Then("^Menu ([^\"]*) has link id ([^\"]*)$")
    public void menuHasLinkId(String menu, String linkId) throws Throwable {
        Map<String, String> jsOptions = new HashMap<>();
        jsOptions.put("menu", menu);
        jsOptions.put("linkId", linkId);
        boolean success = executeJavascriptTest("TrMenuHasLinkId", jsOptions);
        assertThat(String.format("%s hasn't link id %s", menu, linkId), success, is(true));
    }

    @And("^Find web element by Xpath \"([^\"]*)\"$")
    public void findWebElementByXpath(String query) throws Throwable {
        Map<String, String> xpathOptions = new HashMap<>();
        xpathOptions.put("xpath", query);
        Assert.assertTrue(executeJavascriptTest("TrEvaluateXpath", xpathOptions));
    }

    private void testTrGetApplicationState() {
        Assert.assertTrue(executeJavascriptTest("TrGetApplicationState", null));
    }

    @Override
    @After("@SMOKE")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
