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

    public WebElement logACaseForAccountOption(String key) {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'"+key+"')]"));
    }

    public void clickOnLogAcaseForAccountOption(String key) throws InterruptedException {
        logACaseForAccountOption(key).click();
    }
}
