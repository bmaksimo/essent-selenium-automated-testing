package com.essent.testing.dwp.pageobject.quote.impl;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import stepdefinitions.dwp.tables.ConnectionDetails;
import stepdefinitions.dwp.tables.ProductType;
import stepdefinitions.dwp.tables.plus.CheckBoxState;

import java.util.HashMap;
import java.util.Map;

import static com.essent.testing.dwp.DwpTimingParameters.INPUT;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.VIEW_SELECTOR;
import static com.essent.testing.dwp.quote.elements.ConnectionElements.*;

public class ConnectionDetailsPage extends CreateQuoteGuidedStep {


    private ConnectionDetails electroConnectionDetails;
    private ConnectionDetails gasConnectionDetails;

    public ConnectionDetailsPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(By.xpath(VIEW_SELECTOR.getQuery())), seleniumDriver);
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

    public boolean toggleMeter(ProductType productType, CheckBoxState state) {
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
}
