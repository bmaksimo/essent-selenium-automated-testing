package stepdefinitions.dwp.menu;

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

    @Before("@SMOKE, @QUOTE, @MENU, @FILTER")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Available Left Menu items are:$")
    public void checkMenuItemLink(DataTable menuItems) throws Throwable {
        List<String> failedMenuItems = menuItems.asList(String.class).stream()
            .filter(
            menuItem -> {
                Map<String, String> jsOptions = new HashMap<>();
                jsOptions.put("menu", "left");
                jsOptions.put("item", menuItem);
                return !executeJavascriptTest("TrCheckMenuItem", jsOptions);
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
                    Map<String, String> options = new HashMap<>();
                    options.put("item", menuItem);
                    options.put("menu", "top");
                    return !executeJavascriptTest("TrCheckMenuItem", options);
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
        clickLeftMenuItem(itemName);
    }

    @When("^Top Tab is ([^\"]*)$")
    public void check_top_menu_item(String itemName) throws Throwable {
        clickTopMenuItem(itemName);
    }

    @And("^The following Plus menu items are available at positions:$")
    public void checkPositionsOfPlusMenuItems(DataTable plusItems) throws Throwable {
        final List<String> failedItems = plusItems.asList(Item.class)
            .stream()
            .filter(
             item -> {
                 Map<String, Object> options = new HashMap<>();
                 options.put("item", item.getItem());
                 options.put("position", item.getPosition());
                 return !executeJavascriptTest("TrPlusMenuHasItem", options);
             })
            .map(item -> {
                return item.getItem();
            }).collect(Collectors.toList());
        assertThat("The following left menu items were not available: "
            + StringUtils.join(failedItems, ", "), failedItems.isEmpty(), is(true));
    }

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @FILTER")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
