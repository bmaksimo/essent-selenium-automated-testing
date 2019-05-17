package com.essent.testing.dwp.pageobject.impl.elements;

import com.essent.testing.dwp.pageobject.elements.Button;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.util.Optional;

public class ButtonImpl extends Component implements Button {

  public ButtonImpl() {
    this(null);
  }

  public ButtonImpl(WebElement element) {
    this.element = Optional.ofNullable(element);
  }

  private static final Logger logger = Logger.getLogger(ButtonImpl.class);

  protected Optional<WebElement> element;

  @Override
  public void click() {
    if (!element.isPresent()) {
      throw new IllegalStateException("Wrapped web element is undefined");
    }
    logger.info("STEP:");
    logger.info(" - BUTTON TEXT: " + element.get().getText());
    seleniumDriver.waitForRequestsToFinish();
    element.get().click();
    seleniumDriver.waitForRequestsToFinish();
    logger.info(" - ACTION: CLICK");
  }
}
