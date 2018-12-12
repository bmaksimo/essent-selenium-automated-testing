package com.essent.testing.jbilling.pageobject.impl.filter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.essent.testing.jbilling.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;

public class FilterPage extends Component {
	
	private final static String XPATH_CONTAINS_TEXT_TEMPLATE          = "//div[span[contains(text(),'${text}')]]//input";

    public FilterPage(SeleniumDriver seleniumDriver){
        super(seleniumDriver);
    }
    
    public void filterBy(String label, String value){
    	String query = createQuery(XPATH_CONTAINS_TEXT_TEMPLATE, "text", label);
    	WebElement we = seleniumDriver.findElementWhenVisible(By.xpath(query));
    	
        seleniumDriver.waitAndSendKeys(we, value);
    }

	public void clickApplyFilters(String label) {
		seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//a[span[contains(text(),'"+label+"')]]")));
	}

}
