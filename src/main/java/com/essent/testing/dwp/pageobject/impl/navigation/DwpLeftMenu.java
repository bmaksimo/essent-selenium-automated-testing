package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DwpLeftMenu extends Component {

    public DwpLeftMenu(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    private  WebElement leftElement(String element)  {
        return seleniumDriver.findElementWhenVisible(By.xpath("//main-menu-link[@name='" + element + "']//a"));
    }

    public void clickOnLeftElemet(String element) {
        seleniumDriver.waitAndClick(leftElement(element.toLowerCase()));
    }
}
