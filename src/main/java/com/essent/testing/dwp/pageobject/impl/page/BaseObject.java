package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;

import static com.essent.automation.autocrat.Action.CLICK;
import static com.essent.automation.autocrat.Action.SLEEP;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.UPLOAD_FILE;
import static com.essent.testing.selenium.helper.autocrat.AutocratExecutionAdapter.newExecution;

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
        seleniumDriver.waitForRequestsToFinish();
        try {
            seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("(.//tbody[@id='rows']//list-plus-cell//a)[1]")));
        }
        catch(org.openqa.selenium.StaleElementReferenceException ex) {
            seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("(.//tbody[@id='rows']//list-plus-cell//a)[1]")));
        }
    }

    public void plusSubaction(String action) {
        try {
            seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list-row-action[@label='" + action + "']/a")));
        }
        catch(org.openqa.selenium.StaleElementReferenceException ex) {
            seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list-row-action[@label='" + action + "']/a")));
        }
    }

    public void clickOnToggle(String label) {
        seleniumDriver.waitForRequestsToFinish();
        ToggleImpl toggle = new ToggleImpl(seleniumDriver);
        if (!toggle.checkIfCheckboxIsChecked(label)) {
            seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//validation-wrapper[@label='" + label + "?']//toggle-form-element/label")));
        }
    }

    public String documentText(){
        return seleniumDriver.findElementWhenVisible(By.xpath("(//list-simple-two-liner-cell//span[1])[2]")).getText();
    }
}
