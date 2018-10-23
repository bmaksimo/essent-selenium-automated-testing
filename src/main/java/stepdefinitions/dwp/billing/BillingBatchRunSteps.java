package stepdefinitions.dwp.billing;

import com.essent.testing.database.DBUtility;
import com.essent.testing.dwp.pageobject.impl.modal.confirm.ConfirmSignatureDialogImpl;
import com.essent.testing.dwp.pageobject.modal.confirm.ConfirmSignatureDialog;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.jcraft.jsch.JSchException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.apache.commons.lang3.StringUtils;
import stepdefinitions.billing.test.BatchSteps;
import stepdefinitions.billing.test.DunningSteps;
import stepdefinitions.transformers.DateMapper;

import java.sql.SQLException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BillingBatchRunSteps extends DwpScenario {

    private BatchSteps batchSteps;
    private DunningSteps dunningSteps;

    @Before("@DWP, @E2E, @BUSINESS-DESK, @BILLING")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
        batchSteps = new BatchSteps();
        dunningSteps = new DunningSteps();
    }

    @And("^Invoice run is scheduled$")
    public void invoiceRunIsScheduled() throws Throwable {
        ConfirmSignatureDialog dialog = new ConfirmSignatureDialogImpl(webDriver);
        boolean success = dialog.confirm();
        assertThat("Invoice rin dialog was not confirmed.",
            success,
            is(true));
    }

    @Given("^User account ([^\"]*) is billed with a total of ([^\"]*) and with days until due ([^\"]*)$")
    public void billUserAccount(String accountNumber, String invoiceTotal, String daysUntilDue) {
        String accountNumberToBill = parameterProvider.getValueOrParameterAsString(accountNumber);
        String billingCustomerId = getBillingCustomerId(accountNumberToBill);

        final String endDate = "2018-02-01";

        try {
            batchSteps.execute_mediation_job_for_settlement_date("Metered", new DateMapper().transform(endDate));
            batchSteps.start_bill_run_for_billingUser_and_process_date("ONETIME", billingCustomerId, new DateMapper().transform(endDate));

            String maxPublicNumber = DBUtility.selectValueOfLatestRecordInTable("invoice", "public_number");

            dunningSteps.i_change_the_balance_of_invoice_to(maxPublicNumber, invoiceTotal);
            dunningSteps.i_adjust_invoice_for_due_on_day(maxPublicNumber, Integer.parseInt(daysUntilDue));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private String getBillingCustomerId(String accountNumber) {
        try {
            long billingCustomerId = DBUtility.getBillingCustomerIdByUserId(accountNumber);
            return String.valueOf(billingCustomerId);
        } catch (SQLException | JSchException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String requireProperty(Map<String, String> props, String key) {
        String value = StringUtils.trimToNull(props.get(key));
        if (value == null) {
            throw new IllegalArgumentException("undefined property: '" + key + "'");
        }
        return value;
    }

    @Override
    @After("@DWP, @E2E")
    public void tearDown() {
        super.tearDown();
    }
}
