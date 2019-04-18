package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.details;

import com.essent.testing.dwp.pageobject.impl.Component;
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

    public String getAddress(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='card__content__inner-wrapper']/p[1]")).getText();
    }

    public String getPhone(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='card__content__inner-wrapper']/p[2]/span[2]")).getText();
    }

    public String getEmail(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='card__content__inner-wrapper']/p[2]/span[3]")).getText();
    }

    public String getCustomerName(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='card__header']/h1")).getText();
    }
}
