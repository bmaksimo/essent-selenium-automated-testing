package com.essent.testing.dwp.pageobject.modal;

import com.essent.testing.dwp.pageobject.modal.Dialog;

public interface ConfirmDialog extends Dialog {
  boolean confirm();

  boolean reject();

  boolean isShown();
}
