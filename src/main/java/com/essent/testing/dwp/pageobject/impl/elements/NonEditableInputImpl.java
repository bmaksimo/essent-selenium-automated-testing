package com.essent.testing.dwp.pageobject.impl.elements;

import com.essent.testing.dwp.pageobject.elements.NonEditableInput;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

public class NonEditableInputImpl implements NonEditableInput {

  public NonEditableInputImpl(WebElement element) {
    this.element = element;
  }

  private static final Logger logger = Logger.getLogger(NonEditableInputImpl.class);

  protected WebElement element;

  @Override
  public String getValue() {
    logger.info("STEP:");
    String innerText = element.getAttribute("innerText");
    logger.info(" - NON_EDITABLE: " + innerText);
    return innerText;
  }
}
