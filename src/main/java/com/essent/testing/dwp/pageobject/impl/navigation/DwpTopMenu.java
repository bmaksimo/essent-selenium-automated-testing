package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.testing.dwp.pageobject.elements.Button;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.elements.ButtonImpl;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

public class DwpTopMenu extends Component {

  private static final String XPATH_SUBMENU_TEMPLATE =
      "//sub-menu-link[normalize-space(@label)='${label}']//a";
  private static final String CSS_HAMBURGER_TOP_MENU = ".top.mobile-menu [name='top-menu-toggle']";
  private static final String XPATH_HOME_BUTTON = "//div[@class='top']/a[2]/span";

  public void findAndClickTopMenu(String label) {
    checkAndOpenTopMenu();
    String query = createQuery(XPATH_SUBMENU_TEMPLATE, "label", label);
    WebElement element = seleniumDriver.findElementWhenVisible(By.xpath(query));
    seleniumDriver.clickNow(element);
  }

  public void findAndClickTopMenuNow(String label) {
    checkAndOpenTopMenu();
    String query = createQuery(XPATH_SUBMENU_TEMPLATE, "label", label);
    WebElement element = seleniumDriver.findElementWhenVisible(By.xpath(query));
    seleniumDriver.clickNow(element);
  }

  private void checkAndOpenTopMenu() {
    try {
      WebElement hamburger =
          seleniumDriver.findElementWhenPresent(
              By.cssSelector(CSS_HAMBURGER_TOP_MENU),
              Duration.ofSeconds(5),
              Duration.ofMillis(100));
      Button hamButton = new ButtonImpl(hamburger);
      hamButton.click();
    } catch (TimeoutException te) {
      logger().debug("no hamburger was found");
    }
  }

  public void goBackToHomePage() {
    seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(XPATH_HOME_BUTTON)));
  }
}
