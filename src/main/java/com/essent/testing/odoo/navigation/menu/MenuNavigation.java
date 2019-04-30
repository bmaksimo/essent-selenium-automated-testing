package com.essent.testing.odoo.navigation.menu;

import com.essent.testing.odoo.pageobject.impl.Component;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.billinghouse.test_automation.util.gherkin.DateTimeFormatUtil.printPeriod;

/** Migrated version of TrPlusMenuSelectAction */
public class MenuNavigation extends Component {

  private static String MAIN_NEMU_ITEM_SELECTOR_TEMPLATE =
      "//div[@id='oe_main_menu_placeholder']//a[normalize-space()='${text}']";
  private static String MENU_LEAF_SELECTOR_TEMPLATE =
      "//a[span[normalize-space() = '${text}'] and starts-with(@class,'oe_menu_leaf')]";
  private static String MENU_TOGGLER_SELECTOR_TEMPLATE =
      "//a[span[normalize-space() = '${text}'] and starts-with(@class,'oe_menu_toggler')]";
  private String status = "UNDEFINED";
  private String reason = "Not executed";

  public String getStatus() {
    return status;
  }

  public String getReason() {
    return reason;
  }

  public boolean findAndClickMainMenuItem(String item) {
    By by = By.xpath(createQuery(MAIN_NEMU_ITEM_SELECTOR_TEMPLATE, "text", item));
    try {
      WebElement elementOrNull =
          seleniumDriver.findElementWhenPresent(by, Duration.ofSeconds(30), Duration.ofSeconds(5));
      elementOrNull.click();
      return true;
    } catch (TimeoutException te) {
      status = "FAILED";
      reason = "Main menu item" + item + "is not found";
      return false;
    }
  }

  public void executeAction(String menuPath) {
    String pathSeparator = "\\s*->\\s*";
    List<String> menu = new ArrayList<>(Arrays.asList(menuPath.split(pathSeparator)));
    List<String> path = menu.subList(0, menu.size() - 1);
    String action = menu.get(menu.size() - 1);
    WebElement match = findMenu(null, path);
    Optional<WebElement> clickAction = findActionOptional(match, action);
    if (!clickAction.isPresent()) {
      status = "FAILED";
      reason = "Menu path " + menuPath + " was not found";
    } else {
      status = "PASSED";
      clickAction.get().click();
    }
  }

  private Optional<WebElement> findActionOptional(WebElement accordion, String actionText) {
    if (accordion != null) {
      By menuLeaf = By.xpath(createQuery(MENU_LEAF_SELECTOR_TEMPLATE, "text", actionText));
      return findElementOptional(accordion, menuLeaf);
    } else {
      By mainMenu = By.xpath(createQuery(MENU_LEAF_SELECTOR_TEMPLATE, "text", actionText));
      return Optional.of(findElementWhenVisible(mainMenu));
    }
  }

  private WebElement findMenu(WebElement item, List<String> menu) {
    if (menu == null) {
      return null;
    }
    if (menu.isEmpty()) {
      return item;
    }
    String menuItem = menu.remove(0);
    List<WebElement> result;
    String query = createQuery(MENU_TOGGLER_SELECTOR_TEMPLATE, "text", menuItem);
    By menuTogglerQuery = By.xpath(query);

    if (item != null) {
      result = item.findElements(menuTogglerQuery);
    } else {
      result = seleniumDriver.getDriver().findElements(menuTogglerQuery);
    }

    if (result.size() > 0) {
      WebElement parent = result.get(0);
      findAndClick(parent, By.xpath(query));
      return this.findMenu(parent, menu);
    }
    return null;
  }

  private Optional<WebElement> findElementOptional(WebElement element, By selector) {
    logger().info("STEP:");
    DateTime startOfMeasurement = DateTime.now();
    FluentWait<WebElement> waiter =
        new FluentWait<>(element)
            .withTimeout(Duration.ofMinutes(1))
            .pollingEvery(Duration.ofSeconds(10))
            .ignoring(NoSuchElementException.class);

    WebElement elementFound =
        waiter.until(
            context -> {
              logger().info(" - WAIT: polling findElementWhenPresent()");
              return context.findElement(selector);
            });
    Period periodOfMeasurement = new Period(startOfMeasurement, DateTime.now());
    logger().info(" - MEASURED_TIME: " + printPeriod(periodOfMeasurement));
    if (element == null) {
      logger().warn(" - RESULT: empty");
    } else {
      logger()
          .debug(
              String.format(
                  " - RESULT: %s -> %s", selector, elementFound.getAttribute("innerHTML")));
    }
    return Optional.ofNullable(elementFound);
  }

  private void findAndClick(WebElement parent, By by) {
    FluentWait<WebDriver> waiter =
        new FluentWait<>(seleniumDriver.getDriver()).withTimeout(Duration.ofSeconds(5));
    WebElement childElement =
        waiter.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parent, by));
    waiter.until(ExpectedConditions.elementToBeClickable(childElement));
    childElement.click();
  }
}
