package com.essent.testing.dwp.pageobject.impl.elements;

import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.CARD_TEMPLATE;

import com.essent.testing.dwp.pageobject.elements.ToggleSwitch;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.tables.plus.SwitchState;

public class ToggleSwitchImpl extends ButtonImpl implements ToggleSwitch {

  private static final String XPATH_TOGGLE_SWITCH_TEPMPLATE =
      "//div[label/text()='${label}']//label[@class='input__toggle']//input";
  private static final String VALUE_ATTRIBUTE = "autotest-value";

  private static final Logger logger = Logger.getLogger(ToggleSwitchImpl.class);

  public ToggleSwitchImpl() {
    super(null);
  }

  public ToggleSwitchImpl(WebElement element) {
    super(element);
  }

  @Override
  public boolean isOn(String label) {
    Map<String, String> valuesMapper = new HashMap<>();
    valuesMapper.put("label", label);
    By xpathSelector = By.xpath(createQuery(XPATH_TOGGLE_SWITCH_TEPMPLATE, valuesMapper));
    WebElement webElement = findElementWhenVisible(xpathSelector);
    this.element = Optional.ofNullable(webElement);
    String switchState = webElement.getAttribute(VALUE_ATTRIBUTE);
    logger.debug("--ToggleSwitch, element value is: " + switchState);
    return SwitchState.valueOf(switchState).isOn();
  }

  @Override
  public boolean isOn(String card, String label) {
    Map<String, String> valuesMapper = new HashMap<>();
    valuesMapper.put("title", card);
    valuesMapper.put("label", label);
    By xpathSelector =
        By.xpath(
            createQuery(CARD_TEMPLATE.getQuery() + XPATH_TOGGLE_SWITCH_TEPMPLATE, valuesMapper));
    WebElement webElement =
        findElementWhenPresent(xpathSelector, Duration.ofSeconds(20), Duration.ofMillis(1));
    this.element = Optional.ofNullable(webElement);
    String switchState = webElement.getAttribute(VALUE_ATTRIBUTE);
    logger.debug("--ToggleSwitch, element value is: " + switchState);
    return SwitchState.On.hasState(switchState);
  }

  @Override
  public boolean isOn() {
    if (!element.isPresent()) {
      throw new IllegalStateException("Wrapped web element is undefined");
    }
    logger.debug("STEP:");
    String status = element.get().getAttribute(VALUE_ATTRIBUTE);
    logger.debug(" - TOGGLE STATUS: " + status);
    return BooleanUtils.toBoolean(status);
  }

  @Override
  public void switchOn(String label) {
    if (!isOn(label)) {
      element.ifPresent(WebElement::click);
    }
  }

  @Override
  public void toggle(SwitchState switchState, String label) {
    if (switchState.isOn()) {
      switchOn(label);
    } else if (switchState.isOff()) {
      switchOff(label);
    } else {
      logger.warn("Element was not toggled to " + switchState + " state.");
    }
  }

  @Override
  public void toggle(SwitchState switchState, String card, String label) {
    if (switchState.isOn()) {
      switchOn(card, label);
    } else if (switchState.isOff()) {
      switchOff(card, label);
    } else {
      logger.warn("Element was not toggled to " + switchState + " state.");
    }
  }

  @Override
  public void switchOn(String card, String label) {
    if (!isOn(card, label)) {
      element.get().click();
    }
  }

  @Override
  public void switchOff(String label) {
    if (isOn(label)) {
      element.get().click();
    }
  }

  @Override
  public void switchOff(String card, String label) {
    if (isOn(card, label)) {
      element.get().click();
    }
  }

  @Override
  public boolean checkVisibility(String card, String label) {
    Map<String, String> valuesMapper = new HashMap<>();
    valuesMapper.put("title", card);
    valuesMapper.put("label", label);
    By xpathSelector =
        By.xpath(
            createQuery(CARD_TEMPLATE.getQuery() + XPATH_TOGGLE_SWITCH_TEPMPLATE, valuesMapper));
    findElementWhenPresent(xpathSelector, Duration.ofSeconds(20), Duration.ofSeconds(1));
    return true;
  }
}
