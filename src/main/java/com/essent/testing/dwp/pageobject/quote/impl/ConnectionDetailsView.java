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
import stepdefinitions.dwp.tables.ConnectionDetails;
import stepdefinitions.dwp.tables.ProductType;
import stepdefinitions.dwp.tables.plus.CheckBoxState;

import java.util.HashMap;
import java.util.Map;

import static com.essent.testing.dwp.DwpTimingParameters.*;
import static com.essent.testing.dwp.elements.BasicElements.NEXT_BUTTON;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.TITLE_SELECTOR_TEMPLATE;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.VIEW_SELECTOR;
import static com.essent.testing.dwp.quote.elements.B2CQuoteElements.BILLING_DETAILS_ACTIVE;
import static com.essent.testing.dwp.quote.elements.ConnectionElements.*;

public class ConnectionDetailsView extends Component implements CreateQuoteView, CreateQuoteStepView {


    private ConnectionDetails electroConnectionDetails;
    private ConnectionDetails gasConnectionDetails;

    public ConnectionDetailsView(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(By.xpath(VIEW_SELECTOR.getQuery())), seleniumDriver);
        WebElement title = new WebDriverWait(seleniumDriver.getDriver(), 5).withoutException().until(
            driver -> {
                logger().info("STEP:");
                logger().info(" - ACTION: SELENIUM_FIND_ELEMENT");
                By by = By.xpath(TITLE_SELECTOR_TEMPLATE.getQuery().replace("${value}", Quote.CONNECTION_DETAILS.getText()));
                logger().info(" - BY: " + by.toString());
                return driver.findElement(by);
            }
        );
        if (title == null) {
            throw new CucumberException("Connection Details was not found.");
        }
        logger().info(" - RESULT: " + "element: <" + title.getTagName() + " class='" + title.getAttribute("class") + "'>" + title.getText() + "/<" + title.getTagName() + ">");
    }


    @Override
    public CreateQuoteStepView next() {
        Model.Execution execution = newExecution();
        execution
            .element(NEXT_BUTTON.element())
            .element(BILLING_DETAILS_ACTIVE.element())
            .step(createStep(Action.CLICK).timeoutInSeconds(NEXT_STEP.getWaitInSeconds()).element(NEXT_BUTTON.name())).
            step(createStep(Action.REQUIRE).timeoutInSeconds(WAIT_NEXT_PAGE.getWaitInSeconds()).element(BILLING_DETAILS_ACTIVE.name()));
        execute(execution);
        return new BillingDetailsView(seleniumDriver);
    }

    @Override
    public boolean fillInInputValues() {
        Model.Execution execution = newExecution();
        execution.
            element(ELEC_MOVE_CHECKBOX.element()).
            element(ELEC_OTHEREAN_CHECKBOX.element()).
            element(ELEC_EAN.element()).
            element(ELEC_METER_NR.element()).
            element(GAS_MOVE_CHECKBOX.element()).
            element(GAS_OTHEREAN_CHECKBOX.element()).
            element(GAS_EAN.element()).
            element(GAS_METER_NR.element()).
            //element(NEXT_BUTTON.element()).element(BILLING_DETAILS_ACTIVE.element()).
            step(createStep(Action.CLICK).requireDisplayed(false).element(ELEC_MOVE_CHECKBOX.name()), TOGGLE_CHECKBOX.getSleepInMillis()).
            step(createStep(Action.CLICK).requireDisplayed(false).element(ELEC_OTHEREAN_CHECKBOX.name()), TOGGLE_CHECKBOX.getSleepInMillis()).
            step(createStep(Action.TYPING).element(ELEC_EAN.name()).value(electroConnectionDetails.getEan()), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(ELEC_METER_NR.name()).value(electroConnectionDetails.getMeterNumber()), INPUT.getSleepInMillis()).
            step(createStep(Action.CLICK).requireDisplayed(false).element(GAS_MOVE_CHECKBOX.name()), TOGGLE_CHECKBOX.getSleepInMillis()).
            step(createStep(Action.CLICK).requireDisplayed(false).element(GAS_OTHEREAN_CHECKBOX.name()), TOGGLE_CHECKBOX.getSleepInMillis()).

            step(createStep(Action.TYPING).element(GAS_EAN.name()).value(gasConnectionDetails.getEan()), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(GAS_METER_NR.name()).value(gasConnectionDetails.getMeterNumber()), INPUT.getSleepInMillis());
           /* step(createStep(Action.CLICK).timeoutInSeconds(NEXT_STEP.getWaitInSeconds()).element(NEXT_BUTTON.name())).
            step(createStep(Action.REQUIRE).timeoutInSeconds(WAIT_NEXT_PAGE.getWaitInSeconds()).element(BILLING_DETAILS_ACTIVE.name()));*/

        return execute(execution);
    }

    public void setElectroConnectionDetails(ConnectionDetails electroConnectionDetails) {
        this.electroConnectionDetails = electroConnectionDetails;
    }

    public void setGasConnectionDetails(ConnectionDetails gasConnectionDetails) {
        this.gasConnectionDetails = gasConnectionDetails;
    }

    public boolean openMeter(ProductType productType, CheckBoxState state) {
        Model.Element meterOpenCheckboxElement = ELEC_METER_OPEN_CHECKBOX.element();
        String meterOpenCheckboxName = ELEC_METER_OPEN_CHECKBOX.name();
        switch (productType) {
            case Gas:
                meterOpenCheckboxElement = GAS_METER_OPEN_CHECKBOX.element();
                meterOpenCheckboxName = GAS_METER_OPEN_CHECKBOX.name();
                break;
            default:
                break;
        }
        Map<String, String> options = new HashMap<>();
        options.put("id", meterOpenCheckboxElement.query);
        boolean result = seleniumDriver.executeJavascriptTest("TrToggleInputState", options);
        return result;
    }
}
