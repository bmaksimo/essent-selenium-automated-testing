package com.essent.testing.dwp.pageobject.impl.elements;

import com.essent.testing.dwp.pageobject.elements.SelectWithSearch;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class SelectWithSearchImpl extends Component implements SelectWithSearch {

    private static final String XPATH_SELECT_WITH_SEARCH_TEPMPLATE =
        "//div[label/text()='${label}']//button";

    @Override
    public void click(String label) {
        Map<String, String> valuesMapper = new HashMap<>();
        valuesMapper.put("label", label);
        By xpathSelector =
            By.xpath(createQuery(XPATH_SELECT_WITH_SEARCH_TEPMPLATE, valuesMapper));
        WebElement webElement = findElementWhenVisible(xpathSelector);
        seleniumDriver.waitAndClick(webElement);
    }
}
