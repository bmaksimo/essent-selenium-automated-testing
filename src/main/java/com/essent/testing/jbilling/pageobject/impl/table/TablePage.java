package com.essent.testing.jbilling.pageobject.impl.table;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.table.TableFilter;
import com.essent.testing.jbilling.pageobject.impl.Component;
import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TablePage extends Component {

	public boolean clickTextLink(String label) {
		WebElement we = seleniumDriver.findElementWhenVisible(By.xpath("//a[contains(text(),' " +label+ "')]"));
		if(we != null) {
            seleniumDriver.waitAndClick(we);
			return true;
		}
		return false;
	}

	public boolean clickFirstCellInTable(String firstCellValue) {
		WebElement we = seleniumDriver.findElementWhenVisible(By.xpath("//a[strong[contains(text(),'" +firstCellValue+ "')]]"));
		if(we != null) {
            seleniumDriver.waitAndClick(we);
			return true;
		}
		return false;
	}

    // Use only if there is one table in jbilling UI
	public String checkFirstCellValueInFirstRow() {
		return seleniumDriver.findElementWhenVisible(By.xpath("//table//td[1]/a/strong")).getText();
	}

    // Use only if there is one table in jbilling UI
	public boolean clickOnRowInTable(String rowNumber) {
        seleniumDriver.waitForRequestsToFinish();
		WebElement we = seleniumDriver.findElementWhenVisible(By.xpath("//table//tr["+rowNumber+"]/td["+rowNumber+"]/a"));
		if(we != null) {
            seleniumDriver.waitAndClick(we);
			return true;
		}
		return false;
	}

	public String checkValueNextToLabel(String label) {
		return seleniumDriver.findElementWhenVisible(By.xpath("//tr[td[contains(text(),'"+label+"')]]/td[2]")).getText();
	}

	public boolean checkInnerTablesNotEmpty() {
        seleniumDriver.waitForRequestsToFinish();
		List<WebElement> rows = seleniumDriver.findElements(By.xpath("//table[@class='innerTable']/tbody"));

		return CollectionUtils.isNotEmpty(rows);
	}

}
