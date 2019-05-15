package com.essent.testing.dwp.pageobject.modal.search;

import com.essent.testing.dwp.pageobject.modal.Dialog;

public interface SearchAndMultipeChoiceModalDialog extends Dialog {
  String getTitle();

  void setSearchOption(String searchOption);

  void search(String searchButton);

  boolean checkSearchResult(String match);

  void submitSearchResult(String submitBtnLabel);
}
