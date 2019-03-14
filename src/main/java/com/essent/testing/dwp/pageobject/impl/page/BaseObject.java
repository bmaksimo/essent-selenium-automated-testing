package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.customer_dashboard.contracts.ContractPage;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import org.openqa.selenium.By;


public class BaseObject extends Component {

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

    public void clickOnToggle (String label){
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(2);
        ToggleImpl toggle = new ToggleImpl();
        if (!toggle.checkIfCheckboxIsChecked(label)) {
            seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//validation-wrapper[@label='" + label + "?']//toggle-form-element/label")));
        }
    }

    public void dateIsNow(String label) throws InterruptedException {
        ContractPage cp = new ContractPage();
        seleniumDriver.waitForRequestsToFinish();
        Thread.sleep(2000);
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.xpath("//validation-wrapper[@label='"+label+"']//input")),cp.date);
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath("//span[@class='icon-kalender']")));
    }
}
