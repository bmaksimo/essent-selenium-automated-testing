package com.essent.testing.dwp.pageobject.quote.impl;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.automation.core.WebDriverWait;
import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.dwp.pageobject.constant.Quote;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteStepView;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteView;
import com.essent.testing.selenium.SeleniumDriver;
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
import static org.junit.Assert.fail;

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
            fail("Connection Details was not found.");
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
    public boolean fillInFormData() {
        Map<String, String> options = new HashMap<>();
        options.put("selector", ELEC_EAN.element().query);
        options.put("value", electroConnectionDetails.getEan());
        seleniumDriver.executeJavascriptTest("TrApplyFormInput", options, true);

        options.put("selector", GAS_EAN.element().query);
        options.put("value", gasConnectionDetails.getEan());
        seleniumDriver.executeJavascriptTest("TrApplyFormInput", options, true);

        Model.Execution execution = newExecution();
        execution.
            element(ELEC_METER_NR.element()).
            element(GAS_METER_NR.element()).
            step(createStep(Action.TYPING).element(ELEC_METER_NR.name()).value(electroConnectionDetails.getMeterNumber()), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(GAS_METER_NR.name()).value(gasConnectionDetails.getMeterNumber()), INPUT.getSleepInMillis());
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
        switch (productType) {
            case Gas:
                meterOpenCheckboxElement = GAS_METER_OPEN_CHECKBOX.element();
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
