package com.essent.testing.dwp.pageobject.impl.Navigation;

import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DwpLowerLeftMenu {

    private SeleniumDriver seleniumDriver;

    public DwpLowerLeftMenu(SeleniumDriver seleniumDriver) {
        this.seleniumDriver = seleniumDriver;
    }

    public WebElement serviceButton() throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Service')]"));
    }

    public void clickOnServiceButton() throws InterruptedException {
        serviceButton().click();
    }
}
