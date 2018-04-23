package com.essent.testing.dwp.pageobject.impl;

import com.essent.automation.core.WebDriverWait;
import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.menu.AccordionWrapperMenu;
import com.essent.testing.dwp.pageobject.menu.Menu;
import com.essent.testing.dwp.pageobject.menu.impl.Navigation;
import com.essent.testing.dwp.pageobject.menu.impl.PlusMenu;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

public class MainWindow extends Component implements Window {

    protected static final By MAIN_WINDOW_SELECTOR = By.xpath("//div[@ui-view = 'main-content']//div[@class = 'main']");

    public MainWindow(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(MAIN_WINDOW_SELECTOR), seleniumDriver);
    }

    @Override
    public Menu getNavigation() {
        return new Navigation(element, seleniumDriver);
    }

    @Override
    public AccordionWrapperMenu getPlusMenu() {
        Boolean result = new WebDriverWait(seleniumDriver.getDriver(), 250).until(webDriver -> {
            final Map trPlusMenuClickItem = seleniumDriver.executeJavascriptMethod("TrPlusMenuClickItem", new HashMap<>());
            final boolean success = (boolean) trPlusMenuClickItem.get("plusMenuOpen");
            return success;
        });
        return new PlusMenu(element, seleniumDriver);
    }

}
