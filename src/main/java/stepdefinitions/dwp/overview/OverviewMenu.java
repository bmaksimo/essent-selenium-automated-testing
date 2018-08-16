package stepdefinitions.dwp.overview;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.navigation.NavigationElements;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class OverviewMenu extends NavigationElements {

    private class ClickOverviewMenu implements Predicate<String> {
        @Override
        public boolean test(String menu) {
            int sec = 5;
            Map<String, Object> options = new HashMap<>();
            options.put("menu", menu);
            return executeJavascriptTest("TrClickOverviewMenuButton", options);
        }
    }

    @Before("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL, @INVOICE")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Overview is ([^\"]*)")
    public void check_overview_menu_item(String menuItem) throws Throwable {
        boolean success = new ClickOverviewMenu().test(menuItem);
        assertThat(String.format("Overview Menu  %s is undefined.", menuItem),
            success, is(true));
    }

    @Then("^Advanced Invoice is available")
    public void checkInvoiceAttributes() {
        By invoiceNumberSelector = By.id("number-field");
        WebElement invoiceNumber = webDriver.findElementOrNull(invoiceNumberSelector);
        assertThat("Invoice number is undefined", invoiceNumber.getText().isEmpty(), is(false));

        By invoiceStatusSelector = By.id("status-field");
        WebElement invoiceStatus = webDriver.findElementOrNull(invoiceStatusSelector);
        assertThat("Invoice status is undefined", invoiceStatus.getText().isEmpty(), is(false));
        if (!invoiceStatus.getText().isEmpty())
            assertThat("Invalid Invoice status: should be ISSUED. Status found: " + invoiceStatus.getText(),
                invoiceStatus.getText(), is("ISSUED"));

        By invoiceTotalAmountSelector = By.id("total-amount-field");
        WebElement invoiceTotalAmount = webDriver.findElementOrNull(invoiceTotalAmountSelector);
        assertThat("Invoice total amount is undefined", invoiceTotalAmount.getText().isEmpty(), is(false));
        if (!invoiceTotalAmount.getText().isEmpty()) {
            String totalAmount = invoiceTotalAmount.getText().substring(0, invoiceTotalAmount.getText().length() - 2);
            assertThat("Invoice total amount is ZERO. Should be bigger than ZERO.",
                Long.parseLong(totalAmount), greaterThan(0L));
        }

        By invoicePaymentStatusSelector = By.id("payment-status-field");
        WebElement invoicePaymentStatus = webDriver.findElementOrNull(invoicePaymentStatusSelector);
        assertThat("Invoice status is undefined", invoicePaymentStatus.getText().isEmpty(), is(false));
        if (!invoicePaymentStatus.getText().isEmpty())
            assertThat("Invalid Invoice status: should be NOT_PAID. Status found: " + invoicePaymentStatus.getText(),
                invoicePaymentStatus.getText(), is("NOT_PAID"));

        By invoicePaymentAmountSelector = By.id("payment-amount-field");
        WebElement invoicePaymentAmount = webDriver.findElementOrNull(invoicePaymentAmountSelector);
        assertThat("Invoice total amount is undefined", invoicePaymentAmount.getText().isEmpty(), is(false));
        if (!invoicePaymentAmount.getText().isEmpty()) {
            String paymentAmount = invoicePaymentAmount.getText().substring(0, invoicePaymentAmount.getText().length() - 2);
            assertThat("Invoice payment amount is not ZERO. Should be ZERO.",
                Long.parseLong(paymentAmount), is(0L));
        }

    }

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL, @INVOICE")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
