package com.essent.testing.odoo.pageobject.impl.modal.login;


import com.essent.testing.odoo.pageobject.Window;
import com.essent.testing.odoo.pageobject.impl.Component;
import org.openqa.selenium.WebElement;

public abstract class LoginComponent extends Component {
    public LoginComponent(WebElement element) { super(element); }
    public abstract Window login(String username, String password) throws Throwable;
}
