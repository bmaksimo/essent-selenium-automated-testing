package com.essent.testing.dwp.pageobject.impl.modal;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Optional;

public class ModalBase extends Component {

    private static final By CONFIRM_BUTTON_SELECTOR = By.xpath("//a[text() !=\"YES\" and text() != \"NO\" and @id=\"confirm-button\"]");

    public boolean confirm(String scenarioInfo) {
        seleniumDriver.waitForRequestsToFinish();
        int loopCounter = 0;
        // Is there a normal 'Confirm' button
        if (!seleniumDriver.findElements(CONFIRM_BUTTON_SELECTOR).isEmpty()) {
            logger().info("Button found ... trying to click it");
            do {
                try {
                    seleniumDriver.waitAndClick(seleniumDriver.findElement(CONFIRM_BUTTON_SELECTOR));
                    // If the button exist, and we clicked it, the button should not be present anymore
                    loopCounter++;
                } catch (Exception e) {
                    break;
                }
            }
            while (loopCounter < 20 && !seleniumDriver.findElements(CONFIRM_BUTTON_SELECTOR).isEmpty());
            return true;
        }
        validateForm(scenarioInfo);
        handleAlert();
        return true;
    }

    public boolean confirmNow(String scenarioInfo, int waitingTime) {
        Sleeper.sleepTightInSeconds(waitingTime);
        Optional<WebElement> confirm = seleniumDriver.findElementOptional(CONFIRM_BUTTON_SELECTOR);
        confirm.ifPresent(WebElement::click);
        Sleeper.sleepTightInSeconds(waitingTime);
        validateForm(scenarioInfo);
        handleAlert();
        Sleeper.sleepTightInSeconds(waitingTime);

        return true;
    }
}
