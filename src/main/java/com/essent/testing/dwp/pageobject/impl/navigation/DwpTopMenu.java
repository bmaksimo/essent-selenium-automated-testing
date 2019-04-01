package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.testing.dwp.pageobject.elements.Button;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.elements.ButtonImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;


public class DwpTopMenu extends Component {

    public void findAndClickTopMenu(String label) {
        checkAndOpenTopMenu();
        String XPATH_SUBMENU_TEMPLATE = "//sub-menu-link[@label='${label}']//a";
        String query = createQuery(XPATH_SUBMENU_TEMPLATE, "label", label);
        WebElement element = seleniumDriver.findElementWhenVisible(By.xpath(query));
        seleniumDriver.waitAndClick(element);
    }

    public void findAndClickTopMenuNow(String label) {
        checkAndOpenTopMenu();
        String XPATH_SUBMENU_TEMPLATE = "//sub-menu-link[@label='${label}']//a";
        String query = createQuery(XPATH_SUBMENU_TEMPLATE, "label", label);
        WebElement element = seleniumDriver.findElementWhenVisible(By.xpath(query));
        seleniumDriver.clickNow(element);
    }

    private void checkAndOpenTopMenu() {
        String query = ".top.mobile-menu [name='top-menu-toggle']";
        WebElement hamburger = seleniumDriver.findElementOrNull(By.cssSelector(query), Duration.ofSeconds(30), Duration.ofMillis(100));
        if(hamburger!=null) {
            Button hamButton = new ButtonImpl(hamburger);
            hamButton.click();
        }
    }

    public void goBackToHomePage(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='top']/a[2]/span")));
    }
}
