package com.essent.testing.dwp.pageobject.service_contracting;

import com.essent.testing.dwp.scenario.DwpScenario;
import org.junit.Assert;
import org.openqa.selenium.By;

public class ServicePage extends DwpScenario {

    protected void validateCreatedTask(String input) {
        webDriver.waitForRequestsToFinish();
        Assert.assertTrue(webDriver.findElementWhenVisible(By.xpath("(//span[.='" + input + "'])[1]")).isDisplayed());
    }
}
