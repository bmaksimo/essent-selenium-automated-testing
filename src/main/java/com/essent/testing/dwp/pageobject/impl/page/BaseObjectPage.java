package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts.ContractPage;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;

import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.NEXT_BUTTON;


public class BaseObjectPage extends Component {

    private static final String actionKey="action_key";
    private static final String plusMenuXPath = "//list-row-action[normalize-space(@label)=${"+actionKey+"}]/a";
   
    
    public void clickOnPlus() {

        seleniumDriver.waitAndClick(seleniumDriver.findElementOrNull(By.xpath("(//list-plus-cell//a)[1]")));
    }

    public void plusSubaction(String actionValue) {
        String xpath = createQuery(plusMenuXPath, actionKey, actionValue); 
	try {
            seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(xpath)));

        }
        catch(org.openqa.selenium.StaleElementReferenceException ex) {
            seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(xpath)));
        }
    }

    public void clickOnMarkAsDonePlusMenuSubAction() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list-row-action/a/span[@class='icon-checkmark']")));
    }

    public void dateIsNow(String label){
        ContractPage cp = new ContractPage();
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(2);
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.xpath("//validation-wrapper[@label='"+label+"']//input")),cp.date);
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath("//span[@class='icon-kalender']")));
    }

    public void confirmQuote() {
        String query = NEXT_BUTTON.getQuery();
        logger().debug("Searching element by " + query);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.cssSelector(NEXT_BUTTON.getQuery())));
    }

    public void clickOnLabel(String label, String value) {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//validation-wrapper[@label='" + label + "']//option[@label = '" + value + "']")));
    }
}
