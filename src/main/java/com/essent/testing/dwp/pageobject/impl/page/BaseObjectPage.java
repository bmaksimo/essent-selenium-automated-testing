package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts.ContractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.NEXT_BUTTON;
import java.util.HashMap;
import java.util.Map;

public class BaseObjectPage extends Component {

    private static final String PLUS_BUTTON_XPATH = "(//list-plus-cell//a)[1]";
    private static final String REPLACEMENT_KEY = "replacement_key";
    private static final String PLUS_MENU_XPATH = "//list-row-action[normalize-space(@label)=${" + REPLACEMENT_KEY
	    + "}]/a";
    private static final String PLUS_MARK_DONE_XPATH = "//list-row-action/a/span[@class='icon-checkmark']";

    private static final String DATE_SELECTOR_XPATH = "//validation-wrapper[@label='${" + REPLACEMENT_KEY + "}']//input";
    private static final String ICON_CALENDAR_XPATH = "//span[@class='icon-kalender']";

    private static final String REPLACEMENT_KEY1 = "replacement_key1";
    private static final String LABEL_CLICK_XPATH = "//validation-wrapper[@label='${" + REPLACEMENT_KEY
	    + "}']//option[@label = ${'" + REPLACEMENT_KEY1 + "}']";

    private static final String CARD_TEXT_XPATH = "//h2[normalize-space(text())='${"+REPLACEMENT_KEY+"}']/parent::div/parent::div/div[@class='form__group']//label[normalize-space(text())='${"+REPLACEMENT_KEY1+"}']/parent::div//strong";

    public void clickOnPlus() {

	seleniumDriver.waitAndClick(seleniumDriver.findElementOrNull(By.xpath(PLUS_BUTTON_XPATH)));
    }

    public void plusSubaction(String actionValue) {
	String xpathSubaction = createQuery(PLUS_MENU_XPATH, REPLACEMENT_KEY, actionValue);
	try {
	    seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(xpathSubaction)));

	} catch (org.openqa.selenium.StaleElementReferenceException ex) {
	    seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(xpathSubaction)));
	}
    }

    public void clickOnMarkAsDonePlusMenuSubAction() {
	seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(PLUS_MARK_DONE_XPATH)));
    }

    public void dateIsNow(String labelValue) {
	ContractPage cp = new ContractPage();
	seleniumDriver.waitForRequestsToFinish();
	Sleeper.sleepTightInSeconds(2);
	String xpathDate = createQuery(DATE_SELECTOR_XPATH, REPLACEMENT_KEY, labelValue);
	seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.xpath(xpathDate)), cp.date);
	seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath(ICON_CALENDAR_XPATH)));
    }

    public void confirmQuote() {
	String query = NEXT_BUTTON.getQuery();
	logger().debug("Searching element by " + query);
	seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.cssSelector(NEXT_BUTTON.getQuery())));
    }

    public void clickOnLabel(String labelValue, String valueValue) {
	Map<String, String> valuesMap = new HashMap<>();
	valuesMap.put(REPLACEMENT_KEY, labelValue);
	valuesMap.put(REPLACEMENT_KEY1, valueValue);
	String xpathLabel = createQuery(LABEL_CLICK_XPATH, valuesMap);
	seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(xpathLabel)));
    }

    public WebElement cardTextXPathValue(String cardName, String label) {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put(REPLACEMENT_KEY, cardName);
        valuesMap.put(REPLACEMENT_KEY1, label);
        String cardTextXPath = createQuery(CARD_TEXT_XPATH, valuesMap);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(cardTextXPath);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        return seleniumDriver.findElementWhenVisible(By.xpath(cardTextXPath));
    }
}
