package com.essent.testing.dwp.pageobject.impl.modal.login;

import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

public class LoginAction extends Component {
    private final static By IWELCOME_SELECTOR = By.xpath("//input[@value='Inloggen']");

    public Window doLogin(String username, String password) throws Throwable {
        return getCurrentLoginDialog().login(username, password);
    }

    private LoginComponent getCurrentLoginDialog() {
        try {
            seleniumDriver.findElementWhenPresent(IWELCOME_SELECTOR, Duration.ofSeconds(90), Duration.ofSeconds(10));
            return new IWelcomeLoginDialog(seleniumDriver);
        } catch (TimeoutException te) {
            throw new TimeoutException("No login page was found");
        }
    }
}
