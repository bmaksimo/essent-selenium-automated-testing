package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.documents;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.junit.Assert;
import org.openqa.selenium.By;

public class DocumentsPage extends Component {

    public String documentText(){
//        return findElementWhenVisible(By.xpath("(//list-simple-two-liner-cell//span[1])[4]")).getText();
        return findElementWhenVisible(By.xpath("//span[contains(text(), 'customer-signature')]")).getText();

    }
    public void findDocument() {
        seleniumDriver.waitForRequestsToFinish();
        Assert.assertTrue(findElementWhenVisible(By.xpath("(//span[.='customer-signature.pdf'])[1]")).isDisplayed());
    }
}
