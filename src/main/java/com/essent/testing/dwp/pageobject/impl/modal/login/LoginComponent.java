package com.essent.testing.dwp.pageobject.impl.modal.login;

import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.WebElement;

public abstract class LoginComponent extends Component {
  public LoginComponent(WebElement element) {
    super(element);
  }

  public abstract Window login(String username, String password) throws Throwable;
}
