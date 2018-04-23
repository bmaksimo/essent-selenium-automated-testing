package stepdefinitions.dwp;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.DwpScenario;
import com.essent.testing.dwp.menu.model.*;
import cucumber.api.DataTable;
import org.apache.commons.lang3.StringUtils;
import stepdefinitions.dwp.tables.DwpLeftMenuConverter;
import stepdefinitions.dwp.tables.TopMenuItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public abstract class NavigationElements extends DwpScenario  {
    private static final String ITEM_PARAM = "{item}";
    private static final String LINK_PARAM = "{link}";
    private static final String LEFT_ITEM_XPATH = "//a[@id='{item}']";


    private static final String TOP_MENU_ITEM_ELEMENT = "TOP_MENU_{item}_ITEM_ELEMENT";
    private static final String TOP_MENU_ITEM_QUERY = "//div[@class='top-menu']/sub-menu/sub-menu-link/a[@id='{link}']";

    protected static Map<DwpLeftMenu, TopMenu> menu = new HashMap<>();

    static {

        menu.put(DwpLeftMenu.SALES_MARKETING, SalesMarketingMenu.getTopMenu());
        menu.put(DwpLeftMenu.CONTRACTING_SWITCHING, ContractingSwitchingUpperMenu.getTopMenu());
        menu.put(DwpLeftMenu.BILLING, BillingUpperMenu.getTopMenu());
        menu.put(DwpLeftMenu.CREDIT_MANAGEMENT, CreditManagementUpperMenu.getTopMenu());
        menu.put(DwpLeftMenu.FINANCE, FinanceUpperMenu.getTopMenu());
        menu.put(DwpLeftMenu.SERVICE, ServiceUpperMenu.getTopMenu());
        menu.put(DwpLeftMenu.ESS, EssUpperMenu.getTopMenu());
        menu.put(DwpLeftMenu.TASKS, TasksUpperMenu.getTopMenu());
        menu.put(DwpLeftMenu.ADMIN, AdminUpperMenu.getTopMenu());
        menu.put(DwpLeftMenu.TEST, TestUpperMenu.getTopMenu());
    }


    private class GoToLeftItem implements Predicate<DwpLeftMenu> {

        @Override
        public boolean test(DwpLeftMenu item) {
            String query = LEFT_ITEM_XPATH.replace(ITEM_PARAM, item.getMenuItemLink());
            Model.Execution execution = newExecution().element("DWP_LEFT_MENU_ITEM",
                new Model.Element().search("XPATH").query(query));
            execution.flow()
                .step(new Model.Step().action(Action.CLICK).element("DWP_LEFT_MENU_ITEM"))
                .step(new Model.Step().action(Action.SLEEP).sleepInMillis(2500));
            return execute(webDriver.getDriver(), execution);
        }
    }

    private class FindTopMenuItem implements Predicate<TopMenu.Item> {
        @Override
        public boolean test(TopMenu.Item item) {
            String elementKey = TOP_MENU_ITEM_ELEMENT.replace(ITEM_PARAM, item.getLabel());
            String elementQuery = TOP_MENU_ITEM_QUERY.replace(LINK_PARAM, item.getLink());
            Model.Execution execution = newExecution().element(elementKey, new Model.Element().search("XPATH").query(elementQuery));
            Model.Step step = new Model.Step().action(Action.REQUIRE).element(elementKey).withExecution(execution);
            return executeStep(webDriver.getDriver(), step);
        }
    }


    private class VisitTopItem implements Predicate<TopMenu.Item> {

        @Override
        public boolean test(TopMenu.Item item) {
            String elementKey = TOP_MENU_ITEM_ELEMENT.replace(ITEM_PARAM, item.getLabel());
            String elementQuery = TOP_MENU_ITEM_QUERY.replace(LINK_PARAM, item.getLink());
            Model.Execution execution = newExecution().element(elementKey,
                new Model.Element().search("XPATH").query(elementQuery));
            execution.flow()
                .step(new Model.Step().action(Action.CLICK).element(elementKey))
                .step(new Model.Step().action(Action.SLEEP).sleepInMillis(2500));
            return execute(webDriver.getDriver(), execution);
        }
    }

    protected void visitLeftMenuItems(DataTable menuItems) throws Throwable {

        List<DwpLeftMenuConverter> leftMenuItems = menuItems.asList(DwpLeftMenuConverter.class);
        List<DwpLeftMenu> failedToVisitItems = leftMenuItems.stream()
            .map(converter -> {
                return converter.toMenuEnum();
            }).filter(
                new GoToLeftItem().negate()).collect(Collectors.toList());
        boolean success = failedToVisitItems.isEmpty();
        assertThat(String.format("The following left menu items were not visited: %s", StringUtils.join(failedToVisitItems)),
            success, is(true));
    }

    protected void visitLeftMenuItem(DwpLeftMenu menuItem) throws Throwable {

        GoToLeftItem goToLeftItem = new GoToLeftItem();
        boolean success = goToLeftItem.test(menuItem);
        assertThat(String.format("Left menu item %s was not visited", menuItem),
            success, is(true));
    }

    protected void i_Click_on_Top_Menu_Items(DwpLeftMenu leftMenuSelection, DataTable menuItems) throws Throwable {

        TopMenu topMenu = menu.get(leftMenuSelection);
        final List<TopMenu.Item> failedToVisitItems = menuItems.asList(TopMenuItem.class).
            stream().
            map(item -> {
                return topMenu.getItem(item.getName());
            }).
            filter(new VisitTopItem().negate()).
            collect(Collectors.toList());
        boolean success = failedToVisitItems.isEmpty();
        assertThat(String.format("The following items that were expected for " +
                leftMenuSelection +
                " were not visited: %s", StringUtils.join(failedToVisitItems)),
            success, is(true));
    }

    protected void i_Click_on_Top_Menu_Item(DwpLeftMenu leftMenuSelection, String menuItem) throws Throwable {

        TopMenu topMenu = menu.get(leftMenuSelection);
        TopMenu.Item topMenuItem = topMenu.getItem(menuItem);
        boolean success = new VisitTopItem().test(topMenuItem);
        assertThat(String.format("Top menu utem %s item of %s left menu item was not visited", menuItem, leftMenuSelection.name()),
            success, is(true));
    }

    protected void verifyTopMenu(DwpLeftMenu leftMenuSelection) throws Throwable {
        TopMenu topMenu = menu.get(leftMenuSelection);
        List<TopMenu.Item> items = topMenu.items();
        final List<TopMenu.Item> failedToFindItems = items.
            stream().
            filter(new FindTopMenuItem().negate()).
            collect(Collectors.toList());
        boolean success = failedToFindItems.isEmpty();
        assertThat(String.format("The following items that were expected for " +
                leftMenuSelection +
                " were not available: %s", StringUtils.join(failedToFindItems)),
            success, is(true));
    }
}
