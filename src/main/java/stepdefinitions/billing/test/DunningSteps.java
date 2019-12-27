package stepdefinitions.billing.test;

import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.be.jbilling.api.rest.batch.RSTriggerDunningRequest;
import com.essent.be.jbilling.api.rest.dunning.DunningStepRequest;
import com.essent.testing.client.billing.BillingBatch;
import com.essent.testing.database.DBUtility;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import java.util.Collections;
import java.util.List;
import org.joda.time.LocalDate;

public class DunningSteps extends DwpScenario {

  public LocalDate dunningStartDate = null;
  protected RestResponse balanceResponse = null;
  private int days_passed;

  @Before("@DWP or @E2E or @REGRESSION or @DUNNING")
  public void setupTest(Scenario scenario) {
    registerActiveScenario(scenario);
  }

  @When("^Dunning day countdown for \"([^\"]*)\" goes down (\\d+) days$")
  public void dunningStepRequest(String accountNumberParam, int count) {

    DunningStepRequest request = new DunningStepRequest();
    String accountNumber = parameterProvider.getValueOrParameterAsString(accountNumberParam);
    request.setAccountUUID(accountNumber);
    request.setCount(count);

    new DunningService().callDunningStep(request);
  }

  @When("^Dunning is advanced for (\\d+) day\\(s\\)$")
  public void i_advance_dunning_for_day_s(int nr_days) throws Throwable {
    RSTriggerDunningRequest request = new RSTriggerDunningRequest();
    String accountNumber = (String) parameterProvider.get("accountNumber");
    List<String> accountIds = Collections.singletonList(accountNumber);
    request.setJobName("DunningDailyStep");
    request.setAccountIds(accountIds);
    request.setDateStrategy("ADVANCE_FOR_VALIDATION");
    BillingBatch service = new BillingBatch();
    LocalDate today = LocalDate.now();
    dunningStartDate = today.plusDays(1);

    for (int ix = 0; ix < nr_days; ix++) {
      logger().info("Dunning on " + dayToDate(days_passed) + " ");
      days_passed++;
      RestResponse response = service.triggerDunning(request);
      if (response == null) {
        throw new AssertionError("Triggering dunning failed: JBilling probably not running");
      } else if (!response.getResult()) {
        throw new AssertionError("Triggering dunning failed: " + response.getMsg());
      }

      // wait for requested dunning job to finish.
      while (service.checkDunningRunning().getResult()) {
        Thread.sleep(100);
      }
    }
  }

  private LocalDate dayToDate(int days) {
    return dunningStartDate.plusDays(days);
  }

  @When("^Customer with CRM Id \"([^\"]*)\" is added to dunning whitelist$")
  public void whitelistDunningCustomer(String suiteCRMCustomer) throws Throwable {
    String customerId = parameterProvider.getValueOrParameterAsString(suiteCRMCustomer);
    DBUtility.enableDunningForCrmId(customerId);
  }
}
