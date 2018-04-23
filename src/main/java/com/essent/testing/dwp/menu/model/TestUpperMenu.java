package com.essent.testing.dwp.menu.model;

import com.essent.testing.dwp.menu.model.impl.TopMenuImpl;

/**
 * Generated Java Enum
 * 
 * @author Dmitry Chebayewski <dmitry.chebayewski@billinghouse.nl>
 *
 */
public enum TestUpperMenu {
  NON_INVOICED_CONNECTIONS("NonInvoicedConnections",
      "non-invoiced-connections-link"), SELLINGPRODUCT_DETAILS("SellingProduct_details",
          "sellingproduct-details-link"), CONTRACTLINES_EPLUS("Contractlines_eplus",
              "contractlines-eplus-link"), CONFIGLES_LIESBET("Configles_Liesbet",
                  "configles-liesbet-link"), KEY_CONFIGLES_EBOECKX("configles_eboeckx",
                      "key-configles-eboeckx-link"), CONDITIONAL_MESSAGES_TEST(
                          "Conditional messages test",
                          "conditional-messages-test-link"), CONFIGLES_KATYCONFIG(
                              "Configles Katy Config", "configles-katyconfig-link");

  private String label;
  private String link;

  private TestUpperMenu(String label, String dashboardLink) {
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
    for (TestUpperMenu upperMenu : TestUpperMenu.values()) {
      menu.item(upperMenu.name(), new TopMenu.Item(upperMenu.getLabel(), upperMenu.getLink()));
    }
    return menu;
  }
}
