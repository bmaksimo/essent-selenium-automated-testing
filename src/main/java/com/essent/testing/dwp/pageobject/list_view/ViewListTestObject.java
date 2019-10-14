package com.essent.testing.dwp.pageobject.list_view;

import com.essent.testing.dwp.pageobject.ViewList;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry.*;

/**
 * The class has been created as a placeholder for the future migration of technical stack from
 * JsTestRunner framework to Java Page Object / Test Object pattern Guidelines: Assert statements
 * should not be added in this class; You must return Optional or empty list but not null
 */
public class ViewListTestObject extends Component implements ViewList {

  private static final String TRANSACTIONS_TABLE_HEADERS = "//list[@list-key='TransactionsOnAccount']//th[@class='list__cell']";

  private Optional<DefaultTableModel> tableModel;

  public ViewListTestObject() {
    this.tableModel = Optional.of(getViewTableModel());
  }

  public ViewListTestObject(String table) {
    this.tableModel = Optional.of(getViewTableModel(table));
  }

  public Optional<Integer> getColumnCount() {
    if (tableModel.isPresent()) {
        int count = tableModel.get().getColumnCount();
        return count > 0 ? Optional.of(count) : Optional.empty();
    }
    return Optional.empty();
  }

  public Optional<String> getColumnName(int column) {
      return tableModel.map(defaultTableModel -> defaultTableModel.getColumnName(column - 1));
  }

  public Optional<Object> getValueAt(int row, int column) {
      return tableModel.map(defaultTableModel -> defaultTableModel.getValueAt(row - 1, column - 1));
  }

  public DefaultTableModel getViewTableModel() {
    DefaultTableModel tableModel = new DefaultTableModel();
    return getDefaultTableModel(tableModel, new HashMap<>());
  }

  public DefaultTableModel getViewTableModel(String tableName) {
    DefaultTableModel tableModel = new DefaultTableModel();
    HashMap<Object, Object> options = new HashMap<>();
    options.put("list_header", tableName);
    return getDefaultTableModel(tableModel, options);
  }

  private DefaultTableModel getDefaultTableModel(DefaultTableModel tableModel, HashMap<Object, Object> options) {
    Map<String, Object> viewTable = executeJavascriptMethod(JS_TR_GET_TABLE_MODEL, options);
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
      if (i < columnCount - 1) columns.append(", ");
    }
    columns.append("]");
    logger().debug("- RESULT: Columns: " + columns.toString());
    logger().debug("- RESULT: Data vector: " + viewTableModel.getDataVector());
  }

  private List<List> getData(Map viewTable) {
    return (List) viewTable.get("rows");
  }

  private int getColumnNameIndex(String columnName, Map viewTable) {
    List<String> columnNames = (List) viewTable.get("column_names");
    return columnNames.indexOf(columnName);
  }

  public boolean containsDataAt(int row, String value, String columnName) {
    Optional<String> result = getCellValueAt(row, columnName);
    return result.isPresent() && result.get().contains(value);
  }

  public boolean containsCellValue(
      int rowFromOne, String value, String columnName, String tableName) {
    Optional<String> cell = getValueAt(rowFromOne, columnName, tableName);
    return cell.isPresent() && cell.get().contains(value);
  }

  public boolean openListPlusActions(int row) {
    Map<String, Object> options = new HashMap<>();
    options.put("index", row);
    return executeJavascriptTest(JS_TR_OPEN_LIST_PLUS_ACTIONS, options);
  }

  public List<Integer> fetchListRowsIndices(String value, String columnName) {
    Map viewTable = executeJavascriptMethod(JS_TR_GET_TABLE_MODEL, new HashMap<>());
    int index = getColumnNameIndex(columnName, viewTable);
    if (index < 0) {
      return Collections.emptyList();
    }
    List<List> rows = getData(viewTable);
    AtomicInteger idx = new AtomicInteger(1);
    List<Integer> collect =
        IntStream.range(1, rows.size() + 1)
            .filter(
                i ->
                    idx.compareAndSet(i, i + 1)
                        & ((ArrayList<String>) rows.get(i - 1)).get(index).contains(value))
            .boxed()
            .collect(Collectors.toList());
    return collect;
  }

  public boolean selectListRow(int row, String value, String columnName) {
    List<Integer> indices = fetchListRowsIndices(value, columnName);
    return CollectionUtils.isNotEmpty(indices) && row <= indices.size();
  }

  public List<String> fetchDataSelection(String columnName) {
    Map<String, Object> options = new HashMap<>();
    options.put("include_selection", true);
    Map<String, Object> viewTable = executeJavascriptMethod(JS_TR_FETCH_DATA_SELECTION, options);
    int index = getColumnNameIndex(columnName, viewTable);
    if (index < 0) {
      return Collections.emptyList();
    }
    List<List> rows = getData(viewTable);
    return rows.stream().map((e) -> e.get(index).toString()).collect(Collectors.toList());
  }

  public List<String> fetchColumnData(String table, String columnName) {
    return fetchColumnDataNow(table, columnName, false);
  }

  public List<String> fetchColumnDataNow(String table, String columnName, boolean immediate) {
    Map<String, Object> options = new HashMap<>();
    options.put("include_selection", false);
    options.put("table", table);
    Map viewTable =
        immediate
            ? executeJavascriptMethodImmediately(JS_TR_FETCH_DATA_SELECTION, options)
            : executeJavascriptMethod(JS_TR_FETCH_DATA_SELECTION, options);

    int index = getColumnNameIndex(columnName, viewTable);
    if (index < 0) {
      return Collections.emptyList();
    }
    List<List> rows = getData(viewTable);
    return rows.stream().map((e) -> e.get(index).toString()).collect(Collectors.toList());
  }

  public int fetchRowIndexFromData(String table, String columnName, String data) {
      return fetchRowFromDataNow(table, columnName, data, false);
  }

  private int fetchRowFromDataNow(String table, String columnName, String data, boolean immediate) {
    Map<String, Object> options = new HashMap<>();
    options.put("include_selection", false);
    options.put("table", table);
    Map viewTable =
        immediate
            ? executeJavascriptMethodImmediately(JS_TR_FETCH_DATA_SELECTION, options)
            : executeJavascriptMethod(JS_TR_FETCH_DATA_SELECTION, options);

    List<List> rows = getData(viewTable);

    int currentIndex = -1;
    Optional row;
    for (int i = 0; i < rows.size(); i++) {
        List currentRow = rows.get(i);
        row = currentRow.stream().filter(r -> r.toString().contains(data)).findFirst();
        if (row.isPresent()) {
            currentIndex = i;
            break;
        }
    }

    return currentIndex;
  }

  public Optional<String> getCellValueAt(int row, String columnName) {
    seleniumDriver.waitForRequestsToFinish();
    logger().debug("STEP: JAVASCRIPT_FETCH_DATA");
    Map<String, Object> viewTable = executeJavascriptMethod(JS_TR_GET_TABLE_MODEL, new HashMap<>());
    logger().debug(" - RESULT: " + viewTable);
    int index = getColumnNameIndex(columnName, viewTable);
    if (index < 0) {
      return Optional.empty();
    }
    List<List> rows = getData(viewTable);
    if (rows.isEmpty()) {
      return Optional.empty();
    }
    if (row > rows.size()) {
      logger().error(String.format(
              "--  Row number \"%s\" was greater than actual table size \"%s\"", row, rows.size()));
      return Optional.empty();
    }
    List currentRow = rows.get(row - 1);
    return Optional.of((String) currentRow.get(index));
  }

  public Optional<String> getValueAt(int row, String columnName) {
    return getCellValueAt(row, columnName);
  }

  public Optional<String> getValueAt(int row, String columnName, String tableName) {
    logger().debug("STEP: JAVASCRIPT_FETCH_DATA");
    DefaultTableModel viewTableModel = getViewTableModel(tableName);
    logger().debug(" - RESULT: Table name: " + tableName);
    logTableModel(viewTableModel);
    int column = viewTableModel.findColumn(columnName);
    if (column < 0) return Optional.empty();
    return Optional.of((String) viewTableModel.getValueAt(row - 1, column));
  }

  public Optional<String> getCurrencyValueAt(int row, String columnName, String tableName) {
    Optional<String> result = getValueAt(row, columnName, tableName);
    return result.map(value -> value.replaceAll("\\s+", " "));
  }

  @Override
  public Optional<Integer> getTransactionsColumnIndex(String columnName) {
    List<WebElement> headers = seleniumDriver.findElements(By.xpath(TRANSACTIONS_TABLE_HEADERS));

    for (int i = 0; i < headers.size() - 1; i++) {
      if (columnName.equalsIgnoreCase(headers.get(i).getText())) return Optional.of(i);
    }
    return Optional.empty();
  }
}
