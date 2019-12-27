package com.essent.testing.dwp.pageobject.modal.quote;

import com.essent.testing.dwp.pageobject.modal.Dialog;

public interface SimilarAccountDialog extends Dialog {
  void clickOnLink(String linkText) throws Throwable;

  String getTitle();
}
