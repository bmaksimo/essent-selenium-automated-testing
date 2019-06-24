package com.essent.testing.dwp.pageobject.impl.modal.login;

import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.impl.main.MainWindow;
import com.essent.testing.dwp.pageobject.modal.Dialog;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

public class IWelcomeLoginDialog extends LoginComponent implements Dialog {
    private final static By SELECTOR = By.id("login-base");

    public IWelcomeLoginDialog(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementWhenPresent(SELECTOR));
    }

    public Window login(String username, String password) throws Throwable {
        final String usernameField = "idToken1";
        final String passwordField = "idToken2";
        final String submitButtonField = "loginButton_0";

        WebElement element = seleniumDriver.findElementWhenPresent(By.id(usernameField));
        assertNotNull(element);
        element.clear();
        element.sendKeys(username);
        element = seleniumDriver.findElementWhenPresent(By.id(passwordField));
        assertNotNull(element);
        element.clear();
        element.sendKeys(password);
        element = seleniumDriver.findElementWhenPresent(By.id(submitButtonField));
        assertNotNull(element);
        element.click();

        return new MainWindow(seleniumDriver);
    }
}
