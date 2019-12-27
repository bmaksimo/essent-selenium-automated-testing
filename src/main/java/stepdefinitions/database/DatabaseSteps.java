package stepdefinitions.database;

import static com.essent.testing.database.DBUtility.switchSuiteCrmStatusExternal;

import com.essent.testing.scenario.RegisteredScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import stepdefinitions.dwp.tables.plus.SwitchState;

public class DatabaseSteps extends RegisteredScenario {

  @Before("@DWP or @CORE or @E2E or @REGRESSION or @DB-CORE")
  public void setUp(Scenario scenario) {
    registerActiveScenario(scenario);
  }

  @Then("^External status is \"([^\"]*)\" for SuiteCRM Customer Number \"([^\"]*)\"$")
  public void externalStatusIsForSuiteCRMCustomerNumber(SwitchState state, String suiteCrmCustomer)
      throws Throwable {
    String inputValue = parameterProvider.getValueOrParameterAsString(suiteCrmCustomer);
    Integer value = Integer.parseInt(inputValue);
    switchSuiteCrmStatusExternal(state, value);
  }
}
