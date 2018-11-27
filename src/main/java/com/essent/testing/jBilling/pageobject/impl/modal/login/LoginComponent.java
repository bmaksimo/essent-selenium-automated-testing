package com.essent.testing.jBilling.pageobject.impl.modal.login;


import com.essent.testing.jBilling.pageobject.Window;
import com.essent.testing.jBilling.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.WebElement;

public abstract class LoginComponent extends Component {
    public LoginComponent(WebElement element, SeleniumDriver seleniumDriver) { super(element, seleniumDriver); }
    public abstract Window login(String username, String password) throws Throwable;
}
