package com.essent.testing.dwp.pageobject.impl.modal.quote;

import com.essent.automation.core.WebDriverWait;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.modal.quote.SimilarAccountDialog;
import com.essent.testing.selenium.SeleniumDriver;
import cucumber.runtime.CucumberException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SimilarAccountDialogImpl extends Component implements SimilarAccountDialog {

    private final static By SELECOR = By.cssSelector(".view__modal .modal__header");


    private String title;

    public SimilarAccountDialogImpl(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
        waitForRequestsToFinish();
    }

    public SimilarAccountDialogImpl(SeleniumDriver seleniumDriver, String title) {
        super(seleniumDriver.findElementOrNull(SELECOR), seleniumDriver);
        this.title = title;
        waitForRequestsToFinish();
    }

    @Override
    public String getTitle() {
        return title;
    }


    @Override
    public void clickOnLink(String linkText) throws Throwable {
        goToLink(linkText);
    }

    private void goToLink(String linkText) {
        By byLinkText = By.linkText(linkText);
        logger().info("Searching element by link text " + linkText);
        seleniumDriver.waitForElementToBeVisibleBy(byLinkText, 20, 200);
        WebElement link = seleniumDriver.findElement(byLinkText);
        new Actions(seleniumDriver.getDriver()).moveToElement(link).perform();
        logger().info("Found  element: " + link.getTagName());
        if (link != null) {
            logger().info("CLICK ");
            link.click();
        } else {
            seleniumDriver.takeScreenshot(linkText + "-");
            throw new CucumberException("Element not found by link text " + linkText);
        }
        logger().info("ACTION REQIURED: CONFIRM_ALERT");
        (new WebDriverWait(seleniumDriver.getDriver(), 2)).until(ExpectedConditions.alertIsPresent());
        Alert alert = seleniumDriver.getDriver().switchTo().alert();

        alert.accept();
        logger().info("RESULT: ALERT_CONFIRMED");
    }
}
