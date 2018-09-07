package com.essent.testing.dwp.pageobject.impl.Navigation;

import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DwpLeftMenu {
    private SeleniumDriver seleniumDriver;

    public DwpLeftMenu(SeleniumDriver seleniumDriver) {

        this.seleniumDriver = seleniumDriver;
    }

    public WebElement salesMarketingLink() throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.id("sales-marketing-link"));

    }

    public void clickOnsalesMarketingLink() throws InterruptedException {
        salesMarketingLink().click();
    }
}
