package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.pageobject.elements.ToggleSwitch;
import com.essent.testing.dwp.pageobject.impl.elements.ToggleSwitchImpl;
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


    private final static String MM_MODE_LABEL = "test";
    private final static String MM_MODE_ON_LABEL = "MM should respond?";


    private ConnectionDetails electricityConnectionDetails;
    private ConnectionDetails gasConnectionDetails;


    private ToggleSwitch  electricityMarketMockTestSwitch;
    private ToggleSwitch  gasMarketMockTestSwitch;

    public ConnectionDetailsPage() {
        this.electricityMarketMockTestSwitch = new ToggleSwitchImpl();
        this.gasMarketMockTestSwitch = new ToggleSwitchImpl();
    }

    @Override
    public boolean fillInFormData() {
        Map<String, String> options = new HashMap<>();
        options.put("selector", ELEC_EAN.element().query);
        options.put("value", electricityConnectionDetails.getEan());
        seleniumDriver.executeJavascriptTest("TrApplyFormInput", options, true);

        options.put("selector", GAS_EAN.element().query);
        options.put("value", gasConnectionDetails.getEan());
        seleniumDriver.executeJavascriptTest("TrApplyFormInput", options, true);

        Model.Execution execution = createExecution();
        execution.
            element(ELEC_METER_NR.element()).
            element(GAS_METER_NR.element()).
            step(createStep(Action.TYPING).element(ELEC_METER_NR.name()).value(electricityConnectionDetails.getMeterNumber()), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(GAS_METER_NR.name()).value(gasConnectionDetails.getMeterNumber()), INPUT.getSleepInMillis());
        return execute(execution);

    }

    public boolean fillInElectricityEanCode() {
        Model.Execution execution = createExecution();
        execution.
            element(ELEC_EAN.element()).
            step(createStep(Action.TYPING).element(ELEC_EAN.name()).value(electricityConnectionDetails.getEan()), INPUT.getSleepInMillis());
        return execute(execution);
    }

    public void setElectricityConnectionDetails(ConnectionDetails electricityConnectionDetails) {
        this.electricityConnectionDetails = electricityConnectionDetails;
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
        boolean result = executeJavascriptTest("TrToggleInputState", options);
        return result;
    }

    public boolean toggleMarketMockTest(ProductType productType, SwitchState state) {
        seleniumDriver.waitForRequestsToFinish();
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
        boolean result = executeJavascriptTest("TrToggleInputState", options);
        return result;
    }


    public String getEan() {
        WebElement element = seleniumDriver.findElement(By.cssSelector(ELECTRICITY_EAN_CODE.element().query));
        return element.getAttribute("value");
    }

    /**
     * Method is exceptionally using hard-coded "test" label, for the sake of simplicity of parameters
     * because the 'test' toggle switch is not included in productional versions,
     * and it is unlikely that Dutch and French UAT versions of switch have different labels in UAT
     */
    public void switchOnElectricityMarketMock() {
        electricityMarketMockTestSwitch.switchOn(MM_MODE_LABEL);
    }

    public void switchOnElectricityMarketMock(String card) {
        electricityMarketMockTestSwitch.switchOn(card, MM_MODE_LABEL);
    }
    public void isElectricityMarketMockOn(String card) {
        electricityMarketMockTestSwitch.checkVisibility(card, MM_MODE_ON_LABEL);
    }

    /**
     * Method is exceptionally using hard-coded "test" label, for the sake of simplicity of parameters
     * because the 'test' toggle switch is not included in productional versions,
     * and it is unlikely that Dutch and French UAT versions of switch have different labels in UAT
     */
    public void switchOGasMarketMock() {
        gasMarketMockTestSwitch.switchOn(MM_MODE_LABEL);
    }

    public void switchOnGasMarketMock(String card) {
        gasMarketMockTestSwitch.switchOn(card, MM_MODE_LABEL);
    }

    public void isGasMarketMockOn(String card) {
        gasMarketMockTestSwitch.checkVisibility(card, MM_MODE_ON_LABEL);
    }
}
