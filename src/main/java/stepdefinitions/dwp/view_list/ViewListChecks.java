package stepdefinitions.dwp.view_list;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.list_view.ViewListTestObject;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts.ContractPage;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.CucumberException;
import io.cucumber.datatable.DataTable;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import stepdefinitions.dwp.b2b.Marketberichten;
import stepdefinitions.dwp.navigation.NavigationElements;
import stepdefinitions.dwp.plus.PlusActions;

import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry.*;
import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkTimeBetween;
import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.getFormattedEnd;
import static com.billinghouse.test_automation.util.dsl.NumericUtil.checkAmount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ViewListChecks extends NavigationElements {

    //TODO Remove locale-specific hard code.
    // The project must support official Belgian languages.
    // Locale-specific elements of web element locators must be parameterized.
    // This is basic rule!
    private static final String MARKET_MESSAGES = "Marktberichten";
    private static final String MARKET_MESSAGES_VIEW_LIST = "MarketTransactionsOnAccount";
    private static final String BILLING_CUSTOMER = "Billing customer";
    private static final String BILLING_CUSTOMER_VIEW_LIST = "BillingCustomerOnaccount";
    private static final String PLUS_ACTION = "Plus ActionDTO";
    private static final String REPLACEMENT_KEY1 = "REPLACEMENT_KEY1";
    private static final String TRANSACTIONS_BLOCKED_CHECKMARK = "//list[@list-key='TransactionsOnAccount']//td[@class='list__cell cell__text'][${" + REPLACEMENT_KEY1 + "}]//div[@class='customer__status icon-checkmark']";

    private class ViewListNavigation {
        void goToLink(String linkText) {
            seleniumDriver.waitForRequestsToFinish();
            WebElement link = seleniumDriver.findElement(By.linkText(linkText));
            link.click();
        }
    }

    private class CheckViewListHeader implements Predicate<String> {
        @Override
        public boolean test(String header) {
            int sec = 2;
            Map<String, Object> options = new HashMap<>();
            options.put("schedule_seconds", sec);
            options.put("header", header);
            return executeJavascriptTest(JS_TR_CHECK_VIEW_LIST_HEADER, options);
        }
    }

    private class GetListAction implements Predicate<String> {
        @Override
        public boolean test(String name) {
            Map<String, Object> options = new HashMap<>();
            options.put("name", name);
            return executeJavascriptTest(JS_TR_GET_LIST_ACTION, options);
        }
    }


    private class ClickTableCellUrl implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            String viewList = (String) options.get("view_list_name");
            if (null == viewList) return executeJavascriptTest(JS_TR_CLICK_TABLE_CELL_URL, options);
            return testKnownColumns(viewList, options, (String) options.get("column"));
        }

        boolean testNow(Map options) {
            String viewList = (String) options.get("view_list_name");
            if (null == viewList)
                return executeJavascriptTestImmediately(JS_TR_CLICK_TABLE_CELL_URL, options, true);
            return testKnownColumns(viewList, options, (String) options.get("column"));
        }

        private boolean testKnownColumns(String viewList, Map options, String column) {
            if (PLUS_ACTION.equalsIgnoreCase(column)) {
                if (MARKET_MESSAGES_VIEW_LIST.equalsIgnoreCase(viewList))
                    return executeJavascriptTest(JS_TR_PLUS_ACTION_IN_MARKET_MESSAGE_TABLE, options);
                else if (BILLING_CUSTOMER_VIEW_LIST.equalsIgnoreCase(viewList))
                    return executeJavascriptTest(JS_TR_PLUS_ACTION_IN_BILLING_CUSTOMER_TABLE, options);
                else
                    throw new IllegalArgumentException(String.format(
                        "Table \"%s\" has no implementation. Please use an implemented table or implement a new one.",
                        viewList));
            }
            return false;
        }
    }

    private class ClickTableRowAction implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest(JS_TR_CLICK_TABLE_ROW_ACTION, options);
        }
    }

    private Map<String, String> getColumnIndexListOptions(String column, String viewListName, String ordinal) {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        Map<String, String> columnIndexListOptions = new HashMap<>();
        columnIndexListOptions.put("column", column);
        columnIndexListOptions.put("index", rowIndex);

        if (PLUS_ACTION.equalsIgnoreCase(column)) {
            if (MARKET_MESSAGES.equalsIgnoreCase(viewListName))
                columnIndexListOptions.put("view_list_name", MARKET_MESSAGES_VIEW_LIST);
            else if (BILLING_CUSTOMER.equalsIgnoreCase(viewListName))
                columnIndexListOptions.put("view_list_name", BILLING_CUSTOMER_VIEW_LIST);
        }
        return columnIndexListOptions;
    }

    private Map<String, String> getColumnIndexListOptionsFromRow(String columnToBeClicked, String tableName, String filterColumn, String data) {
        Integer rowIndex = new ViewListTestObject().fetchRowIndexFromData(tableName, filterColumn, data);
        Map<String, String> columnIndexListOptions = new HashMap<>();
        columnIndexListOptions.put("column", columnToBeClicked);
        columnIndexListOptions.put("index", rowIndex.toString());

        return columnIndexListOptions;
    }

    @Before("@DWP or @CORE or @E2E or @REGRESSION or @API")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @When("^View list header is \"([^\"]*)\"$")
    public void checkViewListHeader(String header){
        seleniumDriver.waitForRequestsToFinish();
        boolean success = new CheckViewListHeader().test(header);
        assertThat(String.format("View list header \"%s\" didn't appear", header),
            success, is(true));
        parameterProvider.put("current-view-list", header);
        seleniumDriver.waitForRequestsToFinish();
    }

    @When("^View list header is \"([^\"]*)\" appears within (\\d+) seconds?$")
    public void checkViewListHeaderUntil(String header, int seconds){
        FluentWait<CheckViewListHeader> waiter = waiter(new CheckViewListHeader(), seconds, 5)
            .withMessage(String.format("View list header \"%s\" didn't appear within %s seconds", header, seconds));
        waiter.until((CheckViewListHeader callback) -> callback.test(header));
        logger().debug(String.format("- STEP: View list header is \"%s\" within %s second(s) - PASSED.", header, seconds));
    }

    @When("^View List is empty$")
    public void checkTableModel(){
        DefaultTableModel viewTableModel = new ViewListTestObject().getViewTableModel();
        boolean success = viewTableModel.getRowCount() == 0;
        assertThat("View Table list is not empty", success, is(true));
        logger().debug("- STEP: View list header is empty - PASSED.");
    }

    @Then("^Table \"([^\"]*)\" has matching value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void isMatchingValueAtColumn(String tableName, String match, String columnName){
        List<String> columnData = new ViewListTestObject().fetchColumnData(tableName, columnName);
        String inputValue = parameterProvider.getValueOrParameterAsString(match);

        Optional<String> first =
            columnData.stream().filter(element -> element.contains(inputValue)).findAny();
        assertThat(
            String.format(
                "Table \"%s\" didn't contain value matching \"%s\" at column \"%s\"",
                tableName, match, columnName),
            first.isPresent(),
            is(true));
    }

    @Then("^Table \"([^\"]*)\" has matching value \"([^\"]*)\" at column \"([^\"]*)\" polling (\\d+) seconds$")
    public void isMatchingValueAtColumnWithPolling(String tableName, String match, String columnName, int waitingTime){
        List<String> columnData = new ViewListTestObject().fetchColumnData(tableName, columnName);
        String inputValue = parameterProvider.getValueOrParameterAsString(match);
        String arrow = parameterProvider.getValueOrParameterAsString("parameter:navigation");
        String dashboardMenu = parameterProvider.getValueOrParameterAsString("parameter:dashboard-menu");

        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), waitingTime, 60);
        waiter.withMessage(String.format(
            "Table does not contain cell value \"%s\" at column \"%s\" within \"%s\" seconds.",
            match, columnName, waitingTime));
        waiter.until((ViewListTestObject callback) -> {
            loopBack(arrow, dashboardMenu);
            return columnData.stream().filter(element -> element.contains(inputValue)).findAny();
        });
    }

    @And(
        "^Table \"([^\"]*)\" contains value \"([^\"]*)\" at column \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void viewListContainsValueAtColumnWithFixedTime(
        String table, String value, String column, int waitingTime){
        Sleeper.sleepTightInSeconds(waitingTime);
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        ViewListTestObject viewListModel = new ViewListTestObject();
        List<String> columnData = viewListModel.fetchColumnDataNow(table, column, true);
        Optional<String> containing =
            columnData.stream().filter(element -> element.contains(inputValue)).findAny();
        String message =
            String.format(
                "Table \"%s\" didn't contain value \"%s\" at column \"%s\"", table, value, column);
        assertThat(message, containing.isPresent(), is(true));
        logger()
            .debug(
                String.format(
                    "- STEP: Table \"%s\" contains value \"%s\" at column \"%s\" - PASSED.",
                    table, value, column));
    }

    @When("^Click on \"([^\"]*)\" link$")
    public void clickOnLink(String input){
        String linkText = parameterProvider.getValueOrParameterAsString(input);
        new ViewListNavigation().goToLink(linkText);
        seleniumDriver.waitForRequestsToFinish();
    }

    @When("^Click on link in View List at \"([^\"]*)\" row and \"([^\"]*)\" column$")
    public void clickOnViewListAtRowAndColumn(String ordinal, String column){
        FluentWait<ClickTableCellUrl> waiter = waiter(new ClickTableCellUrl(), 30, 5).withMessage(
            String.format("Failed click on link in view list at \"%s\" row and \"%s\" column", ordinal, column));
        waiter.until((ClickTableCellUrl callback) -> callback.test(getColumnIndexListOptions(column, null, ordinal)));
        logger().debug(String.format("- STEP: Click on link in view list at \"%s\" row and \"%s\" column - PASSED.",
            ordinal, column));
    }

    @When("^Click on link in View List at \"([^\"]*)\" row and \"([^\"]*)\" column polling (\\d+) seconds?$")
    public void clickOnViewListAtRowAndColumn(String ordinal, String column, int seconds){
        seleniumDriver.waitForRequestsToFinish();
        FluentWait<ClickTableCellUrl> waiter = waiter(new ClickTableCellUrl(), seconds, 5)
            .withMessage(String.format("Failed click on link in view list at \"%s\" row and \"%s\" column within \"%s\" seconds", ordinal, column, seconds));
        waiter.until((ClickTableCellUrl callback) -> callback.test(getColumnIndexListOptions(column, null, ordinal)));
        logger().debug(String.format("- STEP: Click on link in view list at \"%s\" row and \"%s\" column within \"%s\" seconds - PASSED.", ordinal, column, seconds));
        seleniumDriver.waitForRequestsToFinish();
    }

    @When("^Click on link in View List at \"([^\"]*)\" row and \"([^\"]*)\" column waiting for (\\d+) seconds$")
    public void clickOnViewListAtRowAndColumnFixedWait(String ordinal, String column, int seconds){
        Sleeper.sleepTightInSeconds(seconds);
        new ClickTableCellUrl().testNow(getColumnIndexListOptions(column, null, ordinal));
    }

    @When("^Click on link in \"([^\"]*)\" View List at \"([^\"]*)\" row and \"([^\"]*)\" column$")
    public void clickOnSuppliedViewListAtRowAndColumn(String viewListName, String ordinal, String column){
        Map<String, String> columnIndexListOptions = getColumnIndexListOptions(column, viewListName, ordinal);
        FluentWait<ClickTableCellUrl> waiter = waiter(new ClickTableCellUrl(), 60, 5)
            .withMessage(String.format("Failed click on link in view list \"%s\" at \"%s\" row and \"%s\" column",
                viewListName, ordinal, column));
        waiter.until((ClickTableCellUrl callback) -> callback.test(columnIndexListOptions));
        logger().debug(
            String.format("- STEP: Click on link in view list \"%s\" at \"%s\" row and \"%s\" column - PASSED.",
                viewListName, ordinal, column));

    }

    @Then("^Row actions \"([^\"]*)\" is clicked$")
    public void clickOnRowAction(String rowAction) {
        Map<String, String> options = new HashMap<>();
        options.put("rowAction", rowAction);
        FluentWait<ClickTableRowAction> waiter = waiter(new ClickTableRowAction(), 30, 5);
        waiter.withMessage(String.format("Row actions \"%s\" was not clicked", rowAction));
        waiter.until((ClickTableRowAction callback) -> callback.test(options));
        logger().debug(String.format("- STEP: Row actions \"%s\" is clicked - PASSED.", rowAction));
    }

    @And("^\"([^\"]*)\" list element has cell value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void listElementWith(String ordinal, String value, String columnName){
        int row = extractNumericValue(ordinal);
        String expectedValue = parameterProvider.getValueOrParameterAsString(value);
        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), 120, 5);
        waiter.withMessage(String.format("\"%s\" list element value \"%s\" at column \"%s\" was not found", ordinal,
            expectedValue, columnName));
        waiter.until((ViewListTestObject callback) -> callback.containsDataAt(row, expectedValue, columnName));
        logger().debug(String.format("- STEP: \"%s\" list element has cell value \"%s\" at column \"%s\"  - PASSED.",
            ordinal, expectedValue, columnName));
    }

    @And("^Table \"([^\"]*)\" contains cell value \"([^\"]*)\" at column \"([^\"]*)\" on \"([^\"]*)\" row$")
    public void listElementWithFromTable(String tableName, String value, String columnName, String ordinal){
        int row = extractNumericValue(ordinal);
        String expectedValue = parameterProvider.getValueOrParameterAsString(value);
        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), 30, 1);
        waiter.withMessage(
            String.format("Table \"%s\" did not contain expected cell value \"%s\" at column \"%s\" on \"%s\" row",
                tableName, expectedValue, columnName, ordinal));
        waiter.until((ViewListTestObject callback) -> callback.containsCellValue(row, expectedValue, columnName, tableName));
        logger().debug(String.format(
            "- STEP: Table \"%s\" did contains expected cell value \"%s\" at column \"%s\" on \"%s\" row  - PASSED.",
            tableName, expectedValue, columnName, ordinal));
    }

    @And("^\"([^\"]*)\" list element has cell value \"([^\"]*)\" at column \"([^\"]*)\" within (\\d+) seconds?$")
    public void containsElementWithin(String ordinal, String value, String columnName, int seconds){
        String expectedValue = parameterProvider.getValueOrParameterAsString(value);
        PlusActions scenario = (PlusActions) getScenarioInstance(PlusActions.class);
        int row = extractNumericValue(ordinal);
        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), seconds, 20);
        waiter.withMessage(String.format(
            "\"%s\" list element did not contain expected cell value \"%s\" at column \"%s\" within \"%s\" seconds  - PASSED.",
            ordinal, expectedValue, columnName, seconds));
        String plusMenu = parameterProvider.getValueOrParameterAsString("parameter:plus-menu-item");

        waiter.until((ViewListTestObject callback) -> {
            scenario.checkPlusMenu(plusMenu);
            return callback.containsDataAt(row, expectedValue, columnName);
        });
        logger().debug(String.format(
            "- STEP: \"%s\" list element has cell value \"%s\" at column \"%s\" within \"%s\" seconds  - PASSED.",
            ordinal, expectedValue, columnName, seconds));
    }

    @And("^\"([^\"]*)\" list element has cell value \"([^\"]*)\" at column \"([^\"]*)\" polling (\\d+) seconds?$")
    public void containsElementAt(String ordinal, String value, String columnName, int seconds){
        int row = extractNumericValue(ordinal);
        String expectedValue = parameterProvider.getValueOrParameterAsString(value);
        String arrow = parameterProvider.getValueOrParameterAsString("parameter:navigation");
        String dashboardMenu = parameterProvider.getValueOrParameterAsString("parameter:dashboard-menu");
        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), seconds, 20);
        waiter.withMessage(String.format(
            "\"%s\" list element did not have cell value \"%s\" at column \"%s\" within \"%s\" seconds.", ordinal,
            expectedValue, columnName, seconds));
        waiter.until((ViewListTestObject callback) -> {
            loopBack(arrow, dashboardMenu);
            return callback.containsDataAt(row, expectedValue, columnName);
        });
        logger().debug(String.format(
            "\"- STEP: \"%s\" list element did not have cell value \"%s\" at column \"%s\" within \"%s\" seconds - PASSED.",
            ordinal, expectedValue, columnName, seconds));
    }


    private void loopBack(String arrow, String dashboardMenu) {
        try {
            seleniumDriver.waitForRequestsToFinish();
            clickTopArrow(arrow);
            seleniumDriver.waitForRequestsToFinish();
            clickDashboardMenu(dashboardMenu);
        } catch (Throwable t) {
            throw new CucumberException(t);
        }
    }

    @And("^\"([^\"]*)\" list element has status \"([^\"]*)\" at column \"([^\"]*)\" within (\\d+) seconds? refreshing \"([^\"]*)\"$")
    public void refreshTillVisible(String ordinal, String status, String columnName, int seconds, String linkText){
        int row = extractNumericValue(ordinal);
        String expectedValue = parameterProvider.getValueOrParameterAsString(status);
        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), seconds, 5);
        waiter.withMessage(String.format("Status did not switch to \"%s\" within \"%s\" seconds", status, seconds));
        waiter.until((ViewListTestObject callback) -> {
            seleniumDriver.waitAndClick(seleniumDriver.findElement(By.linkText(linkText)));
            return callback.containsDataAt(row, expectedValue, columnName);
        });
    }


    @When("^First list element with value \"([^\"]*)\" at column \"([^\"]*)\" has status \"([^\"]*)\" at column \"([^\"]*)\" within (\\d+) seconds refreshing \"([^\"]*)\"$")
    public void hasStatusWithinTimeout(String value, String columnName, String status, String secondColumnName, int seconds, String linkText){
        String expectedValue = parameterProvider.getValueOrParameterAsString(value);
        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), seconds / 2, 5);
        waiter.withMessage(String.format("Status did not switch to \"%s\" within \"%s\" seconds", status, seconds));
        waiter.until((ViewListTestObject callback) -> {
            seleniumDriver.waitAndClick(seleniumDriver.findElement(By.linkText(linkText)));
            return CollectionUtils.isNotEmpty(callback.fetchListRowsIndices(expectedValue, columnName));
        });
        waiter = waiter(new ViewListTestObject(), seconds / 2, 5);
        waiter.until((ViewListTestObject callback) -> {
            seleniumDriver.waitAndClick(seleniumDriver.findElement(By.linkText(linkText)));
            int row = callback.fetchListRowsIndices(value, columnName).get(0);
            return callback.containsDataAt(row, status, secondColumnName);
        });
    }

    @And("^Cell values? from selected rows? and column \"([^\"]*)\" (?:are|is) checked$")
    public void checkDataSelection(String columnName){
        ViewListTestObject viewListModel = new ViewListTestObject();
        List<String> cellSelection = viewListModel.fetchDataSelection(columnName);
        assertThat(String.format("Data selection at column \"%s\" is empty", columnName), cellSelection,
            not(hasSize(0)));
        parameterProvider.put(columnName, cellSelection);
        logger().debug(String.format(
            "- STEP: Cell value(s) from selected row(s) and column \"%s\" is/are checked - PASSED.", columnName));
    }

    @Then("^\"([^\"]*)\" List element with value at column \"([^\"]*)\" is checked$")
    public void storeColumnValueInSharedProperties(String ordinal, String columnName){
        seleniumDriver.waitForRequestsToFinish();
        int row = extractNumericValue(ordinal);
        Optional<String> optionalValue = new ViewListTestObject().getCellValueAt(row, columnName);

        assertThat(String.format("\"%s\" list element didn't contain any value at column \"%s\"", ordinal, columnName),
            optionalValue.isPresent(), is(true));
        String splitValue = optionalValue.get().split(" ")[0];
        parameterProvider.put(columnName, splitValue);
        logger().debug(String.format("- STEP: \"%s\" list element with value at column \"%s\" is checked - PASSED.",
            ordinal, columnName));
    }

    @Then("^\"([^\"]*)\" list element with date interval at column \"([^\"]*)\" from table \"([^\"]*)\" is \"([^\"]*)\"$")
    public void checkDateIntervalTableCell(String ordinal, String columnName, String tableName, String interval){
        int row = extractNumericValue(ordinal);
        Optional<String> result = new ViewListTestObject().getValueAt(row, columnName, tableName);
        boolean success = result.isPresent() && StringUtils.isNotBlank(result.get());
        assertThat(
            String.format(
                "\"%s\" list element didn't contain any value at column \"%s\"", ordinal, columnName),
            success,
            is(true));
        String[] splitValue = result.get().split("\\s+");
        parameterProvider.put(columnName, result.get());
        Assert.assertThat(
            "Comparison of two dates expression conversion failure",
            checkTimeBetween(splitValue[0], splitValue[1], interval),
            Matchers.equalTo(0));
    }

    @When(
        "^End of interval from \"([^\"]*)\" row of table \"([^\"]*)\" at column \"([^\"]*)\" is checked$")
    public void endOfIntervalFromRowOfTableAtColumnIsChecked(
        String ordinal, String tableName, String column){
        int row = extractNumericValue(ordinal);
        Optional<String> intervalOfContract =
            new ViewListTestObject().getValueAt(row, column, tableName);
        boolean success =
            intervalOfContract.isPresent() && StringUtils.isNotBlank(intervalOfContract.get());
        assertThat(
            String.format(
                "\"%s\" list element didn't contain any value at column \"%s\"", ordinal, column),
            success,
            is(true));
        parameterProvider.put(column + " - start", getFormattedEnd(intervalOfContract.get(), -1));
        parameterProvider.put(column + " - end", getFormattedEnd(intervalOfContract.get(), +1));
    }

    @Then("^List element matching value \"([^\"]*)\" at column \"([^\"]*)\" from table \"([^\"]*)\" is checked$")
    public void storeColumnValueInParameterProvider(String match, String columnName, String tableName){
        seleniumDriver.waitForRequestsToFinish();
        List<String> columnData = new ViewListTestObject().fetchColumnData(tableName, columnName);
        Optional<String> first = columnData.stream().filter(element -> element.contains(match)).findAny();
        assertThat(String.format("\"%s\" list element didn't contain value \"%s\" at column \"%s\"",
            tableName, match, columnName),
            first.isPresent(),
            is(true));
        parameterProvider.put(columnName, first.get());
        logger().debug(String.format("- STEP: \"%s\" list element with value at column \"%s\" is checked - PASSED.", tableName, columnName));
    }

    @When("^Click on \"([^\"]*)\" matching value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void clickOnElementWithMatchingValue(String columnToBeClicked, String match, String filterColumn){
        new ClickTableCellUrl().testNow(getColumnIndexListOptionsFromRow(columnToBeClicked, null, filterColumn, match));
    }

    @And("^All cell values at \"([^\"]*)\" row from table \"([^\"]*)\" are checked$")
    public void cellValuesAtRowFromTableAreChecked(String ordinal, String tableName){
        ViewListTestObject viewListTestObject = new ViewListTestObject(tableName);
        int row = extractNumericValue(ordinal);
        for (int column = 1; viewListTestObject.getColumnCount().isPresent() &&
            column <= viewListTestObject.getColumnCount().get(); column++) {
            Optional<String> columnName = viewListTestObject.getColumnName(column);
            Optional<Object> value = viewListTestObject.getValueAt(row, column);
            if (columnName.isPresent() && value.isPresent()) {
                parameterProvider.put(columnName.get(), value.get());
            }
        }
    }

    @Then("^List element with value at column \"([^\"]*)\" from table \"([^\"]*)\" is checked$")
    public void storeColumnValueInParameterProvider(String columnName, String tableName){
        seleniumDriver.waitForRequestsToFinish();
        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), 300, 10);
        waiter.withMessage(
            String.format("List element didn't contain any value at column \"%s\"", columnName));
        List<String> columnData = waiter.until((ViewListTestObject callback) -> callback.fetchColumnData(tableName, columnName));
        boolean success = CollectionUtils.isNotEmpty(columnData);
        assertThat(String.format("\"%s\" list element didn't contain any value at column \"%s\"", tableName, columnName),
            success, is(true));

        parameterProvider.put(columnName, columnData.get(0));
        logger().debug(String.format("- STEP: \"%s\" list element with value at column \"%s\" is checked - PASSED.",
            tableName, columnName));

    }

    @Then(
        "^Cell value at \"([^\"]*)\" row at column \"([^\"]*)\" from table \"([^\"]*)\" is checked$")
    public void lookupCellValue(String ordinal, String columnName, String tableName){
        int row = extractNumericValue(ordinal);
        Optional<String> cellValue = new ViewListTestObject().getValueAt(row, columnName, tableName);
        assertThat(
            String.format(
                "Table \"%s\" is empty or it did not contain any value at \"%s\" row and column \"%s\"",
                tableName, ordinal, columnName),
            cellValue.isPresent() && StringUtils.isNotEmpty(cellValue.get()),
            is(true));
        parameterProvider.put(columnName, cellValue.get());
        logger()
            .debug(
                String.format(
                    "- STEP: \"%s\" list element with value at column \"%s\" is checked - PASSED.",
                    tableName, columnName));
    }

    @And("^Select \"([^\"]*)\" List row having cell value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void selectListRows(String ordinal, String value, String columnName){
        int row = extractNumericValue(ordinal);
        ViewListTestObject viewListModel = new ViewListTestObject();
        boolean success = viewListModel.selectListRow(row, value, columnName);
        String message = String.format("\"%s\" list row didn't contain value \"%s\" at column \"%s\"", ordinal, value,
            columnName);
        assertThat(message, success, is(true));
        logger().debug(String.format("- STEP: \"%s\" list row having cell value \"%s\" at column \"%s\" - PASSED.",
            ordinal, value, columnName));

    }

    @And("^Plus actions at \"([^\"]*)\" list row having cell value \"([^\"]*)\" at column \"([^\"]*)\" are open$")
    public void openPlusActions(String ordinal, String value, String columnName){
        int row = extractNumericValue(ordinal);
        String expectedValue = parameterProvider.getValueOrParameterAsString(value);
        ViewListTestObject viewListModel = new ViewListTestObject();
        boolean success = viewListModel.openListPlusActions(row);
        String message = String.format("\"%s\" row list didn't have cell value \"%s\" at column \"%s\"", ordinal,
            expectedValue, columnName);
        assertThat(message, success, is(true));
        logger().debug(String.format(
            "- STEP: Plus actions at \"%s\" list row having cell value \"%s\" at column \"%s\" are opened - PASSED.",
            ordinal, value, columnName));
    }

    @And("^\"([^\"]*)\" List rows? having cell value \"([^\"]*)\" at column \"([^\"]*)\" (?:is|are) selected$")
    public void selectListRowHavingCellValueAtColumn(int row, String value, String columnName){
        ViewListTestObject viewListModel = new ViewListTestObject();
        boolean success = viewListModel.selectListRows(row, value, columnName);
        String message = String.format("\"%s\" list row(s) didn't have cell value \"%s\" at column \"%s\"", row, value,
            columnName);
        assertThat(message, success, is(true));
        logger().debug(String.format(
            "- STEP: \"%s\" list row(s) having cell value \"%s\" at column \"%s\" is/are selected - PASSED.", row,
            value, columnName));
    }

    @Then("^Selected List rows have cell value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void checkSelectionData(String value, String columnName){
        ViewListTestObject viewListModel = new ViewListTestObject();
        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), 10, 2).withMessage("Selected table is empty");
        waiter.until((ViewListTestObject callback) -> CollectionUtils.isNotEmpty(callback.fetchDataSelection(columnName)));
        List<String> cellSelection = viewListModel.fetchDataSelection(columnName);
        String message = String.format("Value \"%s\" wasn't found in any row of \"%s\" column", value, columnName);
        assertThat(message, cellSelection.get(0).contains(value), is(true));
        logger().debug(String.format("- STEP: Selected List rows have cell value \"%s\" at column \"%s\" - PASSED.",
            value, columnName));
    }

    @And("^List View action is \"([^\"]*)\"$")
    public void getListAction(String name){
        boolean success = new GetListAction().test(name);
        assertThat(String.format("List action \"%s\" is undefined.", name), success, is(true));
        logger().debug(String.format("- STEP: List view action is \"%s\" - PASSED.", name));
    }

    @And("^View List element \"([^\"]*)\" is collected as parameter at \"([^\"]*)\" list row$")
    public void collectViewListElementAsParameter(String viewListElement, String ordinal) {
        String parameter = getViewListElementAtRow(viewListElement, ordinal);
        parameterProvider.put(viewListElement, parameter);
        logger().debug(String.format("View List element \"%s\" is collected as parameter at \"%s\" list row",
            viewListElement, ordinal));
    }

    @And("^View List element \"([^\"]*)\" using \"([^\"]*)\" as alias is collected as parameter at \"([^\"]*)\" list row$")
    public void collectViewListElementWithAliasAsParameter(String viewListElement, String viewListElementAlias, String ordinal) {
        String parameter = getViewListElementAtRow(viewListElement, ordinal);
        parameter = getPossibleNumeric(parameter);
        parameterProvider.put(viewListElementAlias, parameter);
    }

    private String getPossibleNumeric(String input) {
        String[] possibleAccountNumbers = input.split(" ");
        String message = String.format("Input value \"%s\" didn't contain any numeric substring", input);
        if (possibleAccountNumbers.length > 1) {
            for (int i = 0; i < possibleAccountNumbers.length - 1; i++) {
                if (StringUtils.isNumeric(possibleAccountNumbers[i]))
                    return possibleAccountNumbers[i];
            }
            throw new CucumberException(String.format("Input value \"%s\" didn't contain any numeric substring", input));
        }
        if (StringUtils.isNumeric(input))
            return input;
        else
            throw new CucumberException(message);
    }

    private String getViewListElementAtRow(String viewListElement, String ordinal) {
        int row = extractNumericValue(ordinal);
        ViewListTestObject viewListModel = new ViewListTestObject();
        Optional<String> optionalValue = viewListModel.getCellValueAt(row, viewListElement);
        assertThat(String.format("View List element '%s' was not found.", viewListElement),
            optionalValue.isPresent(), is(true));
        return optionalValue.get();
    }

    @Then("^\"([^\"]*)\" list (is|is_not) empty$")
    public void viewIsNotEmpty(String tableTitle, String verb) {
        DefaultTableModel viewTableModel = new ViewListTestObject().getViewTableModel(tableTitle);
        boolean success = false;
        if (verb.equalsIgnoreCase("is")) {
            success = viewTableModel.getRowCount() == 0;
        } else if (verb.equalsIgnoreCase("is_not")) {
            success = viewTableModel.getRowCount() != 0;
        }
        assertThat(String.format(tableTitle + " doesn't exist or comparation is not valid"), success, is(true));
        logger().debug(String.format("- STEP: \"%s\" list is not empty - PASSED.", tableTitle));
    }

    @And("^Table \"([^\"]*)\" contains value \"([^\"]*)\" at column \"([^\"]*)\" within (\\d+) seconds? after clicking on \"([^\"]*)\"$")
    public void viewListContainsValueAtColumn(String table, String value, String column, int seconds, String buttonName){
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        String dashboardMenu = parameterProvider.getValueOrParameterAsString("parameter:dashboard-menu");

        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), seconds, 30);
        waiter.withMessage(String.format("List element didn't contain any value at column \"%s\"", column));
        waiter.until((ViewListTestObject callback) -> {
            clickDashboardMenu(dashboardMenu);
            new Marketberichten().clickOn(buttonName);
            return !callback.fetchColumnData(table, column)
                .stream().filter(element -> element.contains(inputValue)).collect(Collectors.toList()).isEmpty();
        });

        logger().debug(String.format("- STEP: Table \"%s\" does not contain value \"%s\" at column \"%s\".", table,
            value, column));
    }

    @And("^Table \"([^\"]*)\" contains value \"([^\"]*)\" at column \"([^\"]*)\" within (\\d+) seconds?$")
    public void viewListContainsValueAtColumn(String table, String value, String column, int seconds){
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        String arrow = parameterProvider.getValueOrParameterAsString("parameter:navigation");
        String dashboardMenu = parameterProvider.getValueOrParameterAsString("parameter:dashboard-menu");

        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), seconds, 10);
        waiter.withMessage(String.format("List element didn't contain any value at column \"%s\"", column));
        waiter.until((ViewListTestObject callback) -> {
            loopBack(arrow, dashboardMenu);
            return !(callback.fetchColumnData(table, column).stream().
                filter(element -> element.contains(inputValue)).collect(Collectors.toList()).isEmpty());
        });

        logger().debug(String.format("- STEP: Table \"%s\" does not contain value \"%s\" at column \"%s\".", table,
            value, column));
    }

    @And("^Table \"([^\"]*)\" contains value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void viewListContainsValueAtColumn(String table, String value, String column){
        ViewListTestObject viewListModel = new ViewListTestObject();
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        List<String> columnData = viewListModel.fetchColumnData(table, column);
        List<String> found = columnData.stream().filter(element -> element.contains(inputValue))
            .collect(Collectors.toList());
        String message = String.format("Table \"%s\" didn't contain value \"%s\" at column \"%s\"", table, value,
            column);
        assertThat(message, found, not(empty()));
        logger().debug(String.format("- STEP: Table \"%s\" contains value \"%s\" at column \"%s\" - PASSED.", table,
            value, column));
    }

    @And("^Table \"([^\"]*)\" contains check mark at column \"([^\"]*)\"$")
    public void viewListContainsCheckmarkAtColumn(String table, String column){
        seleniumDriver.waitForRequestsToFinish();
        Optional<Integer> transactionsColumnIndex =
            new ViewListTestObject().getTransactionsColumnIndex(column);
        assertThat(
            String.format("Table \"%s\" did not contain data at column \"%s\"", table, column),
            transactionsColumnIndex.isPresent(),
            is(true));
        By checkmarkSelector =
            By.xpath(
                createQuery(
                    TRANSACTIONS_BLOCKED_CHECKMARK,
                    REPLACEMENT_KEY1,
                    Integer.toString(transactionsColumnIndex.get())));
        WebElement checkmark = seleniumDriver.findElementWhenPresent(checkmarkSelector);
        String message =
            String.format("Table \"%s\" didn't contain check mark at column \"%s\"", table, column);
        assertThat(message, null != checkmark);
        logger()
            .debug(
                String.format(
                    "- STEP: Table \"%s\" contains check mark at column \"%s\" - PASSED.",
                    table, column));
    }

    @And("^Table \"([^\"]*)\" does not contain value \"([^\"]*)\" at column \"([^\"]*)\" within (\\d+) seconds?$")
    public void viewListDoesNotContainsValueAtColumn(String table, String value, String column, int seconds){
        ViewListTestObject viewListModel = new ViewListTestObject();
        String arrow = parameterProvider.getValueOrParameterAsString("parameter:navigation");
        String dashboardMenu = parameterProvider.getValueOrParameterAsString("parameter:dashboard-menu");
        String inputValue = parameterProvider.getValueOrParameterAsString(value);

        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), seconds, 10);
        waiter.withMessage(String.format("List element didn't contain any value at column \"%s\"", column));
        List<String> found =  waiter.until((ViewListTestObject callback) -> {
            loopBack(arrow, dashboardMenu);
            return callback.fetchColumnData(table, column).stream().
                filter(element -> element.contains(inputValue)).collect(Collectors.toList());
        });
        String message = String.format("Table \"%s\" should not contain value \"%s\" at column \"%s\"", table, value,
            column);
        assertThat(message, found, empty());
    }


    //TODO Create a special test harness class for invoice checks,
    //and move the methods, related to invoice checks, there.

    @And("^\"([^\"]*)\" element of table \"([^\"]*)\" at currency column \"([^\"]*)\" is sum of$")
    public void checkCurrencyAmountDableDataAsSum(String ordinal, String table, String columnName, final DataTable subAmounts){
        seleniumDriver.waitForRequestsToFinish();
        int row = extractNumericValue(ordinal);
        Optional<String> currencyValue = new ViewListTestObject().getCurrencyValueAt(row, columnName, table);
        assertThat(String.format("\"%s\" list element didn't contain any value at column \"%s\"", ordinal, columnName),
            currencyValue.isPresent(), is(true));
        int actualAmount = amountInCurrencyAsInt(currencyValue.get());
        List<String> amounts = subAmounts.asList(String.class);
        int sum = sumOf(amounts);
        assertThat("Total advance prepaid amount %s is not equal to sum of sub amounts %s", actualAmount, equalTo(sum));
    }

    @And("^\"([^\"]*)\" in the first \"([^\"]*)\" row of \"([^\"]*)\" table is \"([^\"]*)\"$")
    public void checkCurrencyAmount(String columnToSearch, String optionToSearch, String table,
                                    String expression) {
        seleniumDriver.waitForRequestsToFinish();
        Optional<String> currencyValue = new ViewListTestObject().getCurrencyValueAt(1, columnToSearch, table);

        assertThat(String.format("\"%s\" list element didn't contain any value at column \"%s\"", 1, columnToSearch),
            currencyValue.isPresent(), is(true));
        int actualAmount = amountInCurrencyAsInt(currencyValue.get());
        boolean success = checkAmount(actualAmount, expression);
        assertThat("The expected value differs from the real value", success, is(true));

    }

    @Then("^Transactions table has outstanding amount of \"([^\"]*)\"")
    public void checkInvoiceCurrencyAmount(String expectedCurrencyAmountAsString) {
        ContractPage cp = new ContractPage();
        String actualCurrencyAmountAsString = cp.getActualOutstandingValueCurrencyInvoiceAsString();
        String message = String.format("Table Transactions doesn't have value for invoice outstanding amount of \"%s\"", expectedCurrencyAmountAsString);
        assertThat(message, actualCurrencyAmountAsString, equalTo(expectedCurrencyAmountAsString));
    }

    @Then("Credit invoice has same negative amount as advance invoice")
    public void checkInvoiceAmount() {
        Map<String, String> actualValuesOfInvoices = new ContractPage().getNumericInvoicesAmounts();

        final String message = "Invoices amounts are not matching.";

        Long unsignedCreditInvoice = Long.parseLong(actualValuesOfInvoices.get("creditInvoice").substring(1));
        Long debitInvoice = Long.parseLong(actualValuesOfInvoices.get("debitInvoice"));
        Long additionalInvoice = Long.parseLong(actualValuesOfInvoices.get("additionalInvoice"));

        parameterProvider.put("additionalInvoice", additionalInvoice);

        assertThat(message, unsignedCreditInvoice.equals(debitInvoice));
    }

    @Then("Balance is the same as from the latest invoice")
    public void checkValue() {
        ContractPage cp = new ContractPage();

        String expectedBalance = parameterProvider.getValueOrParameterAsString("parameter:additionalInvoice") + ",00";

        int refreshCount = 600;
        String actualBalance = "";
        for (int i = 0; i < refreshCount; i++) {
            actualBalance = cp.getBalance();
            if (expectedBalance.equals(actualBalance)) {
                break;
            } else {
                seleniumDriver.getDriver().navigate().back();
                seleniumDriver.getDriver().navigate().forward();
            }
        }
        Assert.assertEquals("Balance is not correct", expectedBalance, actualBalance);
    }

    @Then("^Table Offertelijnen has value \"([^\"]*)\"$")
    public void checkTableValue(String expectedTableValue) {
        ContractPage cp = new ContractPage();
        int refreshCount = 5;
        for (int i = 0; i < refreshCount; i++) {
            if (cp.getTableValue().contains(expectedTableValue)) {
                break;
            } else {
                seleniumDriver.getDriver().navigate().refresh();
            }
        }
        Assert.assertTrue("There is no expected value in the table", cp.getTableValue().contains(expectedTableValue));
    }

    @And("Sum of Rate for signature received is \"([^\"]*)\"$")
    public void sumRates(String typeRate) {
        ContractPage cp = new ContractPage();
        switch (typeRate) {
            case "High":
                parameterProvider.put("sumRatesHighSignature", cp.sumRates(typeRate));
                break;
            case "Low":
                parameterProvider.put("sumRatesLowSignature", cp.sumRates(typeRate));
                break;
            default:
                throw new CucumberException(getClass() + ": Only High and Low values can be passed as parameters");
        }
    }

    @Then("\"([^\"]*)\" and \"([^\"]*)\" equals Sum of High&Low rates for rejected rates$")
    public void compareSumOfRatesForSignatureQuoteAndRejectedQuote(String sumRatesHighSignature, String sumRatesLowSignature) {
        ContractPage cp = new ContractPage();
        float sumHighRatesSignatureToFloat = Float.parseFloat(parameterProvider.getValueOrParameterAsString(sumRatesHighSignature));
        float sumLowRatesSignatureToFloat = Float.parseFloat(parameterProvider.getValueOrParameterAsString(sumRatesLowSignature));
        cp.sumRates(sumRatesHighSignature);
        cp.sumRates(sumRatesLowSignature);
        assertThat("Sum of signature and rejected quote for High prices is not equal", sumHighRatesSignatureToFloat, equalTo(cp.sumRates(sumRatesHighSignature)));
        assertThat("Sum of signature and rejected quote for Low prices is not equal", sumLowRatesSignatureToFloat, equalTo(cp.sumRates(sumRatesLowSignature)));
    }


    //TODO Create a special test harness class for wait methods,
    //and move the methods, related to test execution timing, there.logger().info(column);
    @And("^Wait for (\\d+) seconds$")
    public void waitForSeconds(int seconds) {
        Sleeper.sleepTightInSeconds(seconds);
    }

    @Override
    @After("@DWP or @CORE or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
