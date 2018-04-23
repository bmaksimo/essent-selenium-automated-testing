package com.essent.testing.dwp.menu.model;

import com.essent.testing.dwp.menu.model.impl.TopMenuImpl;

/**
 * Generated Java Enum
 * 
 * @author Dmitry Chebayewski <dmitry.chebayewski@billinghouse.nl>
 *
 */
public enum CreditManagementUpperMenu {
  MARKETTRANSACTIONS_DASHBOARD("Market Transactions",
      "markettransactions-dashboard-link"), ACCOUNTS_LIST("Accounts",
          "accounts-list-link"), QUOTES_LIST("Quotes",
              "quotes-list-link"), CONTRACT_LIST("Contracts", "contract-list-link");

  private String label;
  private String link;

  private CreditManagementUpperMenu(String label, String dashboardLink) {
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
    for (CreditManagementUpperMenu upperMenu : CreditManagementUpperMenu.values()) {
      menu.item(upperMenu.name(), new TopMenu.Item(upperMenu.getLabel(), upperMenu.getLink()));
    }
    return menu;
  }
}
