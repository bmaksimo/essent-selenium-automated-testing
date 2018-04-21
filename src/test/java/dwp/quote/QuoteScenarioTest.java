package dwp.quote;

import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import com.essent.testing.dwp.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.joda.time.DateTime;

public class QuoteScenarioTest extends DwpScenario {

    @Before
    public void setUp(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @OutputParameter(name="contractor")
    private String contractor;

    @OutputParameter(name="dateTime")
    private DateTime timestamp;

    @Given("^I execute the scenario step that has different annotated output parameters$")
    public void i_execute_the_scenario_step_that_has_different_annotated_output_parameters() {
        contractor = "Sjaak van Vliet";
        timestamp = new DateTime();
        logger().info("I start the unittest scenario");
    }
}
