package com.essent.testing.dwp.menu.model;

import com.essent.testing.dwp.menu.model.impl.TopMenuImpl;

/**
 * Generated Java Enum
 * 
 * @author Dmitry Chebayewski <dmitry.chebayewski@billinghouse.nl>
 *
 */
public enum AdminUpperMenu {
  FAILED_JOBS("Failed jobs", "failed-jobs-link"), JOB_LOG("Job Log", "job-log-link"), DGO_DASHBOARD(
      "DGO/Supplier/BRP", "dgo-dashboard-link"), FAQS("FAQs",
          "faqs-link"), ACTIVITIPROCESSINSTANCES("ActivitiProcessInstances",
              "activitiprocessinstances-link"), GRAYLOG_ERRORS("graylog_errors",
                  "graylog-errors-link"), DEALER_AGENT_OID("dealer_agent_oid",
                      "dealer-agent-oid-link"), DOCUMENTS("Documents",
                          "documents-link"), USERS("Users", "users-link");

  private String label;
  private String link;

  private AdminUpperMenu(String label, String dashboardLink) {
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
    for (AdminUpperMenu upperMenu : AdminUpperMenu.values()) {
      menu.item(upperMenu.name(), new TopMenu.Item(upperMenu.getLabel(), upperMenu.getLink()));
    }
    return menu;
  }
}
