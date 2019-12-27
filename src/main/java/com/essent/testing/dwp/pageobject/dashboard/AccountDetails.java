package com.essent.testing.dwp.pageobject.dashboard;

import com.essent.testing.dwp.pageobject.Form;

public interface AccountDetails extends Form {
  default boolean fillInFormData() {
    return true;
  }

  String getNonEdtableValue(String label);

  boolean isToggleSwitchEnabled(String label);
}
