package com.essent.testing.dwp.pageobject.impl.modal;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Optional;

public class ModalBase extends Component {

    private static final By CONFIRM_BUTTON_SELECTOR = By.id("confirm-button");

    public boolean confirm() {
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(2);
        Optional<WebElement> confirm = seleniumDriver.findElementOptional(CONFIRM_BUTTON_SELECTOR);
        confirm.ifPresent(WebElement::click);
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(5);
        logMandatoryInputStatus();
        handleAlert();
        seleniumDriver.waitForRequestsToFinish();

        return true;
    }

    public boolean confirmNow(int waitingTime) {
        Sleeper.sleepTightInSeconds(waitingTime);
        Optional<WebElement> confirm = seleniumDriver.findElementOptional(CONFIRM_BUTTON_SELECTOR);
        confirm.ifPresent(WebElement::click);
        Sleeper.sleepTightInSeconds(waitingTime);
        logMandatoryInputStatus();
        handleAlert();
        Sleeper.sleepTightInSeconds(waitingTime);

        return true;
    }
}
