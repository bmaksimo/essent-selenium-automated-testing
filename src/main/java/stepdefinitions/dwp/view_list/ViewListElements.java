package stepdefinitions.dwp.view_list;

import com.billinghouse.cucumber.runtime.annotations.InputParameter;
import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;
public class ViewListElements extends NavigationElements {


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
        public DefaultTableModel getViewTableModel() {
            DefaultTableModel tableModel = new DefaultTableModel();
            Map viewTable = executeJavascriptMethod("TrGetTableModel", new HashMap<>());
            List columnNames = (List)viewTable.get("column_names");
            List rows = getData(viewTable);
            tableModel.setColumnIdentifiers(columnNames.toArray());
            for(int i = 0; i < rows.size(); i++) {
                Object[] row = ((List) rows.get(i)).toArray();
                tableModel.addRow(row);
            }
            return tableModel;
        }

        public boolean containsDataAt(int row, String value, String columnName) {
            String cell = getCellValueAt(row, columnName);
            boolean success = cell.contains(value);
            return success;
        }
        public boolean selectListRow(int row) {
            Map<String, Object> options = new HashMap<>();
            options.put("index", row);
            boolean success = executeJavascriptTest("TrSelectListRow", options);
            return success;
        }

        public List<Integer> fetchListRowsIndices(String value, String columnName) {
            Map viewTable = executeJavascriptMethod("TrGetTableModel", new HashMap<>());
            int index = getColimnNameIndex(columnName, viewTable);
            if(index < 0) {
                fail(String.format("View List did not contain column %s", columnName));
            }
            List<ArrayList> rows = getData(viewTable);
            AtomicInteger idx = new AtomicInteger(1);
            List<Integer> indices = IntStream.range(1, rows.size() + 1)
                .filter(i ->
                idx.compareAndSet(i, i + 1) & ((ArrayList<String>) rows.get(i - 1)).get(index).contains(value))
                .boxed()
                .collect(Collectors.toList());
            return indices;
        }

        public boolean selectListRow(int row, String value, String columnName) {
            List<Integer> indices = fetchListRowsIndices(value, columnName);
            return indices.size() > 0 && row <= indices.size();
        }

        public boolean  selectListRows(int numRows, String value, String columnName) {
            List<Integer> rows = fetchListRowsIndices(value, columnName);
            if(numRows > rows.size()) {
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
            int index = getColimnNameIndex(columnName, viewTable);
            if(index < 0) {
                fail(String.format("View List did not contain column %s", columnName));
            }
            List<ArrayList> rows = getData(viewTable);
            List<String> selection = (List)rows.stream().map((e) -> {
                return e.get(index);
            }).collect(Collectors.toList());
            return selection;
        }

        private String getCellValueAt(int row, String columnName) {
            Map viewTable = executeJavascriptMethod("TrGetTableModel", new HashMap<>());
            int index = getColimnNameIndex(columnName, viewTable);
            if(index < 0) {
                fail(String.format("View List did not contain column %s", columnName));
            }
            List<ArrayList> rows = getData(viewTable);
            if (row > rows.size()) {
                fail(String.format("--Error in Test Input: Given %s row index cannot be greater that actual View List size %s", row, rows.size()));
            }
            ArrayList<String> cells= rows.get(row -1);
            return cells.get(index);
        }

        private List<ArrayList> getData(Map viewTable) {
            return (List)viewTable.get("rows");
        }

        private int getColimnNameIndex(String columnName, Map viewTable) {
            List<String> columnNames = (List)viewTable.get("column_names");
            return columnNames.indexOf(columnName);
        }

    }

    private class ClickTableCellUrl implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrClickTableCellUrl", options);
        }
    }

    @Before("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^View List Header is \"([^\"]*)\"$")
    public void checkViewListHeader(String header) throws Throwable {
        boolean success = new CheckViewListHeader().test(header);
        assertThat(String.format("View list did not contain header '%s'", header),
            success, is(true));
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
        boolean success = new ClickTableCellUrl().test(columnIndexListOptions);
        assertThat(String.format("View list did not contain URL at row %s header '%s'", ordinal, column),
            success, is(true));
    }

    @And("^([^\"]*) List element has cell value ([^\"]*) at column ([^\"]*)$")
    public void listElementWith(String ordinal, String value, String columnName) throws Throwable {
        int row = extractNumericValue(ordinal);
        boolean success = new ViewListModel().containsDataAt(row, value, columnName);
        assertThat(String.format("View list did not contain cell value %s at %s row, column '%s'", value, ordinal, columnName),
            success, is(true));
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

    @And("^Select ([^\"]*) List rows having cell value ([^\"]*) at column ([^\"]*)$")
    public void selectListRowHavingCellValueAtColumn(int row, String value, String columnName) throws Throwable {
        ViewListModel viewListModel = new ViewListModel();
        boolean success = viewListModel.selectListRows(row, value, columnName);
        String message = String.format("View list did not %s rows having cell value %s at column '%s'", row, value, columnName);
        assertThat(message,
            success, is(true));
    }

    @OutputParameter(name = "toRenewContractsContact")
    private Map<String, Object> toRenewContractsContacts = new HashMap<>();
    @And("^Store cell values of selected list rows at column \"([^\"]*)\" as \"([^\"]*)\"$")
    public void storeDataSelectionOutputParameter(String columnName, String outParamName) throws Throwable {
        ViewListModel viewListModel = new ViewListModel();
        List<String> cellSelection = viewListModel.fetchDataSelection(columnName);
        toRenewContractsContacts.put(outParamName, cellSelection);
        assertThat(String.format("Data selection at column %s is empty", columnName),
            cellSelection, not(hasSize(0)));
    }

    @InputParameter(name = "toRenewContractsContact")
    private  Map<String, Object> selection;
    @Then("^Selected List rows have cell value \"([^\"]*)\" at column \"([^\"]*)\"$")
    public void checkSelectionData(String value, String columnName) throws Throwable {
        ViewListModel viewListModel = new ViewListModel();
        List<String> cellSelection = viewListModel.fetchDataSelection(columnName);
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

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
