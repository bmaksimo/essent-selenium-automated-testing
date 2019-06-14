package stepdefinitions.dwp.input;

import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry.JS_TR_APPLY_MULTIPLE_FILTER_INPUT;
import static com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry.JS_TR_OPEN_MULTIPLE_INPUT_DIALOG;
import static org.hamcrest.Matchers.is;

public class MultipleInputElements extends DwpScenario {

    @Before("@DWP or @CORE or @E2E  or @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

  private class MultipleInputDialog implements Predicate<Map> {
    @Override
    public boolean test(Map options) {
      return executeJavascriptTest(JS_TR_OPEN_MULTIPLE_INPUT_DIALOG, options);
    }
  }

  private class ApplyMultipleInput implements Predicate<Map> {
    @Override
    public boolean test(Map options) {
      return executeJavascriptTest(JS_TR_APPLY_MULTIPLE_FILTER_INPUT, options);
    }
  }

    @When("^Multiple product input selected is \"([^\"]*)\"$")
    public void setMultipleInput(String product) {

        Map<String, String> options = new HashMap<>();
        options.put("product", product);

        boolean openedDialog = new MultipleInputDialog().test(options);
        assertThat("Dialog could not be opened.",
            openedDialog, is(true));

        boolean success = new ApplyMultipleInput().test(options);
        assertThat(String.format("Product %s is undefined.", product),
            success, is(true));
    }

    @Override
    @After("@DWP or @CORE or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
