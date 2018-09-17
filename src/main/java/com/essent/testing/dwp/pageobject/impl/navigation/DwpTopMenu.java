package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class DwpTopMenu extends Component {


    public DwpTopMenu(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    private WebElement topMenu(String label) {
        return seleniumDriver.findElementWhenVisible(By.xpath("//sub-menu-link[@label='" + label + "']//a"));
    }

    public void clickTopMenu(String label) {
        seleniumDriver.waitAndClick(topMenu(label));
    }
}
