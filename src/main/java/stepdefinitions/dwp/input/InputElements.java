package stepdefinitions.dwp.input;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import stepdefinitions.dwp.tables.plus.SwitchState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class InputElements extends DwpScenario {

    @Before("@DWP, @CORE, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    private class ApplyInput implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("BaseFormInput", options);
        }
    }
    private class ApplySelection implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrFormSelection", options);
        }
    }

    private class ApplyDateInput implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrDatePickerInput", options);
        }
    }


    @And("^\"([^\"]*)\" input is \"([^\"]*)\"$")
    public void setInput(String label, String value) throws Throwable {
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        parameterProvider.put("inputValue", inputValue);
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", inputValue);
        FluentWait<ApplyInput> waiter = waiter(new ApplyInput(), 10, 1);
        waiter.until((ApplyInput callback) ->{
            waiter.withMessage(String.format("Input field %s is undefined.", label));
            return callback.test(options);
        });
    }

    @And("^Label input for \"([^\"]*)\" is \"([^\"]*)\"$")
    public void setLabelInput(String label, String value) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        setInput(label, "string:"+value);
        seleniumDriver.waitForRequestsToFinish();
    }

    @And("^\"([^\"]*)\" date is \"([^\"]*)\"$")
    public void setDateInput(String label, String value) throws Throwable {
        Sleeper.sleepTightInSeconds(2);
        String inputValue = toDwpDate(parameterProvider.getValueOrParameterAsString(value));
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", inputValue);
        FluentWait<ApplyDateInput> waiter = waiter(new ApplyDateInput(), 10, 1);
        waiter.until((ApplyDateInput callback) ->{
            waiter.withMessage(String.format("Date input %s is undefined.", label));
            return callback.test(options);
        });
    }

    @And("^\"([^\"]*)\" selection is \"([^\"]*)\"$")
    public void setSelection(String label, String value) throws Throwable {
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", value);
        FluentWait<ApplySelection> waiter = waiter(new ApplySelection(), 10, 1);
        waiter.until((ApplySelection callback) ->{
            waiter.withMessage(String.format("Selection %s is undefined.", label));
            return callback.test(options);
        });
    }

    @And("^Option \"([^\"]*)\" is ([^\"]*)$")
    public void switchOption(String option, SwitchState state) throws Throwable {
        Map<String, String> options = new HashMap<>();
        options.put("label", option);
        FluentWait<InputElements> waiter = waiter(this, 10, 1);
        waiter.until((InputElements callback) ->{
            waiter.withMessage(String.format("Option %s is undefined.", option));
            return executeJavascriptTest("TrClickToggleInput", options);
        });
    }

    @And("^Form is submitted$")
    public void formIsSubmitted() throws Throwable {
        Map<String, String> options = new HashMap<>();
        boolean success = executeJavascriptTest("TrSubmitForm", options);
    }

    @And("^Field \"([^\"]*)\" input is \"([^\"]*)\"$")
    public void setInputByPlaceholder(String placeholder, String value) {
        seleniumDriver.waitForRequestsToFinish();
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        WebElement placeHolderInputElement = seleniumDriver.findElement(By.xpath("//input[@placeholder='"+placeholder+"']"));
        boolean placeHolderWasFound = placeHolderInputElement != null;
        assertThat(String.format("Placeholder element '%s' was not found.", placeholder), placeHolderWasFound, is(true));
        placeHolderInputElement.sendKeys(inputValue);
    }

    @Override
    @After("@DWP, @CORE, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
