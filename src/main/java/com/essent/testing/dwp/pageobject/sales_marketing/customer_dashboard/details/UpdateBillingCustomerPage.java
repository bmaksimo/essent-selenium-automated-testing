package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.details;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class UpdateBillingCustomerPage extends Component {

    private static final String HOUSE_NUM_ELEMENT = "address-number-field";

    public WebElement getHouseNumElement() {
        return seleniumDriver.findElementWhenVisible(By.id(HOUSE_NUM_ELEMENT));
    }

    public String getCurrentHouseNumber(){
        return getHouseNumElement().getText();
    }

    public void changeHouseNumber(int num) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(getHouseNumElement(), Integer.toString(num));
    }
}
