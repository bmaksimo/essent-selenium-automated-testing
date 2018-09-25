package com.essent.testing.dwp.pageobject;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BaseObject extends Component {

    public BaseObject(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public BaseObject(WebElement element, SeleniumDriver seleniumDriver) {
        super(element, seleniumDriver);
    }

    public String getTaskId() {
        final String taskId;
        taskId = findElementWhenVisible(By.xpath("//tbody[@id='rows']/tr[1]/td[2]/list-link-bold-top-two-liner-cell[@icon='null']//a/h5")).getText();
        return taskId;
    }

    public void clickOnPlus() {
        findElementWhenVisible(By.xpath("(//a[@class='show-actions icon-plus'])[1]")).click();
    }

    public void plusSubaction(String action) {
        findElementWhenVisible(By.xpath("//list-row-action[@label='" + action + "']")).click();
    }
}
