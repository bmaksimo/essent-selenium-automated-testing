package com.essent.testing.jbilling.pageobject.impl.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.essent.testing.jbilling.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;

public class OrdersPage extends Component {
	
    public OrdersPage(SeleniumDriver seleniumDriver){
        super(seleniumDriver);
    }
    
	public boolean checkOrderTableNotEmpty() {
		waitForRequestsToFinish();
		List<WebElement> rows = seleniumDriver.findElements(By.xpath("//table[@id='orders']/tbody"));
		
		if(rows.size() > 0) {
			return true;
		}
		
		return false;
	}

	public String checkValueNextToLabel(String label, String columnNumber) {
		return seleniumDriver.findElementWhenVisible(By.xpath("//table[@class='innerTable']//tr[td[contains(text(),'"+label+"')]]/td["+columnNumber+"]")).getText();
	}

}
