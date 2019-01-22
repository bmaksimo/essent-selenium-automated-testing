package com.essent.testing.odoo.pageobject.impl.main;


import com.essent.testing.odoo.pageobject.Window;
import com.essent.testing.odoo.pageobject.impl.Component;
import com.essent.testing.selenium.webdriver.dwp.SeleniumDriverDwpImpl;
import com.essent.testing.selenium.webdriver.odoo.SeleniumDriverOdooImpl;
import org.openqa.selenium.By;

public class OdooMainWindow extends Component implements Window {

    protected static final By MAIN_WINDOW_SELECTOR = By.className("openerp_webclient_container");

    public OdooMainWindow(SeleniumDriverOdooImpl seleniumDriver) {
        super(seleniumDriver.findElementOrNull(MAIN_WINDOW_SELECTOR), seleniumDriver);
    }
}
