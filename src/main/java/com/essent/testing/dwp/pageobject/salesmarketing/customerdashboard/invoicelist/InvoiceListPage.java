package com.essent.testing.dwp.pageobject.salesmarketing.customerdashboard.invoicelist;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.junit.Assert;
import org.openqa.selenium.By;

public class InvoiceListPage extends Component {

    private static final String NEW_PAYMENT_DATE_SELECTOR = "(//list-simple-two-liner-cell[@icon='null']//span)[6]";
    private static final String LIST_OPTION_SELECTOR = "//span[.='${option}']";

    public void openListOption(String option) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath(createQuery(LIST_OPTION_SELECTOR, "option", option))));
    }

    public void checkPayDate(String payDate) {
        seleniumDriver.waitForRequestsToFinish();
        final String newPayDate = findElementWhenVisible(By.xpath(NEW_PAYMENT_DATE_SELECTOR)).getText();
        Assert.assertFalse("Date was not changed. Old date is : " + payDate + ", and new date is same : " + newPayDate, newPayDate.equalsIgnoreCase(payDate));
    }
}
