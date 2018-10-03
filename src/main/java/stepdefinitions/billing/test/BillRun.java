package stepdefinitions.billing.test;

import com.essent.be.billing.invoice.BillingInvoice;
import com.essent.be.billing.invoice.BillingInvoiceOrder;
import com.essent.be.billing.invoice.BillingInvoiceOrderLine;
import com.essent.be.jbilling.api.rest.invoice.RSGetInvoicesForBillingIDs;
import com.essent.be.jbilling.api.rest.invoice.RSInvoicesForBillingIDsResp;
import com.essent.testing.client.billing.BillingInvoiceRest;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import stepdefinitions.billing.generic.BillingScenario;
import stepdefinitions.billing.test.tables.PeriodTable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class BillRun extends BillingScenario {

	private List<BillingInvoice> billingInvoiceCache = new ArrayList<>();

	@Before("@SMOKE, @QUOTE, @QUOTE_MI, @QUOTE_SS, @DROP, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }


	@Then("^I expect an invoice for \"([^\"]*)\" after date \"([^\"]*)\" with periods:$")
	public void i_expect_an_invoice_for_after_date_with_periods(String billingId, String invoiceDate, DataTable periods)
	        throws Throwable {
		List<PeriodTable> expectedResult = null;
		expectedResult = periods.asList(PeriodTable.class);
		// get the invoice

		RSGetInvoicesForBillingIDs request = new RSGetInvoicesForBillingIDs();
		DateTime startDate = new DateTime(invoiceDate);
		request.setStartDate(startDate.toDate());
		request.setEndDate(new DateTime("2999-01-01").toDate());

		request.getBillingIDs().add(billingId);

		BillingInvoiceRest bInvoiceRest = new BillingInvoiceRest();

		RSInvoicesForBillingIDsResp invoices = bInvoiceRest.getInvoicesForBillingIds(request);

        Assert.assertEquals("Number of invoice is not correct", 1, invoices.getBillingInvoices().size());

		BillingInvoice billingInvoice = invoices.getBillingInvoices().get(0);

		List<PeriodTable> foundResult = new ArrayList<PeriodTable>();

		for (Entry<Integer, BillingInvoiceOrder> entry : billingInvoice.getInvoicedOrders().entrySet()) {

			if (!"ADVANCE".equals(entry.getValue().getOrderType())) {
				// we are looking at advanced lines
				continue;
			}

			System.out.println(entry.getValue().getOrderType());

			for (BillingInvoiceOrderLine bline : entry.getValue().getLines()) {

				boolean inList = false;
				for (PeriodTable founded : foundResult) {

					if (DateUtils.isSameDay(bline.getPeriodStart(), founded.getStartDateAsDate())
					        && DateUtils.isSameDay(bline.getPeriodEnd(), founded.getEndDateAsDate())) {
						inList = true;
						break;
					}

				}

				if (!inList) {
					PeriodTable element = new PeriodTable();

					element.setStartDateAsDate(bline.getPeriodStart());
					element.setEndDateAsDate(bline.getPeriodEnd());
					foundResult.add(element);
				}

			}

		}

		Assert.assertEquals(expectedResult.size(), foundResult.size());
		// now check the periods itself

		for (PeriodTable expected : expectedResult) {
			boolean found = false;
			for (PeriodTable founded : foundResult) {
				if (DateUtils.isSameDay(founded.getStartDateAsDate(), expected.getStartDateAsDate())
				        && DateUtils.isSameDay(founded.getEndDateAsDate(), expected.getEndDateAsDate())) {
					found = true;
					break;
				}
			}
			Assert.assertTrue("No period found for :" + expected.getStartDate() + " " + expected.getEndDate(), found);

		}

	}

	@Then("^I expect an invoice for \"([^\"]*)\" after date \"([^\"]*)\" for an amount of \"([^\"]*)\"$")
	public void i_expect_an_invoice_for_after_date_for_an_amount_of(String billingId, String invoiceDate, String amount)
	        throws Throwable {
		RSGetInvoicesForBillingIDs request = new RSGetInvoicesForBillingIDs();
		DateTime startDate = new DateTime(invoiceDate);
		request.setStartDate(startDate.toDate());
		request.setEndDate(new DateTime("2999-01-01").toDate());

		request.getBillingIDs().add(billingId);

		BillingInvoiceRest bInvoiceRest = new BillingInvoiceRest();

		RSInvoicesForBillingIDsResp invoices = bInvoiceRest.getInvoicesForBillingIds(request);

		Assert.assertEquals(1, invoices.getBillingInvoices().size());

		BillingInvoice billingInvoice = invoices.getBillingInvoices().get(0);

		Assert.assertEquals(new BigDecimal(amount).setScale(2), billingInvoice.getAmount().setScale(2));
	}

	@Then("^I expect an invoice for \"([^\"]*)\" after date \"([^\"]*)\" with advance amount change \"([^\"]*)\"$")
	public void i_expect_an_invoice_for_after_date_with_advance_amount_change(String billingId, String invoiceDate,
	        String advanceChanged) throws Throwable {

		RSGetInvoicesForBillingIDs request = new RSGetInvoicesForBillingIDs();
		DateTime startDate = new DateTime(invoiceDate);
		request.setStartDate(startDate.toDate());
		request.setEndDate(new DateTime("2999-01-01").toDate());

		request.getBillingIDs().add(billingId);

		BillingInvoiceRest bInvoiceRest = new BillingInvoiceRest();

		RSInvoicesForBillingIDsResp invoices = bInvoiceRest.getInvoicesForBillingIds(request);

		Assert.assertEquals(1, invoices.getBillingInvoices().size());

		BillingInvoice billingInvoice = invoices.getBillingInvoices().get(0);

		BillingInvoiceOrder order = null;
		for (Entry<Integer, BillingInvoiceOrder> entry : billingInvoice.getInvoicedOrders().entrySet()) {
			order = entry.getValue();
			break;
		}

		Assert.assertEquals("unexpecetd value for advance changed ", Boolean.valueOf(advanceChanged),
		        order.getAdvanceAmountChanged());
	}

	@Then("^I expect no invoice for \"([^\"]*)\" after date \"([^\"]*)\"$")
	public void i_expect_no_invoice_for_after_date(String billingId, String invoiceDate) throws Throwable {
		RSGetInvoicesForBillingIDs request = new RSGetInvoicesForBillingIDs();
		DateTime startDate = new DateTime(invoiceDate);
		request.setStartDate(startDate.toDate());
		request.setEndDate(new DateTime("2999-01-01").toDate());

		request.getBillingIDs().add(billingId);

		BillingInvoiceRest bInvoiceRest = new BillingInvoiceRest();

		RSInvoicesForBillingIDsResp invoices = bInvoiceRest.getInvoicesForBillingIds(request);

		Assert.assertEquals(0, invoices.getBillingInvoices().size());
	}

	@Then("^I expect billing user \"([^\"]*)\" to have (\\d+) invoices? after date \"([^\"]*)\"$")
	public void i_expect_billing_user_to_have_invoices_after_date(String billingId, int number, String invoiceDate)
	        throws Throwable {
		RSGetInvoicesForBillingIDs request = new RSGetInvoicesForBillingIDs();
		DateTime startDate = new DateTime(invoiceDate);
		request.setStartDate(startDate.toDate());
		request.setEndDate(new DateTime("2999-01-01").toDate());

		request.getBillingIDs().add(billingId);

		BillingInvoiceRest bInvoiceRest = new BillingInvoiceRest();

		RSInvoicesForBillingIDsResp invoices = bInvoiceRest.getInvoicesForBillingIds(request);

		Assert.assertEquals(number, invoices.getBillingInvoices().size());
		Assert.assertNotNull(invoices.getBillingInvoices().get(0).getPaymentReference());
		billingInvoiceCache.add(invoices.getBillingInvoices().get(0));
	}

	@When("^I clear the billing invoice cache$")
	public void i_clear_the_billing_invoice_cache() {
		billingInvoiceCache.clear();
	}

	@Then("^I expect an entry in the billing invoice cache with payment reference \"([^\"]*)\"$")
	public void i_expect_an_entry_in_the_billing_invoice_cache_with_payment_reference(String reference) {
		Assert.assertEquals(1, billingInvoiceCache //
				.stream()
				.filter(it -> reference.equals(it.getPaymentReference()) )
				.count());
	}

    @Then("^I expect an invoice for \"([^\"]*)\" after date \"([^\"]*)\" with reactive energy \"([^\"]*)\"$")
    public void i_expect_an_invoice_for_after_date_with_reactive_energy(String billingId, String invoiceDate,
            String amount) throws Throwable {
        RSGetInvoicesForBillingIDs request = new RSGetInvoicesForBillingIDs();
        DateTime startDate = new DateTime(invoiceDate);
        request.setStartDate(startDate.toDate());
        request.setEndDate(new DateTime("2999-01-01").toDate());

        request.getBillingIDs().add(billingId);

        BillingInvoiceRest bInvoiceRest = new BillingInvoiceRest();

        RSInvoicesForBillingIDsResp invoices = bInvoiceRest.getInvoicesForBillingIds(request);

        Assert.assertEquals(1, invoices.getBillingInvoices().size());

        BillingInvoice billingInvoice = invoices.getBillingInvoices().get(0);

        for (Entry<Integer, BillingInvoiceOrder> entry : billingInvoice.getInvoicedOrders().entrySet()) {
            Assert.assertEquals(new BigDecimal(amount).setScale(2), entry.getValue().getReactiveEnergy().setScale(2));
        }

    }

    @Then("^I expect an invoice for \"([^\"]*)\" after date \"([^\"]*)\" with kWmax  \"([^\"]*)\"$")
    public void i_expect_an_invoice_for_after_date_with_kWmax(String billingId, String invoiceDate, String amount)
            throws Throwable {
        RSGetInvoicesForBillingIDs request = new RSGetInvoicesForBillingIDs();
        DateTime startDate = new DateTime(invoiceDate);
        request.setStartDate(startDate.toDate());
        request.setEndDate(new DateTime("2999-01-01").toDate());

        request.getBillingIDs().add(billingId);

        BillingInvoiceRest bInvoiceRest = new BillingInvoiceRest();

        RSInvoicesForBillingIDsResp invoices = bInvoiceRest.getInvoicesForBillingIds(request);

        Assert.assertEquals(1, invoices.getBillingInvoices().size());

        BillingInvoice billingInvoice = invoices.getBillingInvoices().get(0);

        for (Entry<Integer, BillingInvoiceOrder> entry : billingInvoice.getInvoicedOrders().entrySet()) {
            Assert.assertEquals(new BigDecimal(amount).setScale(2), entry.getValue().getkWmax().setScale(2));
        }

    }

}
