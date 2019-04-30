package stepdefinitions.dwp.parameters;

import com.essent.testing.scenario.RegisteredScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.runtime.CucumberException;

import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkAndConvertToDwpDate;

public class ParametersSupport extends RegisteredScenario {

  @Before("@DWP, @CORE, @E2E, @REGRESSION")
  public void setUp(Scenario scenario) {
    registerActiveScenario(scenario);
  }

  @Given("^Parameter \"([^\"]*)\" is \"([^\"]*)\"$")
  public void putParameter(String key, String value) throws Throwable {
    String inputValue = value;
    try {
      inputValue = checkAndConvertToDwpDate(value);
    } catch (CucumberException e) {
      // Consume the exception
    }
    parameterProvider.put(key, inputValue);
  }
}
