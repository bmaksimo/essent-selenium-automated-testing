package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.navigation.TopActionsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

public class TopActionsPageImpl extends Component implements TopActionsPage {

    private static final String TOPACTION_BUTTON_ELEMENT_XPATH = "//div[@class='top-actions']/a[@name='%s']";

    @Override
    public boolean executeTopAction(String name) {
        String query = String.format(TOPACTION_BUTTON_ELEMENT_XPATH, name);
        try {
            do {
                seleniumDriver.findElementWhenClickable(By.xpath(query)).click();
                seleniumDriver.waitForRequestsToFinish();
                // Check if the click worked
            } while (!seleniumDriver.findElement(By.xpath(query)).getAttribute("class").contains("is-active"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Override
    public boolean executeTopActionWithFixedWait(String name, int waitingTime) {
        Sleeper.sleepTightInSeconds(waitingTime);
        return this.executeTopAction(name);
    }
}
