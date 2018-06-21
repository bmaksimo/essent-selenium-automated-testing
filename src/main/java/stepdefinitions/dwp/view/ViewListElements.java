package stepdefinitions.dwp.view;

import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import stepdefinitions.dwp.NavigationElements;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ViewListElements extends NavigationElements {


    private class CheckViewListHeader implements Predicate<String> {
        @Override
        public boolean test(String header) {
            int sec = 5;
            Map<String, Object> options = new HashMap<>();
            options.put("schedule_seconds", sec);
            options.put("header", header);
            return executeJavascriptTest("TrCheckViewListHeader", options);
        }
    }

    private class ClickTableCellUrl implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrClickTableCellUrl", options);
        }
    }

    @Before("@SMOKE, @QUOTE, @MENU, @DWP_SETUP, @FILTER, @RENEWAL")
    public void SetupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^View List Header is \"([^\"]*)\"$")
    public void checkViewListHeader(String header) throws Throwable {
        boolean success = new CheckViewListHeader().test(header);
        assertThat(String.format("View list did not contain heeder '%s'", header),
            success, is(true));
    }

    @When("^Click on View List at ([^\"]*) row and \"([^\"]*)\" column$")
    public void clickOnViewListAtRowAndColumn(String ordinal, String column) throws Throwable {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        Map<String, String> columnIndexListOptions = new HashMap<>();
        columnIndexListOptions.put("column", column);
        columnIndexListOptions.put("index", rowIndex);
        boolean success = new ClickTableCellUrl().test(columnIndexListOptions);
        assertThat(String.format("View list did not contain URL at row %s header '%s'", ordinal, column),
            success, is(true));
    }

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @DWP_SETUP, @FILTER, @RENEWAL")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}

