package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.details;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.junit.Assert;
import org.openqa.selenium.By;

public class DetailsPage extends Component {

    private static final String NUMBER_BILLING_CUSTOMER = "//list[@list-key='BillingCustomerOnaccount']//tr[@class='list__row']";
    private static final String IBAN = "//span[.='${iban}']";
    private static final String REPLACEMENT_KEY = "replacement_key";

    public void findIban(String iban) {
        seleniumDriver.waitForRequestsToFinish();
        String xpathFindIban = createQuery(IBAN, REPLACEMENT_KEY, iban);
        seleniumDriver.findElementWhenVisible(By.xpath(xpathFindIban)).isDisplayed();

    }

    public int getNumberOfBillingCustomers(){
        return seleniumDriver.findElements(By.xpath(NUMBER_BILLING_CUSTOMER)).size();
    }
}
