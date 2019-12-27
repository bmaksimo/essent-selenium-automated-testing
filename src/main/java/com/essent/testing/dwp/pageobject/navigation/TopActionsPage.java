package com.essent.testing.dwp.pageobject.navigation;

public interface TopActionsPage {
  boolean executeTopAction(String name);

  boolean executeTopActionWithFixedWait(String name, int waitingTime);
}
