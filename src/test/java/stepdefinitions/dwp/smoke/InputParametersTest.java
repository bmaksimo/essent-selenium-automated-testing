package stepdefinitions.dwp.smoke;

import com.billinghouse.cucumber.runtime.annotations.InputParameter;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import stepdefinitions.dwp.TestData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class InputParametersTest extends DwpScenario {


    @Before
    public void setUp(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @InputParameter(name="contractor")
    private String employee;

    @InputParameter(name = "startOfTenure")
    private DateTime startOfTenure;


    @Then("^Print period of tenure$")
    public void printContractorTenureDate() throws Throwable {
        assertThat("'employee' was null", employee, is(notNullValue()));
        assertThat("'startOfTenure' was null", startOfTenure, is(notNullValue()));
        assertThat("world", world.getBillingCustomer(), is(notNullValue()));
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("DD-MM-YYYY");
        logger().info("STEP:");
        logger().info(" - ACTION: GET_INPUT_PARAM");
        Period diff = new Period(startOfTenure, DateTime.now());
        logger().info(String.format(" - RESULT: %s's period of tenure is %s years", employee, diff.getYears()));

    }
}
