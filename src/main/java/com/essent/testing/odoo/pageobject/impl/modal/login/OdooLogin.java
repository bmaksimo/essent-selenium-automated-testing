package com.essent.testing.odoo.pageobject.impl.modal.login;


import com.essent.testing.odoo.pageobject.Window;
import com.essent.testing.odoo.pageobject.impl.main.OdooMainWindow;
import com.essent.testing.odoo.pageobject.modal.Dialog;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertNotNull;

public class OdooLogin extends LoginComponent implements Dialog {

    private final static By SELECTOR = By.cssSelector(".oe_login_form");

    public OdooLogin(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(SELECTOR), seleniumDriver);
    }

    public Window login(String username, String password) throws Throwable {
        WebElement element = seleniumDriver.findElementOrNull(By.id("login"));
        assertNotNull(element);
        element.clear();
        element.sendKeys(username);
        element = seleniumDriver.findElementOrNull(By.id("password"));
        assertNotNull(element);
        element.clear();
        element.sendKeys(password);
        element.submit();
        return new OdooMainWindow(seleniumDriver);
    }
}
