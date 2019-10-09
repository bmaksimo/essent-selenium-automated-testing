package com.essent.testing.dwp.pageobject.impl.modal.market_messages;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.modal.ModalBase;
import com.essent.testing.dwp.pageobject.modal.ConfirmDialog;
import org.apache.commons.collections4.CollectionUtils;
import org.awaitility.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.awaitility.Duration.TWO_SECONDS;

public class SearchContractLinesDialog extends ModalBase implements ConfirmDialog  {

    private final static By SELECTOR = By.cssSelector(".view__modal .modal__header");

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

        WebElement sendButton = seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='modal__header']/a[@class='button']"));
        seleniumDriver.waitAndClick(sendButton);
    }

    @Override
    public boolean reject() {
        return true;
    }

    @Override
    public boolean isShown() {
        seleniumDriver.findElementWhenPresent(SELECTOR);
        return true;
    }
}
