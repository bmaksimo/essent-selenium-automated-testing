package com.essent.testing.dwp.menu.model;

import com.essent.testing.dwp.menu.model.impl.TopMenuImpl;

/**
 * Generated Java Enum
 * 
 * @author Dmitry Chebayewski <dmitry.chebayewski@billinghouse.nl>
 *
 */
public enum FinanceUpperMenu {
  ACCOUNTS_LIST("Accounts", "accounts-list-link");

  private String label;
  private String link;

  private FinanceUpperMenu(String label, String dashboardLink) {
    this.label = label;
    this.link = dashboardLink;
  }

  public String getLabel() {
    return label;
  }

  public String getLink() {
    return link;
  }

  public static TopMenu getTopMenu() {
    TopMenu menu = new TopMenuImpl();
    for (FinanceUpperMenu upperMenu : FinanceUpperMenu.values()) {
      menu.item(upperMenu.name(), new TopMenu.Item(upperMenu.getLabel(), upperMenu.getLink()));
    }
    return menu;
  }
}
