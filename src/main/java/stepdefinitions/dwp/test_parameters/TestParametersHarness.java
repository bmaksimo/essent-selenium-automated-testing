package stepdefinitions.dwp.test_parameters;

import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import com.essent.testing.scenario.RegisteredScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;

import java.util.HashMap;
import java.util.Map;

public class TestParametersHarness extends RegisteredScenario {

    @Before
    public void setUp(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @OutputParameter(name = "@text-parameters")
    private Map<String, String> textInputParameters = new HashMap<>();

    @OutputParameter(name = "@date-parameters")
    private Map<String, String> dateInputParameters = new HashMap<>();


    @And("^Text parameter \"([^\"]*)\" is \"([^\"]*)\"$")
    public void putTextParameter(String key, String value) throws Throwable {
        logger().info("STEP:");
        logger().info(" - ACTION: PUT_OUTPUT_PARAM");
        logger().info(" - NAME: " + key);
        this.textInputParameters.put(key, value);
        logger().info(" - VALUE: " + textInputParameters.get(key));
    }

}
