package com.essent.testing.dwp.menu.model;

import com.essent.testing.dwp.menu.model.impl.TopMenuImpl;

/**
 * Generated Java Enum
 * 
 * @author Dmitry Chebayewski <dmitry.chebayewski@billinghouse.nl>
 *
 */
public enum TasksUpperMenu {
  REJECTIONS_TASKS_INTERNAL("Rejections - internal",
      "rejections-tasks-internal-link"), DB_DEBIT_TASKS_FULL_VIEW("debit request tasks full view",
          "db-debit-tasks-full-view-link"), DB_GUARANTEE_TASKS_FULL_VIEW(
              "Guarantee Tasks full view",
              "db-guarantee-tasks-full-view-link"), DB_DUNNING_CALLS_TASK_LIST("Dunning call tasks",
                  "db-dunning-calls-task-list-link"), DB_PAYMENTPLAN_TASKS("Payment Plan Tasks",
                      "db-paymentplan-tasks-link"), TASKS_CONTRACTING_MOVE("Move",
                          "tasks-contracting-move-link"), SALES_COMPLAINTS("Sales complaints",
                              "sales-complaints-link"), TASKS_CUSTOM_DUTIES("Customs & duties",
                                  "tasks-custom-duties-link"), INTERNAL_COMPLAINTS(
                                      "Internal complaints",
                                      "internal-complaints-link"), TASKS_DEDUPLICATION(
                                          "Deduplication",
                                          "tasks-deduplication-link"), GRIDFEE_TASKS(
                                              "Gridfee tasks",
                                              "gridfee-tasks-link"), RECTIFICATION_TASKS(
                                                  "Rectification tasks",
                                                  "rectification-tasks-link"), ADVANCE_CASES(
                                                      "Advance cases/tasks",
                                                      "advance-cases-link"), SOCTAR_TASKS("Soctar",
                                                          "soctar-tasks-link"), OFFICIAL_EMAILS(
                                                              "Official E-mail",
                                                              "official-emails-link"), EXCEPTIONAL_INVOICE_CASES(
                                                                  "Exceptional invoice cases/tasks",
                                                                  "exceptional-invoice-cases-link"), SETTLEMENT_CASES(
                                                                      "Settlement cases/tasks",
                                                                      "settlement-cases-link"), BUDGET_METER_TASKS(
                                                                          "Budget Meter  Tasks",
                                                                          "budget-meter-tasks-link"), OFFICIAL_LETTERS(
                                                                              "Official Letters",
                                                                              "official-letters-link"), TASKS_EPLUS(
                                                                                  "Tasks E+",
                                                                                  "tasks-eplus-link"), EXTRA_SMILE(
                                                                                      "Extra Smile",
                                                                                      "extra-smile-link");

  private String label;
  private String link;

  private TasksUpperMenu(String label, String dashboardLink) {
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
    for (TasksUpperMenu upperMenu : TasksUpperMenu.values()) {
      menu.item(upperMenu.name(), new TopMenu.Item(upperMenu.getLabel(), upperMenu.getLink()));
    }
    return menu;
  }
}
