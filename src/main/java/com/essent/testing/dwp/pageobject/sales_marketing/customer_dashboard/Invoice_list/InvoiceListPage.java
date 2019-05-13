package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.Invoice_list;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.junit.Assert;
import org.openqa.selenium.By;

public class InvoiceListPage extends Component {

    private static  String payDate;


    public void openListOption(String option) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath("//span[.='" + option + "']")));
    }

    public void checkPayDate(String payDate) {
        seleniumDriver.waitForRequestsToFinish();
        final String newPayDate = findElementWhenVisible(By.xpath("(//list-simple-two-liner-cell[@icon='null']//span)[6]")).getText();
        Assert.assertFalse("Date was not changed. Old date is : " + payDate + ", and new date is same : " + newPayDate, newPayDate.equalsIgnoreCase(payDate));
    }

    public void findIssuedAndPayDelay(String type, String option) {
        seleniumDriver.waitForRequestsToFinish();
        int counter = 1;
        String payType = findElementWhenVisible(By.xpath("//*[@id='rows']/tr[1]/td[8]//span[1]")).getText();
        while(!payType.equalsIgnoreCase(type)){
            counter = counter + 2;
            payType = findElementWhenVisible(By.xpath("//*[@id='rows']/tr[" + counter + "]/td[8]//span[1]")).getText();
        }
        payDate = findElementWhenVisible(By.xpath("//*[@id='rows']/tr[" + counter + "]/td[7]//span[2]")).getText();
        findElementWhenVisible(By.xpath("(//*[@id='rows']/tr[" + counter + "]/td[10]/list-plus-cell//a)[1]")).click();
        findElementWhenVisible(By.xpath("//list-row-action[@label='" + option + "']/a")).click();
    }
}
