package com.essent.testing.dwp.pageobject.impl.Navigation;

import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DwpLeftMenu extends Component {

    public DwpLeftMenu(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public  WebElement leftElement(String element) throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.xpath("//main-menu-link[@name='"+element+"']//a"));
    }

    public void clickOnLeftElemet(String element) throws InterruptedException{
        //leftElement(element).click();
        seleniumDriver.waitAndClick(leftElement(element));
    }
}
