package stepdefinitions.dwp.smoke;

import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.joda.time.DateTime;

import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.expandFrom;

public class OutputParameterTest extends DwpScenario {

    @Before
    public void setUp(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @OutputParameter(name="contractor")
    private String contractor;

    @OutputParameter(name="startOfTenure")
    private DateTime startOfTenure;


    @When("^Contractor is ([^\"]*)$")
    public void setContractor(String contractor) throws Throwable {
        logger().info("STEP:");
        logger().info(" - ACTION: SET_OUTPUT_PARAM");
        logger().info(" - NAME: contractor");
        logger().info(" - VALUE: " + contractor);
        this.contractor = contractor;
    }

    @And("^Start of tenure is ([^\"]*)$")
    public void startOfTenureIs(String startOfTenure) throws Throwable {
        logger().info("STEP:");
        logger().info(" - ACTION: SET_OUTPUT_PARAM");
        logger().info(" - NAME: startOfTenure");
        this.startOfTenure = expandFrom(startOfTenure);
        logger().info(" - VALUE: " + this.startOfTenure.toString());
    }
}
