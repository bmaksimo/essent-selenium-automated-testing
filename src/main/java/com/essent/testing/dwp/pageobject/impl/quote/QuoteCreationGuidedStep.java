package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.Form;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.apache.commons.lang3.StringUtils;
import org.awaitility.Duration;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.NEXT_BUTTON;
import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.VIEW;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.awaitility.Duration.FIVE_HUNDRED_MILLISECONDS;
import static org.awaitility.Duration.ONE_HUNDRED_MILLISECONDS;

public abstract class QuoteCreationGuidedStep extends Component implements Form {

    private static By STANDARD_UI_VIEW = By.xpath(VIEW.getQuery());
    private static By NEXT_BUTTON_CSS_SELECTOR = By.cssSelector(NEXT_BUTTON.getQuery());

    public QuoteCreationGuidedStep() {
        super(STANDARD_UI_VIEW);
    }

    public void fillFieldByXPath(String xPath, String value) {
        // Silent ignore any empty inputs
        if (StringUtils.isEmpty(value)) return;
        WebElement e = seleniumDriver.findElement(By.xpath(xPath));
        // Click the element to move the focus there, that's what a user would do
        e.click();
        e.sendKeys(value);
    }

    public void next(String scenarioInfo) {
        seleniumDriver.waitForRequestsToFinish();
        logger().debug("Guided step to be confirmed");
        validateForm(scenarioInfo);
        closeGuidanceModalIfPresent();
//        WebElement nextButton = seleniumDriver.findElement(NEXT_BUTTON_CSS_SELECTOR);
        WebElement nextButton = null;
        try {
            nextButton = findElementWithRetries(By.id("primaryButton"), 50);
            boolean disabled = true;
            int currentAttempt = 0;
            int maxAttempts = 50;
            while (disabled && currentAttempt < maxAttempts) {
                currentAttempt++;
                if (StringUtils.isBlank(nextButton.getAttribute("disabled"))) {
                    disabled = false;
                    nextButton.click();
                }
                Sleeper.sleepTightInSeconds(1);
            }

        } catch (Exception e) {
            Assert.fail("NEXT button was not found or is disabled.");
        }
//        logger().debug("Found  element: " + nextButton.getTagName());
//        logger().debug("- RESULT: Confirm guidance step, confirmation button attribute value: Next[disabled] = " + nextButton.getAttribute("disabled"));
//        given().await()
//            .ignoreExceptions()
//            .pollInterval(FIVE_HUNDRED_MILLISECONDS)
//            .pollDelay(ONE_HUNDRED_MILLISECONDS)
//            .atMost(new Duration(60, SECONDS)).until(this::isNextButtonEnabled);
//        nextButton.click();
        seleniumDriver.waitForRequestsToFinish();
    }

    public Boolean isNextButtonEnabled() {
        return seleniumDriver.findElementOptional(NEXT_BUTTON_CSS_SELECTOR).isPresent();
    }
}
