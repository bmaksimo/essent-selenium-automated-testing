package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DwpLeftMenu extends Component {

    private static final String NAME_KEY = "name_key";
    private static final String LEFT_MENU_XPATH = "//main-menu-link[@name='${" + NAME_KEY + "}']//a";

    private WebElement getLeftElement(String nameValue) {
	String xpath = createQuery(LEFT_MENU_XPATH, NAME_KEY, nameValue);
	return seleniumDriver.findElementWhenVisible(By.xpath(xpath));
    }

    public void clickOnLeftElement(String element) {
        Sleeper.sleepTightInSeconds(5);
        seleniumDriver.waitAndClick(getLeftElement(element));
        seleniumDriver.waitForRequestsToFinish();
    }

    public void clickOnLeftElement(String element, int waitingTime) {
        Sleeper.sleepTightInSeconds(waitingTime);
        seleniumDriver.clickNow(getLeftElement(element));
    }
}
