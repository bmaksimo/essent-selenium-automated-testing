package stepdefinitions.dwp.viewlist;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.listview.ViewListTestObject;
import com.essent.testing.dwp.pageobject.salesmarketing.customerdashboard.contracts.ContractPage;
import com.essent.testing.dwp.pageobject.salesmarketing.customerdashboard.workflows.MarketMessagesPage;
import com.essent.testing.table.Filter;
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
import stepdefinitions.dwp.marketmessages.MarketMessagesSteps;
import stepdefinitions.dwp.navigation.NavigationElements;
import stepdefinitions.dwp.overview.DashboardMenu;
import stepdefinitions.dwp.plus.PlusActions;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.billinghouse.testautomation.javascript.testrunner.JsTestRegistry.JS_TR_CLICK_TABLE_CELL_URL;
import static com.billinghouse.testautomation.util.dsl.DateExpressionsUtil.checkTimeBetween;
import static com.billinghouse.testautomation.util.dsl.DateExpressionsUtil.getFormattedEnd;
import static com.billinghouse.testautomation.util.dsl.NumericUtil.amountAsInt;
import static com.billinghouse.testautomation.util.dsl.NumericUtil.checkAmount;
import static com.essent.testing.dwp.constant.DwpConstants.FLEMISCH_LOCALE;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ViewListChecks extends NavigationElements {

    private static final String REPLACEMENT_KEY1 = "REPLACEMENT_KEY1";
    private static final String TRANSACTIONS_BLOCKED_CHECKMARK = "//list[@list-key='TransactionsOnAccount']//td[@class='list__cell cell__text'][${" + REPLACEMENT_KEY1 + "}]//div[@class='customer__status icon-checkmark']";

    private class ViewListNavigation {
        void goToLink(String linkText) {
            seleniumDriver.waitForRequestsToFinish();
            seleniumDriver.findElement(By.linkText(linkText)).click();
        }
    }

    private class ClickTableCellUrl implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            seleniumDriver.waitForRequestsToFinish();
            return executeJavascriptTest(JS_TR_CLICK_TABLE_CELL_URL, options);
        }

        boolean testNow(Map options) {
            return executeJavascriptTestImmediately(JS_TR_CLICK_TABLE_CELL_URL, options, true);
        }
    }

    private Map<String, String> getColumnIndexListOptions(String column, String ordinal) {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        Map<String, String> columnIndexListOptions = new HashMap<>();
        columnIndexListOptions.put("column", column);
        columnIndexListOptions.put("index", rowIndex);

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
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @When("^View List is empty$")
    public void checkTableModel() {
        DefaultTableModel viewTableModel = new ViewListTestObject().getViewTableModel();
        boolean success = viewTableModel.getRowCount() == 0;
        assertThat("View Table list is not empty", success, is(true));
        logger().debug("STEP: View list is empty - PASSED.");
    }

    @Then("^Table \"([^\"]*)\" has matching value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void isMatchingValueAtColumn(String tableName, String match, String columnName) {
        seleniumDriver.waitForRequestsToFinish();
        String inputValue = parameterProvider.getValueOrParameterAsString(match);
        String queryTemplate = "//h2[contains(text(), \"%s\")]/parent::div/parent::div/parent::*/parent::div //table[@class=\"list__content\"]/tbody/tr/td[count(//table/thead/tr/th[.=\"%s\"]/preceding-sibling::th)+1]/*[@line-1=\"%s\" or @line-2=\"%s\"]";
        String query = String.format(queryTemplate, tableName, columnName, inputValue, inputValue);

        try {
            seleniumDriver.findElementWhenPresent(By.xpath(query));
        } catch (Exception e) {
            fail("Table " + tableName + " didn't contain value matching " + inputValue + " at column " + columnName);
        }
    }

    @Then("^Table \"([^\"]*)\" has matching value \"([^\"]*)\" at column \"([^\"]*)\" polling (\\d+) seconds$")
    public void isMatchingValueAtColumnWithPolling(String tableName, String match, String columnName, int waitingTime) {
        List<String> columnData = new ViewListTestObject().fetchColumnData(tableName, columnName);
        String inputValue = parameterProvider.getValueOrParameterAsString(match);
        String arrow = parameterProvider.getValueOrParameterAsString("parameter:navigation");
        String dashboardMenu = parameterProvider.getValueOrParameterAsString("parameter:dashboard-menu");

        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), waitingTime, 1);
        String message = "Table does not contain cell value " + match + " at column " + columnName
            + " within " + waitingTime + " seconds.";
        waiter.withMessage(message);
        waiter.until((ViewListTestObject callback) -> {
            loopBack(arrow, dashboardMenu);
            return columnData.stream().filter(element -> element.contains(inputValue)).findAny();
        });
    }

    @And(
        "^Table \"([^\"]*)\" contains value \"([^\"]*)\" at column \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void viewListContainsValueAtColumnWithFixedTime(
        String table, String value, String column, int waitingTime) {
        Sleeper.sleepTightInSeconds(waitingTime);
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        ViewListTestObject viewListModel = new ViewListTestObject();
        List<String> columnData = viewListModel.fetchColumnDataNow(table, column, true);
        Optional<String> containing =
            columnData.stream().filter(element -> element.contains(inputValue)).findAny();
        String message = "Table " + table + " didn't contain value " + value  + " at column " + column;
        assertThat(message, containing.isPresent(), is(true));
        logger().debug("STEP: Table " + table + " contains value " + value + " at column " + column + " - PASSED.");
    }

    @When("^Click on \"([^\"]*)\" link$")
    public void clickOnLink(String input) {
        String linkText = parameterProvider.getValueOrParameterAsString(input);
        new ViewListNavigation().goToLink(linkText);
        seleniumDriver.waitForRequestsToFinish();
    }

    @When("^Click on link in View List at \"([^\"]*)\" row and \"([^\"]*)\" column$")
    public void clickOnViewListAtRowAndColumn(String ordinal, String column) {
        FluentWait<ClickTableCellUrl> waiter = waiter(new ClickTableCellUrl(), 30, 5);
        String message = "click on link in view list at " + ordinal + " row and " + column + " column";
        waiter.withMessage("Failed " + message);
        waiter.until((ClickTableCellUrl callback) -> callback.test(getColumnIndexListOptions(column, ordinal)));
        logger().debug("STEP: " + message + " - PASSED.");
    }

    @When("^Click on link in View List at \"([^\"]*)\" row and \"([^\"]*)\" column polling (\\d+) seconds?$")
    public void clickOnViewListAtRowAndColumn(String ordinal, String column, int seconds) {
        seleniumDriver.waitForRequestsToFinish();
        FluentWait<ClickTableCellUrl> waiter = waiter(new ClickTableCellUrl(), seconds, 5);
        String message = "click on link in view list at " + ordinal + " row and " + column + " column within " + seconds + " seconds";
        waiter.withMessage("Failed " + message);
        waiter.until((ClickTableCellUrl callback) -> callback.test(getColumnIndexListOptions(column, ordinal)));
        logger().debug("STEP: " + message + " - PASSED.");
        seleniumDriver.waitForRequestsToFinish();
    }

    @When("^Click on link in View List at \"([^\"]*)\" row and \"([^\"]*)\" column waiting for (\\d+) seconds$")
    public void clickOnViewListAtRowAndColumnFixedWait(String ordinal, String column, int seconds) {
        Sleeper.sleepTightInSeconds(seconds);
        new ClickTableCellUrl().testNow(getColumnIndexListOptions(column, ordinal));
    }

    @When("^Click on link in \"([^\"]*)\" View List at \"([^\"]*)\" row and \"([^\"]*)\" column$")
    public void clickOnSuppliedViewListAtRowAndColumn(String viewListName, String ordinal, String column) {
        Map<String, String> columnIndexListOptions = getColumnIndexListOptions(column, ordinal);
        FluentWait<ClickTableCellUrl> waiter = waiter(new ClickTableCellUrl(), 60, 5);
        String message = "click on link in view list " + viewListName + " at " + ordinal + " row and " + column + " column";
        waiter.withMessage("Failed " + message);
        waiter.until((ClickTableCellUrl callback) -> callback.test(columnIndexListOptions));
        logger().debug("STEP: " + message + " - PASSED.");

    }

    @And("^\"([^\"]*)\" list element has cell value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void listElementWith(String ordinal, String value, String columnName) {
        int row = extractNumericValue(ordinal);
        String expectedValue = parameterProvider.getValueOrParameterAsString(value);
        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), 120, 5);
        String message = ordinal + " list element value " + expectedValue + " at column " + columnName;
        waiter.withMessage(message + " was not found");
        waiter.until((ViewListTestObject callback) -> callback.containsDataAt(row, expectedValue, columnName));
        logger().debug("STEP: " + message + " - PASSED.");
    }

    @And("^Table \"([^\"]*)\" contains cell value \"([^\"]*)\" at column \"([^\"]*)\" on \"([^\"]*)\" row$")
    public void listElementWithFromTable(String tableName, String value, String columnName, String ordinal) {
        int row = extractNumericValue(ordinal);
        String expectedValue = parameterProvider.getValueOrParameterAsString(value);
        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), 30, 1);
        String message = "Table " + tableName + " did not contain expected cell value "
            + expectedValue + " at column " + columnName + " on " + ordinal + " row";
        waiter.withMessage(message);
        waiter.until((ViewListTestObject callback) -> callback.containsCellValue(row, expectedValue, columnName, tableName));
        logger().debug("STEP: Table " + tableName + " did contains expected cell value "
            + expectedValue + " at column " + columnName + " on " + ordinal + " row  - PASSED.");
    }

    @And("^\"([^\"]*)\" list element has cell value \"([^\"]*)\" at column \"([^\"]*)\" within (\\d+) seconds?$")
    public void containsElementWithin(String ordinal, String value, String columnName, int seconds) {
        String expectedValue = parameterProvider.getValueOrParameterAsString(value);
        PlusActions scenario = (PlusActions) getScenarioInstance(PlusActions.class);
        int row = extractNumericValue(ordinal);
        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), seconds, 20);
        String message = ordinal + " list element did not contain expected cell value "
            + expectedValue + " at column " + columnName + " within " + seconds + " seconds  - PASSED.";
        waiter.withMessage(message);
        String plusMenu = parameterProvider.getValueOrParameterAsString("parameter:plus-menu-item");

        waiter.until((ViewListTestObject callback) -> {
            scenario.checkPlusMenu(plusMenu);
            return callback.containsDataAt(row, expectedValue, columnName);
        });
        logger().debug("STEP: " + ordinal + " list element has cell value "
            + expectedValue + " at column " + columnName + " within " + seconds + " seconds  - PASSED.");
    }

    @And("^\"([^\"]*)\" list element has cell value \"([^\"]*)\" at column \"([^\"]*)\" polling (\\d+) seconds?$")
    public void containsElementAt(String ordinal, String value, String columnName, int seconds) {
        seleniumDriver.waitForRequestsToFinish();
        int row = extractNumericValue(ordinal);
        String expectedValue = parameterProvider.getValueOrParameterAsString(value);
        String arrow = parameterProvider.getValueOrParameterAsString("parameter:navigation");
        String dashboardMenu = parameterProvider.getValueOrParameterAsString("parameter:dashboard-menu");
        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), seconds, 30);
        String message = ordinal + " list element did not have cell value "
            + expectedValue + " at column " + columnName + " within " + seconds + " seconds.";
        waiter.withMessage(message);
        waiter.until((ViewListTestObject callback) -> {
            loopBack(arrow, dashboardMenu);
            return callback.containsDataAt(row, expectedValue, columnName);
        });
        logger().debug("STEP: " + ordinal + " list element did not have cell value "
            + expectedValue + " at column " + columnName + " within " + seconds + " seconds - PASSED.");
    }

    @And("^\"([^\"]*)\" list element has status \"([^\"]*)\" at column \"([^\"]*)\" within (\\d+) seconds? refreshing \"([^\"]*)\"$")
    public void refreshTillVisible(String ordinal, String status, String columnName, int seconds, String linkText) {
        int row = extractNumericValue(ordinal);
        String expectedValue = parameterProvider.getValueOrParameterAsString(status);
        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), seconds, 5);
        String message = "Status did not switch to " + status + " within " + seconds + " seconds";
        waiter.withMessage(message);
        waiter.until((ViewListTestObject callback) -> {
            seleniumDriver.waitAndClick(seleniumDriver.findElement(By.linkText(linkText)));
            return callback.containsDataAt(row, expectedValue, columnName);
        });
    }

    @And("^Cell values? from selected rows? and column \"([^\"]*)\" (?:are|is) checked$")
    public void checkDataSelection(String columnName) {
        ViewListTestObject viewListModel = new ViewListTestObject();
        List<String> cellSelection = viewListModel.fetchDataSelection(columnName);
        assertThat("Data selection at column " + columnName + " is empty", cellSelection, not(hasSize(0)));
        parameterProvider.put(columnName, cellSelection);
        logger().debug("STEP: Cell value(s) from selected row(s) and column "
            + columnName + " is/are checked - PASSED.");
    }

    @Then("^\"([^\"]*)\" List element with value at column \"([^\"]*)\" is checked$")
    public void storeColumnValueInSharedProperties(String ordinal, String columnName) {
        seleniumDriver.waitForRequestsToFinish();
        int row = extractNumericValue(ordinal);
        Optional<String> optionalValue = new ViewListTestObject().getCellValueAt(row, columnName);
        String message = ordinal + " list element didn't contain any value at column " + columnName;
        assertThat(message, optionalValue.isPresent(), is(true));
        String splitValue = optionalValue.get().split(" ")[0];
        parameterProvider.put(columnName, splitValue);
        logger().debug("STEP: " + ordinal + " list element with value at column "
            + columnName + " is checked - PASSED.");
    }

    @Then("^\"([^\"]*)\" list element with date interval at column \"([^\"]*)\" from table \"([^\"]*)\" is \"([^\"]*)\"$")
    public void checkDateIntervalTableCell(String ordinal, String columnName, String tableName, String interval) {
        int row = extractNumericValue(ordinal);
        Optional<String> result = new ViewListTestObject().getValueAt(row, columnName, tableName);
        boolean success = result.isPresent() && StringUtils.isNotBlank(result.get());
        String message = ordinal + " list element didn't contain any value at column " + columnName;
        assertThat(message, success, is(true));
        String[] splitValue = result.get().split("\\s+");
        parameterProvider.put(columnName, result.get());
        String errorMessage = "Comparison of two dates expression conversion failure";
        Assert.assertThat(errorMessage,
            checkTimeBetween(splitValue[0], splitValue[1], interval), Matchers.equalTo(0));
    }

    @When("^End of interval from \"([^\"]*)\" row of table \"([^\"]*)\" at column \"([^\"]*)\" is checked$")
    public void endOfIntervalFromRowOfTableAtColumnIsChecked(String ordinal, String tableName, String column) {
        int row = extractNumericValue(ordinal);
        Optional<String> intervalOfContract = new ViewListTestObject().getValueAt(row, column, tableName);
        boolean success = intervalOfContract.isPresent() && StringUtils.isNotBlank(intervalOfContract.get());
        String message = ordinal + " list element didn't contain any value at column " + column;
        assertThat(message, success, is(true));
        parameterProvider.put(column + " - start", getFormattedEnd(intervalOfContract.get(), -1));
        parameterProvider.put(column + " - end", getFormattedEnd(intervalOfContract.get(), +1));
    }

    @Then("^List element matching value \"([^\"]*)\" at column \"([^\"]*)\" from table \"([^\"]*)\" is checked$")
    public void storeColumnValueInParameterProvider(String match, String columnName, String tableName) {
        seleniumDriver.waitForRequestsToFinish();
        List<String> columnData = new ViewListTestObject().fetchColumnData(tableName, columnName);
        Optional<String> first = columnData.stream().filter(element -> element.contains(match)).findAny();
        String message = tableName + " list element didn't contain value " + match + " at column " + columnName;
        assertThat(message, first.isPresent(), is(true));
        parameterProvider.put(columnName, first.get());
        logger().debug("STEP: " + tableName + " list element with value at column "
            + columnName + " is checked - PASSED.");
    }

    @When("^Click on \"([^\"]*)\" matching value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void clickOnElementWithMatchingValue(String columnToBeClicked, String match, String filterColumn) {
        new ClickTableCellUrl().test(getColumnIndexListOptionsFromRow(columnToBeClicked, null, filterColumn, match));
    }

    @And("^All cell values at \"([^\"]*)\" row from table \"([^\"]*)\" are checked$")
    public void cellValuesAtRowFromTableAreChecked(String ordinal, String tableName) {
        seleniumDriver.waitForRequestsToFinish();
        ViewListTestObject viewListTestObject = new ViewListTestObject(tableName);
        int row = extractNumericValue(ordinal);

        Optional<Integer> columnCountOptional = Optional.empty();
        boolean found = false;
        int currentAttempt = 0;
        int maxAttempts = 30;
        while (!found && currentAttempt <= maxAttempts) {
            currentAttempt++;
            columnCountOptional = viewListTestObject.getColumnCount();
            found = columnCountOptional.isPresent();
            if (!found) Sleeper.sleepTightInSeconds(30);
        }

        if (!found) throw new CucumberException("Column count not found");

        for (int column = 1; column <= columnCountOptional.get(); column++) {
            Optional<String> columnName = viewListTestObject.getColumnName(column);
            Optional<Object> value = viewListTestObject.getValueAt(row, column);

            if (columnName.isPresent() && value.isPresent()) {
                parameterProvider.put(columnName.get(), value.get());
            }
        }
    }

    @Then("^List element with value at column \"([^\"]*)\" from table \"([^\"]*)\" is checked$")
    public void storeColumnValueInParameterProvider(String columnName, String tableName) {
        seleniumDriver.waitForRequestsToFinish();
        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), 300, 10);
        waiter.withMessage("List element didn't contain any value at column " + columnName );
        List<String> columnData = waiter.until((ViewListTestObject callback) -> callback.fetchColumnData(tableName, columnName));
        boolean success = CollectionUtils.isNotEmpty(columnData);
        String message = tableName + " list element didn't contain any value at column " + columnName;
        assertThat(message, success, is(true));

        parameterProvider.put(columnName, columnData.get(0));
        logger().debug("STEP: " + tableName + " list element with value at column "
            + columnName + " is checked - PASSED.");

    }

    @Then("^Cell value at \"([^\"]*)\" row at column \"([^\"]*)\" from table \"([^\"]*)\" is checked$")
    public void lookupCellValue(String ordinal, String columnName, String tableName) {
        int row = extractNumericValue(ordinal);
        Optional<String> cellValue = new ViewListTestObject().getValueAt(row, columnName, tableName);
        String message = "Table " + tableName + " is empty or did not contain any value at " + ordinal + " row and column " + columnName;
        assertThat( message, cellValue.isPresent() && StringUtils.isNotEmpty(cellValue.get()), is(true));
        parameterProvider.put(columnName, cellValue.get());
        logger().debug("STEP: " + tableName + " list element with value at column " + columnName + " is checked - PASSED.");
    }

    @Then("^Selected List rows have cell value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void checkSelectionData(String value, String columnName) {
        ViewListTestObject viewListModel = new ViewListTestObject();
        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), 10, 2);
        waiter.withMessage("Selected table is empty");
        waiter.until((ViewListTestObject callback) -> CollectionUtils.isNotEmpty(callback.fetchDataSelection(columnName)));
        List<String> cellSelection = viewListModel.fetchDataSelection(columnName);
        String message = "Value " + value + " wasn't found in any row of " + columnName + " column";
        assertThat(message, cellSelection.get(0).contains(value), is(true));
        logger().debug("STEP: Selected List rows have cell value " + value + " at column " + columnName + " - PASSED.");
    }

    @And("^View List element \"([^\"]*)\" is collected as parameter at \"([^\"]*)\" list row$")
    public void collectViewListElementAsParameter(String viewListElement, String ordinal) {
        String parameter = getViewListElementAtRow(viewListElement, ordinal);
        parameterProvider.put(viewListElement, parameter);
        logger().debug("View List element " + viewListElement + " is collected as parameter at "
            + ordinal + " list row");
    }

    @And("^View List element \"([^\"]*)\" using \"([^\"]*)\" as alias is collected as parameter at \"([^\"]*)\" list row$")
    public void collectViewListElementWithAliasAsParameter(String viewListElement,
                                                           String viewListElementAlias, String ordinal) {
        String parameter = getViewListElementAtRow(viewListElement, ordinal);
        parameter = getPossibleNumeric(parameter);
        parameterProvider.put(viewListElementAlias, parameter);
    }

    private String getPossibleNumeric(String input) {
        String[] possibleAccountNumbers = input.split(" ");
        String message = "Input value " + input + " didn't contain any numeric substring";
        if (possibleAccountNumbers.length > 1) {
            for (int i = 0; i < possibleAccountNumbers.length - 1; i++) {
                if (StringUtils.isNumeric(possibleAccountNumbers[i]))
                    return possibleAccountNumbers[i];
            }
            throw new CucumberException("Input value " + input + " didn't contain any numeric substring");
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
        assertThat("View List element " + viewListElement + " was not found.",
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
        assertThat(tableTitle + " doesn't exist or comparation is not valid", success, is(true));
        logger().debug("STEP: " + tableTitle + " list is not empty - PASSED.");
    }

    @And("^Table \"([^\"]*)\" contains value \"([^\"]*)\" at column \"([^\"]*)\" within (\\d+) seconds? after clicking on \"([^\"]*)\"$")
    public void viewListContainsValueAtColumn(String table, String value, String column, int seconds, String buttonName) {
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        String dashboardMenu = parameterProvider.getValueOrParameterAsString("parameter:dashboard-menu");

        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), seconds, 30);
        waiter.withMessage("List element didn't contain any value at column " + column);
        waiter.until((ViewListTestObject callback) -> {
            new DashboardMenu().goToDashboardMenuItem(dashboardMenu);
            new MarketMessagesSteps().clickOn(buttonName);
            return !callback.fetchColumnData(table, column)
                .stream().filter(element -> element.contains(inputValue)).collect(Collectors.toList()).isEmpty();
        });

        logger().debug("STEP: Table " + table + " does not contain value " + value + " at column " + column + ".");
    }

    @And("^Table \"([^\"]*)\" contains value \"([^\"]*)\" at column \"([^\"]*)\" within (\\d+) seconds?$")
    public void viewListContainsValueAtColumn(String table, String value, String column, int seconds) {
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        String arrow = parameterProvider.getValueOrParameterAsString("parameter:navigation");
        String dashboardMenu = parameterProvider.getValueOrParameterAsString("parameter:dashboard-menu");

        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), seconds, 10);
        waiter.withMessage("List element didn't contain any value at column " + column);
        waiter.until((ViewListTestObject callback) -> {
            loopBack(arrow, dashboardMenu);
            return CollectionUtils.isNotEmpty(callback.fetchColumnData(table, column)
                .stream()
                .filter(element -> element.contains(inputValue))
                .collect(Collectors.toList()));
        });

        logger().debug("STEP: Table " + table + " does not contain value " + value + " at column " + column + ".");
    }

    @Then("^Table \"([^\"]*)\" contains value \"([^\"]*)\" at column \"([^\"]*)\" retrying (\\d+) times?$")
    public void viewListContainsValueAtColumnRetrying(String table, String value, String column, int maxRetries) {
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        String arrow = parameterProvider.getValueOrParameterAsString("parameter:navigation");
        String dashboardMenu = parameterProvider.getValueOrParameterAsString("parameter:dashboard-menu");

        int attempt = 0;
        boolean found = false;

        while (attempt < maxRetries && !found) {
            attempt++;
            found = CollectionUtils.isNotEmpty(new ViewListTestObject().fetchColumnData(table, column).stream().
                filter(element -> element.contains(inputValue)).collect(Collectors.toList()));

            if (!found) {
                Sleeper.sleepTightInSeconds(10);
                loopBack(arrow, dashboardMenu);
            }
        }

        assertThat(value + " was not found in " + table + " at " + column + " column", found);
        logger().debug(value + " was found in " + table + " at " + column + " column at attempt #" + attempt);
    }

    @And("^Table \"([^\"]*)\" contains value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void viewListContainsValueAtColumn(String table, String value, String column) {
        seleniumDriver.waitForRequestsToFinish();
        ViewListTestObject viewListModel = new ViewListTestObject();
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        List<String> columnData = viewListModel.fetchColumnData(table, column);
        List<String> found = columnData.stream().filter(element -> element.contains(inputValue))
            .collect(Collectors.toList());
        String message = "Table " + table + " didn't contain value " + inputValue + " at column " + column;
        assertThat(message, found, not(empty()));
        logger().debug("STEP: Table " + table + " contains value " + inputValue + " at column " + column + " - PASSED.");
    }

    @And("^Table \"([^\"]*)\" contains check mark at column \"([^\"]*)\"$")
    public void viewListContainsCheckmarkAtColumn(String table, String column) {
        seleniumDriver.waitForRequestsToFinish();
        Optional<Integer> transactionsColumnIndex =
            new ViewListTestObject().getTransactionsColumnIndex(column);
        String message = "Table " + table + " did not contain data at column " + column;
        assertThat(message, transactionsColumnIndex.isPresent(), is(true));
        By checkmarkSelector = By.xpath(createQuery(TRANSACTIONS_BLOCKED_CHECKMARK, REPLACEMENT_KEY1,
            Integer.toString(transactionsColumnIndex.get())));
        WebElement checkmark = seleniumDriver.findElementWhenPresent(checkmarkSelector);
        message = "Table " + table + " didn't contain check mark at column " + column;
        assertThat(message, null != checkmark);
        logger().debug("STEP: Table " + table + " contains check mark at column " + column + " - PASSED.");
    }

    @And("^Table \"([^\"]*)\" does not contain value \"([^\"]*)\" at column \"([^\"]*)\" within (\\d+) seconds?$")
    public void viewListDoesNotContainsValueAtColumn(String table, String value, String column, int seconds) {
        String arrow = parameterProvider.getValueOrParameterAsString("parameter:navigation");
        String dashboardMenu = parameterProvider.getValueOrParameterAsString("parameter:dashboard-menu");
        String inputValue = parameterProvider.getValueOrParameterAsString(value);

        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), seconds, 10);
        waiter.withMessage("List element didn't contain any value at column " + column);
        List<String> found = waiter.until((ViewListTestObject callback) -> {
            loopBack(arrow, dashboardMenu);
            return callback.fetchColumnData(table, column)
                .stream()
                .filter(element -> element.contains(inputValue))
                .collect(Collectors.toList());
        });
        String message = "Table " + table + " should not contain value " + value + " at column " + column;
        assertThat(message, found, empty());
    }

    @And("^Table \"([^\"]*)\" does not contain any value at column \"([^\"]*)\" within (\\d+) seconds?$")
    public void viewListDoesNotContainAnyValueAtColumn(String table, String column, int seconds) {
        String arrow = parameterProvider.getValueOrParameterAsString("parameter:navigation");
        String dashboardMenu = parameterProvider.getValueOrParameterAsString("parameter:dashboard-menu");

        FluentWait<ViewListTestObject> waiter = waiter(new ViewListTestObject(), seconds, 10);
        waiter.withMessage("List element contains some value at column " + column);
        List<String> found = waiter.until((ViewListTestObject callback) -> {
            loopBack(arrow, dashboardMenu);
            return callback.fetchColumnData(table, column).stream().filter(String::isEmpty).collect(Collectors.toList());
        });
        String message = "Table " + table + " should not contain value any value at column " + column;
        assertThat(message, found, empty());
    }

    //TODO Create a special test harness class for invoice checks,
    //and move the methods, related to invoice checks, there.

    @And("^\"([^\"]*)\" element of table \"([^\"]*)\" at currency column \"([^\"]*)\" is the sum of contracts amounts$")
    public void checkCurrencyAmountDableDataAsSum(String ordinal, String table, String columnName) {
        seleniumDriver.waitForRequestsToFinish();
        String stringSumOfContracts = parameterProvider.getValueOrParameterAsString("parameter:sum-of-contracts");
        int sumOfContracts = Integer.parseInt(stringSumOfContracts);

        int row = extractNumericValue(ordinal);
        Optional<String> currencyValue = new ViewListTestObject().getCurrencyValueAt(row, columnName, table);
        String message = ordinal + " list element didn't contain any value at column " + columnName;
        assertThat(message, currencyValue.isPresent(), is(true));
        int actualAmount = amountInCurrencyAsInt(currencyValue.get());
        assertThat("Total advance prepaid amount %s is not equal to sum of sub amounts %s", actualAmount, equalTo(sumOfContracts));
    }

    @And("^\"([^\"]*)\" in the first \"([^\"]*)\" row of \"([^\"]*)\" table is \"([^\"]*)\"$")
    public void checkCurrencyAmount(String columnToSearch, String optionToSearch, String table, String expression) {
        seleniumDriver.waitForRequestsToFinish();
        Optional<String> currencyValue = new ViewListTestObject().getCurrencyValueAt(1, columnToSearch, table);
        String message = "1st list element didn't contain any value at column " + columnToSearch;
        assertThat(message, currencyValue.isPresent(), is(true));
        int actualAmount = amountInCurrencyAsInt(currencyValue.get());
        boolean success = checkAmount(actualAmount, expression);
        assertThat("The expected value differs from the real value", success, is(true));

    }

    private int amountInCurrencyAsInt(String amountInCurrency) {
        return amountAsInt(amountInCurrency, FLEMISCH_LOCALE);
    }

    @Then("^Transactions table has outstanding amount of \"([^\"]*)\"")
    public void checkInvoiceCurrencyAmount(String expectedCurrencyAmountAsString) {
        ContractPage cp = new ContractPage();
        String actualCurrencyAmountAsString = cp.getActualOutstandingValueCurrencyInvoiceAsString();
        String message = "Table Transactions doesn't have value for invoice outstanding amount of "
            + expectedCurrencyAmountAsString;
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
        boolean tableContainsExpectedValue = cp.getTableValue().contains(expectedTableValue);
        Assert.assertTrue("There is no expected value in the table", tableContainsExpectedValue);
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
        assertThat("Sum of signature and rejected quote for High prices are not equal", sumHighRatesSignatureToFloat, equalTo(cp.sumRates(sumRatesHighSignature)));
        assertThat("Sum of signature and rejected quote for Low prices are not equal", sumLowRatesSignatureToFloat, equalTo(cp.sumRates(sumRatesLowSignature)));
    }

    @Then("Table contains matching data on given columns:")
    public void checkDataInTable(final DataTable dbTable) {
        List<List<String>> dataTableFilters = dbTable.asLists();
        List<Filter> filters = new ArrayList<>();
        String currentColumnName;
        String currentColumnValue;

        for (int i = 1; i <= dataTableFilters.size(); i++) {
            currentColumnName = dataTableFilters.get(0).get(i);
            currentColumnValue = dataTableFilters.get(1).get(i);
            Filter filter = new Filter(currentColumnName.toUpperCase(), currentColumnValue);
            filters.add(filter);
        }

        int attempt = 1;
        boolean found = false;
        int maxRetries = 10;
        String arrow = parameterProvider.getValueOrParameterAsString("parameter:navigation");
        String dashboardMenu = parameterProvider.getValueOrParameterAsString("parameter:dashboard-menu");

        while (attempt < maxRetries && !found) {
            try {
                List<WebElement> results = new MarketMessagesPage().selectRowOnTable(dataTableFilters.get(1).get(0), filters, parameterProvider.getCurrentContextParameters());
                found = CollectionUtils.isNotEmpty(results);            } catch (Exception e) {
                logger().warn(parameterProvider.getCurrentContextParameters() + " - Data was not found.");
            } finally {
                attempt++;
                if (!found) {
                    Sleeper.sleepTightInSeconds(10);
                    loopBack(arrow, dashboardMenu);
                }
            }
        }

        Assert.assertTrue(found);
    }

    @And("Number of contract lines is \"([^\"]*)\"$")
    public void numberContractLines(int expectedNumber) {
        int actualNumber = new ContractPage().getContractLinesNumber();
        assertThat("Number of given contract lines " + expectedNumber + " is not equal to actual number of contract lines " + actualNumber + "", actualNumber, equalTo(expectedNumber));
    }

    @Then("Start dates are same for all contract lines as contract start date \"([^\"]*)\"$")
    public void checkAllDates(String contractStartDate) {
        Assert.assertTrue("Start dates are not same for all contract lines", new ContractPage().checkAllStartDatesEqual(parameterProvider.getValueOrParameterAsString(contractStartDate)));
    }

    @Then("^Save invoice number")
    public void getInvoiceNumber() {
        String invoiceNumber = new ContractPage().getInvoiceNumber();
        parameterProvider.put("invoiceNumber", invoiceNumber);
        assertNotNull(invoiceNumber);
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
