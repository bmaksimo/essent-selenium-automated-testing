package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts.ContractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.NEXT_BUTTON;


public class BaseObjectPage extends Component {

    public void clickOnPlus() {

        seleniumDriver.waitAndClick(seleniumDriver.findElementOrNull(By.xpath("(//list-plus-cell//a)[1]")));
    }

    public void plusSubaction(String action) {
        try {
            seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list-row-action[normalize-space(@label)='" + action + "']/a")));

        }
        catch(org.openqa.selenium.StaleElementReferenceException ex) {
            seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list-row-action[normalize-space(@label)='" + action + "']/a")));
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

    public WebElement cardTextXPathValue(String cardName, String label) {
        return seleniumDriver.findElement(By.xpath("//h2[normalize-space(text())='"+cardName+"']/parent::div/parent::div/div[@class='form__group']//label[normalize-space(text())='"+label+"']/parent::div//strong"));
    }
}
