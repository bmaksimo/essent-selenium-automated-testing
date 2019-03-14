package com.essent.testing.dwp.pageobject.customer_dashboard.service;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class DwpServicePage extends Component {

    public void validateCreatedTask(String input) {
        seleniumDriver.waitForRequestsToFinish();
        Assert.assertTrue(findElementWhenVisible(By.xpath("(//span[.='" + input + "'])[1]")).isDisplayed());
    }

    public WebElement newCase() {
        return seleniumDriver.findElementWhenVisible(By.name("CASE TOEVOEGEN"));
    }

    public void clickOnNewCase(){
        seleniumDriver.waitAndClick(newCase());
    }

}
