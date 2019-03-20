package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DwpLeftMenu extends Component {

    private static final String nameKey="name_key";
    private static final String leftMenuXPath = "//main-menu-link[@name='${" + nameKey + "}']//a"; 
    
    private WebElement getLeftElement(String nameValue)  {
	String xpath = createQuery(leftMenuXPath, nameKey, nameValue); 
	return seleniumDriver.findElementWhenVisible(By.xpath(xpath));
    }

    public void clickOnLeftElement(String element) {
        seleniumDriver.waitAndClick(getLeftElement(element));
    }
}
