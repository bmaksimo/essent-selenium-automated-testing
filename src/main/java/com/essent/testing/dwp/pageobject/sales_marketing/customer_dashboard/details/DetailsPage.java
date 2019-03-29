package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.details;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.junit.Assert;
import org.openqa.selenium.By;

public class DetailsPage extends Component {

    private static final String numberBillingCustomer = "//list[@list-key='BillingCustomerOnaccount']//tr[@class='list__row']";
    public void findIban(String iban) {
        seleniumDriver.waitForRequestsToFinish();
        Assert.assertTrue(seleniumDriver.findElementWhenVisible(By.xpath("//span[.='" + iban + "']")).isDisplayed());
    }

    public int getNumberOfBillingCustomers(){
        return seleniumDriver.findElements(By.xpath(numberBillingCustomer)).size();
    }
}
