package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.automation.autocrat.Model;
import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.navigation.TopActionsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import static com.essent.automation.autocrat.Action.CLICK;
import static com.essent.automation.autocrat.Action.SLEEP;
import static com.essent.testing.selenium.helper.autocrat.AutocratExecutionAdapter.newExecution;

public class TopActionsPageImpl extends Component implements TopActionsPage {

    private static final String BUTTON_ELEMENT = "button.element.name";
    private static final String BUTTON_ELEMENT_QUERY_TEMPLATE = ".top-actions > a[name='${name}']";
    private static final String TOPACTION_BUTTON_ELEMENT_XPATH = "//div[@class='top-actions']/a[@name='%s']";

    @Override
    public boolean executeTopAction(String name) {
        String query = String.format(TOPACTION_BUTTON_ELEMENT_XPATH, name);
        try {
            seleniumDriver.findElementWhenClickable(By.xpath(query)).click();
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
