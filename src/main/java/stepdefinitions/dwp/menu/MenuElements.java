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
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MenuElements extends NavigationElements {

    @Before("@SMOKE, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Available left menu items are:$")
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

    @And("^Available top menu items are:$")
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

    @When("^Top menu item is ([^\"]*)$")
    public void clickTopMenuItem(String tabName) throws Throwable {
        DwpTopMenu tm = new DwpTopMenu(webDriver);
        tm.findAndClickTopMenu(tabName);
    }

    @When("^Left menu is ([^\"]*)$")
    public void clickLeftMenuItem(String tabName) throws Throwable {
        DwpLeftMenu lm = new DwpLeftMenu(webDriver);
        lm.clickOnLeftElemet(tabName.toLowerCase());
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
    @After("@SMOKE, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
