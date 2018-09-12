package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;

public class ServicePage extends Component {

    public ServicePage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public void validateCreatedTask(String input) {
        waitForRequestsToFinish();
        Assert.assertTrue(findElementWhenVisible(By.xpath("(//span[.='" + input + "'])[1]")).isDisplayed());
    }
}
