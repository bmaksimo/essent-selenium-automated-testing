package stepdefinitions.dwp.page_object;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.navigation.NavigationElements;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PaymentInfoSteps extends NavigationElements {

    private class PaymentDetailsModalSaveAction implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrPaymentDetailsModalSaveAction", options);
        }
    }

    private class PaymentMethodSwitch implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            Map result = executeJavascriptMethod("TrSwitchPaymentMethod", options);
            String status = ((String) result.get("status"));
            boolean success = StringUtils.equals("PASSED", status);
            if (success) {
                String switchedPaymentMethod = ((String) result.get("paymentMethod")).equalsIgnoreCase("string:OV") ?
                    "Overschrijving" : "Domiciliëring";
                parameterProvider.put("paymentMethod", switchedPaymentMethod);
            }

            return success;
        }
    }

    private class PaymentDetailsIBANChange implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrAddIBANToPaymentDetails", options);
        }
    }

    @Before("@DWP, @CORE, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Payment details are confirmed$")
    public void clickSaveOnPaymentDetailsModal() {
        boolean success = new PaymentDetailsModalSaveAction().test(null);
        assertThat("Billing customer update has failed.", success, is(true));
    }

    @When("^Payment method is switched$")
    public void switchPaymentMethod() {
        boolean success = new PaymentMethodSwitch().test(new HashMap<>());
        assertThat("Payment method has not been switched", success, is(true));
    }

    @And("^IBAN is \"([^\"]*)\" if not empty$")
    public void changeIBAN(String iban) {
        Map<String, String> options = new HashMap<>();
        options.put("iban", iban);
        boolean success = new PaymentDetailsIBANChange().test(options);
        assertThat("IBAN has failed to be updated", success, is(true));
    }

    @And("^Payment method is updated$")
    public void listSwitchedPaymentMethod() throws Throwable {
        String updatedPaymentMethodName = parameterProvider.getValueOrParameterAsString("parameter:paymentMethod");
        final String UPDATED_PAYMENT_METHOD = "//list-simple-two-liner-cell[contains(@line-2,'" + updatedPaymentMethodName + "')]";
        WebElement element = seleniumDriver.findElementWhenPresent(By.xpath(UPDATED_PAYMENT_METHOD));
        assertThat(String.format("View list did not contain payment method %s", updatedPaymentMethodName),
            element, is(notNullValue()));
    }

    @Override
    @After("@DWP, @CORE, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
