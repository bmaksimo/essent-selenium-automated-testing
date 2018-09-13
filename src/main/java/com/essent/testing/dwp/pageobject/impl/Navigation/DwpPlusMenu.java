package com.essent.testing.dwp.pageobject.impl.Navigation;

import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DwpPlusMenu extends Component {

    public DwpPlusMenu(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public WebElement plusIcon() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//a[@name='Plus Menu']"));
    }

    public void clickOnplusIcon() throws InterruptedException {
        plusIcon().click();
    }

    public WebElement plusElement(String element) {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'"+element+"')]"));

    }

    public void clickplusElement(String element) throws InterruptedException {
        plusElement(element).click();
    }



    public WebElement serviceElemet(String key) {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'"+key+"')]"));
    }

    public void clickServiceElemet(String key) throws InterruptedException {
        serviceElemet(key).click();
    }
}
