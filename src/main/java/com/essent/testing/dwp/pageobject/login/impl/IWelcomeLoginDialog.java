package com.essent.testing.dwp.pageobject.login.impl;

import com.essent.testing.dwp.pageobject.Dialog;
import com.essent.testing.dwp.pageobject.LoginComponent;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.impl.MainWindow;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

public class IWelcomeLoginDialog extends LoginComponent implements Dialog {
    private final static By SELECTOR = By.id("login-base");


    public IWelcomeLoginDialog(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(SELECTOR), seleniumDriver);
    }

    public Window login(String username, String password) throws Throwable {
        final String usernameField = "idToken1";
        final String passwordField = "idToken2";
        final String submitButtonField = "loginButton_0";

        WebElement element = seleniumDriver.findElementOrNull(By.id(usernameField));
        assertNotNull(element);
        element.clear();
        element.sendKeys(username);
        element = seleniumDriver.findElementOrNull(By.id(passwordField));
        assertNotNull(element);
        element.clear();
        element.sendKeys(password);
        element = seleniumDriver.findElementOrNull(By.id(submitButtonField));
        assertNotNull(element);
        element.click();
        seleniumDriver.waitUntilAngularPageIsLoaded();
        seleniumDriver.getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        return new MainWindow(seleniumDriver);
    }
}
