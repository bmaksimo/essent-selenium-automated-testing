package stepdefinitions.dwp.quote;

import com.billinghouse.cucumber.runtime.annotations.InputParameter;
import com.essent.testing.dwp.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.joda.time.DateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class AssignOutputToInputParametersTest extends DwpScenario {

    @Before
    public void setUp(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @InputParameter(name="contractor")
    private String employee;

    @InputParameter(name = "dateTime")
    private DateTime dateTime;


    @Given("^I execute next scenario step and see input parameters initialized with output parameters from previous steps$")
    public void i_execute_next_scenario_step_and_see_input_parameters_initialized_with_output_parameters_from_previous_steps() {
        logger().info("I execute a step with annotated output parameter");
        assertThat("'employee' field was not initialised", employee, is(notNullValue()));
        assertThat("'datetime' field was not initialised", dateTime, is(notNullValue()));
    }
}
