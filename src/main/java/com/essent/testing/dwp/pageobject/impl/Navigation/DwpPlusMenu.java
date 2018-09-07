package com.essent.testing.dwp.pageobject.impl.Navigation;

import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DwpPlusMenu {

    private SeleniumDriver seleniumDriver;

    public DwpPlusMenu(SeleniumDriver seleniumDriver) {
        this.seleniumDriver = seleniumDriver;
    }

    public WebElement plusIcon() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//a[@name='Plus Menu']"));
    }

    public void clickOnplusIcon() throws InterruptedException {
        plusIcon().click();
    }

    public WebElement serviceDropdownMenu() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Service')]"));
    }

    public void clickOnServiceDropdownMenu() throws InterruptedException {
        serviceDropdownMenu().click();
    }

    public WebElement logAcaseForAccountOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Log a case for account')]"));
    }

    public void clickOnLogAcaseForAccountOption() throws InterruptedException {
        logAcaseForAccountOption().click();
    }
}
