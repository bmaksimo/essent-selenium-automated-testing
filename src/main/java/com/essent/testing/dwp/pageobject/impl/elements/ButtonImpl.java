package com.essent.testing.dwp.pageobject.impl.elements;

import com.essent.testing.dwp.pageobject.elements.Button;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

public class ButtonImpl implements Button {
    public ButtonImpl(WebElement element) {
        this.element = element;
    }

    private static final Logger logger = Logger.getLogger(ButtonImpl.class);

    private WebElement element;
    @Override
    public void click() {
        logger.info("STEP:");
        logger.info(" - BUTTON TEXT: " + element.getText());
        element.click();
        logger.info(" - ACTION: CLICK");
    }
}
