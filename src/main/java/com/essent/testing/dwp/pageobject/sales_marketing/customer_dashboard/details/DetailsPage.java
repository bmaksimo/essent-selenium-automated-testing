package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.details;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;

public class DetailsPage extends Component {


    private static final String NUMBER_BILLING_CUSTOMER = "//list[@list-key='BillingCustomerOnaccount']//tr[@class='list__row']";
    private static final String IBAN = "//span[.='${iban}']";
    private static final String ADDRESS = "//div[@class='card__content__inner-wrapper']/p[1]";
    private static final String PHONE = "//div[@class='card__content__inner-wrapper']/p[2]/span[2]";
    private static final String EMAIL = "//div[@class='card__content__inner-wrapper']/p[2]/span[3]";
    private static final String CUSTOMER_NAME = "//div[@class='card__header']/h1";

    public void findIban(String iban) {
        seleniumDriver.waitForRequestsToFinish();
        String xpathFindIban = createQuery(IBAN, "iban", iban);
        seleniumDriver.findElementWhenVisible(By.xpath(xpathFindIban)).isDisplayed();
    }

    public int getNumberOfBillingCustomers(){
        return seleniumDriver.findElements(By.xpath(NUMBER_BILLING_CUSTOMER)).size();
    }

    public String getAddress(){
        return seleniumDriver.findElementWhenVisible(By.xpath(ADDRESS)).getText();
    }

    public String getPhone(){
        return seleniumDriver.findElementWhenVisible(By.xpath(PHONE)).getText();
    }

    public String getEmail(){
        return seleniumDriver.findElementWhenVisible(By.xpath(EMAIL)).getText();
    }

    public String getCustomerName(){
        return seleniumDriver.findElementWhenVisible(By.xpath(CUSTOMER_NAME)).getText();
    }

}
