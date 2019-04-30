package com.essent.testing.odoo.pageobject.impl.main;

import com.essent.testing.odoo.pageobject.Window;
import com.essent.testing.odoo.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;

public class OdooMainWindow extends Component implements Window {

  protected static final By MAIN_WINDOW_SELECTOR = By.className("openerp_webclient_container");

  public OdooMainWindow(SeleniumDriver seleniumDriver) {
    super(seleniumDriver.findElementWhenPresent(MAIN_WINDOW_SELECTOR));
  }
}
