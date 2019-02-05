package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;

public class SoctarBatchPage extends Component {
    private static final By STATUS_SELECTOR = By.id("status-field");
    private static final By TYPE_SELECTOR = By.id("type-field");


    public SoctarBatchPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public void clickOnAction(String actionName) {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.name(actionName)));
    }

    public boolean checkStatus(String status) {
        return status.equalsIgnoreCase(seleniumDriver.findElementWhenVisible(STATUS_SELECTOR).getText());
    }

    public boolean checkType(String type) {
        String currentType = seleniumDriver.findElementWhenVisible(TYPE_SELECTOR).getText();
        return type.equalsIgnoreCase(currentType);
    }

}
