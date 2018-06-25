package stepdefinitions.dwp;

import com.essent.testing.dwp.DwpScenario;
import cucumber.api.DataTable;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public abstract class NavigationElements extends DwpScenario  {

    private class ClickLeftTab implements Predicate<String> {
        @Override
        public boolean test(String label) {
            String kebabCaseLabel = label.replaceAll("\\s", "-").toLowerCase();
            Map<String, String> options = new HashMap<>();
            options.put("menu", kebabCaseLabel);
            boolean success = executeJavascriptTest("TrGetLeftMenu", options);
            return success;
        }
    }

    private class ClickTopAction implements Predicate<String> {
        @Override
        public boolean test(String name) {
            Map<String, String> options = new HashMap<>();
            options.put("name", name);
            boolean success = executeJavascriptTest("TrGetTopAction", options);
            return success;
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

    private class ClickTopArrowButton implements Predicate<String> {
        @Override
        public boolean test(String arrow) {
            Map<String, String> options = new HashMap<>();
            options.put("arrow", arrow);
            boolean success = executeJavascriptTest("TrArrowAction", options);
            return success;
        }
    }

    private class VisitTopItem implements Predicate<String> {
        @Override
        public boolean test(String label) {
            Map<String, String> options = new HashMap<>();
            options.put("label", label);
            boolean success = executeJavascriptTest("TrGetTopTab", options);
            return success;
        }
    }

    protected int parseOrdinal(String ordinal) {
        return Integer.parseInt(ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", ""));
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

    protected void clickTopAction(String name) {
        boolean success = new ClickTopAction().test(name);
        assertThat(String.format("Top Menu item %s was not available.", name),
            success, is(true));
    }

    protected void clickTopArrow(String arrow) throws Throwable {
        boolean success = new ClickTopArrowButton().test(arrow);
        assertThat(String.format("Top Arrow %s is undefined.", arrow),
            success, is(true));
    }

    protected void clickPlusAction(String path) {
        boolean success = new ClickPlusAction().test(path);
        assertThat(String.format("Plus Menu Path %s undefined.", path),
            success, is(true));
    }

    protected void clickCockpitItem(String item) {
        boolean success = new ClickCockpitItem().test(item);
        assertThat(String.format("Cockpit item %s was not available.", item),
            success, is(true));
    }

}
