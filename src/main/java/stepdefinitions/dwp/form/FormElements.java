package stepdefinitions.dwp.form;

import com.essent.testing.dwp.pageobject.elements.NonEditable;
import com.essent.testing.dwp.pageobject.impl.elements.ComboBoxImpl;
import com.essent.testing.dwp.pageobject.impl.elements.NonEditableImpl;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class FormElements extends DwpScenario {

    @Before("@DWP or @CORE or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Then("^Form header is \"([^\"]*)\"$")
    public void checkFormHeader(String expectedFormHeader) {
        seleniumDriver.waitForRequestsToFinish();
        WebElement actualFormHeader = seleniumDriver.findElementWhenVisible(By.xpath("//div[contains(@class, 'form__header')]/*[normalize-space()='" + expectedFormHeader + "']"));
        assertThat("Form header " + expectedFormHeader + " was not displayed within given time.", actualFormHeader.isDisplayed());
    }

    @And("^\"([^\"]*)\" field value is \"([^\"]*)\"$")
    public void setFieldValue(String label, String expectedValue) {
        NonEditable field = new NonEditableImpl();
        FluentWait<NonEditable> waiter = waiter(field, 20, 5);
        waiter.until((NonEditable p) -> {
            String actualValue = p.getValue(label);
            String assertionMessage = String.format("Actual value of \"%s\" was \"%s\" differs from expected \"%s\"", label, actualValue, expectedValue);
            waiter.withMessage(assertionMessage);
            return StringUtils.equalsIgnoreCase(expectedValue, actualValue);
        });
    }

    @And("^Numeric value at \"([^\"]*)\" in the card \"([^\"]*)\" is \"([^\"]*)\"$")
    public void checkNumericValueInCard(String label, String cardName, String expectedExpression) {
        NonEditable card = new NonEditableImpl();
        boolean result = card.checkAmountUsingExpression(cardName, label, expectedExpression);
        assertThat("The expected value differs from the real value", result, is(true));
    }

    @And("^Value at \"([^\"]*)\" in the card \"([^\"]*)\" is \"([^\"]*)\"$")
    public void checkValueInCard(String label, String cardName, String expectedParameter) {
        String expectedValue = parameterProvider.getValueOrParameterAsString(expectedParameter);
        NonEditable card = new NonEditableImpl();
        FluentWait<NonEditable> waiter = waiter(card, 60, 3);
        waiter.until(field -> StringUtils.equalsIgnoreCase(field.getValue(cardName, label), expectedValue));

    }

    @And("^Option value of \"([^\"]*)\" selection in the card \"([^\"]*)\" is matching \"([^\"]*)\"$")
    public void checkSelectionOption(String label, String cardName, String expected) {
        String expectedValue = parameterProvider.getValueOrParameterAsString(expected);
        String message =
            String.format("Dropdown box labelled \"%s\" in the card \"%s\"", label, cardName);
        Optional<String> option = new ComboBoxImpl().getOption(cardName, label);
        assertThat(message + " was empty", option.isPresent(), is(true));
        String actual = option.get();
        assertThat(
            message + String.format(" expected \"%s\" but actually was \"%s\"", expectedValue, actual),
            actual,
            containsString(expectedValue));
    }

    @Override
    @After("@DWP or @CORE or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

}
