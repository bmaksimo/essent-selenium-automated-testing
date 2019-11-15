package com.billinghouse.testautomation.util.dsl;

public enum DateTimeRegex {
  DWP_START_END_DATE_FORMAT_REGEX("[0-9]{2}-[0-9]{2}-[0-9]{4}\\s+[0-9]{2}-[0-9]{2}-[0-9]{4}"),

  DWP_DATE_FORMAT_REGEX("[0-9]{2}/[0-9]{2}/[0-9]{4}"),

  DWP_API_DATE_FORMAT_REGEX("[0-9]{4}/[0-9]{2}/[0-9]{2}");

  private String expression;

  public String getExpression() {
    return expression;
  }

  DateTimeRegex(String expression) {
    this.expression = expression;
  }
}
