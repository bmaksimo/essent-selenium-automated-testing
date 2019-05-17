package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.testing.dwp.pageobject.Form;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.quote.GuidedStep;
import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.BooleanUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry.JS_TR_IS_NEXT_BUTTON_ENABLED;
import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.NEXT_BUTTON;
import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.VIEW;

public abstract class QuoteCreationGuidedStep extends Component implements GuidedStep, Form {

  private static By STANDARD_UI_VIEW = By.xpath(VIEW.getQuery());

  public QuoteCreationGuidedStep() {
    super(STANDARD_UI_VIEW);
  }

  @Override
  public void next() {
    seleniumDriver.waitForRequestsToFinish();
    logger().debug("Guided step to be confirmed");

    Optional<WebElement> nextButtonOptional =
        Optional.of(findElementWhenClickable(By.cssSelector(NEXT_BUTTON.getQuery())));
//    seleniumDriver.takeScreenshot("guidance-confirm-");
    if (nextButtonOptional.isPresent()) {
      WebElement nextButton = nextButtonOptional.get();
      logger().debug("Found  element: " + nextButton.getTagName());
      logger().debug("CLICK ");
      seleniumDriver.waitForRequestsToFinish();
      nextButton.click();
    } else {
            if(logger().isDebugEnabled()) {
                seleniumDriver.takeScreenshot("guidance-confirm-failure");
            }
            throw new CucumberException("Element not found by selector " + NEXT_BUTTON.getQuery());
    }
    seleniumDriver.waitForRequestsToFinish();
  }

  public Boolean isNextButtonEnabled() {
    Map options = new HashMap<>();
    Map result = seleniumDriver.executeJavascriptMethod(JS_TR_IS_NEXT_BUTTON_ENABLED, options);
    return BooleanUtils.toBoolean((String) result.get("enabled"));
  }
}
