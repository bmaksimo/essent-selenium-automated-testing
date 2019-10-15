package stepdefinitions.dwp.navigation;


import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.navigation.DwpPlusMenu;
import com.essent.testing.dwp.pageobject.impl.navigation.TopActionsPageImpl;
import com.essent.testing.dwp.pageobject.navigation.TopActionsPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry.*;
import static org.hamcrest.Matchers.is;


public abstract class NavigationElements extends DwpScenario {


    private class ClickTopAction implements Predicate<String> {
        @Override
        public boolean test(String action) {
            TopActionsPage topActions = new TopActionsPageImpl();
            return topActions.executeTopAction(action);
        }

        private boolean testWithFixedTime(String action, int waitingTime) {
            TopActionsPage topActions = new TopActionsPageImpl();
            return topActions.executeTopActionWithFixedWait(action, waitingTime);
        }
    }


    private class ClickCockpitItem implements Predicate<String> {
        @Override
        public boolean test(String item) {
            seleniumDriver.waitForRequestsToFinish();
            Map<String, String> options = new HashMap<>();
            options.put("item", item);
            return executeJavascriptTest(JS_TR_GET_COCKPIT_ITEM, options);
        }
    }

    private class ClickListPlusAction implements Predicate<String> {
        @Override
        public boolean test(String item) {
            seleniumDriver.waitForRequestsToFinish();
            Map<String, String> options = new HashMap<>();
            options.put("item", item);
            return executeJavascriptTest(JS_TR_LIST_PLUS_MENU_ACTION, options);
        }
    }

    private class ClickTopArrowButton implements Predicate<String> {
        @Override
        public boolean test(String arrow) {
            seleniumDriver.waitForRequestsToFinish();
            Map<String, String> options = new HashMap<>();
            options.put("arrow", arrow.toLowerCase());
            return executeJavascriptTest(JS_TR_ARROW_ACTION, options);
        }

        public boolean testNow(String arrow) {
            Map<String, String> options = new HashMap<>();
            options.put("arrow", arrow);
            return executeJavascriptTestImmediately(JS_TR_ARROW_ACTION, options, true);
        }
    }

    private class ClickDashboardMenu implements Predicate<String> {
        @Override
        public boolean test(String menu) {
            Map<String, Object> options = new HashMap<>();
            options.put("menu", menu);
            return executeJavascriptTest(JS_TR_CLICK_DASHBOARD_MENU_BUTTON, options);
        }

        public boolean testNow(String menu) {
            Map<String, Object> options = new HashMap<>();
            options.put("menu", menu);
            return executeJavascriptTestImmediately(JS_TR_CLICK_DASHBOARD_MENU_BUTTON, options, true);
        }
    }

    public class SearchCustomer implements Predicate<String> {
        @Override
        public boolean test(String name) {
            seleniumDriver.waitForRequestsToFinish();
            return executeJavascriptTest(JS_TR_SEARCH_CUSTOMER, name);
        }
    }

    protected void clickTopAction(String name) {
        seleniumDriver.waitForRequestsToFinish();
        boolean success = new ClickTopAction().test(name);
        assertThat(String.format("Top Menu item %s was not available.", name),
            success, is(true));
    }

    protected void clickTopAction(String name, int waitingTime) {
        seleniumDriver.waitForRequestsToFinish();
        boolean success = new ClickTopAction().testWithFixedTime(name, waitingTime);
        assertThat(String.format("Top Menu item %s was not available.", name),
            success, is(true));
    }

    protected void clickTopArrow(String arrow) {
        seleniumDriver.waitForRequestsToFinish();
        boolean success = new ClickTopArrowButton().test(arrow);
        assertThat(String.format("Top Arrow %s is undefined.", arrow),
            success, is(true));
    }

    protected void clickTopArrow(String arrow, int waitingTime) {
        Sleeper.sleepTightInSeconds(waitingTime);
        boolean success = new ClickTopArrowButton().testNow(arrow);
        assertThat(String.format("Top Arrow %s is undefined.", arrow),
            success, is(true));
    }

    protected void clickPlusAction(String path) {
        seleniumDriver.waitForRequestsToFinish();
        DwpPlusMenu plusMenu = new DwpPlusMenu();
        boolean success = plusMenu.executeAction(path);
        assertThat(String.format("Plus Menu Path %s undefined.", path),
            success, is(true));
    }

    protected void clickCockpitItem(String item) {
        seleniumDriver.waitForRequestsToFinish();
        boolean success = new ClickCockpitItem().test(item);
        assertThat(String.format("Cockpit item %s was not available.", item),
            success, is(true));
    }

    protected void clickListPlusAction(String item) {
        FluentWait<ClickListPlusAction> waiter = waiter(new ClickListPlusAction(), 20, 1);
        waiter.withMessage(String.format("List Plus Action \"%s\" is undefined or disabled.", item));
        waiter.until((ClickListPlusAction action) -> action.test(item));
    }

    protected void clickDashboardMenu(String menu) {
        seleniumDriver.waitForRequestsToFinish();
        FluentWait<ClickDashboardMenu> waiter = waiter(new ClickDashboardMenu(), 120, 1);
        waiter.withMessage(String.format("Dashboard Menu  \"%s\" is undefined.", menu));
        waiter.until((ClickDashboardMenu dashboardMenu) -> dashboardMenu.test(menu));
    }

    protected void clickDashboardMenu(String menu, int waitingTime) {
        Sleeper.sleepTightInSeconds(waitingTime);
        new ClickDashboardMenu().testNow(menu);
    }

    protected boolean isAlertPresent() {
        try {
            seleniumDriver.getDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException ex) {
            return false;
        }
    }
}
