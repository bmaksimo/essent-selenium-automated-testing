package com.essent.testing.dwp.pageobject.impl;

import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;

public class MainWindow extends Component implements Window {

    protected static final By MAIN_WINDOW_SELECTOR = By.xpath("//div[@ui-view = 'main-content']//div[@class = 'main']");

    public MainWindow(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(MAIN_WINDOW_SELECTOR), seleniumDriver);
    }
}
