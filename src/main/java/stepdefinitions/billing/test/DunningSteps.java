package stepdefinitions.billing.test;

import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.be.jbilling.api.rest.batch.RSTriggerDunningRequest;
import com.essent.be.jbilling.api.rest.dunning.DunningStepRequest;
import com.essent.be.jbilling.api.rest.invoice.RSChangeInvoiceBalanceRequest;
import com.essent.testing.client.billing.BillingBatch;
import com.essent.testing.client.billing.BillingInvoiceRest;
import com.essent.testing.database.DBUtility;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class DunningSteps extends DwpScenario {

    private static final Logger LOG = LoggerFactory.getLogger(DunningSteps.class);

    public LocalDate dunningStartDate = null;
    protected RestResponse balanceResponse = null;
    private int days_passed;

    @Before("@SMOKE, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @BILLING, @B2B_REGRESSION, @DUNNING")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Dunning day countdown for \"([^\"]*)\" goes down (\\d+) days$")
    public void dunningStepRequest(String accountNumberParam, int count) throws Throwable {
        DunningStepRequest request = new DunningStepRequest();
        String accountNumber = parameterProvider.getValueOrParameterAsString(accountNumberParam);
        request.setAccountUUID(accountNumber);
        request.setCount(count);

        List dunningSteps = (List) new DunningService().callDunningStep(request);
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

    @When("I change the balance of invoice with public number \"([^\"]*)\" to \"([^\"]*)\":?")
    public void i_change_the_balance_of_invoice_to(String invoiceNumber, String newBalance) {
        RSChangeInvoiceBalanceRequest request = new RSChangeInvoiceBalanceRequest();
        request.setInvoiceNumber(invoiceNumber);
        request.setNewBalance(new BigDecimal(newBalance));
        BillingInvoiceRest service = new BillingInvoiceRest();
        balanceResponse = service.changeInvoiceBalance(request);
    }

    @When("^I adjust invoice with number \"([^\"]*)\" for due on day (\\d+)$")
    public void i_adjust_invoice_for_due_on_day(String invoiceNr, int dueDay) throws Throwable {
        LocalDate dueDate = dayToDate(dueDay);
        LocalDate creationDate = dayToDate(dueDay - 10);
        DBUtility.setInvoiceDates(DBUtility.getInvoiceId(invoiceNr), creationDate, dueDate);
    }
}
