package com.essent.testing.dwp.pageobject;

import java.util.List;
import java.util.Optional;
import javax.swing.table.DefaultTableModel;

public interface ViewList {

  DefaultTableModel getViewTableModel();

  DefaultTableModel getViewTableModel(String tableName);

  boolean containsDataAt(int row, String value, String columnName);

  boolean containsCellValue(int rowFromOne, String value, String columnName, String tableName);

  List<String> fetchDataSelection(String columnName);

  List<String> fetchColumnData(String table, String columnName);

  List<String> fetchColumnDataNow(String table, String columnName, boolean immediate);

  Optional<String> getValueAt(int row, String columnName, String tableName);

  Optional<String> getCellValueAt(int row, String columnName);

  Optional<String> getValueAt(int row, String columnName);

  Optional<String> getCurrencyValueAt(int row, String columnName, String tableName);

  Optional<Integer> getTransactionsColumnIndex(String columnName);

  Optional<Integer> getColumnCount();

  Optional<String> getColumnName(int column);

  Optional<Object> getValueAt(int row, int column);
}
