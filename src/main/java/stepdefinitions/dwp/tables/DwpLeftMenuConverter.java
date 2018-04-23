package stepdefinitions.dwp.tables;

import com.essent.testing.dwp.menu.model.DwpLeftMenu;

public class DwpLeftMenuConverter {

  private String menuItem = DwpLeftMenu.SALES_MARKETING.getMenuItemLink();

  public String getMenuItem() {
    return menuItem;
  }

  public void setMenuItem(String menuItem) {
    this.menuItem = menuItem;
  }

  public DwpLeftMenu toMenuEnum() {
    return DwpLeftMenu.valueOf(menuItem);
  }

}
