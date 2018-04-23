package com.essent.testing.dwp.pageobject.menu.impl;

import com.essent.automation.core.WebDriverWait;
import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.dwp.pageobject.menu.AccordionWrapperMenu;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteView;
import com.essent.testing.dwp.pageobject.quote.impl.SelectQuoteTypeView;
import com.essent.testing.selenium.SeleniumDriver;
import cucumber.runtime.CucumberException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PlusMenu extends Component implements AccordionWrapperMenu {

    private final static By SELECTOR                                = By.xpath("//div[@class='plus-menu']");
    private final static String LABELED_ACCORDION_WRAPPER_SELECTOR  = "//labeled-accordion-wrapper[@label='${value}']/a";
    private final static String MENU_ITEM_LINK                      = "//menu-link[@label='${value}']/li/a";


    public PlusMenu(WebElement parent, SeleniumDriver seleniumDriver) {
        super(parent.findElement(SELECTOR), seleniumDriver);
    }

    @Override
    public CreateQuoteView createB2CQuote(String path) {
        WebElement[] current = new WebElement[1];
        WebElement[] parent = new WebElement[1];
        current[0] = element;
        for(String item: path.split("->")){
            final String currentItem = item.trim();
            current[0] = new WebDriverWait(seleniumDriver.getDriver(), 5, 250).withoutException().until(
                driver -> {
                    parent[0] = current [0];
                    logger().info("STEP:");
                    logger().info(" - ACTION: SELENIUM_FIND_ELEMENT");
                    By by = By.xpath(LABELED_ACCORDION_WRAPPER_SELECTOR.replace("${value}", currentItem));
                    logger().info(" - BY: " + by.toString());
                    WebElement child = current[0].findElement(by);
                    return child;
                }
            );
            if(current[0] == null) {
                current[0] = new WebDriverWait(seleniumDriver.getDriver(), 5, 250).withoutException().until(
                    driver -> {
                        logger().info("STEP:");
                        logger().info(" - ACTION: SELENIUM_FIND_ELEMENT");
                        By by = By.xpath(MENU_ITEM_LINK.replace("${value}", currentItem));
                        logger().info(" - BY: " + by.toString());
                        WebElement child = parent[0].findElement(by);
                        return child;
                    }
                );
            }
            if(current[0] == null) {
                throw new CucumberException("Plus-Item '" + currentItem + "' was not found in path '" + path + "'");
            } else {
                current[0].click();
                logger().info(" - RESULT: " + "element: <" + current[0].getTagName() + " class='" + current[0].getAttribute("class") + "'>");
            }
        }
        return new SelectQuoteTypeView(seleniumDriver);
    }
}
