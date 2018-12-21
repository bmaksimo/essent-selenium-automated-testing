package com.essent.testing.jbilling.pageobject.impl.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.apache.commons.collections.CollectionUtils;
import com.essent.testing.jbilling.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;

public class InvoicesPage extends Component {

    public InvoicesPage(SeleniumDriver seleniumDriver){
        super(seleniumDriver);
    }

	public boolean checkInvoiceTableNotEmpty() {
		waitForRequestsToFinish();
		List<WebElement> rows = seleniumDriver.findElements(By.xpath("//table[@id='invoices']/tbody"));
		
		return CollectionUtils.isNotEmpty(rows);
	}
}
