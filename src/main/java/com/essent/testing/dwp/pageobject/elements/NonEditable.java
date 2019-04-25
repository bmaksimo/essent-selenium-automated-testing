package com.essent.testing.dwp.pageobject.elements;

public interface NonEditable {

    String getValue(String label);

    String getValue(String title, String label);

    boolean checkAmountUsingExpression(String title, String label, String expression);

}
