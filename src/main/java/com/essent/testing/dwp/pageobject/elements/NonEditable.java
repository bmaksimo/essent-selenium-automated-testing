package com.essent.testing.dwp.pageobject.elements;

public interface NonEditable {

  String getValue(String label);

  String getValue(String card, String label);

  boolean checkAmountUsingExpression(String card, String label, String expression);
}
