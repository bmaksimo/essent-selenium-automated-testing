package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;

public class BaseObject extends Component {


    public BaseObject(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public String getTaskId() {
        final String taskId;
        taskId = seleniumDriver.findElementWhenVisible(By.xpath("(//h6)[2]")).getText();
        return taskId;
    }

    public void insertEANcode(String eanCode) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("sql-i-aos-contracts-i-aos-products-quotes-i-ean-c-default-value-field")),eanCode);
    }

    public void clickOnPlus() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementOrNull(By.xpath("(//tbody[@id='rows']//list-plus-cell//a[@class='show-actions icon-plus'])[1]")));
    }

    public void plusSubaction(String action) {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list-row-action[@label='" + action + "']/a")));
    }

    public void clickOnToggle(String label) {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//validation-wrapper[@label='" + label + "?']//toggle-form-element")));
    }
}
