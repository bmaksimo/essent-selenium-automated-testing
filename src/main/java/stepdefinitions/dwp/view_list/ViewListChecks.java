package stepdefinitions.dwp.view_list;

import com.billinghouse.cucumber.runtime.annotations.InputParameter;
import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts.ContractPage;
import com.essent.testing.dwp.pageobject.ViewList;
import com.essent.testing.dwp.pageobject.list_view.ViewListTestObject;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.CucumberException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import stepdefinitions.dwp.navigation.NavigationElements;
import stepdefinitions.dwp.plus.PlusActions;

import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkTimeBetween;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

public class ViewListChecks extends NavigationElements {

    private static final String MARKET_MESSAGES = "Marktberichten";
    private static final String MARKET_MESSAGES_VIEW_LIST = "MarketTransactionsOnAccount";
    private static final String BILLING_CUSTOMER = "Billing customer";
    private static final String BILLING_CUSTOMER_VIEW_LIST = "BillingCustomerOnaccount";
    private static final String PLUS_ACTION = "Plus ActionDTO";

    private class ViewListNavigation {
        public void goToLink(String linkText) {
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
            return executeJavascriptTest("TrCheckViewListHeader", options);
        }
    }

    private class GetListAction implements Predicate<String> {
        @Override
        public boolean test(String name) {
            Map<String, Object> options = new HashMap<>();
            options.put("name", name);
            return executeJavascriptTest("TrGetListAction", options);
        }
    }

    /**
     * The class is deprecated.
     * Please, don't amend it.
     * <p>
     * Replacement code is
     *
     * @see ViewListTestObject
     * Switch your logic to
     * @see ViewListTestObject
     * and make code amendments there.
     * @deprecated
     */
    @Deprecated
    private class ViewListModel implements ViewList {

        private DefaultTableModel getViewTableModel() {
            DefaultTableModel tableModel = new DefaultTableModel();
            return getDefaultTableModel(tableModel, new HashMap<>());
        }

        private DefaultTableModel getViewTableModel(String tableName) {
            DefaultTableModel tableModel = new DefaultTableModel();
            HashMap<Object, Object> options = new HashMap<>();
            options.put("list_header", tableName);
            return getDefaultTableModel(tableModel, options);
        }

        private DefaultTableModel getDefaultTableModel(DefaultTableModel tableModel, HashMap<Object, Object> options) {
            Map viewTable = executeJavascriptMethod("TrGetTableModel", options);
            List columnNames = (List) viewTable.get("column_names");
            List rows = getData(viewTable);
            tableModel.setColumnIdentifiers(columnNames.toArray());
            for (Object row1 : rows) {
                Object[] row = ((List) row1).toArray();
                tableModel.addRow(row);
            }
            return tableModel;
        }

        private void logTableModel(DefaultTableModel viewTableModel) {
            int columnCount = viewTableModel.getColumnCount();
            StringBuilder columns = new StringBuilder("[");
            for (int i = 0; i < columnCount; i++) {
                columns.append(String.format("'%s'", viewTableModel.getColumnName(i)));
                if (i < columnCount - 1)
                    columns.append(", ");
            }
            columns.append("]");
            logger().info("- RESULT: Columns: " + columns.toString());
            logger().info("- RESULT: Data vector: " + viewTableModel.getDataVector());
        }

        private List<List> getData(Map viewTable) {
            return (List) viewTable.get("rows");
        }

        private int getColumnNameIndex(String columnName, Map viewTable) {
            List<String> columnNames = (List) viewTable.get("column_names");
            return columnNames.indexOf(columnName);
        }

        public boolean containsDataAt(int row, String value, String columnName) {
            String cell = getCellValueAt(row, columnName);
            boolean success = cell.contains(value);
            return success;
        }

        public boolean containsCellValue(int rowFromOne, String value, String columnName, String tableName) {
            String cell = getValueAt(rowFromOne, columnName, tableName);
            boolean success = cell.contains(value);
            return success;
        }

        public boolean selectListRow(int row) {
            Map<String, Object> options = new HashMap<>();
            options.put("index", row);
            boolean success = executeJavascriptTest("TrSelectListRow", options);
            return success;
        }

        public boolean openListPlusActions(int row) {
            Map<String, Object> options = new HashMap<>();
            options.put("index", row);
            boolean success = executeJavascriptTest("TrOpenListPlusActions", options);
            return success;
        }

        public List<Integer> fetchListRowsIndices(String value, String columnName) {
            Map viewTable = executeJavascriptMethod("TrGetTableModel", new HashMap<>());
            int index = getColumnNameIndex(columnName, viewTable);
            if (index < 0) {
                fail(String.format("View List did not contain column %s", columnName));
            }
            List<List> rows = getData(viewTable);
            AtomicInteger idx = new AtomicInteger(1);
            List<Integer> collect = IntStream.range(1, rows.size() + 1).filter(
                i -> idx.compareAndSet(i, i + 1) & ((ArrayList<String>) rows.get(i - 1)).get(index).contains(value))
                .boxed().collect(Collectors.toList());
            return collect;
        }

        public boolean selectListRow(int row, String value, String columnName) {
            List<Integer> indices = fetchListRowsIndices(value, columnName);
            return indices.size() > 0 && row <= indices.size();
        }

        public boolean selectListRows(int numRows, String value, String columnName) {
            List<Integer> rows = fetchListRowsIndices(value, columnName);
            if (numRows > rows.size()) {
                return false;
            }
            List<Integer> indices = IntStream.range(1, numRows + 1).boxed().collect(Collectors.toList());
            Map<String, Object> options = new HashMap<>();
            options.put("indices", indices);
            boolean success = executeJavascriptTest("TrSelectListRows", options, true);
            return success;
        }

        public List<String> fetchDataSelection(String columnName) {
            Map<String, Object> options = new HashMap<>();
            options.put("include_selection", true);
            Map viewTable = executeJavascriptMethod("TrFetchDataSelection", options);
            int index = getColumnNameIndex(columnName, viewTable);
            if (index < 0) {
                fail(String.format("View List did not contain column %s", columnName));
            }
            List<List> rows = getData(viewTable);
            List selection;
            selection = rows.stream().map((e) -> e.get(index)).collect(Collectors.toList());
            return selection;
        }

        public List<String> fetchColumnData(String table, String columnName) {
            return fetchColumnDataNow(table, columnName, false);
        }

        public List<String> fetchColumnDataNow(String table, String columnName, boolean immediate) {
            Map<String, Object> options = new HashMap<>();
            options.put("include_selection", false);
            options.put("table", table);

            Map viewTable = immediate ? executeJavascriptMethodImmediately("TrFetchDataSelection", options)
                : executeJavascriptMethod("TrFetchDataSelection", options);

            int index = getColumnNameIndex(columnName, viewTable);
            if (index < 0) {
                fail(String.format("View List did not contain column %s", columnName));
            }
            List<List> rows = getData(viewTable);
            List selection;
            selection = rows.stream().map((e) -> e.get(index)).collect(Collectors.toList());
            return selection;
        }

        private String getCellValueAt(int row, String columnName) {
            logger().info("STEP: JAVASCRIPT_FETCH_DATA");
            Map viewTable = executeJavascriptMethod("TrGetTableModel", new HashMap<>());
            logger().info(" - RESULT: " + viewTable);
            int index = getColumnNameIndex(columnName, viewTable);
            if (index < 0) {
                throw new CucumberException(String.format("View List did not contain column \"%s\"", columnName));
            }
            List<List> rows = getData(viewTable);
            if (rows.size() == 0) {
                throw new CucumberException("--  Table is empty.");
            }
            if (row > rows.size()) {
                throw new CucumberException(String
                    .format("--  Row number \"%s\" was greater than actual table size \"%s\"", row, rows.size()));
            }
            List currentRow = rows.get(row - 1);
            return (String) currentRow.get(index);
        }

        public String getValueAt(int row, String columnName, String tableName) {
            logger().info("STEP: JAVASCRIPT_FETCH_DATA");
            HashMap<Object, Object> options = new HashMap<>();
            options.put("list_header", tableName);
            DefaultTableModel viewTableModel = getViewTableModel(tableName);
            logger().info(" - RESULT: Table name: " + tableName);
            logTableModel(viewTableModel);
            int column = viewTableModel.findColumn(columnName);
            if (column < 0)
                throw new CucumberException(String.format("View List did not contain column %s", columnName));
            return (String) viewTableModel.getValueAt(row - 1, column);

        }

        @Override
        public String getCurrencyValueAt(int row, String columnName, String tableName) {
            throw new UnsupportedOperationException("As deprecated, the operation is not supported anymore. Use provided replacement.");
        }

    }

    private class ClickTableCellUrl implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            String viewList = (String) options.get("view_list_name");
            if (null == viewList)
                return executeJavascriptTest("TrClickTableCellUrl", options);
            return testKnownColumns(viewList, options, (String) options.get("column"));
        }

        public boolean testNow(Map options) {
            String viewList = (String) options.get("view_list_name");
            if (null == viewList)
                return executeJavascriptTestImmediately("TrClickTableCellUrl", options, true);
            return testKnownColumns(viewList, options, (String) options.get("column"));
        }

        private boolean testKnownColumns(String viewList, Map options, String column) {
            if (PLUS_ACTION.equalsIgnoreCase(column)) {
                if (MARKET_MESSAGES_VIEW_LIST.equalsIgnoreCase(viewList))
                    return executeJavascriptTest("TrPlusActionInMarketMessageTable", options);
                else if (BILLING_CUSTOMER_VIEW_LIST.equalsIgnoreCase(viewList))
                    return executeJavascriptTest("TrPlusActionInBillingCustomerTable", options);
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
            return executeJavascriptTest("TrClickTableRowAction", options);
        }
    }

    private class CheckEmptyTableAction implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrCheckEmptyTable", options);
        }
    }

    private class CheckFirstRowByOption implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrCheckFirstRowByOption", options);
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

    @Before("@DWP, @CORE, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^View list header is \"([^\"]*)\"$")
    public void checkViewListHeader(String header) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        boolean success = new CheckViewListHeader().test(header);
        assertThat(String.format("View list header \"%s\" didn't appear", header),
            success, is(true));
        parameterProvider.put("current-view-list", header);
        logger().info(String.format("- STEP: View list header is \"%s\" - PASSED.", header));
        seleniumDriver.waitForRequestsToFinish();
    }

    @When("^View list header is \"([^\"]*)\" appears within (\\d+) seconds?$")
    public void checkViewListHeaderUntil(String header, int seconds) throws Throwable {
        FluentWait<CheckViewListHeader> waiter = waiter(new CheckViewListHeader(), seconds, 5)
            .withMessage(String.format("View list header \"%s\" didn't appear within %s seconds", header, seconds));
        waiter.until((CheckViewListHeader callback) -> callback.test(header));
        logger().info(String.format("- STEP: View list header is \"%s\" within %s second(s) - PASSED.", header, seconds));
    }

    @When("^View List is empty$")
    public void checkTableModel() throws Throwable {
        javax.swing.table.TableModel viewTableModel = new ViewListModel().getViewTableModel();
        boolean success = viewTableModel.getRowCount() == 0;
        assertThat("View Table list is not empty", success, is(true));
        logger().info("- STEP: View list header is empty - PASSED.");
    }

    @When("^Click on \"([^\"]*)\" link$")
    public void clickOnLink(String input) throws Throwable {
        String linkText = parameterProvider.getValueOrParameterAsString(input);
        new ViewListNavigation().goToLink(linkText);
        seleniumDriver.waitForRequestsToFinish();
    }

    @When("^Click on link in View List at \"([^\"]*)\" row and \"([^\"]*)\" column$")
    public void clickOnViewListAtRowAndColumn(String ordinal, String column) throws Throwable {
        FluentWait<ClickTableCellUrl> waiter = waiter(new ClickTableCellUrl(), 30, 5).withMessage(
            String.format("Failed click on link in view list at \"%s\" row and \"%s\" column", ordinal, column));
        waiter.until((ClickTableCellUrl callback) -> callback.test(getColumnIndexListOptions(column, null, ordinal)));
        logger().info(String.format("- STEP: Click on link in view list at \"%s\" row and \"%s\" column - PASSED.",
            ordinal, column));
    }

    @When("^Click on link in View List at \"([^\"]*)\" row and \"([^\"]*)\" column polling (\\d+) seconds?$")
    public void clickOnViewListAtRowAndColumn(String ordinal, String column, int seconds) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        FluentWait<ClickTableCellUrl> waiter = waiter(new ClickTableCellUrl(), seconds, 5)
            .withMessage(String.format("Failed click on link in view list at \"%s\" row and \"%s\" column within \"%s\" seconds", ordinal, column, seconds));
        waiter.until((ClickTableCellUrl callback) -> callback.test(getColumnIndexListOptions(column, null, ordinal)));
        logger().info(String.format("- STEP: Click on link in view list at \"%s\" row and \"%s\" column within \"%s\" seconds - PASSED.", ordinal, column, seconds));
        seleniumDriver.waitForRequestsToFinish();
    }

    @When("^Click on link in View List at \"([^\"]*)\" row and \"([^\"]*)\" column waiting for (\\d+) seconds$")
    public void clickOnViewListAtRowAndColumnFixedWait(String ordinal, String column, int seconds) throws Throwable {
        Sleeper.sleepTightInSeconds(seconds);
        new ClickTableCellUrl().testNow(getColumnIndexListOptions(column, null, ordinal));
    }

    @When("^Click on link in \"([^\"]*)\" View List at \"([^\"]*)\" row and \"([^\"]*)\" column$")
    public void clickOnSuppliedViewListAtRowAndColumn(String viewListName, String ordinal, String column)
        throws Throwable {
        Map<String, String> columnIndexListOptions = getColumnIndexListOptions(column, viewListName, ordinal);
        FluentWait<ClickTableCellUrl> waiter = waiter(new ClickTableCellUrl(), 60, 5)
            .withMessage(String.format("Failed click on link in view list \"%s\" at \"%s\" row and \"%s\" column",
                viewListName, ordinal, column));
        waiter.until((ClickTableCellUrl callback) -> callback.test(columnIndexListOptions));
        logger().info(
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
        logger().info(String.format("- STEP: Row actions \"%s\" is clicked - PASSED.", rowAction));
    }

    @And("^\"([^\"]*)\" list element has cell value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void listElementWith(String ordinal, String value, String columnName) throws Throwable {
        int row = extractNumericValue(ordinal);
        String expectedValue = parameterProvider.getValueOrParameterAsString(value);
        FluentWait<ViewListModel> waiter = waiter(new ViewListModel(), 120, 5);
        waiter.withMessage(String.format("\"%s\" list element value \"%s\" at column \"%s\" was not found", ordinal,
            expectedValue, columnName));
        waiter.until((ViewListModel callback) -> callback.containsDataAt(row, expectedValue, columnName));
        logger().info(String.format("- STEP: \"%s\" list element has cell value \"%s\" at column \"%s\"  - PASSED.",
            ordinal, expectedValue, columnName));
    }

    @And("^Table \"([^\"]*)\" contains cell value \"([^\"]*)\" at column \"([^\"]*)\" on \"([^\"]*)\" row$")
    public void listElementWithFromTable(String tableName, String value, String columnName, String ordinal)
        throws Throwable {
        int row = extractNumericValue(ordinal);
        String expectedValue = parameterProvider.getValueOrParameterAsString(value);
        FluentWait<ViewListModel> waiter = waiter(new ViewListModel(), 30, 1);
        waiter.withMessage(
            String.format("Table \"%s\" did not contain expected cell value \"%s\" at column \"%s\" on \"%s\" row",
                tableName, expectedValue, columnName, ordinal));
        waiter.until((ViewListModel callback) -> callback.containsCellValue(row, expectedValue, columnName, tableName));
        logger().info(String.format(
            "- STEP: Table \"%s\" did contains expected cell value \"%s\" at column \"%s\" on \"%s\" row  - PASSED.",
            tableName, expectedValue, columnName, ordinal));
    }

    @InputParameter(name = "plus-menu-item")
    String plusMenuItem;

    @And("^\"([^\"]*)\" list element has cell value \"([^\"]*)\" at column \"([^\"]*)\" within (\\d+) seconds?$")
    public void containsElementWithin(String ordinal, String value, String columnName, int seconds) throws Throwable {
        String expectedValue = parameterProvider.getValueOrParameterAsString(value);
        PlusActions scenario = (PlusActions) getScenarioInstance(PlusActions.class);
        int row = extractNumericValue(ordinal);
        FluentWait<ViewListModel> waiter = waiter(new ViewListModel(), seconds, 20);
        waiter.withMessage(String.format(
            "\"%s\" list element did not contain expected cell value \"%s\" at column \"%s\" within \"%s\" seconds  - PASSED.",
            ordinal, expectedValue, columnName, seconds));
        waiter.until((ViewListModel callback) -> {
            scenario.checkPlusMenu(plusMenuItem);
            return callback.containsDataAt(row, expectedValue, columnName);
        });
        logger().info(String.format(
            "- STEP: \"%s\" list element has cell value \"%s\" at column \"%s\" within \"%s\" seconds  - PASSED.",
            ordinal, expectedValue, columnName, seconds));
    }

    @And("^\"([^\"]*)\" list element has cell value \"([^\"]*)\" at column \"([^\"]*)\" polling (\\d+) seconds?$")
    public void containsElementAt(String ordinal, String value, String columnName, int seconds) throws Throwable {
        int row = extractNumericValue(ordinal);
        String expectedValue = parameterProvider.getValueOrParameterAsString(value);
        String arrow = parameterProvider.getValueOrParameterAsString("parameter:navigation");
        String dashboardMenu = parameterProvider.getValueOrParameterAsString("parameter:dashboard-menu");
        FluentWait<ViewListModel> waiter = waiter(new ViewListModel(), seconds, 20);
        waiter.withMessage(String.format(
            "\"%s\" list element did not have cell value \"%s\" at column \"%s\" within \"%s\" seconds.", ordinal,
            expectedValue, columnName, seconds));
        waiter.until((ViewListModel callback) -> {
            loopBack(arrow, dashboardMenu);
            return callback.containsDataAt(row, expectedValue, columnName);
        });
        logger().info(String.format(
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
    public void refreshTillVisible(String ordinal, String status, String columnName, int seconds, String linkText)
        throws Throwable {
        int row = extractNumericValue(ordinal);
        String expectedValue = parameterProvider.getValueOrParameterAsString(status);
        FluentWait<ViewListModel> waiter = waiter(new ViewListModel(), seconds, 5);
        waiter.withMessage(String.format("Status did not switch to \"%s\" within \"%s\" seconds", status, seconds));
        waiter.until((ViewListModel callback) -> {
            seleniumDriver.waitAndClick(seleniumDriver.findElement(By.linkText(linkText)));
            return callback.containsDataAt(row, expectedValue, columnName);
        });
    }

    // TODO migrate to io.cucumber synthax:
    // TODO When List element with values {"INITIATE STOP ACCESS", "Geaccepteerd"}
    // at columns {"Module & Label, "Status & ED"} appears within 450 seconds
    // refreshing "REFRESH MARKTBERICHTEN"
    @When("^First list element with value \"([^\"]*)\" at column \"([^\"]*)\" has status \"([^\"]*)\" at column \"([^\"]*)\" within (\\d+) seconds refreshing \"([^\"]*)\"$")
    public void hasStatusWithinTimeout(String value, String columnName, String status, String secondColumnName,
                                       int seconds, String linkText) throws Throwable {
        String expectedValue = parameterProvider.getValueOrParameterAsString(value);
        FluentWait<ViewListModel> waiter = waiter(new ViewListModel(), seconds / 2, 5);
        waiter.withMessage(String.format("Status did not switch to \"%s\" within \"%s\" seconds", status, seconds));
        waiter.until((ViewListModel callback) -> {
            seleniumDriver.waitAndClick(seleniumDriver.findElement(By.linkText(linkText)));
            return callback.fetchListRowsIndices(expectedValue, columnName).size() >= 1;
        });
        waiter = waiter(new ViewListModel(), seconds / 2, 5);
        waiter.until((ViewListModel callback) -> {
            seleniumDriver.waitAndClick(seleniumDriver.findElement(By.linkText(linkText)));
            int row = callback.fetchListRowsIndices(value, columnName).get(0);
            return callback.containsDataAt(row, status, secondColumnName);
        });
    }

    @And("^Cell values? from selected rows? and column \"([^\"]*)\" (?:are|is) checked$")
    public void checkDataSelection(String columnName) throws Throwable {
        ViewListModel viewListModel = new ViewListModel();
        List<String> cellSelection = viewListModel.fetchDataSelection(columnName);
        assertThat(String.format("Data selection at column \"%s\" is empty", columnName), cellSelection,
            not(hasSize(0)));
        parameterProvider.put(columnName, cellSelection);
        logger().info(String.format(
            "- STEP: Cell value(s) from selected row(s) and column \"%s\" is/are checked - PASSED.", columnName));
    }

    @Then("^\"([^\"]*)\" List element with value at column \"([^\"]*)\" is checked$")
    public void storeColumnValueInSharedProperties(String ordinal, String columnName) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        int row = extractNumericValue(ordinal);
        String value = new ViewListModel().getCellValueAt(row, columnName);
        boolean success = StringUtils.isNotBlank(value);
        assertThat(String.format("\"%s\" list element didn't contain any value at column \"%s\"", ordinal, columnName),
            success, is(true));
        String splitValue = value.split(" ")[0];
        parameterProvider.put(columnName, splitValue);
        logger().info(String.format("- STEP: \"%s\" list element with value at column \"%s\" is checked - PASSED.",
            ordinal, columnName));
    }

    @Then("^\"([^\"]*)\" list element with date interval at column \"([^\"]*)\" from table \"([^\"]*)\" is \"([^\"]*)\"$")
    public void checkDateIntervalTableCell(String ordinal, String columnName, String tableName, String interval) throws Throwable {
        int row = extractNumericValue(ordinal);
        String value = new ViewListTestObject().getValueAt(row, columnName, tableName);
        boolean success = StringUtils.isNotBlank(value);
        assertThat(String.format("\"%s\" list element didn't contain any value at column \"%s\"", ordinal, columnName),
            success, is(true));
        String[] splitValue = value.split("\\s+");
        parameterProvider.put(columnName, value);
        Assert.assertThat("Comparison of two dates expression conversion failure", checkTimeBetween(splitValue[0],
            splitValue[1], interval), Matchers.equalTo(0));
    }


    @Then("^List element matching value \"([^\"]*)\" at column \"([^\"]*)\" from table \"([^\"]*)\" is checked$")
    public void storeColumnValueInParameterProvider(String match, String columnName, String tableName) throws Throwable {
        List<String> columnData = new ViewListTestObject().fetchColumnData(tableName, columnName);
        Optional<String> first = columnData.stream().filter(element -> element.contains(match)).findAny();
        assertThat(String.format("\"%s\" list element didn't contain value \"%s\" at column \"%s\"", tableName, match, columnName),
            first.isPresent(), is(true));
        parameterProvider.put(columnName, first.get());
        logger().info(String.format("- STEP: \"%s\" list element with value at column \"%s\" is checked - PASSED.", tableName, columnName));

    }

    @Then("^List element with value at column \"([^\"]*)\" from table \"([^\"]*)\" is checked$")
    public void storeColumnValueInParameterProvider(String columnName, String tableName) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        List<String> columnData = new ViewListModel().fetchColumnData(tableName, columnName);
        boolean success = CollectionUtils.isNotEmpty(columnData);
        assertThat(
            String.format("\"%s\" list element didn't contain any value at column \"%s\"", tableName, columnName),
            success, is(true));
        parameterProvider.put(columnName, columnData.get(0));
        logger().info(String.format("- STEP: \"%s\" list element with value at column \"%s\" is checked - PASSED.",
            tableName, columnName));
    }

    @And(
        "^\"([^\"]*)\" element of table \"([^\"]*)\" at currency column \"([^\"]*)\" is sum of$")
    public void checkCurrencyAmountDableDataAsSum(String ordinal, String table, String columnName, final DataTable subAmounts)
        throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        int row = extractNumericValue(ordinal);
        String currencyValue = new ViewListTestObject().getCurrencyValueAt(row, columnName, table);
        boolean success = StringUtils.isNotBlank(currencyValue);
        assertThat(String.format("\"%s\" list element didn't contain any value at column \"%s\"", ordinal, columnName),
            success, is(true));
        Integer actualAmount = amountInCurrencyAsInt(currencyValue);
        List<String> amounts = subAmounts.asList(String.class);
        Integer sum = sumOf(amounts);
        assertThat("Total advance prepaid amount %s is not equal to sum of sub amounts %s", actualAmount, equalTo(sum));
    }


    @And("^Select \"([^\"]*)\" List row having cell value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void selectListRows(String ordinal, String value, String columnName) throws Throwable {
        int row = extractNumericValue(ordinal);
        ViewListModel viewListModel = new ViewListModel();
        boolean success = viewListModel.selectListRow(row, value, columnName);
        String message = String.format("\"%s\" list row didn't contain value \"%s\" at column \"%s\"", ordinal, value,
            columnName);
        assertThat(message, success, is(true));
        logger().info(String.format("- STEP: \"%s\" list row having cell value \"%s\" at column \"%s\" - PASSED.",
            ordinal, value, columnName));

    }

    @And("^Plus actions at \"([^\"]*)\" list row having cell value \"([^\"]*)\" at column \"([^\"]*)\" are open$")
    public void openPlusActions(String ordinal, String value, String columnName) throws Throwable {
        int row = extractNumericValue(ordinal);
        String expectedValue = parameterProvider.getValueOrParameterAsString(value);
        ViewListModel viewListModel = new ViewListModel();
        boolean success = viewListModel.openListPlusActions(row);
        String message = String.format("\"%s\" row list didn't have cell value \"%s\" at column \"%s\"", ordinal,
            expectedValue, columnName);
        assertThat(message, success, is(true));
        logger().info(String.format(
            "- STEP: Plus actions at \"%s\" list row having cell value \"%s\" at column \"%s\" are opened - PASSED.",
            ordinal, value, columnName));
    }

    @And("^\"([^\"]*)\" List rows? having cell value \"([^\"]*)\" at column \"([^\"]*)\" (?:is|are) selected$")
    public void selectListRowHavingCellValueAtColumn(int row, String value, String columnName) throws Throwable {
        ViewListModel viewListModel = new ViewListModel();
        boolean success = viewListModel.selectListRows(row, value, columnName);
        String message = String.format("\"%s\" list row(s) didn't have cell value \"%s\" at column \"%s\"", row, value,
            columnName);
        assertThat(message, success, is(true));
        logger().info(String.format(
            "- STEP: \"%s\" list row(s) having cell value \"%s\" at column \"%s\" is/are selected - PASSED.", row,
            value, columnName));
    }

    @Then("^Selected List rows have cell value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void checkSelectionData(String value, String columnName) throws Throwable {
        ViewListModel viewListModel = new ViewListModel();
        FluentWait<ViewListModel> waiter = waiter(new ViewListModel(), 10, 2).withMessage("Selected table is empty");
        waiter.until((ViewListModel callback) -> !callback.fetchDataSelection(columnName).isEmpty());
        List<String> cellSelection = viewListModel.fetchDataSelection(columnName);
        String message = String.format("Value \"%s\" wasn't found in any row of \"%s\" column", columnName);
        assertThat(message, cellSelection.get(0).contains(value), is(true));
        logger().info(String.format("- STEP: Selected List rows have cell value \"%s\" at column \"%s\" - PASSED.",
            value, columnName));
    }

    @And("^List View action is \"([^\"]*)\"$")
    public void getListAction(String name) throws Throwable {
        boolean success = new GetListAction().test(name);
        assertThat(String.format("List action \"%s\" is undefined.", name), success, is(true));
        logger().info(String.format("- STEP: List view action is \"%s\" - PASSED.", name));
    }

    @And("^View List element \"([^\"]*)\" is collected as parameter at \"([^\"]*)\" list row$")
    public void collectViewListElementAsParameter(String viewListElement, String ordinal) {
        String parameter = getViewListElementAtRow(viewListElement, ordinal);
        parameterProvider.put(viewListElement, parameter);
        logger().info(String.format("View List element \"%s\" is collected as parameter at \"%s\" list row",
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
            throw new CucumberException(String.format("Input value \"%s\" didn't contain any numeric substring"));
        }
        if (StringUtils.isNumeric(input))
            return input;
        else
            throw new CucumberException(message);
    }

    private String getViewListElementAtRow(String viewListElement, String ordinal) {
        int row = extractNumericValue(ordinal);
        ViewListModel viewListModel = new ViewListModel();
        String parameter = viewListModel.getCellValueAt(row, viewListElement);
        assertThat(String.format("View List element '%s' was not found.", viewListElement),
            StringUtils.isNotBlank(parameter), is(true));
        return parameter;
    }

    @And("^\"([^\"]*)\" list is not empty$")
    public void viewIsNotEmpty(String tableTitle) throws Throwable {
        Map<String, String> options = new HashMap<>();
        options.put("tableTitle", tableTitle);
        boolean success = new CheckEmptyTableAction().test(options);
        assertThat(String.format(tableTitle + " doesn't exist"), success, is(true));
        logger().info(String.format("- STEP: \"%s\" list is not empty - PASSED.", tableTitle));
    }

    @And("^\"([^\"]*)\" list is empty$")
    public void isViewEmpty(String tableTitle) throws Throwable {
        Map<String, String> options = new HashMap<>();
        options.put("table", tableTitle);
        boolean hasData = new CheckEmptyTableAction().test(options);
        assertThat(String.format(tableTitle + " is not empty"), hasData, is(false));
        logger().info(String.format("- STEP \"%s\" list is empty - PASSED.", tableTitle));
    }

    @And("^Table \"([^\"]*)\" contains value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void viewListContainsValueAtColumn(String table, String value, String column) throws Throwable {
        ViewListModel viewListModel = new ViewListModel();
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        List<String> columnData = viewListModel.fetchColumnData(table, column);
        List<String> found = columnData.stream().filter(element -> element.contains(inputValue))
            .collect(Collectors.toList());
        String message = String.format("Table \"%s\" didn't contain value \"%s\" at column \"%s\"", table, value,
            column);
        assertThat(message, found, not(empty()));
        logger().info(String.format("- STEP: Table \"%s\" contains value \"%s\" at column \"%s\" - PASSED.", table,
            value, column));
    }


    @And("^Table \"([^\"]*)\" contains value \"([^\"]*)\" at column \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void viewListContainsValueAtColumnWithFixedTime(String table, String value, String column, int waitingTime) throws Throwable {
        Sleeper.sleepTightInSeconds(waitingTime);
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        ViewListModel viewListModel = new ViewListModel();
        List<String> columnData = viewListModel.fetchColumnDataNow(table, column, true);
        List<String> found = columnData.stream().filter(element -> element.contains(inputValue)).collect(Collectors.toList());
        String message = String.format("Table \"%s\" didn't contain value \"%s\" at column \"%s\"", table, value, column);
        assertThat(message, found, not(empty()));
        logger().info(String.format("- STEP: Table \"%s\" contains value \"%s\" at column \"%s\" - PASSED.", table, value, column));
    }

    @And("^\"([^\"]*)\" in the first \"([^\"]*)\" row of \"([^\"]*)\" table is \"([^\"]*)\"$")
    public void firstRowByOptionContains(String columnToSearch, String optionToSearch, String list,
                                         String textToCheck) {
        Map<String, String> options = new HashMap<>();
        options.put("column", columnToSearch);
        options.put("option", optionToSearch);
        options.put("list", list);
        options.put("text", textToCheck);
        boolean success = new CheckFirstRowByOption().test(options);
        assertThat(String.format("The column cannot be found, no rows were found or value does not match"
                + "the one requested. Please check all the parameters passed, remember that they are case sensitive!"),
            success, is(true));
        logger().info(String.format("- STEP: \"%s\" in the first \"%s\" row of \"%s\" table is \"%s\" - PASSED.",
            columnToSearch, optionToSearch, list, textToCheck));
    }


    @Then("^Invoice Amounts are among$")
    public void checkInvoicesAmounts(final DataTable dbTable) {
        List<List<String>> info = dbTable.raw();

        String amountInvoice1 = info.get(1).get(0);
        String amountInvoice2 = info.get(1).get(1);
        String amountInvoice3 = info.get(1).get(2);
        ContractPage cp = new ContractPage();
        String actualValuesOfInvoices = cp.getActualValuesOfInvoicesAsString();
        if (amountInvoice1.equals(actualValuesOfInvoices)){
            logger().info(String.format("- STEP: Values of invoices \"%s\" are correct - PASSED.", amountInvoice1));

        }
        else if (amountInvoice2.equals(actualValuesOfInvoices))
            logger().info(String.format("- STEP: Values of invoices \"%s\" are correct - PASSED.", amountInvoice2));

        else if (amountInvoice3.equals(actualValuesOfInvoices))
            logger().info(String.format("- STEP: Values of invoices \"%s\" are correct - PASSED.", amountInvoice3));

        else throw new CucumberException("Actual invoices values " + actualValuesOfInvoices + " don't match expected ones");

    }


    @Then("^Balance is among values \"([^\"]*)\"$")
    public void checkValue (String expectedSaldo) {
        ContractPage cp = new ContractPage();
        String actualSaldo = cp.getBalance();
        assertThat(String.format("Actual credit invoice \"%s\" differs from the expected one \"%s\" on saldo", actualSaldo, expectedSaldo), expectedSaldo, containsString(actualSaldo));
        logger().info(String.format("- STEP: Saldo \"%s\" is correct - PASSED.", expectedSaldo));
    }


    @And("^Wait for (\\d+) seconds$")
    public void waitForSeconds(int seconds) {
        Sleeper.sleepTightInSeconds(seconds);
    }

    @Override
    @After("@DWP, @CORE, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
