package stepdefinitions.dwp.parameters;

import com.essent.testing.scenario.RegisteredScenario;
import com.essent.testing.vocabulary.DwpEntity;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.runtime.CucumberException;

import java.util.Optional;


import static com.billinghouse.MatcherAssert.assertThat;
import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkAndConvertToDwpDate;
import static org.hamcrest.Matchers.is;

public class ParametersSupport extends RegisteredScenario {

  @Before("@DWP, @CORE, @B2C, @E2E, @REGRESSION")
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

  @And("^([^\"]*) is extracted as \"([^\"]*)\" word from \"([^\"]*)\"$")
  public void extractPackageName(DwpEntity dwpEntity, String ordinal, String packageAndProduct)
      throws Throwable {
    int index = asArrayIndex(ordinal);
    Optional<String> value = parameterProvider.getParameterAsString(packageAndProduct);
    assertThat(
        String.format("Parameter \"%s\" is unknown", packageAndProduct),
        value.isPresent(),
        is(true));
    String[] words = value.get().split("\\s");
    parameterProvider.put(dwpEntity.name(), words[index]);
  }
}
