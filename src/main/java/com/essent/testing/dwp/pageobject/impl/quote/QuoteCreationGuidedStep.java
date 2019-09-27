package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.Form;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

import static com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry.JS_TR_IS_NEXT_BUTTON_ENABLED;
import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.NEXT_BUTTON;
import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.VIEW;

public abstract class QuoteCreationGuidedStep extends Component implements Form {

  private static By STANDARD_UI_VIEW = By.xpath(VIEW.getQuery());

  public QuoteCreationGuidedStep() {
    super(STANDARD_UI_VIEW);
  }

  public void fillFieldByXPath (String xPath, String value) {
    // Silent ignore any empty inputs
    if (StringUtils.isEmpty(value)) return;
    WebElement e = seleniumDriver.findElement(By.xpath(xPath));
    // Click the element to move the focus there, that's what a user would do
    e.click();
    e.sendKeys(value);
  }

  public void next(String scenarioInfo) {
    seleniumDriver.waitForRequestsToFinish();
    Sleeper.sleepTightInSeconds(30);
    logger().debug("Guided step to be confirmed");
    validateForm(scenarioInfo);
    WebElement nextButton = findElementWhenClickable(By.cssSelector(NEXT_BUTTON.getQuery()));
    logger().debug("Found  element: " + nextButton.getTagName());
    logger().debug("- RESULT: Confirm guidance step, confirmation button attribute value: Next[disabled] = " + nextButton.getAttribute("disabled"));
    seleniumDriver.waitForRequestsToFinish();
    Sleeper.sleepTightInSeconds(10);
    nextButton.click();
    seleniumDriver.waitForRequestsToFinish();
  }

  public Boolean isNextButtonEnabled() {
    Map result = seleniumDriver.executeJavascriptMethod(JS_TR_IS_NEXT_BUTTON_ENABLED, new HashMap<>());
    return BooleanUtils.toBoolean((String) result.get("enabled"));
  }
}
