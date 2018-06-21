package stepdefinitions.dwp.javascript;

import com.billinghouse.javascript.model.Data;
import com.essent.testing.dwp.DwpScenario;
import com.essent.testing.dwp.menu.model.DwpLeftMenu;
import com.essent.testing.dwp.menu.model.TopMenuItems;
import com.essent.testing.util.ResourceUtils;
import com.google.gson.Gson;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class JavascriptTestRunnerTest extends DwpScenario {

    @Before("@SMOKE")
    public void SetupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }

    @Given("^I deserialize DWP Test Data Json from file \"([^\"]*)\"$")
    public void testDwpTestData(String filePath) throws Throwable {
        logger().info("I read DwpTest Json");
        String path = ResourceUtils.toPath(filePath);
        File document = new File(path);
        String jsonAsString = FileUtils.readFileToString(document, Charset.forName("UTF-8"));
        Gson json = new Gson();
        Data data = json.fromJson(jsonAsString, Data.class);
        logger().info(data.getData());
        assertThat("data was not initialised", data, is(notNullValue()));
    }

    @Override
    @After("@SMOKE")
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @When("^I smoke test all Javascript functions$")
    public void iRunAllJavascriptFunctions() throws Throwable {
        testTrGetApplicationState();
        testTrEvaluateXpath();
    }

    @Then("^Menu ([^\"]*) has link id ([^\"]*)$")
    public void menuHasLinkId(String menu, String linkId) throws Throwable {
        Map<String, String> jsOptions = new HashMap<>();
        jsOptions.put("menu", menu);
        jsOptions.put("linkId", linkId);
        boolean success = executeJavascriptTest("TrMenuHasLinkId", jsOptions);
        assertThat(String.format("%s hasn't link id %s", menu, linkId), success, is(true));
    }

    private void testTrGetApplicationState() {
        Assert.assertTrue(executeJavascriptTest("TrGetApplicationState", null));
    }

    private void testTrEvaluateXpath() {
        final String TOP_MENU_ITEM_QUERY = "//div[@class='top-menu']/sub-menu/sub-menu-link/a[@id='Market Transactions']";
        Map<String, String> xpathOptions = new HashMap<>();
        xpathOptions.put("xpath", TOP_MENU_ITEM_QUERY.replace("{link}", TopMenuItems.MARKETTRANSACTIONS_DASHBOARD.getLink()));
        Assert.assertTrue(executeJavascriptTest("TrEvaluateXpath", xpathOptions));
    }
}
