package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.elements.ButtonImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;


public class DwpTopMenu extends Component {

    private static final String XPATH_SUBMENU_TEMPLATE = "//sub-menu-link[@label='${label}']//a";
    private static final String CSS_HAMBURGER_TOP_MENU = ".top.mobile-menu [name='top-menu-toggle']";

    public void findAndClickTopMenu(String label) {
        checkAndOpenTopMenu();
        String query = createQuery(XPATH_SUBMENU_TEMPLATE, "label", label);
        WebElement element = seleniumDriver.findElementWhenVisible(By.xpath(query));
        seleniumDriver.waitAndClick(element);
    }

    public void findAndClickTopMenuNow(String label) {
        checkAndOpenTopMenu();
        String query = createQuery(XPATH_SUBMENU_TEMPLATE, "label", label);
        WebElement element = seleniumDriver.findElementWhenVisible(By.xpath(query));
        seleniumDriver.clickNow(element);
    }

    private void checkAndOpenTopMenu() {
        WebElement hamburger = seleniumDriver.findElementOrNull(By.cssSelector(CSS_HAMBURGER_TOP_MENU), Duration.ofSeconds(30), Duration.ofMillis(100));
        if (hamburger != null) {
            new ButtonImpl(hamburger).click();
        }
    }

    public void goBackToHomePage(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='top']/a[2]/span")));
    }
}
