package com.essent.testing.dwp.pageobject.guided_flow.soctar;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;

public class SoctarTariffBatchDetails extends Component {

    public String getTariffType() {
        return seleniumDriver.findElementWhenVisible(By.id("type-field")).getText();
    }
    public String getTariffStatus() {
        return seleniumDriver.findElementWhenVisible(By.id("status-field")).getText();
    }
}
