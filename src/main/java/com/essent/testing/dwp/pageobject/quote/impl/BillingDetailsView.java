package com.essent.testing.dwp.pageobject.quote.impl;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.automation.core.WebDriverWait;
import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.dwp.pageobject.constant.Quote;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteStepView;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteView;
import com.essent.testing.selenium.SeleniumDriver;
import cucumber.runtime.CucumberException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.tables.BillingInformation;
import stepdefinitions.dwp.tables.PaymentMethod;

import static com.essent.testing.dwp.DwpTimingParameters.*;
import static com.essent.testing.dwp.elements.BasicElements.NEXT_BUTTON;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.TITLE_SELECTOR_TEMPLATE;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.VIEW_SELECTOR;
import static com.essent.testing.dwp.quote.elements.B2CQuoteElements.SIGNATURE_OPTIONS_ACTIVE;
import static com.essent.testing.dwp.quote.elements.BillingElements.*;

public class BillingDetailsView extends Component implements CreateQuoteView, CreateQuoteStepView {


    private BillingInformation billingInformation;

    public void setBillingInformation(BillingInformation billingInformation) {
        this.billingInformation = billingInformation;
    }

    public BillingDetailsView(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(By.xpath(VIEW_SELECTOR.getQuery())), seleniumDriver);
        WebElement title = new WebDriverWait(seleniumDriver.getDriver(), 5).withoutException().until(
            driver -> {
                logger().info("STEP:");
                logger().info(" - ACTION: SELENIUM_FIND_ELEMENT");
                By by = By.xpath(TITLE_SELECTOR_TEMPLATE.getQuery().replace("${value}", Quote.BILLING_DETAILS.getText()));
                logger().info(" - BY: " + by.toString());
                return driver.findElement(by);
            }
        );
        if(title == null) {
            throw new CucumberException("Billing Details was not found.");
        }
        logger().info(" - RESULT: " + "element: <" + title.getTagName() + " class='" + title.getAttribute("class") + "'>" + title.getText() + "/<" + title.getTagName()+ ">");
    }

    @Override
    public CreateQuoteStepView next() {
        return new QuoteOverviewView(seleniumDriver);
    }

    @Override
    public boolean fillInInputValues() {
        Model.Execution execution = newExecution();
        PaymentMethod paymentMethod = billingInformation.getPaymentMethod();
        String eban = billingInformation.getEban();
        String bic = billingInformation.getBic();
        execution.
        element(PAYMENT_METHOD.element()).
            element(PAYMENT_IBAN.element()).
            element(PAYMENT_BIC.element()).
            element(NEXT_BUTTON.element()).
            element(SIGNATURE_OPTIONS_ACTIVE.element()).
            step(createStep(Action.SELECT).element(PAYMENT_METHOD.name()).value(paymentMethod.getLabel())).
            step(createStep(Action.TYPING).element(PAYMENT_IBAN.name()).value(eban), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(PAYMENT_BIC.name()).value(bic), INPUT.getSleepInMillis()).
            step(createStep(Action.CLICK).timeoutInSeconds(NEXT_STEP.getWaitInSeconds()).element(NEXT_BUTTON.name())).
            step(createStep(Action.REQUIRE).timeoutInSeconds(WAIT_NEXT_PAGE.getWaitInSeconds()).element(SIGNATURE_OPTIONS_ACTIVE.name()));
        return execute(execution);
    }
}
