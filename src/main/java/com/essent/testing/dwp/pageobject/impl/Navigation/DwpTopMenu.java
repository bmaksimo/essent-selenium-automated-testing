package com.essent.testing.dwp.pageobject.impl.Navigation;

import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DwpTopMenu {
    private SeleniumDriver seleniumDriver;

    public DwpTopMenu(SeleniumDriver seleniumDriver) {
        this.seleniumDriver = seleniumDriver;
    }

    public WebElement accountsListLink() {
        return seleniumDriver.findElementWhenVisible(By.id("accounts-list-link"));
    }

    public void clickOnAccountsListLink() throws InterruptedException {
        accountsListLink().click();
    }

}
