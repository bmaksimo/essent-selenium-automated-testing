package com.essent.testing.dwp.pageobject;

import java.util.List;

public interface ViewList {
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

    String getValueAt(int row, String columnName, String tableName);

    String getCurrencyValueAt(int row, String columnName, String tableName);

}
