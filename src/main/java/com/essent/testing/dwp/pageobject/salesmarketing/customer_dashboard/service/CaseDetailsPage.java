package com.essent.testing.dwp.pageobject.salesmarketing.customer_dashboard.service;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CaseDetailsPage extends Component {
    public String getComplaintText() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("description")).getText();
    }

    private WebElement priorityField() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("priority-field"));
    }

    public boolean checkIfPriorityIsHigh() {
        seleniumDriver.waitForRequestsToFinish();
        return priorityField().getText().equalsIgnoreCase("Hoog");
    }

    public String getSolutionFieldText() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("resolution-field")).getText();

    }
}
