package stepdefinitions.database;

import com.essent.testing.database.DBConnector;
import com.essent.testing.database.Database;
import com.essent.testing.scenario.RegisteredScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import stepdefinitions.dwp.tables.plus.SwitchState;

import java.util.HashMap;
import java.util.Map;

import static com.essent.testing.database.DBUtility.switchSuiteCrmStatusExternal;

public class DatabaseSteps extends RegisteredScenario {

    private Map<String, Integer> numberMap = new HashMap<>();

    @Before("@DWP, @CORE, @E2E, @REGRESSION, @DB-CORE")
    public void setUp(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Then("^External status is \"([^\"]*)\" for SuiteCRM Customer Number \"([^\"]*)\"$")
    public void externalStatusIsForSuiteCRMCustomerNumber(SwitchState state, String suiteCrmCustomer) throws Throwable {
        String inputValue = parameterProvider.getValueOrParameterAsString(suiteCrmCustomer);
        Integer value = numberMap.computeIfAbsent(inputValue, String::length);
        switchSuiteCrmStatusExternal(state, value);
    }

}
