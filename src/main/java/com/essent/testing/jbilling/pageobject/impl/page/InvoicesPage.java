package com.essent.testing.jbilling.pageobject.impl.page;

import com.essent.testing.jbilling.pageobject.impl.Component;
import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class InvoicesPage extends Component {

	public boolean checkInvoiceTableNotEmpty() {
        seleniumDriver.waitForRequestsToFinish();
		List<WebElement> rows = seleniumDriver.findElements(By.xpath("//table[@id='invoices']/tbody"));

		return CollectionUtils.isNotEmpty(rows);
	}

}
