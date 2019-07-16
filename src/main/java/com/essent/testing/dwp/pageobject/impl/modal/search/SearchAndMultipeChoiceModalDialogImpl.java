package com.essent.testing.dwp.pageobject.impl.modal.search;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.elements.Button;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.elements.ButtonImpl;
import com.essent.testing.dwp.pageobject.modal.search.SearchAndMultipeChoiceModalDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SearchAndMultipeChoiceModalDialogImpl extends Component
    implements SearchAndMultipeChoiceModalDialog {

    private static final String XPATH_MODAL_SEARCH_RESULT_LOCATOR_TEMPLATE =
        "//div[@class='modal__content']//label[input[@type='checkbox']]";
    private static final By CSS_MODAL_TITLE_LOCATOR = By.cssSelector(".view__modal .modal__header");
    private static final By CSS_MODAL_SEARCH_FIELD_LOCATOR =
        By.cssSelector(".input-holder #search-input");
    private static final By XPATH_MODAL_SEARCH_RESULT_LABEL_LOCATOR =
        By.xpath(XPATH_MODAL_SEARCH_RESULT_LOCATOR_TEMPLATE);

    private static final String XPATH_MODAL_SUBMIT_TEMPLATE =
        "//a[normalize-space(text())='${label}']";
    private static final String CSS_MODAL_SEARCH_BUTTON_TEMPLATE = "input[value='${label}']";

    @Override
    public String getTitle() {
        WebElement titleWebElement = findElementWhenVisible(CSS_MODAL_TITLE_LOCATOR);
        return titleWebElement.getText();
    }

    @Override
    public void setSearchOption(String searchOption) {
        WebElement searchTextWebElement =
            findElementWhenPresent(
                CSS_MODAL_SEARCH_FIELD_LOCATOR, Duration.ofSeconds(10), Duration.ofMillis(500));
        seleniumDriver.waitAndSendKeys(searchTextWebElement, searchOption);
    }

    @Override
    public void search(String label) {
        Map<String, String> valuesMapper = new HashMap<>();
        valuesMapper.put("label", label);
        By selector = By.cssSelector(createQuery(CSS_MODAL_SEARCH_BUTTON_TEMPLATE, valuesMapper));
        Button searchButton =
            new ButtonImpl(
                findElementWhenPresent(selector, Duration.ofSeconds(10), Duration.ofMillis(500)));
        searchButton.click();
    }

    @Override
    public boolean checkSearchResult(String match) {
          Sleeper.sleepTightInSeconds(10);
          List<WebElement> elements =
                 seleniumDriver.findElements(
                     XPATH_MODAL_SEARCH_RESULT_LABEL_LOCATOR,
                     Duration.ofSeconds(10),
                     Duration.ofMillis(500));
             Optional<WebElement> first =
                 elements.stream().filter(e -> e.getText().trim().contains(match)).findFirst();
             first.ifPresent(WebElement::click);
          return first.isPresent();
    }

    @Override
    public void submitSearchResult(String submitBtnLabel) {
        Map<String, String> valuesMapper = new HashMap<>();
        valuesMapper.put("label", submitBtnLabel);
        By selector = By.xpath(createQuery(XPATH_MODAL_SUBMIT_TEMPLATE, valuesMapper));
        Button submitButton =
            new ButtonImpl(
                findElementWhenPresent(selector, Duration.ofSeconds(10), Duration.ofMillis(500)));
        submitButton.click();
    }
}
