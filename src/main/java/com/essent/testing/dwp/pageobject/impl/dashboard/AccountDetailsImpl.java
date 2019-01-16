package com.essent.testing.dwp.pageobject.impl.dashboard;

import com.essent.testing.dwp.pageobject.dashboard.AccountDetails;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AccountDetailsImpl  extends Component implements AccountDetails {

    private static final String NON_EDITABLE_VALUE_SELECTOR_TEMPLATE = "//div[@class='input label-inline' and label/text()='${label}']//div[@class='non-editable-input']";
    private static final String NON_TOGGLE_SWITCH_SELECTOR_TEMPLATE = "//div[@class='input label-inline' and label/text()='Automatische segmentatie?']//input";


    public AccountDetailsImpl(By selector, SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    @Override
    public String getNonEdtableValue(String label) {
        By query = By.xpath(createQuery(NON_EDITABLE_VALUE_SELECTOR_TEMPLATE, "label", label));
        WebElement element = seleniumDriver.findElementWhenVisible(query);
        element.getAttribute("innerText");
        return null;
    }

    @Override
    public boolean isToggleSwitchEnabled(String label) {
        return false;
    }
}
