package com.essent.testing.dwp.pageobject.salesmarketing.customer_dashboard.billing;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;

public class CreateFreeTextInvoicePage extends Component {

    private static final String REPLACEMENT_KEY = "replacement_key";
    private static final String PRODUCT_CODE ="//div/label[text()[contains(.,'${" + REPLACEMENT_KEY + "}')]]/span";

    public void selectProductCode(String productCode) {
        seleniumDriver.waitForRequestsToFinish();
        String xpathProductCode = createQuery(PRODUCT_CODE, REPLACEMENT_KEY, productCode);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenPresent(By.xpath(xpathProductCode)));
    }

}
