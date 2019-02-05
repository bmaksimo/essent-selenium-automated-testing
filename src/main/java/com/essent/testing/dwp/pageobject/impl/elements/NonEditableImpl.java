package com.essent.testing.dwp.pageobject.impl.elements;

import com.essent.testing.dwp.pageobject.elements.NonEditable;
import com.essent.testing.odoo.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class NonEditableImpl extends Component implements NonEditable {

    private static final String XPATH_CONTAINER_TEMPLATE = "//div[div[normalize-space(h2/text())='${title}']]";

    private final static String XPATH_INPUT_TEPMPLATE = "//div[label/text()='${label}']//div[@class='non-editable-input']";

    public NonEditableImpl(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

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
        By xpathSelector = By.xpath(createQuery(XPATH_CONTAINER_TEMPLATE + XPATH_INPUT_TEPMPLATE, valuesMapper));
        WebElement webElement = findElementWhenVisible(xpathSelector);
        return webElement.getAttribute("innerText");
    }

}
