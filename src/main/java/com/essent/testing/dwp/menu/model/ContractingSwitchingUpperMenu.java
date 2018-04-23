package com.essent.testing.dwp.menu.model;

import com.essent.testing.dwp.menu.model.impl.TopMenuImpl;

/**
 * Generated Java Enum
 * 
 * @author Dmitry Chebayewski <dmitry.chebayewski@billinghouse.nl>
 *
 */
public enum ContractingSwitchingUpperMenu {
  MOVE_SEND_ILC("MOVE: Send ILC", "move-send-ilc-link"), MARKETTRANSACTIONS_DASHBOARD(
      "Market Transactions", "markettransactions-dashboard-link"), MS_SEND_ILC("MS: Send ILC",
          "ms-send-ilc-link"), MARKETTRANSACTIONTASKS_DASHBOARD("Tasks market transaction",
              "markettransactiontasks-dashboard-link"), ACCOUNTS_LIST("Accounts",
                  "accounts-list-link"), QUOTES_LIST("Quotes", "quotes-list-link"), CONTRACT_LIST(
                      "Contracts", "contract-list-link"), CHEAPEST_PRODUCTS("Cheapest products",
                          "cheapest-products-link");

  private String label;
  private String link;

  private ContractingSwitchingUpperMenu(String label, String dashboardLink) {
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
    for (ContractingSwitchingUpperMenu upperMenu : ContractingSwitchingUpperMenu.values()) {
      menu.item(upperMenu.name(), new TopMenu.Item(upperMenu.getLabel(), upperMenu.getLink()));
    }
    return menu;
  }
}
