package com.essent.testing.dwp.navigation;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.billinghouse.test_automation.util.gherkin.DateTimeFormatUtil.printPeriod;

/**
 * Migrated version of TrPlusMenuSelectAction
 */
public class PlusMenuNavigation extends Component {
    private String status = "UNDEFINED";
    private String reason = "Not executed";

    public PlusMenuNavigation(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    /*
    findAction(accordion, actionLabel) {
        if (accordion != undefined) {
            let resultAction = $(accordion).find("[label='" + actionLabel + "']").find('.accordion-button');
            if(resultAction.index() == 0) {
                return resultAction;
            }
        }
        return undefined;
    }
     */
    private static String ACCORDION_BUTTON_SELECTOR_TEMPLATE = "[label='${text}'] .accordion-button";
    private static String LABELED_ACCORDION_WRAPPER_SELECTOR  =  "labeled-accordion-wrapper";

    /*
    run() {
        const PATH_PATTERN = new RegExp('\\s*->\\s*');
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let items = options.path.split(PATH_PATTERN);
        let path = items.slice(0, items.length - 1);
        let actionPath = items[items.length - 1];
        let match = this.findMenu(undefined, path);
        let action = this.findAction(match, actionPath);
        if(action == undefined) {
            result.status = 'FAILED';
            result.reason = "Menu path " + options.path + " was not found";
        } else {
            result.status = 'PASSED';
            result.reason = '';
            $(action).trigger('click');
        }
        this.resolveCallback(result);

    }
     */

    void executeAction(String menuPath) {
        String pathSeparator = "\\s*->\\s*";
        List<String> menu = new ArrayList<>(Arrays.asList(menuPath.split(pathSeparator)));
        List<String> path = menu.subList(0, menu.size() - 1);
        String action = menu.get(menu.size() - 1);
        WebElement match = findMenu(null, path);
        WebElement clickAction = findAction(match, action);
        if(clickAction == null) {
            status = "FAILED";
            reason = "Menu path " + menuPath + " was not found";
        } else {
            status = "PASSED";
            Sleeper.sleepTightInSeconds(1);
            clickAction.click();;
        }
    }

    private WebElement findAction(WebElement accordion, String actionLabel) {
       if (accordion != null) {
           By accordionButtonSelector = By.cssSelector(createQuery(ACCORDION_BUTTON_SELECTOR_TEMPLATE, "text", actionLabel));
           return  findElementOrNull(accordion, accordionButtonSelector);
       }
       return null;
    }

    /*
    findMenu(item, menu) {
        if(menu == undefined) {
            return undefined;
        }
        let menuItem = menu.shift();
        if(menuItem == undefined) {
            return item;
        }
        let context;
        if(item != undefined) {
            context = $(item).find('labeled-accordion-wrapper');
        } else {
            context = $('labeled-accordion-wrapper');
        }
        context = context.filter((i, e)=>{
            return $(e).attr('label') === menuItem;
        });
        if (context.index() >= 0) {
            $(context[0]).find('a')[0].click();
            return this.findMenu(context[0], menu);
        }
        return undefined;
    }
    */
    private WebElement findMenu(WebElement item, List<String> menu) {
         if(menu == null) {
            return null;
        }
        if(menu.isEmpty()) {
            return item;
        }
        String menuItem = menu.remove(0);

        List<WebElement> context;
        By labeledAccordionWrapperBy = By.cssSelector(LABELED_ACCORDION_WRAPPER_SELECTOR);
        if(item != null) {
            context = item.findElements(labeledAccordionWrapperBy);
        } else {
            context = seleniumDriver.getDriver().findElements(labeledAccordionWrapperBy);
        }
        List<WebElement> result = context.stream().filter((e) -> {
            String label = e.getAttribute("label");
            return StringUtils.equals(label, menuItem);
        }).collect(Collectors.toList());

        if (result.size() > 0) {
            WebElement parent = result.get(0);
            findAndClick(parent, By.cssSelector("a"));
            return this.findMenu(parent, menu);
        }
        return null;
    }

    private WebElement findElementOrNull(WebElement element, By selector) {
        logger().info("STEP:");
        DateTime startOfMeasurement = DateTime.now();
        FluentWait<WebElement> waiter = new FluentWait<>(element)
            .withTimeout(Duration.ofMinutes(1))
            .pollingEvery(Duration.ofSeconds(10))
            .ignoreAll(
                Arrays.asList(
                    NoSuchElementException.class,
                    StaleElementReferenceException.class)
            );

        List<WebElement> elements = waiter.until(driver -> {
            logger().info(" - WAIT: polling findElementOrNull()");
            return driver.findElements(selector);
        });
        Period periodOfMeasurement = new Period(startOfMeasurement, DateTime.now());
        logger().info(" - MEASURED_TIME: " + printPeriod(periodOfMeasurement));
        if(elements.isEmpty()) {
            logger().warn(" - RESULT: empty");
            return null;
        } else  {
            WebElement webElement = elements.get(0);
            logger().info(String.format(" - RESULT: %s -> %s", selector, webElement.getAttribute("innerHTML")));
            return webElement;
        }
    }

    private void findAndClick(WebElement parent, By by) {
        FluentWait<WebDriver> waiter = new FluentWait<>(seleniumDriver.getDriver()).withTimeout(Duration.ofSeconds(5));
        WebElement childElement = waiter.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parent, by));
        waiter.until(ExpectedConditions.elementToBeClickable(childElement));
        childElement.click();
    }

}
