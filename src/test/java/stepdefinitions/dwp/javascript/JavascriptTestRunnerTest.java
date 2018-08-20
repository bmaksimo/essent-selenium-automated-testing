package stepdefinitions.dwp.javascript;

import com.essent.testing.dwp.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;


public class JavascriptTestRunnerTest extends DwpScenario {

    @Before("@SMOKE")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Find web element by Xpath \"([^\"]*)\"$")
    public void findWebElementByXpath(String query) throws Throwable {
        Map<String, String> xpathOptions = new HashMap<>();
        xpathOptions.put("xpath", query);
        Assert.assertTrue(executeJavascriptTest("TrEvaluateXpath", xpathOptions));
    }


    @Override
    @After("@SMOKE")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
