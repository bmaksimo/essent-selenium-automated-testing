package com.essent.testing.dwp.pageobject.impl;

import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.dwp.pageobject.Dialog;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

public class IWelcomeLoginDialog extends Component implements Dialog {
    private final static By SELECTOR = By.id("login-base");


    public IWelcomeLoginDialog(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(SELECTOR), seleniumDriver);
    }

    public Window login(String username, String password) throws Throwable {
        final String usernameField = "idToken1";
        final String passwordField = "idToken2";
        WebElement element = seleniumDriver.findElementOrNull(By.id(usernameField));
        assertNotNull(element);
        element.clear();
        element.sendKeys(username);
        element = seleniumDriver.findElementOrNull(By.id(passwordField));
        assertNotNull(element);
        element.clear();
        element.sendKeys(password);
        element.submit();
        seleniumDriver.waitUntilAngularPageIsLoaded();
        seleniumDriver.getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        seleniumDriver.getDriver().get("https://uat04.nova.essent.be/dwp/#");
        return new MainWindow(seleniumDriver);
    }
}
