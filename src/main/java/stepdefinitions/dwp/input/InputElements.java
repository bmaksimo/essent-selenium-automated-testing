package stepdefinitions.dwp.input;

import com.billinghouse.test_automation.util.dsl.DateExpressionsUtil;
import com.billinghouse.test_automation.util.dsl.EssentDateTimeFormat;
import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.elements.SelectWithSearch;
import com.essent.testing.dwp.pageobject.impl.elements.SelectWithSearchImpl;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts.ContractPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import stepdefinitions.dwp.tables.IsAre;
import stepdefinitions.dwp.tables.plus.SwitchState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class InputElements extends DwpScenario {
    private static final String CALENDAR_VALIDTO_TIME_ID = "validto-c-time-field";

        /**
     * Cucumber-JVM Before- hook
     * @param scenario Gherkin scenario descriptor
     * @throws Throwable
     */
    @Before("@DWP or @CORE or @E2E or @REGRESSION or @API")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    /**
     * Class, delegating form input to <code>BaseFormInput.js</code>
     */
  private class ApplyInput implements Predicate<Map> {
    @Override
    public boolean test(Map options) {
      return executeJavascriptTest(JS_BASE_FORM_INPUT, options);
    }

    public boolean testNow(Map options) {
      return executeJavascriptTestImmediately(JS_BASE_FORM_INPUT, options, true);
    }
  }

    /**
     * Class, delegating value selection to <code>TrFormSelection.js</code>
     */
  private class ApplySelection implements Predicate<Map> {
    @Override
    public boolean test(Map options) {
      return executeJavascriptTest(JS_TR_FORM_SELECTION, options);
    }

    public boolean testNow(Map options) {
      return executeJavascriptTestImmediately(JS_TR_FORM_SELECTION, options, true);
    }
  }

  /** Class, delegating Date picker selection to <code>TrDatePickerInput.js</code> */
  private class ApplyDateInput implements Predicate<Map> {
    @Override
    public boolean test(Map options) {
      return executeJavascriptTest(JS_TR_DATE_PICKER_INPUT, options);
    }

    public boolean testNow(Map options) {
      return executeJavascriptTestImmediately(JS_TR_DATE_PICKER_INPUT, options, true);
    }
  }

    /**
     * Class, delegating checkbox state toggling to <code>TrToggleCheckBox.js</code>
     */
  private class ToggleCheckBox implements Predicate<Map<String, String>> {
    @Override
    public boolean test(Map<String, String> options) {
      return executeJavascriptTest(JS_TR_TOGGLE_CHECK_BOX, options);
    }
  }


  /**
   * Sets and asynchronously checks text input on any DWP form
   * @param label Text label
   * @param value Input value
     * @throws Throwable Can throw {@link cucumber.runtime.CucumberException} when test step assertion fails
   */
  @And("^\"([^\"]*)\" input is \"([^\"]*)\"$")
  public void setInput(String label, String value){
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
    public void setInput(String label, String card, String value) {
        Sleeper.sleepTightInSeconds(10);
        seleniumDriver.waitForRequestsToFinish();
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        parameterProvider.put("inputValue", inputValue);
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", inputValue);
        options.put("card", card);
        FluentWait<ApplyInput> waiter = waiter(new ApplyInput(), 30, 1);
        waiter.withMessage(String.format("Input field %s is undefined.", label));
        waiter.until((ApplyInput callback) -> callback.test(options));
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(10);
    }

    @And("^\"([^\"]*)\" input is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void setInputWithFixedTime(String label, String value, int waitingTime){
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
    public void setInputWithFixedTime(String label, String card, String value, int waitingTime){
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

    @And("^\"([^\"]*)\" date is first day of next month$")
    public void setDateInputFirstDayNextMonth(String label){
        String value = DateExpressionsUtil
            .getFirstDateOfNextMonth()
            .toString(EssentDateTimeFormat.DWP_FRENCH_DATE_FORMAT.getFormat());
        setDateInput(label, value);
    }

    @And("^\"([^\"]*)\" date is last day of current month next year$")
    public void setDateInputLastDayCurrentMonthNextYear(String label){
        String value = DateExpressionsUtil
            .getLastDayOfCurrentMonthNextYear()
            .toString(EssentDateTimeFormat.DWP_FRENCH_DATE_FORMAT.getFormat());
        setDateInput(label, value);
    }

    /**
     * Sets and asynchronously checks date input on any DWP form
     * @param label Text label
     * @param value Date input value
     * @throws Throwable Can throw {@link cucumber.runtime.CucumberException} when test step assertion fails
     */
    @And("^\"([^\"]*)\" date is \"([^\"]*)\"$")
    public void setDateInput(String label, String value){
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
        Sleeper.sleepTightInSeconds(3);
    }

    @And("^\"([^\"]*)\" date is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void setDateInput(String label, String value, int waitingTime){
        Sleeper.sleepTightInSeconds(waitingTime);
        String inputValue = toDwpDate(parameterProvider.getValueOrParameterAsString(value));
        parameterProvider.put("inputValue", inputValue);
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", inputValue);
        FluentWait<ApplyDateInput> waiter = waiter(new ApplyDateInput(), 10, 1);
        waiter.withMessage(String.format("Date value %s input at '%s' failed.", inputValue, label));
        waiter.until((ApplyDateInput callback) -> callback.testNow(options));
    }

    @And("^\"([^\"]*)\" date is \"([^\"]*)\" and time is \"([^\"]*)\"$")
    public void setDateTimeInput(String label, String date, String time){
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

        setValidToTime(parameterProvider.getValueOrParameterAsString(time));
    }

    private void setValidToTime(String time) {
        String validTo = toDwpTime(parameterProvider.getValueOrParameterAsString(time));
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElement(By.id(CALENDAR_VALIDTO_TIME_ID)), validTo);
    }

    @And("^\"([^\"]*)\" date on \"([^\"]*)\" card is \"([^\"]*)\"$")
    public void setDateInput (String label, String card, String value){
        seleniumDriver.waitForRequestsToFinish();
        String inputValue = toDwpDate(parameterProvider.getValueOrParameterAsString(value));
        parameterProvider.put("inputValue", inputValue);
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", inputValue);
        options.put("card", card);
        FluentWait<ApplyDateInput> waiter = waiter(new ApplyDateInput(), 90, 15);
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
    public void setSelection(String label, String value){
        seleniumDriver.waitForRequestsToFinish();
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", value);
        FluentWait<ApplySelection> waiter = waiter(new ApplySelection(), 60, 5);
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
    public void setSelection(String label, String card, String value){
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
    public void setSelection(String label, String value, int waitingTime){
        Sleeper.sleepTightInSeconds(waitingTime);
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", value);
        FluentWait<ApplySelection> waiter = waiter(new ApplySelection(), 10, 1);
        waiter.withMessage(String.format("Selection %s is undefined.", label));
        waiter.until((ApplySelection callback) -> callback.testNow(options));
    }

    @And("^\"([^\"]*)\" selection on card \"([^\"]*)\" is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void setSelection(String label, String card, String value, int waitingTime){
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
    @And("Options? \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"")
    public void switchOption(String option, String verb, SwitchState state){
        Sleeper.sleepTightInSeconds(5);
        if (!(verb.equalsIgnoreCase("is") || verb.equalsIgnoreCase("are")) ){
            Assert.assertTrue("String is not valid (is/are expected)",false);
        }
        seleniumDriver.waitForRequestsToFinish();
        Map<String, String> options = new HashMap<>();
        options.put("label", option);
        options.put("verb", verb);
        FluentWait<InputElements> waiter = waiter(this, 10, 1);
        waiter.withMessage(String.format("Option %s is undefined.", option));
        waiter.until(
            (InputElements callback) -> executeJavascriptTest(JS_TR_CLICK_TOGGLE_INPUT, options));
        seleniumDriver.waitForRequestsToFinish();
    }

    /**
     * Sets and asynchronously checks a checkbox on any DWP form
     * @param label Text label
     * @param state Checkbox state, one of values: {@link SwitchState}
     * @throws Throwable Can throw {@link cucumber.runtime.CucumberException} when test step assertion fails
     */
    @And("^Checkbox \"([^\"]*)\" is ([^\"]*)$")
    public void toggleCheckbox(String label, SwitchState state){
        seleniumDriver.waitForRequestsToFinish();
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("state", state.name().toLowerCase());
        FluentWait<ToggleCheckBox> waiter = waiter(new ToggleCheckBox(), 30, 2);
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
        placeHolderInputElement.clear();
        placeHolderInputElement.sendKeys(inputValue);
        seleniumDriver.waitForRequestsToFinish();
    }

  @And("^Selection with search is \"([^\"]*)\"$")
  public void selectWithSearchIsClicked(String label){
    seleniumDriver.waitForRequestsToFinish();
    SelectWithSearch button = new SelectWithSearchImpl();
    button.click(label);
  }

    @And("New Amount Invoice is \"([^\"]*)\" for EAN \"([^\"]*)\"$")
    public void setInputByEanLabel(String value, String EAN) {
        seleniumDriver.waitForRequestsToFinish();
        ContractPage cp = new ContractPage();
        String ean = parameterProvider.getValueOrParameterAsString(EAN);
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        parameterProvider.put("inputValue", inputValue);
        cp.getElementByEanNewInvoiceAmount(ean, value);
        seleniumDriver.waitForRequestsToFinish();
    }

    @And("Start date in cards is \"([^\"]*)\"$")
    public void setInputByDate(String value) {
        String inputValue = toDwpDate(parameterProvider.getValueOrParameterAsString(value));
        parameterProvider.put("inputValue", inputValue);
        ContractPage cp = new ContractPage();
        cp.getStartDateInAdvanceElektricityCard(inputValue);
        cp.getStartDateInAdvanceGasCard(inputValue);
    }

    @And("EAN in card Electricity is \"([^\"]*)\"$")
    public void setInputByELecEAN(String value) {
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        parameterProvider.put("EAN-code-generated", inputValue);
        ContractPage cp = new ContractPage();
        cp.eanInAdvanceElektricityCard(inputValue);
    }

    @And("EAN in card Gas is \"([^\"]*)\"$")
    public void setInputByGasEAN(String value) {
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        parameterProvider.put("EAN-code-generated", inputValue);
        ContractPage cp = new ContractPage();
        cp.eanInAdvanceGASCard(inputValue);
    }

    @Override
    @After("@DWP or @CORE or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
