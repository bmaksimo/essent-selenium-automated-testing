package stepdefinitions.dwp.menu;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import java.util.List;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TopMenuActions extends Component {
  private static final String ICON_ARROW_UP_CLASS_NAME = "icon-arrow-up";
  private static final String ICON_PLUS_CLASS_NAME = "icon-plus";
  private static final String ICON_PREVIOUS_CLASS_NAME = "icon-previous";

  private static final String PLUS_MENU_PATH_SEPARATOR = "\\s*->\\s*";
  private static final int TOP_MENU_RETRIES = 20;

  public void clickUpButton() {
    seleniumDriver.waitForRequestsToFinish();
    WebElement element =
        seleniumDriver.findElementWhenClickable(By.className(ICON_ARROW_UP_CLASS_NAME));
    clickWithRetries(element, TOP_MENU_RETRIES);
  }

  public void clickPreviousButton() throws Exception {
    seleniumDriver.waitForRequestsToFinish();
    WebElement element = findElementWithRetries(By.className(ICON_PREVIOUS_CLASS_NAME), 10);
    clickWithRetries(element, TOP_MENU_RETRIES);
    seleniumDriver.waitForRequestsToFinish();
  }

  public void clickPlusButton(String path) throws Exception {
    seleniumDriver.waitForRequestsToFinish();
    WebElement element =
        seleniumDriver.findElementWhenClickable(By.className(ICON_PLUS_CLASS_NAME));
    clickWithRetries(element, TOP_MENU_RETRIES);
    String[] navigationMenus = path.split(PLUS_MENU_PATH_SEPARATOR);
    navigateThruMenus(navigationMenus);
  }

  private void navigateThruMenus(String[] navigationMenus) throws Exception {
    for (String menu : navigationMenus) {
      Sleeper.sleepTightInSeconds(2);
      clickOnMenu(menu);
    }
  }

  private void clickOnMenu(String menu) throws Exception {
    Optional<WebElement> menuOptional = getMenu(buildXPath(menu));
    if (!menuOptional.isPresent()) throw new Exception("Plus menu " + menu + " not found");
    clickWithRetries(menuOptional.get(), TOP_MENU_RETRIES);
  }

  private Optional<WebElement> getMenu(String xpath) {
    List<WebElement> menus = seleniumDriver.findElements(By.xpath(xpath));
    for (WebElement menuElement : menus) {
      if (menuElement.isDisplayed()) return Optional.of(menuElement);
    }
    return Optional.empty();
  }

  private String buildXPath(String label) {
    return "//span[contains(text(),'" + label + "')]/parent::a";
  }
}
