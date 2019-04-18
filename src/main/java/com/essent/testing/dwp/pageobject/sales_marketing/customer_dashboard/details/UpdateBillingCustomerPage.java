package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.details;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class UpdateBillingCustomerPage extends Component {

    public WebElement getHouseNumElement() {
        return seleniumDriver.findElementWhenVisible(By.id("address-number-field"));
    }

    public String getCurrentHouseNumber(){
        return getHouseNumElement().getText();
    }

    public void changeHouseNumber(int num) {
        seleniumDriver.waitAndSendKeys(getHouseNumElement(), Integer.toString(num));
    }
}
