package stepdefinitions.dwp.javascript;

import com.billinghouse.javascript.model.Data;
import com.billinghouse.javascript.model.options.TrMenuHasLinkIdOptions;
import com.billinghouse.javascript.testrunner.dwp.menu.MenuTests;
import com.billinghouse.javascript.testrunner.dwp.views.TitleTests;
import com.essent.testing.dwp.DwpScenario;
import com.essent.testing.dwp.menu.model.DwpLeftMenu;
import com.essent.testing.dwp.menu.model.TopMenuItems;
import com.essent.testing.util.ResourceUtils;
import com.google.gson.Gson;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.apache.commons.io.FileUtils;

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

        // new preferred simpler syntax: Test Upper Item - Markettransactions - Dashboard
        Map jsOptions = new HashMap();
        jsOptions.put("menu", "mainMenu");
        jsOptions.put("linkId", "sales-marketing-link");
        Map testResult = executeJavascriptMethod("TrMenuHasLinkId", jsOptions);

        // old complicated syntax: Test Upper Item - Markettransactions - Dashboard
        TrMenuHasLinkIdOptions options = new TrMenuHasLinkIdOptions();
        options.setLinkId(TopMenuItems.MARKETTRANSACTIONS_DASHBOARD.getLink());
        options.setMenu("subMenu");
        Map result = executeJavascriptMethod("TrMenuHasLinkId", options);
        assertThat(executeJavascriptTest("TrMenuHasLinkId",
            options), is(true));

        // get application state
        result = executeJavascriptMethod("TrGetApplicationState", null);

        //Evaluate XPATH
        final String TOP_MENU_ITEM_QUERY = "//div[@class='top-menu']/sub-menu/sub-menu-link/a[@id='{link}']";
        Map<String, String> xpathOptions = new HashMap<>();
        xpathOptions.put("xpath", TOP_MENU_ITEM_QUERY.replace("{link}", TopMenuItems.MARKETTRANSACTIONS_DASHBOARD.getLink()));
        result = executeJavascriptMethod("TrEvaluateXpath", xpathOptions);



        // deprecated: function based

        //Test Left Item - Billing
        DwpLeftMenu item = DwpLeftMenu.get("Billing");
        assertThat(executeJsTest(MenuTests.LEFT_MENU_ITEM_TEST.getTest(),  item.getMenuItemLink()), is(true));

        //Test Contents Title
        int sec = 5;
        String title = "Quotation - Group tasks";
        assertThat(executeJsTest(TitleTests.CONTENT_PAGE_CONTAINS_TITLE.getTest(), ""+sec, title), is(true));

        //Test index of column
        Map map = executeJsMethod(MenuTests.GET_COLUMN_INDEX.getTest(), "Name & Type & Subtype", "" + 2);

        //Test Plus
        String plusItem = "BILLING";
        String pluPosition = "4";
        assertThat(executeJsTest(MenuTests.PLUS_MENU_ITEM_TEST.getTest(), plusItem, pluPosition), is(true));
    }
}
