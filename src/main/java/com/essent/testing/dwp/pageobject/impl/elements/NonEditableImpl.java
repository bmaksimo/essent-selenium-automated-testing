package com.essent.testing.dwp.pageobject.impl.elements;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.elements.NonEditable;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

import static com.billinghouse.testautomation.util.dsl.NumericUtil.amountAsInt;
import static com.billinghouse.testautomation.util.dsl.NumericUtil.checkAmount;
import static com.essent.testing.dwp.constant.DwpConstants.FLEMISCH_LOCALE;
import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.CARD_TEMPLATE;

public class NonEditableImpl extends Component implements NonEditable {

  private static final String XPATH_INPUT_TEMPLATE = "//div[label/text()='${label}']//div[@class='non-editable-input']";

  @Override
  public String getValue(String label) {
    seleniumDriver.waitForRequestsToFinish();
    Map<String, String> valuesMapper = new HashMap<>();
    valuesMapper.put("label", label);
    By xpathSelector = By.xpath(createQuery(XPATH_INPUT_TEMPLATE, valuesMapper));
    WebElement webElement = findElementWhenVisible(xpathSelector);
    return webElement.getAttribute("innerText");
  }

  @Override
  public String getValue(String title, String label) {
    seleniumDriver.waitForRequestsToFinish();
    logger().info("entering hell...");
    Map<String, String> valuesMapper = new HashMap<>();
    valuesMapper.put("title", title);
    valuesMapper.put("label", label);

//    By xpathSelector = By.xpath(createQuery(CARD_TEMPLATE.getQuery() + XPATH_INPUT_TEMPLATE, valuesMapper));
    By xpathSelectorMenu = By.xpath(createQuery(CARD_TEMPLATE.getQuery(), "title", title));
    By xpathSelectorValue = By.xpath(createQuery(XPATH_INPUT_TEMPLATE, "label", label));
//    WebElement webElement = findElementWhenVisible(xpathSelectorMenu);
    WebElement webElement = findElementWhenVisible(xpathSelectorValue);
//    WebElement value = webElement.findElement(xpathSelectorValue);
    String innerText = webElement.getAttribute("innerText");
    logger().debug("--NonEditable, element value is: " + innerText);

    return innerText;
  }

  @Override
  public boolean checkAmountUsingExpression(String title, String label, String expression) {
    seleniumDriver.waitForRequestsToFinish();
    Sleeper.sleepTightInSeconds(10);
    String amount = getValue(title, label).replaceAll("\\s+", " ");
    Integer amountAsInt = amountInCurrencyAsInt(amount);
    return checkAmount(amountAsInt, expression);
  }

  protected Integer amountInCurrencyAsInt(String amountInCurrency) {
    seleniumDriver.waitForRequestsToFinish();
    return amountAsInt(amountInCurrency, FLEMISCH_LOCALE);
  }
}
