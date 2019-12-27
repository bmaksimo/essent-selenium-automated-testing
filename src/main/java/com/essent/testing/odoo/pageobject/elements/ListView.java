package com.essent.testing.odoo.pageobject.elements;

public interface ListView {

  public void clickCellAt(String columnName, String rowIndex);

  public void checkCellAt(String columnName, String rowIndex, String value);

  public void clickValueAt(String columnName, String value);

  public void checkValueAt(String columnName, String value);
}
