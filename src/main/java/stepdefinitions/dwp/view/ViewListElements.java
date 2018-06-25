package stepdefinitions.dwp.view;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import cucumber.runtime.CucumberException;
import stepdefinitions.dwp.NavigationElements;

import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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

    private class TableModel {
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

        public boolean selectListRow(int row, String value, String columnName) {
            Map viewTable = executeJavascriptMethod("TrGetTableModel", new HashMap<>());
            int index = getColimnNameIndex(columnName, viewTable);
            if(index < 0) {
                throw new CucumberException(String.format("View List did not contain column %s", columnName));
            }
            List<ArrayList> rows = getData(viewTable);
            AtomicInteger idx = new AtomicInteger(1);
            List<Integer> indices = IntStream.range(1, rows.size() + 1)
                .filter(i ->
                idx.compareAndSet(i, i +1) & ((ArrayList<String>) rows.get(i - 1)).get(index).contains(value))
                .boxed()
                .collect(Collectors.toList());
            return indices.size() > 0 && row <= indices.size();
        }

        private String getCellValueAt(int row, String columnName) {
            Map viewTable = executeJavascriptMethod("TrGetTableModel", new HashMap<>());
            int index = getColimnNameIndex(columnName, viewTable);
            if(index < 0) {
                throw new CucumberException(String.format("View List did not contain column %s", columnName));
            }
            List<ArrayList> rows = getData(viewTable);
            if (row > rows.size()) {
                throw new CucumberException(String.format("--Error in Test Input: Given %s row index cannot be greater that actual View List size %s", row, rows.size()));
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

    @When("^View List is empty$")
    public void checkTableModel() throws Throwable {
        javax.swing.table.TableModel viewTableModel = new TableModel().getViewTableModel();
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
        int row = parseOrdinal(ordinal);
        boolean success = new TableModel().containsDataAt(row, value, columnName);
        assertThat(String.format("View list did not contain cell value %s at %s row, column '%s'", value, ordinal, columnName),
            success, is(true));
    }

    @And("^Select ([^\"]*) List row having cell value ([^\"]*) at column ([^\"]*)$")
    public void selectListRows(String ordinal, String value, String columnName) throws Throwable {
        int row = parseOrdinal(ordinal);
        TableModel tableModel = new TableModel();
        boolean success = tableModel.selectListRow(row, value, columnName);
        String message = String.format("View list did not contain cell value %s at %s row, column '%s'", value, ordinal, columnName);
        assertThat(message,
            success, is(true));
        success = tableModel.selectListRow(row);
        assertThat(message,
            success, is(true));

    }

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @DWP_SETUP, @FILTER, @RENEWAL")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}

