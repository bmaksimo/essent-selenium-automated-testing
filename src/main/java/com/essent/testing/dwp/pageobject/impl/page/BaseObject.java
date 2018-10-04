package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseObject extends Component {


    public BaseObject(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public String getTaskId() {
        final String taskId;
        taskId = seleniumDriver.findElementWhenVisible(By.xpath("//tbody[@id='rows']/tr[1]/td[2]/list-link-bold-top-two-liner-cell[@icon='null']//a/h5")).getText();
        return taskId;
    }

    public void insertEANcode(String eanCode) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.findElementWhenVisible(By.id("sql-i-aos-contracts-i-aos-products-quotes-i-ean-c-default-value-field")).sendKeys(eanCode);
    }

    public void clickOnPlus() {
        seleniumDriver.findElementWhenVisible(By.xpath("(//a[@class='show-actions icon-plus'])[1]")).click();
    }

    public void plusSubaction(String action) {
        seleniumDriver.findElementWhenVisible(By.xpath("//list-row-action[@label='" + action + "']")).click();
    }

    public void clickOnToggle(String label) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.findElementWhenVisible(By.xpath("//validation-wrapper[@label='" + label + "?']//toggle-form-element")).click();
    }
}
