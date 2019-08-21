package stepdefinitions.dwp.smoke;

import com.billinghouse.cucumber.runtime.annotations.InputParameter;
import com.essent.testing.scenario.RegisteredScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class InputParametersTest extends RegisteredScenario {

    @Before
    public void setUp(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @InputParameter(name = "contractor")
    private String employee;

    @InputParameter(name = "startOfTenure")
    private DateTime startOfTenure;

    @Then("^Period of tenure is printed.?$")
    public void printContractorTenureDate(){
        assertThat("'employee' was null", employee, is(notNullValue()));
        assertThat("'startOfTenure' was null", startOfTenure, is(notNullValue()));
        assertThat("'startOfTenure' was null", parameterProvider.getValueOrParameterAsString("parameter:startOfTenure"), is(notNullValue()));
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("DD-MM-YYYY");
        logger().debug("STEP:");
        logger().debug(" - ACTION: GET_INPUT_PARAM");
        Period diff = new Period(startOfTenure, DateTime.now());
        logger().debug(String.format(" - RESULT: %s's period of tenure is %s years", employee, diff.getYears()));
    }

    @And("^Contractor \"([^\"]*)\" has value \"([^\"]*)\"$")
    public void contractorHasValue(String parameter, String expected){
        assertThat(parameterProvider.getValueOrParameterAsString(parameter), is(expected));
    }

}
