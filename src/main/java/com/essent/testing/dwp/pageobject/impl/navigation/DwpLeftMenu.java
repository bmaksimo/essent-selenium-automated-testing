package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DwpLeftMenu extends Component {

    private WebElement getLeftElement(String name)  {
        return seleniumDriver.findElementWhenVisible(By.xpath("//main-menu-link[@name='" + name + "']//a"));
    }

    public void clickOnLeftElement(String element) {
        seleniumDriver.waitAndClick(getLeftElement(element));
    }
}
