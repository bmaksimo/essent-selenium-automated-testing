package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.documents;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.junit.Assert;
import org.openqa.selenium.By;

public class DocumentsPage extends Component {

    private static final String labelDocument = "//span[contains(text(), 'customer-signature')]";
    private static final String findDocument = "(//span[.='customer-signature.pdf'])[1]";

    public String documentText(){
        seleniumDriver.waitForRequestsToFinish();
        return findElementWhenVisible(By.xpath(labelDocument)).getText();

    }
    public void findDocument() {
        seleniumDriver.waitForRequestsToFinish();
        Assert.assertTrue(findElementWhenVisible(By.xpath(findDocument)).isDisplayed());
    }
}
