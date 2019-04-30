package com.essent.testing.dwp.pageobject.impl.modal.login;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

public class LoginAction extends Component {
  private static final By IWELCOME_SELECTOR = By.id("login-base");
  private static final By DWP_SELECTOR = By.cssSelector(".modal__container.login");

  public Window doLogin(String username, String password) throws Throwable {
    LoginComponent loginComponent = getCurrentLoginDialog();
    if (null == loginComponent) return null;
    return loginComponent.login(username, password);
  }

  private LoginComponent getCurrentLoginDialog() {
    Sleeper.sleepTightInSeconds(40);
    try {
      seleniumDriver.findElementWhenPresent(IWELCOME_SELECTOR);
      return new IWelcomeLoginDialog(seleniumDriver);
    } catch (TimeoutException te) {
      seleniumDriver.findElementWhenPresent(DWP_SELECTOR);
      return new DWPLoginDialog(seleniumDriver);
    }
  }
}
