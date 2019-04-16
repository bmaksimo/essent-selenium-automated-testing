package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.*;

public class DwpPlusMenu extends Component {

    private static String REPLACEMENT_KEY1 = "REPLACEMENT_KEY1";
    private static String REPLACEMENT_KEY2 = "REPLACEMENT_KEY2";
    private static String ACCORDION_BUTTON_SELECTOR_TEMPLATE_CHILD = "//menu-link[normalize-space(@label)='${" + REPLACEMENT_KEY1 + "}']//a";
    private static String ACCORDION_BUTTON_SELECTOR_TEMPLATE = "//labeled-accordion-wrapper[normalize-space(@label)='${" + REPLACEMENT_KEY2 + "}']";
    private static String LABELED_ACCORDION_WRAPPER_SELECTOR  =  "labeled-accordion-wrapper[label='${text}'] > a";
    private static int LEAF_SIZED = 1;
    private static int PARENT_MENU = 2;

    private final static String XPATH_CONTAINS_TEXT_TEMPLATE = "//span[contains(text(),'${text}')]";

    /**
     * @deprecated Use {@link #executeAction(String)}
     * @param text Plus Element text
     */
    @Deprecated
    public void findAndClickPlusElement(String text)  {
        String query = createQuery(XPATH_CONTAINS_TEXT_TEMPLATE, "text", text);
        WebElement element = seleniumDriver.findElementWhenVisible(By.xpath(query));
        seleniumDriver.waitAndClick(element);
    }

    /**
     * @deprecated Use {@link #executeAction(String)}
     * @param key Plus Element text
     */
    @Deprecated
    public void findAndClickServiceElement(String key)  {
        String query = createQuery(XPATH_CONTAINS_TEXT_TEMPLATE, "text", key);
        WebElement element = seleniumDriver.findElementWhenVisible(By.xpath(query));
        seleniumDriver.waitAndClick(element);
    }

    public void findAndClickOnPlusIcon()  {
        WebElement element = seleniumDriver.findElementWhenVisible(By.xpath("//a[@name='Plus Menu']"));
        seleniumDriver.waitAndClick(element);
    }

    /**
     * Executes DWP Plus Menu
     * @param menuPath Menu path in format "Menu A -> Submenu B ... -> Submenu N -> action"
     * @return
     */
    public boolean executeAction(String menuPath) {
        seleniumDriver.waitForRequestsToFinish();
        String pathSeparator = "\\s*->\\s*";
        List<String> menu = new ArrayList<>(Arrays.asList(menuPath.split(pathSeparator)));
        List<String> path = menu.subList(0, menu.size() - 1);
        String action = menu.get(menu.size() - 1);
        String parentMenu = menu.size() > LEAF_SIZED ? menu.get(menu.size() - PARENT_MENU) : "";
        findMenu(null, path);
        WebElement clickAction = findAction(action, parentMenu);
        seleniumDriver.waitForRequestsToFinish();
        return clickAction !=  null;
    }

    private WebElement findAction(String actionLabel, String parentMenu) {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put(REPLACEMENT_KEY1, actionLabel);

        if (StringUtils.isNotBlank(parentMenu))
            valuesMap.put(REPLACEMENT_KEY2, parentMenu);

        By accordionButtonSelector = valuesMap.size() > 1 ?
            By.xpath(createQuery(ACCORDION_BUTTON_SELECTOR_TEMPLATE + "/child::div" + ACCORDION_BUTTON_SELECTOR_TEMPLATE_CHILD, valuesMap))
            : By.xpath(createQuery(ACCORDION_BUTTON_SELECTOR_TEMPLATE_CHILD, valuesMap));

        WebElement accordionButton = seleniumDriver.findElementWhenPresent(accordionButtonSelector);
        FluentWait<WebDriver> waiter = new FluentWait<>(seleniumDriver.getDriver()).withTimeout(Duration.ofSeconds(5));
        accordionButton = waiter.until(ExpectedConditions.elementToBeClickable(accordionButton));
        accordionButton.click();
        return accordionButton;
    }

    private WebElement findMenu(WebElement item, List<String> menu) {
        if(menu == null) {
            return null;
        }
        if(menu.isEmpty()) {
            return item;
        }
        String menuItem = menu.remove(0);
        By labeledAccordionWrapperBy = By.cssSelector(createQuery(LABELED_ACCORDION_WRAPPER_SELECTOR, "text", menuItem));
        FluentWait<WebDriver> waiter = new FluentWait<>(seleniumDriver.getDriver()).withTimeout(Duration.ofSeconds(5));
        WebElement result = waiter.until(ExpectedConditions.presenceOfElementLocated(labeledAccordionWrapperBy));
        result.click();
        Sleeper.sleepTight(200);
        return this.findMenu(result, menu);
    }
}
