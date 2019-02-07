package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.junit.Assert;
import org.openqa.selenium.By;

public class ServicePage extends Component {

    public void validateCreatedTask(String input) {
        seleniumDriver.waitForRequestsToFinish();
        Assert.assertTrue(findElementWhenVisible(By.xpath("(//span[.='" + input + "'])[1]")).isDisplayed());
    }
}
