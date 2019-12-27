package com.essent.testing.odoo.pageobject.impl.modal.login;

import static org.junit.Assert.assertNotNull;

import com.essent.testing.odoo.pageobject.Window;
import com.essent.testing.odoo.pageobject.impl.main.OdooMainWindow;
import com.essent.testing.odoo.pageobject.modal.Dialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class OdooLogin extends LoginComponent implements Dialog {

  private static final By SELECTOR = By.cssSelector(".oe_login_form");

  public OdooLogin() {
    super(SELECTOR);
  }

  public Window login(String username, String password) {
    WebElement element = seleniumDriver.findElementWhenVisible(By.id("login"));
    assertNotNull(element);
    element.clear();
    element.sendKeys(username);
    element = seleniumDriver.findElementWhenPresent(By.id("password"));
    assertNotNull(element);
    element.clear();
    element.sendKeys(password);
    element.submit();
    return new OdooMainWindow(seleniumDriver);
  }
}
