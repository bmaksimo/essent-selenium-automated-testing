package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts.ContractPage;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.NEXT_BUTTON;
import java.util.HashMap;
import java.util.Map;

public class BaseObjectPage extends Component {

    private static final String plusButtonXPath = "(//list-plus-cell//a)[1]";
    private static final String replacementKey = "replacement_key";
    private static final String plusMenuXPath = "//list-row-action[normalize-space(@label)=${" + replacementKey
	    + "}]/a";
    private static final String plusMarkDoneXPath = "//list-row-action/a/span[@class='icon-checkmark']";

    private static final String dateSelektorXPath = "//validation-wrapper[@label='${" + replacementKey + "}']//input";
    private static final String iconCalendarXPath = "//span[@class='icon-kalender']";

    private static final String replacementKey1 = "replacement_key1";
    private static final String labelClickXPath = "//validation-wrapper[@label='${" + replacementKey
	    + "}']//option[@label = ${'" + replacementKey1 + "}']";

    public void clickOnPlus() {

	seleniumDriver.waitAndClick(seleniumDriver.findElementOrNull(By.xpath(plusButtonXPath)));
    }

    public void plusSubaction(String actionValue) {
	String xpathSubaction = createQuery(plusMenuXPath, replacementKey, actionValue);
	try {
	    seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(xpathSubaction)));

	} catch (org.openqa.selenium.StaleElementReferenceException ex) {
	    seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(xpathSubaction)));
	}
    }

    public void clickOnMarkAsDonePlusMenuSubAction() {
	seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(plusMarkDoneXPath)));
    }

    public void dateIsNow(String labelValue) {
	ContractPage cp = new ContractPage();
	seleniumDriver.waitForRequestsToFinish();
	Sleeper.sleepTightInSeconds(2);
	String xpathDate = createQuery(dateSelektorXPath, replacementKey, labelValue);
	seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.xpath(xpathDate)), cp.date);
	seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath(iconCalendarXPath)));
    }

    public void confirmQuote() {
	String query = NEXT_BUTTON.getQuery();
	logger().debug("Searching element by " + query);
	seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.cssSelector(NEXT_BUTTON.getQuery())));
    }

    public void clickOnLabel(String labelValue, String valueValue) {
	Map<String, String> valuesMap = new HashMap<>();
	valuesMap.put(replacementKey, labelValue);
	valuesMap.put(replacementKey1, valueValue);
	String xpathLabel = createQuery(labelClickXPath, valuesMap);
	seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(xpathLabel)));
    }
}
