package com.essent.testing.dwp.pageobject.impl;

import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.dwp.pageobject.Dialog;
import com.essent.testing.dwp.pageobject.LoginComponent;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.selenium.AngularSeleniumPage;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

public class LoginDialog extends LoginComponent implements Dialog {

    private final static Logger logger = LoggerFactory.getLogger(AngularSeleniumPage.class);
    private final static By SELECOR = By.cssSelector(".modal__container.login");


    public LoginDialog(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(SELECOR), seleniumDriver);
    }

    public Window login(String username, String password) throws Throwable {
        WebElement element = seleniumDriver.findElementOrNull(By.id("username"));
        assertNotNull(element);
        element.clear();
        element.sendKeys(username);
        element = seleniumDriver.findElementOrNull(By.id("password"));
        assertNotNull(element);
        element.clear();
        element.sendKeys(password);
        element.submit();
        seleniumDriver.waitUntilAngularPageIsLoaded();
        return new MainWindow(seleniumDriver);
    }
}
