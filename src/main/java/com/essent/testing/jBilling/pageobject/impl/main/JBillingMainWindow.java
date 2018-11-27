package com.essent.testing.jBilling.pageobject.impl.main;


import com.essent.testing.jBilling.pageobject.Window;
import com.essent.testing.jBilling.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;

public class JBillingMainWindow extends Component implements Window {

    protected static final By MAIN_WINDOW_SELECTOR = By.id("main");

    public JBillingMainWindow(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(MAIN_WINDOW_SELECTOR), seleniumDriver);
    }
}
