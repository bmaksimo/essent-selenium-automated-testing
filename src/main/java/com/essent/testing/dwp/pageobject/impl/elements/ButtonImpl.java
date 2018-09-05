package com.essent.testing.dwp.pageobject.impl.elements;

import com.essent.testing.dwp.pageobject.elements.Button;
import org.openqa.selenium.WebElement;

public class ButtonImpl implements Button {
    public ButtonImpl(WebElement element) {
        this.element = element;
    }

    private WebElement element;

    @Override
    public void click() {
        element.click();
    }
}
