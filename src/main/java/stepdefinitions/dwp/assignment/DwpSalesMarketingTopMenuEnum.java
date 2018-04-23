package stepdefinitions.dwp.assignment;

/**
 * Created by Jim on 3-1-2018.
 */
public enum DwpSalesMarketingTopMenuEnum {
  TASKS("Tasks"),
  MARKET("Market"),
  LEADS("Leads"),
  MY_ACCOUNTS(""),
  ACCOUNTS("Accounts");

  private String menuItem;

  public String getMenuItem() {
    return menuItem;
  }

  private DwpSalesMarketingTopMenuEnum(String menuItem) {
    this.menuItem = menuItem;
  }

}
