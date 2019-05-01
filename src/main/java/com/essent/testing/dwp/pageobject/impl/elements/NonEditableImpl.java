package com.essent.testing.dwp.pageobject.impl.elements;

import com.essent.testing.dwp.pageobject.elements.NonEditable;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

import static com.billinghouse.test_automation.util.dsl.NumericUtil.amountAsInt;
import static com.billinghouse.test_automation.util.dsl.NumericUtil.checkAmount;
import static com.essent.testing.dwp.constant.DwpConstants.FLEMISCH_LOCALE;

public class NonEditableImpl extends Component implements NonEditable {

  private static final String XPATH_INPUT_TEPMPLATE =
      "//div[label/text()='${label}']//div[@class='non-editable-input']";

  @Override
  public String getValue(String label) {
    Map<String, String> valuesMapper = new HashMap<>();
    valuesMapper.put("label", label);
    By xpathSelector = By.xpath(createQuery(XPATH_INPUT_TEPMPLATE, valuesMapper));
    WebElement webElement = findElementWhenVisible(xpathSelector);
    return webElement.getAttribute("innerText");
  }

  @Override
  public String getValue(String title, String label) {
    Map<String, String> valuesMapper = new HashMap<>();
    valuesMapper.put("title", title);
    valuesMapper.put("label", label);
    By xpathSelector =
        By.xpath(createQuery(XPATH_CARD_TEMPLATE + XPATH_INPUT_TEPMPLATE, valuesMapper));
    WebElement webElement = findElementWhenVisible(xpathSelector);
    String innerText = webElement.getAttribute("innerText");
    logger().info("--NonEditable, element value is: " + innerText);
    return innerText;
  }

  @Override
  public boolean checkAmountUsingExpression(String title, String label, String expression) {
    String amount = getValue(title, label).replaceAll("\\s+", " ");
    Integer amountAsInt = amountInCurrencyAsInt(amount);
    return checkAmount(amountAsInt, expression);
  }

  protected Integer amountInCurrencyAsInt(String amountInCurrency) {
    return amountAsInt(amountInCurrency, FLEMISCH_LOCALE);
  }
}
