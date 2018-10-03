package com.essent.testing.dwp.pageobject.impl.modal.market_messages;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.modal.confirm.ConfirmDialog;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SearchContractLinesDialog extends Component implements ConfirmDialog  {

    private final static By SELECTOR = By.cssSelector(".view__modal .modal__header");

    public SearchContractLinesDialog(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public void searchContractLine(String searchInput) {
        
        WebElement searchField = seleniumDriver.findElementOrNull(By.xpath("//input[@id='search-input']"));
        searchField.sendKeys(searchInput);

        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//input[@type='submit']")));

        //wait for search results to load
        Sleeper.sleepTightInSeconds(5);
        List<WebElement> checkboxes = seleniumDriver.findElements(By.xpath("//input[@type='checkbox']"));
        
        //TODO you can try this alternative
        String query = createQuery("//label[starts-with(normalize-space(), '${text}')]/input[@type='checkbox']", "text", searchInput);
        List<WebElement> elements = seleniumDriver.findElements(By.xpath(query));

        //Asserts are normally not written in POs
        assertThat(String.format("Search term %s was not found.", searchInput),
            checkboxes.size() > 1, is(true));
        checkboxes.get(1).click();
        //get(1) looks like hard-coded assumption
        //"//a[@class='button'and starts-with(normalize-space(), '${text}')]" is exact xpath

        WebElement sendButton = seleniumDriver.findElements(By.xpath("//a[@class='button']")).get(1);
        seleniumDriver.waitAndClick(sendButton);
    }

    @Override
    public boolean confirm() {
        Sleeper.sleepTightInSeconds(5);
        seleniumDriver.waitAndClick(seleniumDriver.findElementOrNull(By.id("confirm-button")));
        //might be different logic there. E.g. wait until element is not present
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
