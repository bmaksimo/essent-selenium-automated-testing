package com.essent.testing.dwp.pageobject.service_contracting;

import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.PendingException;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;

public class TaskSteps extends DwpScenario {
    @When("^Plus action of first customer from list$")
    public void plusActionOfFirstCustomerFromList() throws Throwable {
        webDriver.waitForRequestsToFinish();
        webDriver.findElementOrNull(By.id("placeholder-row-8586fd93-c107-8fd2-1d1d-5b922e674133")).click();
    }
}
