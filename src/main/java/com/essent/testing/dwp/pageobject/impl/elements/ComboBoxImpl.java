package com.essent.testing.dwp.pageobject.impl.elements;

import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.CARD_TEMPLATE;

import com.essent.testing.dwp.pageobject.elements.ComboBox;
import com.essent.testing.dwp.pageobject.impl.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ComboBoxImpl extends Component implements ComboBox {

  private static final String XPATH_SELECT_TEMPLATE =
      "//div[label/text()='${label}']//option[@selected='selected']";

  @Override
  public Optional<String> getOption(String title, String label) {
    Map<String, String> valuesMapper = new HashMap<>();
    valuesMapper.put("title", title);
    valuesMapper.put("label", label);
    By xpathSelector =
        By.xpath(createQuery(CARD_TEMPLATE.getQuery() + XPATH_SELECT_TEMPLATE, valuesMapper));
    Optional<WebElement> elementOptional = seleniumDriver.findElementOptional(xpathSelector);
    return elementOptional.map(WebElement::getText);
  }
}
