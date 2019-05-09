package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class UpdateCustomerDetailsPage extends Component {

    private WebElement saveButtonForFinanceAndLegalSection()  {
        //TODO Remove locale-specific hard code.
        // The project must support official Belgian languages.
        // Locale-specific elements of web element locators must be parameterized.
        // This is basic rule!
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[contains(text(),' Finance & legal ')]/preceding-sibling::*[1]"));
    }

    public void clickOnSaveButtonForFinanceAndLegalSection()  {
        seleniumDriver.waitAndClick(saveButtonForFinanceAndLegalSection());
    }
}
