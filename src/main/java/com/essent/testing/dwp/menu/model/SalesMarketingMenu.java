package com.essent.testing.dwp.menu.model;

import com.essent.testing.dwp.menu.model.impl.TopMenuImpl;

/**
 * Generated Java Enum
 *
 * @author Dmitry Chebayewski <dmitry.chebayewski@billinghouse.nl>
 *
 */
public enum SalesMarketingMenu {
  SALES_MARKETING_QUOTATION_TASKS("S&M Tasks",
      "sales-marketing-quotation-tasks-link"), MARKETTRANSACTIONS_DASHBOARD("Market Transactions",
          "market-transactions-dashboard-link"), LEAD_LIST("Leads",
              "lead-list-link"), MY_ACCOUNTS_LIST("My Accounts",
                  "my-accounts-list-link"), ACCOUNTS_LIST("Accounts",
                      "accounts-list-link"), QUOTES_LIST("Quotes",
                          "quotes-list-link"), CONTRACT_LIST("Contracts",
                              "contract-list-link"), PRICING_TOOL_DASHBOARD("Pricing tool",
                                  "pricing-tool-dashboard-link"), CAMPAIGN_LIST("Campaigns",
                                      "campaign-list-link"), EUROCCOR_QUOTES("Euroccor Quotes",
                                          "euroccor-quotes-link"), CASES("Cases",
        "cases-link");

  private String label;
  private String link;

  private SalesMarketingMenu(String label, String dashboardLink) {
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
    for (SalesMarketingMenu upperMenu : SalesMarketingMenu.values()) {
      menu.item(upperMenu.name(), new TopMenu.Item(upperMenu.getLabel(), upperMenu.getLink()));
    }
    return menu;
  }
}
