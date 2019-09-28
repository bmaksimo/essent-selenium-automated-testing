package com.essent.testing.dwp.pageobject.modal;

public interface ConfirmDialog extends Dialog {
  boolean confirm(String scenarioInfo);

  boolean reject();

  boolean isShown();
}
