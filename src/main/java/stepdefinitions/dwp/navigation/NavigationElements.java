package stepdefinitions.dwp.navigation;


import com.essent.testing.dwp.pageobject.impl.navigation.DwpPlusMenu;
import com.essent.testing.dwp.pageobject.impl.navigation.TopActionsPageImpl;
import com.essent.testing.dwp.pageobject.navigation.TopActionsPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.numericValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public abstract class NavigationElements extends DwpScenario {


    private class ClickTopAction implements Predicate<String> {
        @Override
        public boolean test(String name) {
            TopActionsPage topActions = new TopActionsPageImpl();
            return topActions.executeTopAction(name);
        }
    }

    private class ClickPlusAction implements Predicate<String> {
        @Override
        public boolean test(String path) {
            Map<String, String> options = new HashMap<>();
            options.put("path", path);
            boolean success = executeJavascriptTest("TrPlusMenuSelectAction", options);
            return success;
        }
    }

    private class ClickCockpitItem implements Predicate<String> {
        @Override
        public boolean test(String item) {
            Map<String, String> options = new HashMap<>();
            options.put("item", item);
            boolean success = executeJavascriptTest("TrGetCockpitItem", options);
            return success;
        }
    }

    private class ClickListPlusAction implements Predicate<String> {
        @Override
        public boolean test(String item) {
            Map<String, String> options = new HashMap<>();
            options.put("item", item);
            boolean success = executeJavascriptTest("TrListPlusMenuAction", options);
            return success;
        }
    }

    private class ClickTopArrowButton implements Predicate<String> {
        @Override
        public boolean test(String arrow) {
            Map<String, String> options = new HashMap<>();
            options.put("arrow", arrow);
            boolean success = executeJavascriptTest("TrArrowAction", options);
            return success;
        }
    }

    public class ClickConfirm implements Predicate<String> {
        @Override
        public boolean test(String name) {
            boolean success = executeJavascriptTest("TrSelectButton", "");
            return success;
        }
    }

    private class ClickDashboardMenu implements Predicate<String> {
        @Override
        public boolean test(String menu) {
            Map<String, Object> options = new HashMap<>();
            options.put("menu", menu);
             return executeJavascriptTest("TrClickDashboardMenuButton", options);
        }
    }


    public class ValidateCustomer implements Predicate<Map> {
        @Override
        public boolean test(Map name) {
            return executeJavascriptTest("TrFindCustomer", name);
        }
    }

    public class SearchCustomer implements Predicate<String> {
        @Override
        public boolean test(String name) {
            return executeJavascriptTest("TrSearchCustomer", name);
        }
    }

    protected int extractNumericValue(String ordinal) {
        return numericValue(ordinal);
    }

    protected void clickTopAction(String name) {
        boolean success = new ClickTopAction().test(name);
        assertThat(String.format("Top Menu item %s was not available.", name),
            success, is(true));
    }

    protected void clickTopArrow(String arrow)  {
        boolean success = new ClickTopArrowButton().test(arrow);
        assertThat(String.format("Top Arrow %s is undefined.", arrow),
            success, is(true));
    }

    protected void clickPlusAction(String path) {
        DwpPlusMenu plusMenu = new DwpPlusMenu();
        boolean success = plusMenu.executeAction(path);
        assertThat(String.format("Plus Menu Path %s undefined.", path),
            success, is(true));
    }

    protected void clickCockpitItem(String item) {
        boolean success = new ClickCockpitItem().test(item);
        assertThat(String.format("Cockpit item %s was not available.", item),
            success, is(true));
    }

    protected void clickListPlusAction(String item) {
        FluentWait<ClickListPlusAction> waiter = waiter(new ClickListPlusAction(), 20, 2);
        waiter.withMessage(String.format("List Plus Action \"%s\" is undefined or disabled.", item));
        waiter.until((ClickListPlusAction action) -> action.test(item));
    }

    protected void clickDashboardMenu(String menu) {
        FluentWait<ClickDashboardMenu> waiter = waiter(new ClickDashboardMenu(), 20, 2);
        waiter.withMessage(String.format("Dashboard Menu  \"%s\" is undefined.", menu));
        waiter.until((ClickDashboardMenu dashboardMenu)-> dashboardMenu.test(menu));
    }
}
