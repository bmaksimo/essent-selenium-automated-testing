package com.essent.testing.dwp.pageobject;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;

public class BaseObject extends Component {

    private String eanCode;

    public BaseObject(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public String getTaskId() {
        final String taskId;
        taskId = findElementWhenVisible(By.xpath("//tbody[@id='rows']/tr[1]/td[2]/list-link-bold-top-two-liner-cell[@icon='null']//a/h5")).getText();
        return taskId;
    }

    public String getEANCode() {
        final String customerEAN;
        customerEAN = findElementWhenVisible(By.id("aos-products-quotes-ean-c-field")).getText();
        return customerEAN;
    }

    public void insertEANcode(String eanCode) {
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("sql-i-aos-contracts-i-aos-products-quotes-i-ean-c-default-value-field")).sendKeys(eanCode);
    }

    public void clickOnPlus() {
        findElementWhenVisible(By.xpath("(//a[@class='show-actions icon-plus'])[1]")).click();
    }

    public void plusSubaction(String action) {
        findElementWhenVisible(By.xpath("//list-row-action[@label='" + action + "']")).click();
    }

    public void clickOnToggle(String label) {
        waitForRequestsToFinish();
        findElementWhenVisible(By.xpath("//validation-wrapper[@label='" + label + "?']//toggle-form-element")).click();
    }


}
