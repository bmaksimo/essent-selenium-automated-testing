package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.testing.dwp.pageobject.Form;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.quote.GuidedStep;
import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.awaitility.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.NEXT_BUTTON;
import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.VIEW;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.awaitility.Duration.FIVE_HUNDRED_MILLISECONDS;
import static org.awaitility.Duration.TWO_SECONDS;

public abstract class QuoteCreationGuidedStep extends Component implements GuidedStep, Form {

    private static By STANDARD_UI_VIEW = By.xpath(VIEW.getQuery());

    public QuoteCreationGuidedStep() {
        super(STANDARD_UI_VIEW);
    }

    @Override
    public void next() {
        seleniumDriver.waitForRequestsToFinish();
        logMandatoryInputStatus();
        logger().debug("Searching element by " + NEXT_BUTTON.getQuery());
        given().await()
            .ignoreExceptions()
            .pollInterval(FIVE_HUNDRED_MILLISECONDS)
            .pollDelay(TWO_SECONDS)
            .atMost(new Duration(60, SECONDS)).until(this::isNextButtonEnabled);
        WebElement nextButton = findElementWhenClickable(By.cssSelector(NEXT_BUTTON.getQuery()));
        logger().info("- RESULT: Confirm guidance step, confirmation button attribute value: Next[disabled] = " + nextButton.getAttribute("disabled"));
        if(logger().isDebugEnabled())
        {
            seleniumDriver.takeScreenshot("guidance-confirm-");
        }
        if (nextButton != null && nextButton.isEnabled()) {
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

    private void logMandatoryInputStatus() {
        List<WebElement> elements = seleniumDriver.findElements(By.cssSelector(".is-error"),
            java.time.Duration.ofSeconds(1),
            java.time.Duration.ofMillis(200));
        String location = elements.stream().map(WebElement::getText).reduce("", (partialString, element) -> partialString + (" " + element + System.lineSeparator()));
        if(StringUtils.isNotEmpty(location)) {
            logger().error("- WARNING: Mandatory input failure in: " + location);
        }
    }

    public Boolean isNextButtonEnabled() {
        Map options = new HashMap<>();
        Map result = seleniumDriver.executeJavascriptMethod("TrIsNextButtonEnabled", options);
        String buttonStatus = (String) result.get("enabled");
        logger().info("- RESULT: Confirm guidance step, confirmation button is enabled: " + buttonStatus);
        return BooleanUtils.toBoolean(buttonStatus);
    }
}
