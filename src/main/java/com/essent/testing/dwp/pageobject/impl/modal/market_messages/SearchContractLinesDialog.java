package com.essent.testing.dwp.pageobject.impl.modal.market_messages;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.modal.confirm.ConfirmDialog;
import com.essent.testing.selenium.SeleniumDriver;
import org.apache.commons.collections.CollectionUtils;
import org.awaitility.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.awaitility.Duration.TWO_SECONDS;

public class SearchContractLinesDialog extends Component implements ConfirmDialog  {

    private final static By SELECTOR = By.cssSelector(".view__modal .modal__header");

    public SearchContractLinesDialog(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public void searchContractLine(String searchInput) {

        WebElement searchField = seleniumDriver.findElementWhenVisible(By.xpath("//input[@id='search-input']"));
        searchField.sendKeys(searchInput);

        Sleeper.sleepTightInSeconds(1);

        WebElement searchButton = seleniumDriver.findElementWhenVisible(By.xpath("//input[@type='submit']"));
        searchButton.click();
        Sleeper.sleepTightInSeconds(1);
        searchButton.click();

        String checkBoxesQuery = createQuery("//label[starts-with(normalize-space(), '${text}')]/input[@type='checkbox']", "text", searchInput);

        given()
            .await()
            .ignoreExceptions()
            .pollInterval(new Duration(2, SECONDS))
            .pollDelay(TWO_SECONDS)
            .atMost(new Duration(60, SECONDS)).until(()-> CollectionUtils.isNotEmpty(seleniumDriver.findElements(By.xpath(checkBoxesQuery))));

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
