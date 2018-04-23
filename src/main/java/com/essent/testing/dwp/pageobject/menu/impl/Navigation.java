package com.essent.testing.dwp.pageobject.menu.impl;

import com.billinghouse.javascript.testrunner.dwp.menu.MenuTests;
import com.essent.testing.dwp.menu.model.DwpLeftMenu;
import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.dwp.pageobject.menu.Menu;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Navigation extends Component implements Menu {

    private static final By SELECTOR =  By.className("nav");

    public Navigation(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public Navigation(WebElement parent, SeleniumDriver seleniumDriver) {
        super(parent.findElement(SELECTOR), seleniumDriver);
    }

    @Override
    public boolean hasItem(String label) {
        DwpLeftMenu item = DwpLeftMenu.get(label);
        return seleniumDriver.executeJsTest(MenuTests.LEFT_MENU_ITEM_TEST.getTest(),  item.getMenuItemLink());
    }
}
