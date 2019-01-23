package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.selenium.webdriver.dwp.SeleniumDriverDwpImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.tables.ConnectionDetails;
import stepdefinitions.dwp.tables.ProductType;
import stepdefinitions.dwp.tables.plus.SwitchState;

import java.util.HashMap;
import java.util.Map;

import static com.essent.testing.dwp.autocrat.element.quote.B2CQuoteElements.ELECTRICITY_EAN_CODE;
import static com.essent.testing.dwp.autocrat.element.quote.ConnectionElements.*;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.INPUT;

public class ConnectionDetailsPage extends QuoteCreationGuidedStep {


    private ConnectionDetails electroConnectionDetails;
    private ConnectionDetails gasConnectionDetails;

    public ConnectionDetailsPage(SeleniumDriverDwpImpl seleniumDriver) {
        super(seleniumDriver);
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

        Model.Execution execution = createExecution();
        execution.
            element(ELEC_METER_NR.element()).
            element(GAS_METER_NR.element()).
            step(createStep(Action.TYPING).element(ELEC_METER_NR.name()).value(electroConnectionDetails.getMeterNumber()), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(GAS_METER_NR.name()).value(gasConnectionDetails.getMeterNumber()), INPUT.getSleepInMillis());
        return execute(execution);

    }

    public boolean fillInElectricityEanCode() {
        Model.Execution execution = createExecution();
        execution.
            element(ELEC_EAN.element()).
            step(createStep(Action.TYPING).element(ELEC_EAN.name()).value(electroConnectionDetails.getEan()), INPUT.getSleepInMillis());
        return execute(execution);
    }

    public void setElectroConnectionDetails(ConnectionDetails electroConnectionDetails) {
        this.electroConnectionDetails = electroConnectionDetails;
    }

    public void setGasConnectionDetails(ConnectionDetails gasConnectionDetails) {
        this.gasConnectionDetails = gasConnectionDetails;
    }

    public boolean toggleMeter(ProductType productType, SwitchState state) {
        String query = ELEC_METER_OPEN_CHECKBOX.getQuery();
        switch (productType) {
            case Gas:
                query = GAS_METER_OPEN_CHECKBOX.getQuery();
                break;
            default:
                break;
        }
        Map<String, String> options = new HashMap<>();
        options.put("id", query);
        boolean result = seleniumDriver.executeJavascriptTest("TrToggleInputState", options);
        return result;
    }

    public boolean toggleMarketMockTest(ProductType productType, SwitchState state) {
        String query = ELEC_MARKET_MOCK.getQuery();
        switch (productType) {
            case Gas:
                query = GAS_MARKET_MOCK.getQuery();
                break;
            default:
                break;
        }
        Map<String, String> options = new HashMap<>();
        options.put("id", query);
        boolean result = seleniumDriver.executeJavascriptTest("TrToggleInputState", options);
        return result;
    }


    public String getEan() {
        WebElement element = seleniumDriver.findElement(By.cssSelector(ELECTRICITY_EAN_CODE.element().query));
        return element.getAttribute("value");
    }
}
