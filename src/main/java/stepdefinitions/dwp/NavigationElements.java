package stepdefinitions.dwp;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.DwpScenario;
import com.essent.testing.dwp.menu.model.DwpLeftMenu;
import com.essent.testing.dwp.menu.model.TopMenuItems;
import cucumber.api.DataTable;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
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

    private class ClickLeftTab implements Predicate<String> {

        @Override
        public boolean test(String label) {
            DwpLeftMenu leftTab = DwpLeftMenu.get(label);
            String query = LEFT_ITEM_XPATH.replace(ITEM_PARAM, leftTab.getMenuItemLink());
            Model.Execution execution = newExecution().element("DWP_LEFT_MENU_ITEM",
                new Model.Element().search("XPATH").query(query));
            execution.flow()
                .step(new Model.Step().action(Action.CLICK).element("DWP_LEFT_MENU_ITEM"))
                .step(new Model.Step().action(Action.SLEEP).sleepInMillis(2500));
            return execute(webDriver.getDriver(), execution);
        }
    }


    private class VisitTopItem implements Predicate<String> {
        @Override
        public boolean test(String label) {
            TopMenuItems topTab = TopMenuItems.get(label);
            String elementKey = TOP_MENU_ITEM_ELEMENT.replace(ITEM_PARAM, topTab.getLabel());
            String elementQuery = TOP_MENU_ITEM_QUERY.replace(LINK_PARAM, topTab.getLink());
            Model.Execution execution = newExecution().element(elementKey,
                new Model.Element().search("XPATH").query(elementQuery));
            execution.flow()
                .step(new Model.Step().action(Action.CLICK).element(elementKey))
                .step(new Model.Step().action(Action.SLEEP).sleepInMillis(2500));
            return execute(webDriver.getDriver(), execution);
        }
    }

    protected void visitLeftMenuItems(DataTable menuItems) throws Throwable {

        List<String> leftMenuItems = menuItems.asList(String.class);
        List<String> failedToVisitTabs = leftMenuItems.stream().filter(
                new ClickLeftTab().negate()).collect(Collectors.toList());
        boolean success = failedToVisitTabs.isEmpty();
        assertThat(String.format("The following left menu items were not visited: %s", StringUtils.join(failedToVisitTabs)),
            success, is(true));
    }

    protected void visitLeftMenuItem(String leftTab) throws Throwable {
        ClickLeftTab goToLeftItem = new ClickLeftTab();
        boolean success = goToLeftItem.test(leftTab);
        assertThat(String.format("Left menu item %s was not visited", leftTab),
            success, is(true));
    }

    protected void visitTopMenuItem(String label) throws Throwable {
        boolean success = new VisitTopItem().test(label);
        assertThat(String.format("Top Menu item %s was not available.", label),
            success, is(true));
    }


}
