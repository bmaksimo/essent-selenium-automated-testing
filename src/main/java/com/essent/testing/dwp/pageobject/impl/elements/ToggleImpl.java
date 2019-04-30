package com.essent.testing.dwp.pageobject.impl.elements;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ToggleImpl extends Component {
  private static String TOGGLEINPUT_SELECTOR =
      "//validation-wrapper[@label='${text}?']//toggle-form-element/label";
  private static String TOGGLEINPUT_WITH_DOT_SELECTOR =
      "//validation-wrapper[@label='${text}.']//toggle-form-element/label";

  private WebElement getToggleInput(String toggleInputName) {
    By toggleInputCriteria = By.xpath(createQuery(TOGGLEINPUT_SELECTOR, "text", toggleInputName));
    return seleniumDriver.findElementWhenVisible(toggleInputCriteria);
  }

  public boolean isOn(String toggleInputName) {
    String classValue =
        getToggleInput(toggleInputName).findElement(By.cssSelector("input")).getAttribute("class");
    return classValue.contains("not-empty");
  }

  public void switchOn(String toggleInputName) {
    if (!isOn(toggleInputName)) {
      seleniumDriver.waitAndClick(getToggleInput(toggleInputName));
    }
  }

  // TODO: check if switchOnElectricityMarketMock and clickOnToggle can be merged
  public void clickOnToggle(String toggleInputName) {
    seleniumDriver.waitForRequestsToFinish();
    Sleeper.sleepTightInSeconds(2);
    ToggleImpl toggle = new ToggleImpl();
    By toggleInputCriteria = By.xpath(createQuery(TOGGLEINPUT_SELECTOR, "text", toggleInputName));
    if (!toggle.isOn(toggleInputName)) {
      seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(toggleInputCriteria));
    }
  }

  public void switchOnNow(String toggleInputName, int waitingTime) {
    Sleeper.sleepTightInSeconds(waitingTime);
    ToggleImpl toggle = new ToggleImpl();
    By toggleInputCriteria = By.xpath(createQuery(TOGGLEINPUT_SELECTOR, "text", toggleInputName));
    if (!toggle.isOn(toggleInputName)) {
      seleniumDriver.clickNow(seleniumDriver.findElementWhenVisible(toggleInputCriteria));
    }
  }

  private WebElement getToggleInputWithDot(String toggleInputName) {
    By toggleInputCriteria =
        By.xpath(createQuery(TOGGLEINPUT_WITH_DOT_SELECTOR, "text", toggleInputName));
    return seleniumDriver.findElementWhenVisible(toggleInputCriteria);
  }

  private boolean isOnWithDot(String toogleInputName) {
    WebElement cb = getToggleInputWithDot(toogleInputName);
    seleniumDriver.waitForRequestsToFinish();
    String classValue = cb.findElement(By.cssSelector("input")).getAttribute("class");
    return classValue.contains("not-empty");
  }

  public void switchOnWithDot(String toogleInputName) {
    seleniumDriver.waitForRequestsToFinish();
    if (!isOnWithDot(toogleInputName)) {
      seleniumDriver.waitAndClick(getToggleInputWithDot(toogleInputName));
    }
  }
}
