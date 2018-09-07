package com.essent.testing.dwp.pageobject.service_contracting;

import com.essent.testing.dwp.scenario.DwpScenario;
import org.junit.Assert;
import org.openqa.selenium.By;

public class ServicePage extends DwpScenario {

    protected void validateCreatedTask(String input) {
        webDriver.waitUntilAngularPageIsLoaded();
        final String labelText = webDriver.findElementOrNull(By.xpath("//span[.='" + input + "']")).getText();
        Assert.assertEquals(labelText, input);
    }
}
