package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DwpPlusMenu extends Component {

    public DwpPlusMenu(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    private WebElement plusIcon() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//a[@name='Plus Menu']"));
    }


    public void clickOnplusIcon()  {
        seleniumDriver.waitAndClick(plusIcon());
    }

    private WebElement plusElement(String element) {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'"+element+"')]"));
    }

    public void clickplusElement(String element)  {
        seleniumDriver.waitAndClick(plusElement(element));
    }

    private WebElement serviceElemet(String key) {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'"+key+"')]"));
    }

    public void clickServiceElemet(String key)  {
        seleniumDriver.waitAndClick(serviceElemet(key));
    }
}
