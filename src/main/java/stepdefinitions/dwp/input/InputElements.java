package stepdefinitions.dwp.input;

import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import stepdefinitions.dwp.tables.plus.CheckBoxState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static com.billinghouse.test_automation.util.gherkin.ExpressionUtil.checkAndConvertToDwpDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;



public class InputElements extends DwpScenario {

    @Before("@SMOKE, @QUOTE, @QUOTE_MI, @QUOTE_SS, @RENEWAL, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    private class ApplyInput implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TbrFormInput", options);
        }
    }

    private class ApplyDateInput implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrDatePickerInput", options);
        }
    }

    protected String convertToDwpDate(String formattedDate)  {
        return checkAndConvertToDwpDate(formattedDate);
    }

    @And("^\"([^\"]*)\" input is \"([^\"]*)\"$")
    public void setInput(String label, String value) throws Throwable {
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", value);
        boolean success = new ApplyInput().test(options);
        assertThat(String.format("Filter element %s is undefined.", label),
            success, is(true));
    }

    @And("^\"([^\"]*)\" date is \"([^\"]*)\"$")
    public void setDateInput(String label, String value) throws Throwable {
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", convertToDwpDate(value));
        boolean success = new ApplyDateInput().test(options);
        assertThat(String.format("Filter element %s is undefined.", label),
            success, is(true));
    }

    @And("^\"([^\"]*)\" selection is \"([^\"]*)\"$")
    public void setSelection(String label, String value) throws Throwable {
        setInput(label, String.format("string:%s", value));
    }

    @And("^Option \"([^\"]*)\" is ([^\"]*)$")
    public void switchOption(String option, CheckBoxState state) throws Throwable {
        Map<String, String> options = new HashMap<>();
        options.put("label", option);
        boolean success = executeJavascriptTest("TrClickToggleInput", options);
        assertThat(String.format("Option %s is undefined.", option),
            success, is(true));
    }

    @Override
    @After("@SMOKE, @QUOTE, @QUOTE_MI, @QUOTE_SS, @RENEWAL, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
