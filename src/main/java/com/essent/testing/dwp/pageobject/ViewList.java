package com.essent.testing.dwp.pageobject;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Optional;

public interface ViewList {

    DefaultTableModel getViewTableModel();

    boolean containsDataAt(int row, String value, String columnName);

    boolean containsCellValue(int rowFromOne, String value, String columnName, String tableName);

    boolean selectListRow(int row);

    boolean openListPlusActions(int row);

    List<Integer> fetchListRowsIndices(String value, String columnName);

    boolean selectListRow(int row, String value, String columnName);

    boolean selectListRows(int numRows, String value, String columnName);

    List<String> fetchDataSelection(String columnName);

    List<String> fetchColumnData(String table, String columnName);

    List<String> fetchColumnDataNow(String table, String columnName, boolean immediate);

    Optional<String> getValueAt(int row, String columnName, String tableName);
    Optional<String> getValueAt(int row, String columnName);
    Optional<String> getCurrencyValueAt(int row, String columnName, String tableName);
    Optional<Integer> getTransactionsColumnIndex(String columnName);

}
