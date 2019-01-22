package com.essent.testing.jbilling.pageobject.impl.modal.login;


import com.essent.testing.jbilling.pageobject.Window;
import com.essent.testing.jbilling.pageobject.impl.Component;
import com.essent.testing.selenium.webdriver.jbilling.SeleniumDriverJBillingImpl;
import org.openqa.selenium.WebElement;

public abstract class LoginComponent extends Component {
    public LoginComponent(WebElement element, SeleniumDriverJBillingImpl seleniumDriver) { super(element, seleniumDriver); }
    public abstract Window login(String username, String password) throws Throwable;
}
