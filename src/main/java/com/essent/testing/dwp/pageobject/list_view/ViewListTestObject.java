package com.essent.testing.dwp.pageobject.list_view;

import com.essent.testing.dwp.pageobject.ViewList;
import com.essent.testing.dwp.pageobject.impl.Component;
import cucumber.runtime.CucumberException;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.fail;

/**
 * The class has been created as a placeholder for the future migration of technical stack from JsTestRunner framework
 * to Java Page Object / Test Object pattern
 */
public class ViewListTestObject extends Component implements ViewList {

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
            throw new CucumberException(String.format("--  Row number \"%s\" was greater than actual table size \"%s\"", row, rows.size()));
        }
        List currentRow = rows.get(row - 1);
        return (String) currentRow.get(index);
    }

    private void logTableModel(DefaultTableModel viewTableModel) {
        int columnCount = viewTableModel.getColumnCount();
        StringBuilder columns = new StringBuilder("[");
        for(int i = 0; i < columnCount; i++) {
            columns.append(String.format("'%s'", viewTableModel.getColumnName(i)));
            if(i < columnCount - 1)
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

    public String getCurrencyValueAt(int row, String columnName, String tableName) {
        return getValueAt(row, columnName, tableName).replaceAll("\\s+", " ");
    }

    public String getValueAt(int row, String columnName, String tableName) {
        logger().info("STEP: JAVASCRIPT_FETCH_DATA");
        HashMap<Object, Object> options = new HashMap<>();
        options.put("list_header", tableName);
        DefaultTableModel viewTableModel = getViewTableModel(tableName);
        logger().info(" - RESULT: Table name: " + tableName);
        logTableModel(viewTableModel);
        int column = viewTableModel.findColumn(columnName);
        if(column < 0)
            throw new CucumberException(String.format("View List did not contain column %s", columnName));
        return (String) viewTableModel.getValueAt(row - 1, column);

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
        return IntStream.range(1, rows.size() + 1)
            .filter(i ->
                idx.compareAndSet(i, i + 1) & ((ArrayList<String>) rows.get(i - 1)).get(index).contains(value))
            .boxed()
            .collect(Collectors.toList());

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

        Map viewTable = immediate ?
            executeJavascriptMethodImmediately("TrFetchDataSelection", options)
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

}
