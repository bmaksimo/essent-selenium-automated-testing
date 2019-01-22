package com.essent.testing.jbilling.pageobject.impl.main;


import com.essent.testing.jbilling.pageobject.Window;
import com.essent.testing.jbilling.pageobject.impl.Component;
import com.essent.testing.selenium.webdriver.jbilling.SeleniumDriverJBillingImpl;
import org.openqa.selenium.By;

public class JBillingMainWindow extends Component implements Window {

    protected static final By MAIN_WINDOW_SELECTOR = By.id("main");

    public JBillingMainWindow(SeleniumDriverJBillingImpl seleniumDriver) {
        super(seleniumDriver.findElementOrNull(MAIN_WINDOW_SELECTOR), seleniumDriver);
    }
}
