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
            boolean success = executeJavascriptTest("BaseFormInput", options);
            return success;
        }
    }
    private class ApplySelection implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            boolean success = executeJavascriptTest("TrFormSelection", options);
            return success;
        }
    }

    private class ApplyDateInput implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            boolean success = executeJavascriptTest("TrDatePickerInput", options);
            return success;
        }
    }

    private class ToggleCheckBox implements Predicate<Map<String, String>> {
        @Override
        public boolean test(Map<String, String> options) {
            boolean success = executeJavascriptTest("TrToggleCheckBox", options);
            return success;
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

    @And("^\"([^\"]*)\" date is \"([^\"]*)\"$")
    public void setDateInput(String label, String value) throws Throwable {
        Sleeper.sleepTightInSeconds(2);
        String inputValue = toDwpDate(parameterProvider.getValueOrParameterAsString(value));
        parameterProvider.put("inputValue", inputValue);
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", inputValue);
        FluentWait<ApplyDateInput> waiter = waiter(new ApplyDateInput(), 10, 1);
        waiter.until((ApplyDateInput callback) ->{
            waiter.withMessage(String.format("Date value \"%s\" input at \"%s\" field failed.", inputValue, label));
            return callback.test(options);
        });
    }

    @And("^\"([^\"]*)\" selection is \"([^\"]*)\"$")
    public void setSelection(String label, String value) throws Throwable {
        Sleeper.sleepTightInSeconds(0.5);
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

    @And("^Checkbox \"([^\"]*)\" is ([^\"]*)$")
    public void toggleCheckbox(String label, SwitchState state) throws Throwable {
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("state", state.name().toLowerCase());
        FluentWait<ToggleCheckBox> waiter = waiter(new ToggleCheckBox(), 10, 1);
        waiter.until((ToggleCheckBox callback) -> {
            waiter.withMessage(String.format("Failure toggling checkbox %s to  target state %s.", label, state.name()));
            return callback.test(options);
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
