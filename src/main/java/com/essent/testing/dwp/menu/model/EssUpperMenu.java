package com.essent.testing.dwp.menu.model;

import com.essent.testing.dwp.menu.model.impl.TopMenuImpl;

/**
 * Generated Java Enum
 * 
 * @author Dmitry Chebayewski <dmitry.chebayewski@billinghouse.nl>
 *
 */
public enum EssUpperMenu {
  MARKETTRANSACTIONS_DASHBOARD("Market Transactions",
      "markettransactions-dashboard-link"), ACCOUNTS_LIST("Accounts",
          "accounts-list-link"), CONTRACT_LIST("Contracts",
              "contract-list-link"), CASES("Cases", "cases-link");

  private String label;
  private String link;

  private EssUpperMenu(String label, String dashboardLink) {
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
    for (EssUpperMenu upperMenu : EssUpperMenu.values()) {
      menu.item(upperMenu.name(), new TopMenu.Item(upperMenu.getLabel(), upperMenu.getLink()));
    }
    return menu;
  }
}
