package com.essent.testing.dwp.pageobject.impl.elements;

import com.essent.testing.dwp.pageobject.elements.ComboBox;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ComboBoxImpl extends Component implements ComboBox {

    private static final String XPATH_SELECT_TEMPLATE = "//div[label/text()='${label}']//option[@selected='selected']";

    @Override
    public Optional<String> getOption(String title, String label) {
        Map<String, String> valuesMapper = new HashMap<>();
        valuesMapper.put("title", title);
        valuesMapper.put("label", label);
        By xpathSelector =
            By.xpath(createQuery(XPATH_CARD_TEMPLATE + XPATH_SELECT_TEMPLATE, valuesMapper));
        Optional<WebElement> elementOptional = seleniumDriver.findElementOptional(xpathSelector);
        return  elementOptional.map(WebElement::getText);
    }
}
