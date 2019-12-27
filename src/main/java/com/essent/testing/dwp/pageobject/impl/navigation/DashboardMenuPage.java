package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DashboardMenuPage extends Component {
  private static Map<String, String> menuMap = new HashMap<>();
  private static final String DASHBOARD_MENU_BUTTON =
      "//a[@class='col-1-4 nav-item']/span[@class='";
  private static final String CLICKED_DASHBOARD_MENU_BUTTON =
      "//a[@class='col-1-4 nav-item active']/span[@class='";

  static {
    menuMap.put("Details", "icon-bedrijf");
    menuMap.put("Sales", "icon-winkelwagen");
    menuMap.put("Billing", "icon-euro");
    menuMap.put("Service", "icon-agent");
    menuMap.put("Contracten", "icon-contract");
    menuMap.put("Marktberichten", "icon-flowchart");
    menuMap.put("BestRetentionOffer", "icon-opportunity");
    menuMap.put("Documenten", "icon-mappen");
  }

  private WebElement getDashboardElement(String name) {
    seleniumDriver.waitForRequestsToFinish();
    return seleniumDriver.findElementWhenVisible(
        By.xpath(DASHBOARD_MENU_BUTTON + menuMap.get(name) + "']"));
  }

  private Optional<WebElement> getDashboardElementOptional(String name) {
    seleniumDriver.waitForRequestsToFinish();
    return seleniumDriver.findElementOptional(
        By.xpath(DASHBOARD_MENU_BUTTON + menuMap.get(name) + "']"));
  }

  private boolean isDashboardElementClicked(String name) {
    seleniumDriver.waitForRequestsToFinish();
    Optional<WebElement> clickedDashboardElement =
        seleniumDriver.findElementOptional(
            By.xpath(CLICKED_DASHBOARD_MENU_BUTTON + menuMap.get(name) + "']"));
    return clickedDashboardElement.isPresent();
  }

  public void clickOnDashboardElement(String element) {
    seleniumDriver.waitForRequestsToFinish();

    if (isDashboardElementClicked(element)) return;

    Optional<WebElement> dashboardMenuElementOptional;
    WebElement dashboardElement;
    boolean dashboardMenuSelected = false;

    int currentAttempt = 0;
    int maxAttempts = 30;

    while (!dashboardMenuSelected && currentAttempt < maxAttempts) {
      currentAttempt++;
      dashboardMenuElementOptional = getDashboardElementOptional(element);
      if (dashboardMenuElementOptional.isPresent()) {
        dashboardElement = dashboardMenuElementOptional.get();
        clickWithRetries(dashboardElement, 10);
        dashboardMenuSelected = isDashboardElementClicked(element);
      }
      Sleeper.sleepTightInSeconds(2);
    }

    Assert.assertTrue("Dashboard menu element was not successfully clicked", dashboardMenuSelected);
  }

  public void clickOnDashboardElementNow(String element) {
    seleniumDriver.clickNow(getDashboardElement(element));
  }
}
