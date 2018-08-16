package stepdefinitions.dwp.input;

import com.essent.testing.dwp.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static com.billinghouse.test_automation.util.gherkin.ExpressionUtil.checkAndConvertToDwpDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;



public class InputElements extends DwpScenario  {

    @Before("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL, @INVOICE")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    private class ApplyInput implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrApplyFilterInput", options);
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

    @And("^\"([^\"]*)\" date input is \"([^\"]*)\"$")
    public void setDateInput(String label, String value) throws Throwable {
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", convertToDwpDate(value));
        boolean success = new ApplyInput().test(options);
        assertThat(String.format("Filter element %s is undefined.", label),
            success, is(true));
    }

    @And("^\"([^\"]*)\" selection is \"([^\"]*)\"$")
    public void setSelection(String label, String value) throws Throwable {
        setInput(label, String.format("string:%s", value));
    }

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
