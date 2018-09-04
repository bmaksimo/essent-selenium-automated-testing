package com.essent.testing.dwp.pageobject.b2b_regression;

import com.essent.testing.dwp.scenario.DwpScenario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ConsumptionsPage extends DwpScenario {

    public WebElement listIsEmpty() {
        return webDriver.findElementOrNull(By.id("rows"));
    }

    public boolean listIsVisible() {
        return listIsEmpty().isDisplayed();
    }
}
