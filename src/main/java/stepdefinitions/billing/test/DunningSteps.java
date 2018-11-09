package stepdefinitions.billing.test;

import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.be.jbilling.api.rest.batch.RSTriggerDunningRequest;
import com.essent.testing.client.billing.BillingBatch;
import com.essent.testing.database.DBUtility;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import org.joda.time.LocalDate;

import java.util.Collections;
import java.util.List;

public class DunningSteps  extends DwpScenario {

    public LocalDate dunningStartDate = null;
    private int days_passed;

    @Before("@DWP, @E2E, @REGRESSION, @DUNNING")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Dunning is advanced for (\\d+) day\\(s\\)$")
    public void i_advance_dunning_for_day_s(int nr_days) throws Throwable {
        ConsumptionSteps consumptionSteps = new ConsumptionSteps();
        consumptionSteps.generateConsumption("NIGHT_EXCLUSIVE", "6");
        RSTriggerDunningRequest request = new RSTriggerDunningRequest();

        List<String> accountIds = Collections.singletonList("150705507");
//        List<String> accountIds = Arrays.asList(new String[] { account_external_id });
        request.setJobName("DunningDailyStep");
        request.setAccountIds(accountIds);
        request.setDateStrategy("ADVANCE_FOR_VALIDATION");
        BillingBatch service = new BillingBatch();
        LocalDate today = LocalDate.now();
        dunningStartDate = today.plusDays(1);

        for (int ix = 0; ix < nr_days; ix++) {
            System.out.println( "Dunning on " + dayToDate(days_passed) + " ");
            days_passed++;
            RestResponse response = service.triggerDunning(request);
            if( response == null ) {
                throw new AssertionError("Triggering dunning failed: JBilling probably not running");
            } else if (!response.getResult()) {
                throw new AssertionError("Triggering dunning failed: " + response.getMsg());
            }

            // wait for requested dunning job to finish.
            while( service.checkDunningRunning().getResult() ) {
                Thread.sleep(100);
            }
        }
    }

    private LocalDate dayToDate(int days) {
        return dunningStartDate.plusDays(days);
    }

    @When("^Dunning customer is \"([^\"]*)\"$")
    public void dunningCustomerIs(String suiteCRMCustomer) throws Throwable {
        String customerId = parameterProvider.getValueOrParameterAsString(suiteCRMCustomer);
        DBUtility.enableDunningForCrmId(customerId);
    }
}
