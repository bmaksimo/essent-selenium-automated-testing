package com.essent.testing.dwp.pageobject.impl.modal.login;

import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.impl.main.MainWindow;
import com.essent.testing.dwp.pageobject.modal.Dialog;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertNotNull;

public class DWPLoginDialog extends LoginComponent implements Dialog {
    private final static By SELECOR = By.cssSelector(".modal__container.login");

    public DWPLoginDialog(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementWhenPresent(SELECOR));
    }

    public Window login(String username, String password){

        WebElement element = seleniumDriver.findElementWhenPresent(By.id("username"));
        assertNotNull(element);
        element.clear();
        element.sendKeys(username);
        element = seleniumDriver.findElementWhenPresent(By.id("password"));
        assertNotNull(element);
        element.clear();
        element.sendKeys(password);
        element.submit();
        seleniumDriver.waitForRequestsToFinish();
        return new MainWindow(seleniumDriver);
    }
}
