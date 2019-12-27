package com.essent.testing.dwp.constant;

public enum ParameterKeys {
  SuiteCrmCustomer("parameter:suitecrm-customer-name");

  private String key;

  ParameterKeys(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
