package stepdefinitions.dwp.top_actions;

import com.billinghouse.javascript.model.options.TrMenuHasLinkIdOptions;
import com.billinghouse.javascript.testrunner.dwp.menu.MenuTests;
import com.essent.testing.dwp.menu.model.DwpLeftMenu;
import com.essent.testing.dwp.menu.model.TopMenuItems;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import stepdefinitions.dwp.NavigationElements;
import stepdefinitions.dwp.tables.plus.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TopActions extends NavigationElements {

    @Before("@SMOKE, @QUOTE, @MENU, @DWP_SETUP, @FILTER, @RENEWAL")
    public void SetupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Top Action is ([^\"]*)$")
    public void checkTopAction(String action) throws Throwable {
        clickTopAction(action);
    }

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @DWP_SETUP, @FILTER, @RENEWAL")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
