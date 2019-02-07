package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;

public class SoctarTariffBatchDetails extends Component {

    public SoctarTariffBatchDetails(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public String getTariffType() {
        return seleniumDriver.findElementWhenVisible(By.id("type-field")).getText();
    }

    public String getTariffStatus() {
        return seleniumDriver.findElementWhenVisible(By.id("status-field")).getText();
    }

}
