package com.essent.testing.dwp.pageobject.impl.quote;

import static com.billinghouse.testautomation.javascript.testrunner.JsTestRegistry.JS_TR_APPLY_FORM_INPUT;
import static com.essent.testing.dwp.autocrat.element.quote.B2CQuoteElements.ELECTRICITY_EAN_CODE;
import static com.essent.testing.dwp.autocrat.element.quote.ConnectionElements.*;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.INPUT;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.pageobject.elements.ToggleSwitch;
import com.essent.testing.dwp.pageobject.impl.elements.ToggleSwitchImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.tables.ConnectionDetails;
import stepdefinitions.dwp.tables.ProductType;
import stepdefinitions.dwp.tables.plus.SwitchState;

public class ConnectionDetailsPage extends QuoteCreationGuidedStep {

  // These two locators are for web elements, not used in production.
  // These elements are provided in test environments, in English only
  private static final String MM_MODE_LABEL = "test";
  private static final String MM_MODE_ON_LABEL = "MM should respond?";

  private ConnectionDetails electricityConnectionDetails;
  private ConnectionDetails gasConnectionDetails;

  private ToggleSwitch electricityMarketMockTestSwitch;
  private ToggleSwitch gasMarketMockTestSwitch;

  public ConnectionDetailsPage() {
    this.electricityMarketMockTestSwitch = new ToggleSwitchImpl();
    this.gasMarketMockTestSwitch = new ToggleSwitchImpl();
  }

  @Override
  public boolean fillInFormData() {
    Map<String, String> options = new HashMap<>();
    options.put("selector", ELEC_EAN.element().query);
    options.put("value", electricityConnectionDetails.getEan());
    seleniumDriver.executeJavascriptTest(JS_TR_APPLY_FORM_INPUT, options, true);

    options.put("selector", GAS_EAN.element().query);
    options.put("value", gasConnectionDetails.getEan());
    seleniumDriver.executeJavascriptTest(JS_TR_APPLY_FORM_INPUT, options, true);

    Model.Execution execution = createExecution();
    execution
        .element(ELEC_METER_NR.element())
        .element(GAS_METER_NR.element())
        .step(
            createStep(Action.TYPING)
                .element(ELEC_METER_NR.name())
                .value(electricityConnectionDetails.getMeterNumber()),
            INPUT.getSleepInMillis())
        .step(
            createStep(Action.TYPING)
                .element(GAS_METER_NR.name())
                .value(gasConnectionDetails.getMeterNumber()),
            INPUT.getSleepInMillis());
    return execute(execution);
  }

  public boolean fillInElectricityEanCode() {
    Model.Execution execution = createExecution();
    execution
        .element(ELEC_EAN.element())
        .step(
            createStep(Action.TYPING)
                .element(ELEC_EAN.name())
                .value(electricityConnectionDetails.getEan()),
            INPUT.getSleepInMillis());
    return execute(execution);
  }

  public void setElectricityConnectionDetails(ConnectionDetails electricityConnectionDetails) {
    this.electricityConnectionDetails = electricityConnectionDetails;
  }

  public void setGasConnectionDetails(ConnectionDetails gasConnectionDetails) {
    this.gasConnectionDetails = gasConnectionDetails;
  }

  public void toggleMarketMockTest(ProductType productType) {
    String productTypeToggleId =
        productType == ProductType.Gas ? GAS_MARKET_MOCK.getQuery() : ELEC_MARKET_MOCK.getQuery();
    List<WebElement> productTypeToggles = seleniumDriver.findElements(By.id(productTypeToggleId));
    if (CollectionUtils.isNotEmpty(productTypeToggles)) productTypeToggles.get(0).click();
    else Assert.fail("Product type toggle with id " + productTypeToggleId + " was not found.");
  }

  public String getEan() {
    WebElement element =
        seleniumDriver.findElement(By.cssSelector(ELECTRICITY_EAN_CODE.element().query));
    return element.getAttribute("value");
  }

  public void isElectricityMarketMockOn(String card) {
    electricityMarketMockTestSwitch.checkVisibility(card, MM_MODE_ON_LABEL);
  }

  public void isGasMarketMockOn(String card) {
    gasMarketMockTestSwitch.checkVisibility(card, MM_MODE_ON_LABEL);
  }

  public void toggleElectricityMarketMockTest(SwitchState switchState, String card) {
    electricityMarketMockTestSwitch.toggle(switchState, card, MM_MODE_LABEL);
  }

  public void toggleGasMarketMockTest(SwitchState switchState, String card) {
    gasMarketMockTestSwitch.toggle(switchState, card, MM_MODE_LABEL);
  }
}
