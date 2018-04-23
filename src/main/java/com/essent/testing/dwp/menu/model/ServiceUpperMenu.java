package com.essent.testing.dwp.menu.model;

import com.essent.testing.dwp.menu.model.impl.TopMenuImpl;

/**
 * Generated Java Enum
 * 
 * @author Dmitry Chebayewski <dmitry.chebayewski@billinghouse.nl>
 *
 */
public enum ServiceUpperMenu {
  SERVICE_TASKS("Service_Tasks", "service-tasks-link"), WRITTEN("Written",
      "written-link"), OUTBOUND("Outbound", "outbound-link"), ACCOUNTS_LIST("Accounts",
          "accounts-list-link"), CHEAPEST_PRODUCTS("Cheapest products",
              "cheapest-products-link"), CASES("Cases", "cases-link"), MISSING_CHEAPEST_PRODUCTS(
                  "Missing cheapest products", "missing-cheapest-products-link"), SEGMENTATION(
                      "Segmentation", "segmentation-link");

  private String label;
  private String link;

  private ServiceUpperMenu(String label, String dashboardLink) {
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
    for (ServiceUpperMenu upperMenu : ServiceUpperMenu.values()) {
      menu.item(upperMenu.name(), new TopMenu.Item(upperMenu.getLabel(), upperMenu.getLink()));
    }
    return menu;
  }
}
