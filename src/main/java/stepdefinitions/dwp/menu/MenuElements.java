package stepdefinitions.dwp.menu;

import com.essent.testing.dwp.pageobject.impl.navigation.DwpLeftMenu;
import com.essent.testing.dwp.pageobject.impl.navigation.DwpTopMenu;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import stepdefinitions.dwp.navigation.NavigationElements;
import stepdefinitions.dwp.tables.plus.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MenuElements extends NavigationElements {

    @Before("@SMOKE, @E2E_B2C, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Top menu item is ([^\"]*)$")
    public void clickTopMenuItem(String tabName) throws Throwable {
        DwpTopMenu tm = new DwpTopMenu(webDriver);
        tm.findAndClickTopMenu(tabName);
    }

    @When("^Left menu is ([^\"]*)$")
    public void clickLeftMenuItem(String tabName) throws Throwable {
        DwpLeftMenu lm = new DwpLeftMenu(webDriver);
        lm.clickOnLeftElemet(tabName);
    }

    @Override
    @After("@SMOKE, @E2E_B2C, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
