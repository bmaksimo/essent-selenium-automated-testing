package com.essent.testing.jbilling.pageobject.impl.page;

import com.essent.testing.jbilling.pageobject.impl.Component;
import com.essent.testing.selenium.webdriver.jbilling.SeleniumDriverJBillingImpl;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class InvoicesPage extends Component {

    public InvoicesPage(SeleniumDriverJBillingImpl seleniumDriver){
        super(seleniumDriver);
    }

	public boolean checkInvoiceTableNotEmpty() {
		waitForRequestsToFinish();
		List<WebElement> rows = seleniumDriver.findElements(By.xpath("//table[@id='invoices']/tbody"));
		
		return CollectionUtils.isNotEmpty(rows);
	}
}
