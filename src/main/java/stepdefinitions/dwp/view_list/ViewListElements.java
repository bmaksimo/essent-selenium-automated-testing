package stepdefinitions.dwp.view_list;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.awaitility.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.navigation.NavigationElements;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.awaitility.Duration.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;
public class ViewListElements extends NavigationElements {

    private static final String INTERACTIONS = "InteractionsOnAccount";
    private static final String PAYMENTS = "PaymentPlansOnAccount";

    private class CheckViewListHeader implements Predicate<String> {
        @Override
        public boolean test(String header) {
            int sec = 7;
            Map<String, Object> options = new HashMap<>();
            options.put("schedule_seconds", sec);
            options.put("header", header);
            return executeJavascriptTest("TrCheckViewListHeader", options);
        }
    }

    private class CheckSubmitCard implements Predicate<String> {
        @Override
        public boolean test(String item) {
            int sec = 7;
            Map<String, Object> options = new HashMap<>();
            options.put("schedule_seconds", sec);
            options.put("item", item);
            return executeJavascriptTest("TrCheckSubmitCard", options);
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

    private class ViewListModel {
        DefaultTableModel getViewTableModel() {
            DefaultTableModel tableModel = new DefaultTableModel();
            Map viewTable = executeJavascriptMethod("TrGetTableModel", new HashMap<>());
            List columnNames = (List) viewTable.get("column_names");
            List rows = getData(viewTable);
            tableModel.setColumnIdentifiers(columnNames.toArray());
            for (Object row1 : rows) {
                Object[] row = ((List) row1).toArray();
                tableModel.addRow(row);
            }
            return tableModel;
        }

        boolean containsDataAt(int row, String value, String columnName) {
            String cell = getCellValueAt(row, columnName);
            boolean success = cell.contains(value);
            return success;
        }

        boolean containsDataAtFromTable(int row, String value, String columnName, String tableName) {
            String cell = getCellValueAtFromTable(row, columnName, tableName);
            boolean success = cell.contains(value);
            return success;
        }

        boolean selectListRow(int row) {
            Map<String, Object> options = new HashMap<>();
            options.put("index", row);
            boolean success = executeJavascriptTest("TrSelectListRow", options);
            return success;
        }

        boolean openListPlusActions(int row) {
            Map<String, Object> options = new HashMap<>();
            options.put("index", row);
            boolean success = executeJavascriptTest("TrOpenListPlusActions", options);
            return success;
        }

        List<Integer> fetchListRowsIndices(String value, String columnName) {
            Map viewTable = executeJavascriptMethod("TrGetTableModel", new HashMap<>());
            int index = getColumnNameIndex(columnName, viewTable);
            if (index < 0) {
                fail(String.format("View List did not contain column %s", columnName));
            }
            List<ArrayList> rows = getData(viewTable);
            AtomicInteger idx = new AtomicInteger(1);
            List<Integer> collect = IntStream.range(1, rows.size() + 1)
                .filter(i ->
                    idx.compareAndSet(i, i + 1) & ((ArrayList<String>) rows.get(i - 1)).get(index).contains(value))
                .boxed()
                .collect(Collectors.toList());
            return collect;
        }

        boolean selectListRow(int row, String value, String columnName) {
            List<Integer> indices = fetchListRowsIndices(value, columnName);
            return indices.size() > 0 && row <= indices.size();
        }

        boolean selectListRows(int numRows, String value, String columnName) {
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

        List<String> fetchDataSelection(String columnName) {
            Map<String, Object> options = new HashMap<>();
            options.put("include_selection", true);
            Map viewTable = executeJavascriptMethod("TrFetchDataSelection", options);
            int index = getColumnNameIndex(columnName, viewTable);
            if (index < 0) {
                fail(String.format("View List did not contain column %s", columnName));
            }
            List<ArrayList> rows = getData(viewTable);
            List selection;
            selection = rows.stream().map((e) -> e.get(index)).collect(Collectors.toList());
            return selection;
        }

        List<String> fetchColumnData(String table, String columnName) {
            Map<String, Object> options = new HashMap<>();
            options.put("include_selection", false);
            options.put("table", table);
            Map viewTable = executeJavascriptMethod("TrFetchDataSelection", options);
            int index = getColumnNameIndex(columnName, viewTable);
            if (index < 0) {
                fail(String.format("View List did not contain column %s", columnName));
            }
            List<ArrayList> rows = getData(viewTable);
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
                fail(String.format("View List did not contain column %s", columnName));
            }
            List<ArrayList> rows = getData(viewTable);
            if (row > rows.size()) {
                fail(String.format("--Error in Test Input: Given %s row index cannot be greater that actual View List size %s", row, rows.size()));
            }
            List currentRow = rows.get(row - 1);
            return (String) currentRow.get(index);
        }

        private String getCellValueAtFromTable(int row, String columnName, String tableName) {
            logger().info("STEP: JAVASCRIPT_FETCH_DATA");
            Map viewTable = executeJavascriptMethod("TrGetTableModel", new HashMap<>());
            logger().info(" - RESULT: " + viewTable);
            int index = getColumnNameIndexFromTable(columnName, tableName);
            if (index < 0) {
                fail(String.format("View List did not contain column %s", columnName));
            }
            List<ArrayList> rows = getData(viewTable);
            if (row > rows.size()) {
                fail(String.format("--Error in Test Input: Given %s row index cannot be greater that actual View List size %s", row, rows.size()));
            }
            List currentRow = rows.get(row - 1);
            return (String) currentRow.get(index);
        }
        private List<ArrayList> getData(Map viewTable) {
            return (List) viewTable.get("rows");
        }

        private int getColumnNameIndex(String columnName, Map viewTable) {
            List<String> columnNames = (List) viewTable.get("column_names");
            return columnNames.indexOf(columnName);
        }

        private int getColumnNameIndexFromTable(String columnName, String tableName) {
            String tableNameSelector;
            if ("Interacties".equalsIgnoreCase(tableName)) {
                tableNameSelector = INTERACTIONS;
                List<WebElement> columns = webDriver.getDriver().findElements(By.xpath("//list[@list-key='"+tableNameSelector+"']//div//table[@class='list__content']//thead//tr//th"));
                List<String> mappedColumns = columns.stream().map(c -> c.getText().toLowerCase()).collect(Collectors.toList());

                return mappedColumns.indexOf(columnName.toLowerCase());
            }

            if ("Afbetalingsplannen".equalsIgnoreCase(tableName)) {
                tableNameSelector = PAYMENTS;
                List<WebElement> columns = webDriver.getDriver().findElements(By.xpath("//list[@list-key='"+tableNameSelector+"']//div//table[@class='list__content']//thead//tr//th"));
                List<String> mappedColumns = columns.stream().map(c -> c.getText().toLowerCase()).collect(Collectors.toList());

                return mappedColumns.indexOf(columnName.toLowerCase());
            }


            return 0;
        }
    }

    private class ClickTableCellUrl implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrClickTableCellUrl", options);
        }
    }

    private class ClickTableRowAction implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrClickTableRowAction", options);
        }
    }

    private class CheckModalDialog implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrCheckModalDialog", options);
        }
    }

    private class PaymentDetailsModalSaveAction implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrPaymentDetailsModalSaveAction", options);
        }
    }

    private class CheckEmptyTableAction implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrCheckEmptyTable", options);
        }
    }

    private class PaymentMethodSwitch implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            Map result = executeJavascriptMethod("TrSwitchPaymentMethod", options);
            String status = ((String) result.get("status"));
            boolean success = StringUtils.equals("PASSED", status);
            if (success) {
                String switchedPaymentMethod = ((String) result.get("paymentMethod")).equalsIgnoreCase("string:OV") ?
                    "Overschrijving" : "Domiciliëring";
                parameterProvider.put("paymentMethod", switchedPaymentMethod);
            }

            return success;
        }
    }

    private class PaymentDetailsIBANChange implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrAddIBANToPaymentDetails", options);
        }
    }

    private class CheckFirstRowByOption implements Predicate<Map> {
        @Override
        public boolean test(Map options) { return executeJavascriptTest("TrCheckFirstRowByOption", options); }
    }

    @Before("@DWP, @CORE, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^View list header is \"([^\"]*)\"$")
    public void checkViewListHeader(String header) throws Throwable {
        boolean success = new CheckViewListHeader().test(header);
        assertThat(String.format("View list did not contain header '%s'", header),
            success, is(true));
        parameterProvider.put("currrent-view-list", header);
    }

    @When("^View list header is \"([^\"]*)\" appears within (\\d+) seconds?$")
    public void checkViewListHeaderUntil(String header, int seconds) throws Throwable {
        CheckViewListHeader checkViewListHeader = new CheckViewListHeader();
        given().await()
            .pollInterval(FIVE_HUNDRED_MILLISECONDS)
            .pollDelay(TWO_SECONDS)
            .atMost(new Duration(seconds, SECONDS)).until(()-> checkViewListHeader.test(header));
    }

    @When("^View List is empty$")
    public void checkTableModel() throws Throwable {
        javax.swing.table.TableModel viewTableModel = new ViewListModel().getViewTableModel();
        boolean success = viewTableModel.getRowCount() == 0;
        assertThat("View Table list is not empty",
            success, is(true));
    }

    @When("^Click on link in View List at ([^\"]*) row and \"([^\"]*)\" column$")
    public void clickOnViewListAtRowAndColumn(String ordinal, String column) throws Throwable {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        Map<String, String> columnIndexListOptions = new HashMap<>();
        columnIndexListOptions.put("column", column);
        columnIndexListOptions.put("index", rowIndex);
        given().await()
            .ignoreExceptions()
            .pollInterval(TWO_SECONDS)
            .pollDelay(new Duration(FIVE_SECONDS.getValue(), SECONDS))
            .atMost(new Duration(TEN_SECONDS.getValue(), SECONDS)).until(()-> new ClickTableCellUrl().test(columnIndexListOptions));
    }

    @When("^Click on link in View List at ([^\"]*) row and \"([^\"]*)\" column polling (\\d+) seconds?$")
    public void clickOnViewListAtRowAndColumn(String ordinal, String column, int seconds) throws Throwable {
        webDriver.waitForRequestsToFinish();
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        Map<String, String> columnIndexListOptions = new HashMap<>();
        columnIndexListOptions.put("column", column);
        columnIndexListOptions.put("index", rowIndex);
        ClickTableCellUrl clickFunction = new ClickTableCellUrl();
        given().await()
            .ignoreExceptions()
            .pollInterval(ONE_SECOND)
            .pollDelay(new Duration(seconds / 2, SECONDS))
            .atMost(new Duration(seconds, SECONDS)).until(()-> clickFunction.test(columnIndexListOptions));
    }

    @When("^Click on link in \"([^\"]*)\" View List at ([^\"]*) row and \"([^\"]*)\" column$")
    public void clickOnSuppliedViewListAtRowAndColumn(String viewListName, String ordinal, String column) throws Throwable {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        Map<String, String> columnIndexListOptions = new HashMap<>();
        columnIndexListOptions.put("column", column);
        columnIndexListOptions.put("view_list_name", viewListName);
        columnIndexListOptions.put("index", rowIndex);
        boolean success = new ClickTableCellUrl().test(columnIndexListOptions);
        assertThat(String.format("View list did not contain URL at row %s header '%s' and '%s' view list", ordinal, column, viewListName),
            success, is(true));
    }

    @When("^Modal \"([^\"]*)\" is displayed$")
    public void checkModalDialogOpen(String headerText) {
        Map<String, String> options = new HashMap<>();
        options.put("headerText", headerText);
        boolean success = new CheckModalDialog().test(options);
        assertThat(String.format("Action row %s was not found", headerText), success, is(true));
    }

    @When("Payment method is switched$")
    public void switchPaymentMethod() {
        boolean success = new PaymentMethodSwitch().test(new HashMap<>());
        assertThat("Payment method has not been switched", success, is(true));
    }

    @And("IBAN is ([^\"]*) if not empty$")
    public void changeIBAN(String iban) {
        Map<String, String> options = new HashMap<>();
        options.put("iban", iban);
        boolean success = new PaymentDetailsIBANChange().test(options);
        assertThat("IBAN has failed to be updated", success, is(true));
    }

    @And("Payment details are confirmed$")
    public void clickSaveOnPaymentDetailsModal() {
        boolean success = new PaymentDetailsModalSaveAction().test(null);
        assertThat("Billing customer update has failed.", success, is(true));
    }

    @Then("^Row actions \"([^\"]*)\" is clicked$")
    public void clickOnRowAction(String rowAction) {
        Map<String, String> options = new HashMap<>();
        options.put("rowAction", rowAction);
        boolean success = new ClickTableRowAction().test(options);
        assertThat(String.format("Action row %s was not found", rowAction), success, is(true));
    }

    @And("^([^\"]*) list element has cell value ([^\"]*) at column ([^\"]*)$")
    public void listElementWith(String ordinal, String value, String columnName) throws Throwable {
        int row = extractNumericValue(ordinal);
        boolean success = new ViewListModel().containsDataAt(row, value, columnName);
        assertThat(String.format("View list did not contain cell value %s at %s row, column '%s'", value, ordinal, columnName),
            success, is(true));
    }

    @And("^Table ([^\"]*) contains cell value ([^\"]*) at column ([^\"]*) on ([^\"]*) row$")
    public void listElementWithFromTable(String tableName, String value, String columnName, String ordinal) throws Throwable {
        int row = extractNumericValue(ordinal);
        boolean success = new ViewListModel().containsDataAtFromTable(row, value, columnName, tableName);
        assertThat(String.format("View list did not contain cell value %s at %s row, column '%s'", value, ordinal, columnName),
            success, is(true));
    }

    @And("^([^\"]*) list element has cell value ([^\"]*) at column \"([^\"]*)\" polling (\\d+) seconds?$")
    public void containsElementAt(String ordinal, String value, String columnName, int seconds) throws Throwable {
        int row = extractNumericValue(ordinal);
        ViewListModel viewListModel = new ViewListModel();
        given()
            .await()
            .ignoreExceptions()
            .pollInterval(new Duration(20, SECONDS))
            .pollDelay(TWO_SECONDS)
            .atMost(new Duration(seconds, SECONDS)).until(()->
            loopBack() &&
                viewListModel.containsDataAt(row, value, columnName));
    }

    private boolean loopBack()  {
        String arrow = parameterProvider.getValueOrParameterAsString("parameter:navigation");
        String dashboardMenu = parameterProvider.getValueOrParameterAsString("parameter:dashboard-menu");
        clickTopArrow(arrow);
        clickDashboardMenu(dashboardMenu);
        return true;
    }

    @And("^Cell values? from selected rows? and column \"([^\"]*)\" (?:are|is) checked$")
    public void checkDataSelection(String columnName) throws Throwable {
        ViewListModel viewListModel = new ViewListModel();
        List<String> cellSelection = viewListModel.fetchDataSelection(columnName);
        parameterProvider.put(columnName, cellSelection);
        assertThat(String.format("Data selection at column %s is empty", columnName),
            cellSelection, not(hasSize(0)));
    }

    @And("^Payment method is updated$")
    public void listSwitchedPaymentMethod() throws Throwable {
        String updatedPaymentMethodName = parameterProvider.getValueOrParameterAsString("parameter:paymentMethod");
        final String UPDATED_PAYMENT_METHOD = "//list-simple-two-liner-cell[contains(@line-2,'" + updatedPaymentMethodName + "')]";

        WebElement element = webDriver.findElementOrNull(By.xpath(UPDATED_PAYMENT_METHOD));

        assertThat(String.format("View list did not contain payment method %s", updatedPaymentMethodName),
            element, is(notNullValue()));
    }

    @Then("^([^\"]*) List element with value at column \"([^\"]*)\" is checked$")
    public void storeColumnValueInSharedProperties(String ordinal, String columnName) throws Throwable {
        int row = extractNumericValue(ordinal);
        String value = new ViewListModel().getCellValueAt(row, columnName);
        boolean success = StringUtils.isNotBlank(value);
        assertThat(String.format("View list did not contain any value at %s row, column '%s'", ordinal, columnName),
            success, is(true));
        String splitValue = value.split(" ")[0];
        parameterProvider.put(columnName, splitValue);
        

    }

    @And("^Select ([^\"]*) List row having cell value ([^\"]*) at column ([^\"]*)$")
    public void selectListRows(String ordinal, String value, String columnName) throws Throwable {
        int row = extractNumericValue(ordinal);
        ViewListModel viewListModel = new ViewListModel();
        boolean success = viewListModel.selectListRow(row, value, columnName);
        String message = String.format("View list did not contain cell value %s at %s row, column '%s'", value, ordinal, columnName);
        assertThat(message,
            success, is(true));
        success = viewListModel.selectListRow(row);
        assertThat(message,
            success, is(true));
    }

    @And("^Plus actions at ([^\"]*) list row having cell value \"([^\"]*)\" at column \"([^\"]*)\" are open$")
    public void openPlusActions(String ordinal, String value, String columnName) throws Throwable {
        int row = extractNumericValue(ordinal);
        ViewListModel viewListModel = new ViewListModel();
        boolean success = viewListModel.openListPlusActions(row);
        String message = String.format("View list did not contain cell value %s at %s row, column '%s'", value, ordinal, columnName);
        assertThat(message,
            success, is(true));
    }

    @And("^([^\"]*) List rows? having cell value ([^\"]*) at column ([^\"]*) (?:is|are) selected$")
    public void selectListRowHavingCellValueAtColumn(int row, String value, String columnName) throws Throwable {
        ViewListModel viewListModel = new ViewListModel();
        boolean success = viewListModel.selectListRows(row, value, columnName);
        String message = String.format("View list did not contain %s rows having cell value %s at column '%s'", row, value, columnName);
        assertThat(message,
            success, is(true));
    }


    @Then("^Selected List rows have cell value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void checkSelectionData(String value, String columnName) throws Throwable {
        ViewListModel viewListModel = new ViewListModel();
        given()
            .ignoreExceptions()
            .pollDelay(Duration.FIVE_HUNDRED_MILLISECONDS)
            .pollInterval(TWO_SECONDS)
            .atMost(TEN_SECONDS)
            .until(() -> !viewListModel.fetchDataSelection(columnName).isEmpty());
        List<String> cellSelection = viewListModel.fetchDataSelection(columnName);
        assertThat(String.format("Selection of rows by column %s was empty", columnName),
            cellSelection.isEmpty(), is(false));
        assertThat(String.format("Selected rows did not contain cell value %s at column %s", value, columnName),
            cellSelection.get(0).contains(value), is(true));
    }

    @When("^Submit Card is ([^\"]*)$")
    public void checkSubmitCard(String item) throws Exception {
        boolean success = new CheckSubmitCard().test(item);
        assertThat(String.format("Submit Card does not contain '%s'", item),
            success, is(true));
    }

    @And("^List View action is \"([^\"]*)\"$")
    public void getListAction(String name) throws Throwable {
        boolean success = new GetListAction().test(name);
        assertThat(String.format("List Action '%s' undefined.", name),
            success, is(true));
    }

    @And("^View List element \"([^\"]*)\" is collected as parameter at ([^\"]*) list row$")
    public void collectViewListElementAsParameter(String viewListElement, String ordinal) {
        String parameter = getViewListElementAtRow(viewListElement, ordinal);
        parameterProvider.put(viewListElement, parameter);
    }

    @And("^View List element \"([^\"]*)\" using \"([^\"]*)\" as alias is collected as parameter at ([^\"]*) list row$")
    public void collectViewListElementWithAliasAsParameter(String viewListElement, String viewListElementAlias, String ordinal) {
        String parameter = getViewListElementAtRow(viewListElement, ordinal);
        parameter = getPossibleNumeric(parameter);
        parameterProvider.put(viewListElementAlias, parameter);
    }

    private String getPossibleNumeric(String input) {
        String[] possibleAccountNumbers = input.split(" ");
        if (possibleAccountNumbers.length > 1) {
            for (int i = 0; i < possibleAccountNumbers.length - 1; i++) {
                if (StringUtils.isNumeric(possibleAccountNumbers[i])) return possibleAccountNumbers[i];
            }
            return "";
        }

        return StringUtils.isNumeric(input) ? input : "";
    }

    private String getViewListElementAtRow(String viewListElement, String ordinal) {
        int row = extractNumericValue(ordinal);
        ViewListModel viewListModel = new ViewListModel();
        String parameter = viewListModel.getCellValueAt(row, viewListElement);

        assertThat(String.format("View List element '%s' was not found.", viewListElement),
            StringUtils.isNotBlank(parameter), is(true));

        return parameter;
    }

    @And("([^\"]*) list is not empty")
    public void viewIsNotEmpty(String table) throws Throwable {
        Map<String, String> options = new HashMap<>();
        options.put("table", table);
        boolean success = new CheckEmptyTableAction().test(options);
        assertThat(String.format(table + " doesn't exist"),
            success, is(true));
    }

    @And("([^\"]*) list is empty")
    public void isViewEmpty(String header) throws Throwable {
        Map<String, String> options = new HashMap<>();
        options.put("table", header);
        boolean hasData = new CheckEmptyTableAction().test(options);
        assertThat(String.format(header + " is not empty"),
            hasData, is(false));
    }

    @And("^Table ([^\"]*) contains value \"([^\"]*)\" at column ([^\"]*)$")
    public void viewListContainsValueAtColumn(String table, String value, String column) throws Throwable {
        ViewListModel viewListModel = new ViewListModel();
        List<String> columnData = viewListModel.fetchColumnData(table, column);
        List<String> found = columnData.stream().filter(element -> element.contains(value)).collect(Collectors.toList());
        assertThat(String.format("Table %s did not contain %s value at column %s", table, value, column),
            found, not(empty()));
    }

    @And("^\"([^\"]*)\" in the first \"([^\"]*)\" row of \"([^\"]*)\" table is \"([^\"]*)\"$")
    public void firstRowByOptionContains(String columnToSearch, String optionToSearch, String list, String textToCheck) {
        Map<String, String> options = new HashMap<>();
        options.put("column", columnToSearch);
        options.put("option", optionToSearch);
        options.put("list", list);
        options.put("text", textToCheck);
        boolean success = new CheckFirstRowByOption().test(options);
        assertThat(String.format("The column cannot be found, no rows were found or value does not match" +
                "the one requested. Please check all the parameters passed, remember that they are case sensitive!"),
            success, is(true));
    }



    @Override
    @After("@DWP, @CORE, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
