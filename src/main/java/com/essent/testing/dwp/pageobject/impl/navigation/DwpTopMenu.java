package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class DwpTopMenu extends Component {

    public DwpTopMenu(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public void findAndClickTopMenu(String label) {
        String XPATH_SUBMENU_TEMPLATE = "//sub-menu-link[@label='${label}']//a";
        String query = createQuery(XPATH_SUBMENU_TEMPLATE, "label", label);
        WebElement element = seleniumDriver.findElementWhenVisible(By.xpath(query));
        seleniumDriver.waitAndClick(element);
    }
}
