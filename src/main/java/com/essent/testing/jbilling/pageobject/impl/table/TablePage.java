package com.essent.testing.jbilling.pageobject.impl.table;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.essent.testing.jbilling.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;

public class TablePage extends Component {
	
	private final static String XPATH_CONTAINS_TEXT_TEMPLATE          = "/div[span[contains(text(),'${text}')]]//input";

    public TablePage(SeleniumDriver seleniumDriver){
        super(seleniumDriver);
    }
    
    public void filterBy(String label, String value){
    	String query = createQuery(XPATH_CONTAINS_TEXT_TEMPLATE, "text", label);
    	WebElement we = seleniumDriver.findElementWhenVisible(By.xpath(query));
    	
        seleniumDriver.waitAndSendKeys(we, value);
    }

	public void clickTextLink(String label) {
		seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//a[contains(text(),' " +label+ "')]")));
	}

	public void clickFirstCellInTable(String firstCellValue) {
		seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//a[strong[contains(text(),'" +firstCellValue+ "')]]")));
	}

	public String checkFirstCellValueInFirstRow() {
		return seleniumDriver.findElementWhenVisible(By.xpath("//table//td[1]/a/strong")).getText();
	}

	public void clickOnRowInTable(String rowNumber) {
		waitForRequestsToFinish();
		seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//table//tr["+rowNumber+"]/td["+rowNumber+"]/a")));
	}

	public String checkValueNextToLabel(String label, String columnNumber) {
		return seleniumDriver.findElementWhenVisible(By.xpath("//tr[td[contains(text(),'"+label+"')]]/td["+columnNumber+"]")).getText();
	}
	
	public boolean checkInnerTablesNotEmpty() {
		waitForRequestsToFinish();
		List<WebElement> rows = seleniumDriver.findElements(By.xpath("//table[@class='innerTable']/tbody"));
		
		if(rows.size() > 0) {
			return true;
		}
		
		return false;
	}

}
