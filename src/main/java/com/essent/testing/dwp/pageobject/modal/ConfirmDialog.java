package com.essent.testing.dwp.pageobject.modal;

public interface ConfirmDialog extends Dialog {
  boolean confirm();

  boolean reject();

  boolean isShown();
}
