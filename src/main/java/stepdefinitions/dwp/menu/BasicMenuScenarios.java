package stepdefinitions.dwp.menu;

import com.billinghouse.javascript.model.options.TrMenuHasLinkIdOptions;
import com.billinghouse.javascript.testrunner.dwp.menu.MenuTests;
import com.essent.testing.dwp.menu.model.DwpLeftMenu;
import com.essent.testing.dwp.menu.model.TopMenuItems;
import cucumber.api.DataTable;
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

public class BasicMenuScenarios extends NavigationElements {

    @Before("@SMOKE, @QUOTE, @MENU, @DWP_SETUP, @FILTER")
    public void SetupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Available Left Menu items are:$")
    public void checkMenuItemLink(DataTable menuItems) throws Throwable {
        List<String> failedMenuItems = menuItems.asList(String.class).stream()
            .filter(
            menuItem -> {
                return !executeJsTest(MenuTests.LEFT_MENU_ITEM_TEST.getTest(),
                        DwpLeftMenu.get(menuItem).getMenuItemLink());
            })
            .collect(Collectors.toList());
        assertThat("The following left menu items were not available: "
            + StringUtils.join(failedMenuItems, ", "), failedMenuItems.isEmpty(), is(true));

    }

    @And("^Available Top Menu Items are:$")
    public void checkAvailableTopItems(DataTable menuItems) throws Throwable {
        final List<String> failedUpperItems = menuItems.asList(String.class)
            .stream()
            .filter(
                menuItem -> {
                    TrMenuHasLinkIdOptions options = new TrMenuHasLinkIdOptions();
                    options.setLinkId(TopMenuItems.get(menuItem).getLink());
                    options.setMenu("subMenu");
                    return !executeJavascriptTest("TrMenuHasLinkId", options);
                })
            .collect(Collectors.toList());
        assertThat("The following left menu items were not available: "
            + StringUtils.join(failedUpperItems, ", "), failedUpperItems.isEmpty(), is(true));
    }

    @When("^Left Menu Item is ([^\"]*)$")
    public void clickLeftMenuItem(String tabName) throws Throwable {
        super.visitLeftMenuItem(tabName);
    }

    @When("^Top Menu Item is ([^\"]*)$")
    public void clickTopMenuItem(String tabName) throws Throwable {
        super.visitTopMenuItem(tabName);
    }

    @When("^Left Tab is ([^\"]*)$")
    public void check_left_menu_item(String itemName) throws Throwable {
        executeJsTest(MenuTests.GET_LEFT_MENU.getTest(), itemName);
    }

    @When("^Top Tab is ([^\"]*)$")
    public void check_top_menu_item(String itemName) throws Throwable {
        executeJsTest(MenuTests.GET_TOP_TAB.getTest(), itemName);
    }

    @Then("^View title is '(.*)'$")
    public void isViewTitle(String title) throws Throwable {
        int sec = 1;
        Map<String, String> parameters = new HashMap<>();
        parameters.put("seconds", "" + sec);
        parameters.put("title", title);
        boolean result =
        executeJavascriptTest("TrContentPageContainsTitle", parameters);
        assertThat("View title: " + title + " did not appear after " + sec + " seconds",
            result, is(true));
    }

    @And("^The following Plus menu items are available at positions:$")
    public void thesePlusMenuItemsAreAvailableAtTheFollowingPositions(DataTable plusItems) throws Throwable {
        final List<String> failedItems = plusItems.asList(Item.class)
            .stream()
            .filter(
             item -> {
                 return !executeJsTest(MenuTests.PLUS_MENU_ITEM_TEST.getTest(), item.getItem(), ""+item.getPosition());
             })
            .map(item -> {
                return item.getItem();
            }).collect(Collectors.toList());
        assertThat("The following left menu items were not available: "
            + StringUtils.join(failedItems, ", "), failedItems.isEmpty(), is(true));
    }

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @DWP_SETUP, @FILTER")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
