package com.essent.testing.jbilling.pageobject.impl.modal.login;

import com.essent.testing.jbilling.pageobject.Window;
import com.essent.testing.jbilling.pageobject.impl.main.JBillingMainWindow;
import com.essent.testing.jbilling.pageobject.modal.Dialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class JBillingLogin extends LoginComponent implements Dialog {

  private static final By SELECTOR = By.cssSelector("#login-form");

  public JBillingLogin() {
    super(SELECTOR);
  }

  public Window login(String username, String password) {
    WebElement user = seleniumDriver.findElementWhenVisible(By.id("j_username"));
    seleniumDriver.waitAndSendKeys(user, username);

    WebElement pass = seleniumDriver.findElementWhenVisible(By.id("j_password"));
    seleniumDriver.waitAndSendKeys(pass, password);

    WebElement login = seleniumDriver.findElementWhenVisible(By.xpath("//a[@class='submit save']"));

    seleniumDriver.waitAndClick(login);

    return new JBillingMainWindow();
  }
}
