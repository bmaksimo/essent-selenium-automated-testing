package com.essent.testing.dwp.pageobject.impl.modal.market_messages;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.modal.confirm.ConfirmDialog;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchContractLinesDialog extends Component implements ConfirmDialog  {

    private final static By SELECTOR = By.cssSelector(".view__modal .modal__header");

    public SearchContractLinesDialog(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public void searchContractLine(String searchInput) {

        WebElement searchField = seleniumDriver.findElementOrNull(By.xpath("//input[@id='search-input']"));
        seleniumDriver.waitAndSendKeys(searchField, searchInput);

        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//input[@type='submit']")));

        Sleeper.sleepTightInSeconds(5);
        String checkBoxesQuery = createQuery("//label[starts-with(normalize-space(), '${text}')]/input[@type='checkbox']", "text", searchInput);
        seleniumDriver.findElements(By.xpath(checkBoxesQuery)).get(0).click();

        WebElement sendButton = seleniumDriver.findElements(By.xpath("//a[@class='button']")).get(1);
        seleniumDriver.waitAndClick(sendButton);
    }

    @Override
    public boolean confirm() {
        Sleeper.sleepTightInSeconds(5);
        seleniumDriver.waitAndClick(seleniumDriver.findElementOrNull(By.id("confirm-button")));
        return true;
    }

    @Override
    public boolean reject() {
        return true;
    }

    @Override
    public boolean isShown() {
        return seleniumDriver.findElementOrNull(SELECTOR) != null;
    }
}
