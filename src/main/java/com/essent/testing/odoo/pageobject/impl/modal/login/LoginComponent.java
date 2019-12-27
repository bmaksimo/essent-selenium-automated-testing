package com.essent.testing.odoo.pageobject.impl.modal.login;

import com.essent.testing.odoo.pageobject.Window;
import com.essent.testing.odoo.pageobject.impl.Component;
import org.openqa.selenium.By;

public abstract class LoginComponent extends Component {
  public LoginComponent(By selector) {
    super(selector);
  }

  public abstract Window login(String username, String password) throws Throwable;
}
