package stepdefinitions.dwp.menu;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.automation.autocrat.Model.Execution;
import com.billinghouse.javascript.model.options.TrMenuHasLinkIdOptions;
import com.billinghouse.javascript.testrunner.dwp.menu.MenuTests;
import com.billinghouse.javascript.testrunner.dwp.views.TitleTests;
import com.essent.testing.dwp.menu.model.DwpLeftMenu;
import com.essent.testing.dwp.menu.model.UpperMenuItems;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.impl.MainWindow;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import stepdefinitions.dwp.NavigationElements;
import stepdefinitions.dwp.tables.plus.Item;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BasicMenuScenarios extends NavigationElements {

    @Before("@SMOKE, @QUOTE, @MENU, @DWP_SETUP, @FILTER")
    public void SetupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    private static final String LEFT_MENU_ELEMENT = "LEFT_MENU_ELEMENT";
    private static final String LEFT_MENU_QUERY = ".nav main-menu";

    @Before("@DWP_LEFT_UPPER_MENU")
    public void setupTest() throws Exception {
        setUpWebDriver();
        logger().info("Before " + this.getClass().getTypeName() + " scenario");
    }

    @Given("^The DWP Main Menu also called Left Menu appeared$")
    public void verifyLeftDwpAdminMenu() throws Throwable {
        logger().info("Entering the condition");
        Execution execution = newExecution().element(LEFT_MENU_ELEMENT, new Model.Element().search("SELECTOR").query(LEFT_MENU_QUERY)).execution();
        execution.flow().step(new Model.Step().action(Action.REQUIRE).element(LEFT_MENU_ELEMENT));
        execute(webDriver.getDriver(), execution);
    }
    @When("^The following Page Object Left Menu items are available:$")
    public void checkPageObjectMenuItemLink(DataTable menuItems) throws Throwable {

        List<String> failedMenuItems = menuItems.asList(DwpLeftMenu.class).stream()
            .filter(
                menuItem -> {
                    return !executeJsTest(MenuTests.LEFT_MENU_ITEM_TEST.getTest(),
                        menuItem.getMenuItemLink());
                })
            .map(menuItem ->  {
                return menuItem.name();
            })
            .collect(Collectors.toList());

        assertThat("The following left menu items were not available: "
            + StringUtils.join(failedMenuItems, ", "), failedMenuItems.isEmpty(), is(true));
        Window window = new MainWindow(webDriver);

    }

    @When("^The following Left Menu items are available:$")
    public void checkMenuItemLink(DataTable menuItems) throws Throwable {
        List<String> failedMenuItems = menuItems.asList(DwpLeftMenu.class).stream()
            .filter(
            menuItem -> {
                return !executeJsTest(MenuTests.LEFT_MENU_ITEM_TEST.getTest(),
                        menuItem.getMenuItemLink());
            })
            .map(menuItem ->  {
            return menuItem.name();
            })
            .collect(Collectors.toList());

        assertThat("The following left menu items were not available: "
            + StringUtils.join(failedMenuItems, ", "), failedMenuItems.isEmpty(), is(true));
        Window window = new MainWindow(webDriver);

    }

    @When("^I click on the following Left Menu item:$")
    public void visitLeftMenuItems(DataTable menuItems) throws Throwable {
        super.visitLeftMenuItems(menuItems);
    }

    @When("^I click on the following Left Menu item: '(.*)'$")
    public void visitLeftMenuItem(DwpLeftMenu leftMenuSelection) throws Throwable {
        super.visitLeftMenuItem(leftMenuSelection);
    }

    @Then("^The Top Menu that is defined for \"([^\"]*)\" left menu selection is available$")
    public void verifyTopMenu(DwpLeftMenu leftMenuSelection) throws Throwable {
        super.verifyTopMenu(leftMenuSelection);
    }

    @When("^I click on the following Top Menu item that is defined for '(.*)' Left Item:$")
    public void i_Click_on_Top_Menu_Items(DwpLeftMenu leftMenuSelection, DataTable menuItems) throws Throwable {
        logger().info("Upper menu for " + leftMenuSelection);
        super.i_Click_on_Top_Menu_Items(leftMenuSelection, menuItems);
    }

    @When("^I click on '(.*)' Top Menu item$")
    public void i_Click_on_Top_Menu_Item(UpperMenuItems upperMenuItem) throws Throwable {
        logger().info("Upper menu for " + upperMenuItem);
        super.i_Click_on_Top_Menu_Item(upperMenuItem);
    }

    @When("^Left Menu Item '(.*)' is made active$")
    public void left_Menu_Item_is_made_active(DwpLeftMenu leftMenuSelection) throws Throwable {
        super.visitLeftMenuItem(leftMenuSelection);
    }

    @And("^The following Top Menu Items are available:$")
    public void the_FollowingTopMenuItemsAreAvailable(DataTable menuItems) throws Throwable {
        final List<String> failedUpperItems = menuItems.asList(UpperMenuItems.class)
            .stream()
            .filter(
            menuItem -> {
                TrMenuHasLinkIdOptions options = new TrMenuHasLinkIdOptions();
                options.setLinkId(menuItem.getLink());
                options.setMenu("subMenu");
                return !executeJavascriptTest("TrMenuHasLinkId", options);
            })
            .map(menuItem ->  {
                return menuItem.name();
            })
            .collect(Collectors.toList());
        assertThat("The following left menu items were not available: "
            + StringUtils.join(failedUpperItems, ", "), failedUpperItems.isEmpty(), is(true));
    }

    @Then("^Content page contains title '(.*)'$")
    public void contentPageContainsTitleQuotationGroupTasks(String title) throws Throwable {
        int sec = 5;
        boolean result = executeJsTest(TitleTests.CONTENT_PAGE_CONTAINS_TITLE.getTest(), ""+sec, title);
        assertThat("Title: " + title + " did not appear on content page after " + sec + " seconds",
            result, is(true));
    }

    @And("^These Plus menu items are available at the following positions:$")
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
