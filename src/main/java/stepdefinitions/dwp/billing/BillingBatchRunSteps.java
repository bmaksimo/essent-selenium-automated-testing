package stepdefinitions.dwp.billing;

import com.billinghouse.test_automation.util.dsl.EssentDateTimeFormat;
import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.modal.confirm.ConfirmSignatureDialogImpl;
import com.essent.testing.dwp.pageobject.modal.confirm.ConfirmSignatureDialog;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import stepdefinitions.billing.test.BillingRunResult;
import stepdefinitions.billing.test.BillingService;
import stepdefinitions.billing.test.MediationRunResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BillingBatchRunSteps extends DwpScenario {

    @Autowired
    BillingService billingService;

    @Before("@DWP or @E2E or @API")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @And("^Invoice run is scheduled$")
    public void invoiceRunIsScheduled(){
        boolean success = new ConfirmSignatureDialogImpl().confirm(parameterProvider.getScenarioInfo());
        assertThat("Invoice run dialog was not confirmed.", success, is(true));
    }


    @When("^Billing run \"([^\"]*)\" is triggered with process date \"([^\"]*)\"$")
    public void startBillingRun(String jobName, String processDate) {
        seleniumDriver.waitForRequestsToFinish();

        String inputValue = toDwpApiDate(parameterProvider.getValueOrParameterAsString(processDate));
        String billingId = parameterProvider.getValueOrParameterAsString("parameter:billingId");
        SimpleDateFormat formatter = new SimpleDateFormat(EssentDateTimeFormat.DWP_API_DATE_FORMAT.getFormat());

        boolean success = false;
        int maxRetries = 10;
        int currentAttempt = 0;

        while (!success && currentAttempt <= maxRetries) {
            try {
                BillingRunResult result = billingService.startBillRun(jobName, billingId, formatter.parse(inputValue));
                currentAttempt++;
                success = result.getResult();
                Sleeper.sleepTightInSeconds(60);
            } catch (ParseException e) {
                logger().error("Billing job did not execute successfully");
                success = false;
            }
        }

        if (!success) Assert.fail("Billing was not triggered after retrying 10 times");
    }

    @When("^Mediation run \"([^\"]*)\" is triggered with settlement date \"([^\"]*)\"$")
    public void startMediation(String jobName, String settlementDate) {
        String inputValue = toDwpApiDate(parameterProvider.getValueOrParameterAsString(settlementDate));
        String billingCustomerId = parameterProvider.getValueOrParameterAsString("parameter:billingId");
        String deliveryPointId = parameterProvider.getValueOrParameterAsString("parameter:EAN-code");
        SimpleDateFormat formatter = new SimpleDateFormat(EssentDateTimeFormat.DWP_API_DATE_FORMAT.getFormat());

        try {
            Date parsedDate = formatter.parse(inputValue);
            MediationRunResult mediationRunResult = billingService.runMediationJob(jobName, billingCustomerId, deliveryPointId, parsedDate);

            Assert.assertTrue(mediationRunResult.getMessage(), mediationRunResult.getResult());
        } catch (ParseException e) {
            logger().error("Mediation job did not execute successfully");
        }
    }

    @Override
    @After("@DWP or @E2E")
    public void tearDown() {
        super.tearDown();
    }
}
