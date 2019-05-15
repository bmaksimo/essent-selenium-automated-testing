package com.billinghouse.test_automation.util.dsl;

public enum DateTimeLanguageRegex {
  TIME_EXPR_REGEX("((\\d+)\\s*(hour|second)(s*)\\s+(from|before)\\s+)*now"),
  INTERVAL_EXPR_REGEX("((\\d+)\\s*(month|day|year|week)(s*))"),
  DATE_EXPR_REGEX("((\\d+)\\s*(month|day|year|week){1}(s*)\\s+(from|before)\\s+)*now");

  private String expression;

  public String getExpression() {
    return expression;
  }

  DateTimeLanguageRegex(String expression) {
    this.expression = expression;
  }
}
