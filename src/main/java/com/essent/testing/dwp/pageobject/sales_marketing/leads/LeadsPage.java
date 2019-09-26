package com.essent.testing.dwp.pageobject.sales_marketing.leads;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;

public class LeadsPage extends Component {

    public void plusAddLead() {
        seleniumDriver.waitForRequestsToFinish();
        findElementWhenVisible(By.xpath("//span[@class='icon-plus']")).click();
        seleniumDriver.waitForRequestsToFinish();
    }

    public void validateCreatingLead(String name) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.findElementWhenVisible(By.xpath("(//h5)[.='" + name + "'][1]")).isDisplayed();
        seleniumDriver.waitForRequestsToFinish();
    }
}
