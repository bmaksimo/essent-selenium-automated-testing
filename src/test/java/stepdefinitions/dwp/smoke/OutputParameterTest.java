package stepdefinitions.dwp.smoke;

import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import com.essent.testing.scenario.RegisteredScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.joda.time.DateTime;
import org.junit.Assert;

import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.expandFrom;

public class OutputParameterTest extends RegisteredScenario {

  @Before
  public void setUp(Scenario scenario) {
    registerActiveScenario(scenario);
  }

  @OutputParameter(name = "contractor")
  private String contractor;

  @OutputParameter(name = "startOfTenure")
  private DateTime startOfTenure;

  @When("^Contractor is ([^\"]*)$")
  public void setContractor(String contractor) throws Throwable {
    logger().info("STEP:");
    logger().info(" - ACTION: SET_OUTPUT_PARAM");
    logger().info(" - NAME: contractor");
    logger().info(" - VALUE: " + contractor);
    this.contractor = contractor;
  }

  @When("^Contractor \"?([^\"]*)\"? is put as \"?([^\"]*)\"?$")
  public void putContractor(String contractor, String parameterName) throws Throwable {
    setContractor(contractor);
    parameterProvider.put(parameterName, contractor);
  }

  @And("^Start of tenure is \"?([^\"]*)\"?$")
  public void startOfTenureIs(String startOfTenure) throws Throwable {
    logger().info("STEP:");
    logger().info(" - ACTION: SET_OUTPUT_PARAM");
    logger().info(" - NAME: startOfTenure");
    this.startOfTenure = expandFrom(startOfTenure);
    logger().info(" - VALUE: " + this.startOfTenure.toString());
  }

  @And("^Start of tenure \"?([^\"]*)\"? is put as \"?([^\"]*)\"?$")
  public void putStartOfTenure(String value, String parameterName) throws Throwable {
    startOfTenureIs(value);
    parameterProvider.put(parameterName, startOfTenure);
  }

  @And("^Fail$")
  public void fail() throws Throwable {
    Assert.fail("Checkpoint failure.");
  }
}
