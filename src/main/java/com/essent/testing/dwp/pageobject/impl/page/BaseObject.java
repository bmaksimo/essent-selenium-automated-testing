package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseObject extends Component {


    public BaseObject(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    String pattern = "dd/MM/yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    String date = simpleDateFormat.format(new Date());

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

    public void dateIsNow(String label) throws InterruptedException {
        seleniumDriver.waitForRequestsToFinish();
        Thread.sleep(2000);
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.xpath("//validation-wrapper[@label='"+label+"']//input")),date);
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath("//span[@class='icon-kalender']")));
    }
}
