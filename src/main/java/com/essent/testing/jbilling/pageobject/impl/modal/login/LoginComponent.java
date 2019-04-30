package com.essent.testing.jbilling.pageobject.impl.modal.login;

import com.essent.testing.jbilling.pageobject.Window;
import com.essent.testing.jbilling.pageobject.impl.Component;
import org.openqa.selenium.By;

public abstract class LoginComponent extends Component {
  public LoginComponent(By selector) {
    super(selector);
  }

  public abstract Window login(String username, String password) throws Throwable;
}
