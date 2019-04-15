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
import stepdefinitions.dwp.tables.IsAre;
import stepdefinitions.dwp.tables.plus.SwitchState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class InputElements extends DwpScenario {
    private static final String CALENDAR_VALIDTO_TIME_ID = "validto-c-time-field";

        /**
     * Cucumber-JVM Before- hook
     * @param scenario Gherkin scenario descriptor
     * @throws Throwable
     */
    @Before("@DWP, @CORE, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    /**
     * Class, delegating form input to <code>BaseFormInput.js</code>
     */
    private class ApplyInput implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            boolean success = executeJavascriptTest("BaseFormInput", options);
            return success;
        }

        public boolean testNow(Map options) {
            boolean success = executeJavascriptTestImmediately("BaseFormInput", options, true);
            return success;
        }
    }

    /**
     * Class, delegating value selection to <code>TrFormSelection.js</code>
     */
    private class ApplySelection implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            boolean success = executeJavascriptTest("TrFormSelection", options);
            return success;
        }

        public boolean testNow(Map options) {
            boolean success = executeJavascriptTestImmediately("TrFormSelection", options, true);
            return success;
        }
    }

    /**
     * Class, delegating Date picker selection to <code>TrDatePickerInput.js</code>
     */
    private class ApplyDateInput implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            boolean success = executeJavascriptTest("TrDatePickerInput", options);
            return success;
        }
    }

    /**
     * Class, delegating checkbox state toggling to <code>TrToggleCheckBox.js</code>
     */
    private class ToggleCheckBox implements Predicate<Map<String, String>> {
        @Override
        public boolean test(Map<String, String> options) {
            boolean success = executeJavascriptTest("TrToggleCheckBox", options);
            return success;
        }
    }


    /**
     * Sets and asynchronously checks text input on any DWP form
     * @param label Text label
     * @param value Input value
     * @throws Throwable Can throw {@link cucumber.runtime.CucumberException} when test step assertion fails
     */
    @And("^\"([^\"]*)\" input is \"([^\"]*)\"$")
    public void setInput(String label, String value) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        parameterProvider.put("inputValue", inputValue);
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", inputValue);
        FluentWait<ApplyInput> waiter = waiter(new ApplyInput(), 10, 1);
        waiter.withMessage(String.format("Input field %s is undefined.", label));
        waiter.until((ApplyInput callback) -> callback.test(options));
        seleniumDriver.waitForRequestsToFinish();
    }

    /**
     * Sets and asynchronously checks text input on any DWP form
     * @param label Text label
     * @param value Input value
     * @param card Card title, for example "Gas Vooraf"
     * @throws Throwable Can throw {@link cucumber.runtime.CucumberException} when test step assertion fails
     */
    @And("^\"([^\"]*)\" input on \"([^\"]*)\" card is \"([^\"]*)\"$")
    public void setInput(String label, String card, String value) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        parameterProvider.put("inputValue", inputValue);
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", inputValue);
        options.put("card", card);
        FluentWait<ApplyInput> waiter = waiter(new ApplyInput(), 10, 1);
        waiter.withMessage(String.format("Input field %s is undefined.", label));
        waiter.until((ApplyInput callback) -> callback.test(options));
        seleniumDriver.waitForRequestsToFinish();
    }

    @And("^\"([^\"]*)\" input is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void setInputWithFixedTime(String label, String value, int waitingTime) throws Throwable {
        Sleeper.sleepTightInSeconds(waitingTime);
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        parameterProvider.put("inputValue", inputValue);
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", inputValue);
        FluentWait<ApplyInput> waiter = waiter(new ApplyInput(), 10, 1);
        waiter.withMessage(String.format("Input field %s is undefined.", label));
        waiter.until((ApplyInput callback) -> callback.testNow(options));
    }

    @And("^\"([^\"]*)\" input on card \"([^\"]*)\" is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void setInputWithFixedTime(String label, String card, String value, int waitingTime) throws Throwable {
        Sleeper.sleepTightInSeconds(waitingTime);
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        parameterProvider.put("inputValue", inputValue);
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", inputValue);
        options.put("card", card);
        FluentWait<ApplyInput> waiter = waiter(new ApplyInput(), 10, 1);
        waiter.withMessage(String.format("Input field %s is undefined.", label));
        waiter.until((ApplyInput callback) -> callback.testNow(options));
    }

    /**
     * Sets and asynchronously checks date input on any DWP form
     * @param label Text label
     * @param value Date input value
     * @throws Throwable Can throw {@link cucumber.runtime.CucumberException} when test step assertion fails
     */
    @And("^\"([^\"]*)\" date is \"([^\"]*)\"$")
    public void setDateInput(String label, String value) throws Throwable {
        Sleeper.sleepTightInSeconds(2);
        seleniumDriver.waitForRequestsToFinish();
        String inputValue = toDwpDate(parameterProvider.getValueOrParameterAsString(value));
        parameterProvider.put("inputValue", inputValue);
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", inputValue);
        FluentWait<ApplyDateInput> waiter = waiter(new ApplyDateInput(), 10, 1);
        waiter.withMessage(String.format("Date value %s input at '%s' failed.", inputValue, label));
        waiter.until((ApplyDateInput callback) -> callback.test(options));
        seleniumDriver.waitForRequestsToFinish();
    }

    @And("^\"([^\"]*)\" date is \"([^\"]*)\" and time is \"([^\"]*)\"$")
    public void setDateTimeInput(String label, String date, String time) throws Throwable {
        Sleeper.sleepTightInSeconds(2);
        seleniumDriver.waitForRequestsToFinish();
        String inputValue = toDwpDate(parameterProvider.getValueOrParameterAsString(date));
        parameterProvider.put("inputValue", inputValue);
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", inputValue);
        FluentWait<ApplyDateInput> waiter = waiter(new ApplyDateInput(), 10, 1);
        waiter.withMessage(String.format("Date value %s input at '%s' failed.", inputValue, label));
        waiter.until((ApplyDateInput callback) -> callback.test(options));
        seleniumDriver.waitForRequestsToFinish();

        setValidToTime(toDwpTime(parameterProvider.getValueOrParameterAsString(time)));
    }

    private void setValidToTime(String time) {
        String validTo = toDwpTime(parameterProvider.getValueOrParameterAsString(time));
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElement(By.id(CALENDAR_VALIDTO_TIME_ID)), validTo);
    }

    @And("^\"([^\"]*)\" date on \"([^\"]*)\" card is \"([^\"]*)\"$")
    public void setDateInput (String label, String card, String value) throws Throwable {
        Sleeper.sleepTightInSeconds(2);
        seleniumDriver.waitForRequestsToFinish();
        String inputValue = toDwpDate(parameterProvider.getValueOrParameterAsString(value));
        parameterProvider.put("inputValue", inputValue);
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", inputValue);
        options.put("card", card);
        FluentWait<ApplyDateInput> waiter = waiter(new ApplyDateInput(), 10, 1);
        waiter.withMessage(String.format("Date value %s input at '%s' failed.", inputValue, label));
        waiter.until((ApplyDateInput callback) -> callback.test(options));
        seleniumDriver.waitForRequestsToFinish();
    }

    /**
     * Sets and asynchronously checks dropdown selection on any DWP form
     * @param label Text label
     * @param value Input value
     * @throws Throwable Can throw {@link cucumber.runtime.CucumberException} when test step assertion fails
     */
    @And("^\"([^\"]*)\" selection is \"([^\"]*)\"$")
    public void setSelection(String label, String value) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", value);
        FluentWait<ApplySelection> waiter = waiter(new ApplySelection(), 10, 1);
        waiter.withMessage(String.format("Selection %s is undefined.", label));
        waiter.until((ApplySelection callback) -> callback.test(options));
        seleniumDriver.waitForRequestsToFinish();
    }

    /**
     * Sets and asynchronously checks dropdown selection on any DWP form
     * @param label Text label
     * @param value Input value
     * @param card Card title, for example "Gas Vooraf"
     * @throws Throwable Can throw {@link cucumber.runtime.CucumberException} when test step assertion fails
     */
    @And("^\"([^\"]*)\" selection on card \"([^\"]*)\" is \"([^\"]*)\"$")
    public void setSelection(String label, String card, String value) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", value);
        options.put("card", card);
        FluentWait<ApplySelection> waiter = waiter(new ApplySelection(), 10, 1);
        waiter.withMessage(String.format("Selection %s is undefined.", label));
        waiter.until((ApplySelection callback) -> callback.test(options));
        seleniumDriver.waitForRequestsToFinish();
    }

    @And("^\"([^\"]*)\" selection is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void setSelection(String label, String value, int waitingTime) throws Throwable {
        Sleeper.sleepTightInSeconds(waitingTime);
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", value);
        FluentWait<ApplySelection> waiter = waiter(new ApplySelection(), 10, 1);
        waiter.withMessage(String.format("Selection %s is undefined.", label));
        waiter.until((ApplySelection callback) -> callback.testNow(options));
    }

    @And("^\"([^\"]*)\" selection on card \"([^\"]*)\" is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void setSelection(String label, String card, String value, int waitingTime) throws Throwable {
        Sleeper.sleepTightInSeconds(waitingTime);
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", value);
        options.put("card", card);
        FluentWait<ApplySelection> waiter = waiter(new ApplySelection(), 10, 1);
        waiter.withMessage(String.format("Selection %s is undefined.", label));
        waiter.until((ApplySelection callback) -> callback.testNow(options));
    }

    /**
     * Sets and asynchronously checks toggle option on any DWP form
     * @param option Option label
     * @param verb One of verbs: {@link IsAre}
     * @param state Enumerated value: {@link SwitchState}
     * @throws Throwable  Can throw {@link cucumber.runtime.CucumberException} when test step assertion fails
     */
    @And("^Options? \"([^\"]*)\" (is|are) ([^\"]*)$")
    public void switchOption(String option, IsAre verb, SwitchState state) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        Map<String, String> options = new HashMap<>();
        options.put("label", option);
        options.put("verb", verb.getVerb());
        FluentWait<InputElements> waiter = waiter(this, 10, 1);
        waiter.withMessage(String.format("Option %s is undefined.", option));
        waiter.until((InputElements callback) -> executeJavascriptTest("TrClickToggleInput", options));
        seleniumDriver.waitForRequestsToFinish();
    }

    /**
     * Sets and asynchronously checks a checkbox on any DWP form
     * @param label Text label
     * @param state Checkbox state, one of values: {@link SwitchState}
     * @throws Throwable Can throw {@link cucumber.runtime.CucumberException} when test step assertion fails
     */
    @And("^Checkbox \"([^\"]*)\" is ([^\"]*)$")
    public void toggleCheckbox(String label, SwitchState state) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("state", state.name().toLowerCase());
        FluentWait<ToggleCheckBox> waiter = waiter(new ToggleCheckBox(), 10, 1);
        waiter.withMessage(String.format("Failure toggling checkbox %s to  target state %s.", label, state.name()));
        waiter.until((ToggleCheckBox callback) -> callback.test(options));
        seleniumDriver.waitForRequestsToFinish();
    }

    /**
     * Sets and asynchronously checks unlabelled placeholder input on any DWP form
     * @param placeholder Placeholder suggestion text
     * @param value Input value
     * @throws Throwable Can throw {@link cucumber.runtime.CucumberException} when test step assertion fails
     */
    @And("^Field \"([^\"]*)\" input is \"([^\"]*)\"$")
    public void setInputByPlaceholder(String placeholder, String value) {
        seleniumDriver.waitForRequestsToFinish();
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        WebElement placeHolderInputElement = seleniumDriver.findElement(By.xpath("//input[@placeholder='"+placeholder+"']"));
        boolean placeHolderWasFound = placeHolderInputElement != null;
        assertThat(String.format("Placeholder element '%s' was not found.", placeholder), placeHolderWasFound, is(true));
        placeHolderInputElement.sendKeys(inputValue);
        seleniumDriver.waitForRequestsToFinish();
    }

    /**
     * Cucumber-JVM  Aftrer- hook
     *
     */

    @Override
    @After("@DWP, @CORE, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
