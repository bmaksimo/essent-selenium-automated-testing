package stepdefinitions.dwp.billing;

import com.billinghouse.test_automation.util.dsl.DwpDateTimeFormat;
import com.essent.be.jbilling.api.rest.RestResponse;
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
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Invoice run is scheduled$")
    public void invoiceRunIsScheduled() throws Throwable {
        ConfirmSignatureDialog dialog = new ConfirmSignatureDialogImpl();
        boolean success = dialog.confirm();
        assertThat("Invoice run dialog was not confirmed.",
            success,
            is(true));
    }


    @When("^Billing run \"([^\"]*)\" is triggered with process date \"([^\"]*)\"$")
    public void startBillingRun(String jobName, String processDate) {
        String inputValue = toDwpApiDate(parameterProvider.getValueOrParameterAsString(processDate));
        String billingCustomerId = parameterProvider.getValueOrParameterAsString("parameter:Id Billing customer & persoon/familie sleutel");
        SimpleDateFormat formatter = new SimpleDateFormat(DwpDateTimeFormat.DWP_API_DATE_FORMAT.getFormat());
        try {
            Date parsedDate = formatter.parse(inputValue);
            BillingRunResult billingRunResult = billingService.startBillRun(jobName, billingCustomerId, parsedDate);

            Assert.assertTrue(billingRunResult.getMessage(), billingRunResult.getResult());
        } catch (ParseException e) {
            logger().error("Something went wrong with billing run...");
        }
    }

    @When("^Mediation run \"([^\"]*)\" is triggered with settlement date \"([^\"]*)\"$")
    public void startMediation(String jobName, String settlementDate) {
        String inputValue = toDwpApiDate(parameterProvider.getValueOrParameterAsString(settlementDate));
        String billingCustomerId = parameterProvider.getValueOrParameterAsString("parameter:Id Billing customer & persoon/familie sleutel");
        String deliveryPointId = parameterProvider.getValueOrParameterAsString("parameter:EAN-code");
        SimpleDateFormat formatter = new SimpleDateFormat(DwpDateTimeFormat.DWP_API_DATE_FORMAT.getFormat());

        try {
            Date parsedDate = formatter.parse(inputValue);
            MediationRunResult mediationRunResult = billingService.runMediationJob(jobName, billingCustomerId, deliveryPointId, parsedDate);

            Assert.assertTrue(mediationRunResult.getMessage(), mediationRunResult.getResult());
        } catch (ParseException e) {
            logger().error("Something went wrong with mediation run...");
        }
    }

    @Override
    @After("@DWP or @E2E")
    public void tearDown() {
        super.tearDown();
    }
}
