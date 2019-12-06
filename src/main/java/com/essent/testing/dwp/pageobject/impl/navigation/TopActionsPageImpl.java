package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.navigation.TopActionsPage;
import org.openqa.selenium.By;

public class TopActionsPageImpl extends Component implements TopActionsPage {

    private static final String TOPACTION_BUTTON_ELEMENT_XPATH = "//div[@class='top-actions']/a[@name='%s']";

    @Override
    public boolean executeTopAction(String name) {
        String query = String.format(TOPACTION_BUTTON_ELEMENT_XPATH, name);
        int loopCounter = 0;
        try {
            do {
                findElementWithRetries(By.xpath(query), 10).click();
                seleniumDriver.waitForRequestsToFinish();
                loopCounter++;
                if (loopCounter > 20) {
                    return false;
                }
            } while (!seleniumDriver.findElement(By.xpath(query)).getAttribute("class").contains("is-active"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean executeTopActionWithFixedWait(String name, int waitingTime) {
        Sleeper.sleepTightInSeconds(waitingTime);
        return this.executeTopAction(name);
    }
}
