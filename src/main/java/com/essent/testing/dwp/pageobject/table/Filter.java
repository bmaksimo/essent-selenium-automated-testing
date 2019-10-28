package com.essent.testing.dwp.pageobject.table;

public class Filter {
    private String columnName;
    private String columnValue;

    public Filter(String columnName, String columnValue) {
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    @Override
    public String toString() {
        return "columnName='" + columnName + "\'" + ", columnValue='" + columnValue + "\' |";
    }
}
