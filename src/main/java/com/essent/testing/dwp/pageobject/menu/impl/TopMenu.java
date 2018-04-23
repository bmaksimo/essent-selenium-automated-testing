package com.essent.testing.dwp.pageobject.menu.impl;

import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.dwp.pageobject.menu.Menu;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TopMenu extends Component implements Menu {

    private static final By SELECTOR =  By.className("top");

    public TopMenu(WebElement parent, SeleniumDriver seleniumDriver) {
        super(parent.findElement(SELECTOR), seleniumDriver);
    }

    @Override
    public boolean hasItem(String label) {
        return false;
    }
}
