package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.Form;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.selector.CommonSelectors;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.NEXT_BUTTON;
import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.VIEW;

public abstract class QuoteCreationGuidedStep extends Component implements Form {

  private static By STANDARD_UI_VIEW = By.xpath(VIEW.getQuery());
  private static By NEXT_BUTTON_CSS_SELECTOR = By.cssSelector(NEXT_BUTTON.getQuery());

  public QuoteCreationGuidedStep() {
    super(STANDARD_UI_VIEW);
  }

  public void next(String scenarioInfo) {
    seleniumDriver.waitForRequestsToFinish();
    validateForm(scenarioInfo);
    closeGuidanceModalIfPresent();
    WebElement nextButton = findElementWhenClickable(NEXT_BUTTON_CSS_SELECTOR);
    logger().debug("Found  element: " + nextButton.getTagName());
    logger().debug("- RESULT: Confirm guidance step, confirmation button attribute value: Next[disabled] = " + nextButton.getAttribute("disabled"));
    nextButton.click();
    seleniumDriver.waitForRequestsToFinish();
  }

  public Boolean isNextButtonEnabled() {
    return seleniumDriver.findElementOptional(NEXT_BUTTON_CSS_SELECTOR).isPresent();
  }
}
