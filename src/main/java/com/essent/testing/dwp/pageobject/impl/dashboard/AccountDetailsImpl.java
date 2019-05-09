package com.essent.testing.dwp.pageobject.impl.dashboard;

import com.essent.testing.dwp.pageobject.dashboard.AccountDetails;
import com.essent.testing.dwp.pageobject.elements.NonEditableInput;
import com.essent.testing.dwp.pageobject.elements.ToggleSwitch;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.elements.NonEditableInputImpl;
import com.essent.testing.dwp.pageobject.impl.elements.ToggleSwitchImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AccountDetailsImpl  extends Component implements AccountDetails {

    private static final String NON_EDITABLE_VALUE_SELECTOR_TEMPLATE = "//div[@class='input label-inline' and label/text()='${label}']//div[@class='non-editable-input']";
    private static final String TOGGLE_SWITCH_SELECTOR_TEMPLATE = "//div[@class='input label-inline' and label/text()='${label}']//input";

    @Override
    public String getNonEdtableValue(String label) {
        By query = By.xpath(createQuery(NON_EDITABLE_VALUE_SELECTOR_TEMPLATE, "label", label));
        WebElement element = seleniumDriver.findElementWhenVisible(query);
        NonEditableInput input = new NonEditableInputImpl(element);
        return input.getValue();
    }

    @Override
    public boolean isToggleSwitchEnabled(String label) {
        By query = By.xpath(createQuery(TOGGLE_SWITCH_SELECTOR_TEMPLATE, "label", label));
        WebElement element = seleniumDriver.findElementWhenVisible(query);
        ToggleSwitch toggleSwitch = new ToggleSwitchImpl(element);
        return toggleSwitch.isOn();
    }
}
