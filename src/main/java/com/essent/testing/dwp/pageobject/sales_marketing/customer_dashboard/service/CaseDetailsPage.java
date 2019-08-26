package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.service;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CaseDetailsPage extends Component {
    public String getComplaintText() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("description")).getText();
    }

    private WebElement priorityField() {

        return seleniumDriver.findElementWhenVisible(By.id("priority-field"));
    }

    public boolean checkIfPriorityIsHigh() {
        //TODO Remove locale-specific hard code.
        // The project must support official Belgian languages.
        // Locale-specific elements of web element locators must be parameterized.
        // This is basic rule!
        return priorityField().getText().equalsIgnoreCase("Hoog");
    }

    public String getSolutionFieldText() {
        return seleniumDriver.findElementWhenVisible(By.id("resolution-field")).getText();

    }
}
