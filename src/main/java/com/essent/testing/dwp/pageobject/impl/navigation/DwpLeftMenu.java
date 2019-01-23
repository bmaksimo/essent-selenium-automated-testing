package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.webdriver.dwp.SeleniumDriverDwpImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DwpLeftMenu extends Component {

    public DwpLeftMenu(SeleniumDriverDwpImpl seleniumDriver) {
        super(seleniumDriver);
    }

    private  WebElement leftElement(String name)  {
        return seleniumDriver.findElementWhenVisible(By.xpath("//main-menu-link[@name='" + name + "']//a"));
    }

    public void clickOnLeftElemet(String element) {
        seleniumDriver.waitAndClick(leftElement(element));
    }
}
