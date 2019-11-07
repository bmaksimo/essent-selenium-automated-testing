package stepdefinitions.dwp.quote.b2b;

import com.billinghouse.test_automation.util.random.CustomerRandomDataGenerator;
import com.essent.testing.dwp.pageobject.elements.NonEditable;
import com.essent.testing.dwp.pageobject.impl.elements.NonEditableImpl;
import com.essent.testing.dwp.pageobject.impl.quote.CompanyDetailsAddressPage;
import com.essent.testing.dwp.pageobject.impl.quote.ContactDetailsPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import io.cucumber.datatable.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import org.apache.commons.lang3.StringUtils;
import stepdefinitions.dwp.page_object.CustomerAcceptance;
import stepdefinitions.dwp.tables.CustomerDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry.JS_BASE_FORM_INPUT;

public class CompanySteps extends DwpScenario {

    @Before("@DWP or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @And("^Customer acceptance checks page is confirmed$")
    public void confirmCustomerAcceptanceChecksPage() {
        new CustomerAcceptance().next(parameterProvider.getScenarioInfo());
    }

    @And("^Company VAT number is random$")
    public void generateRandomUser(){
        parameterProvider.put("VAT", CustomerRandomDataGenerator.generateVat("generator:vat:BEL"));
    }

    @And("Generated company VAT number has ACCEPTED status")
    public void createValidVAT() {
        int maxAttempts = 10;
        int currentAttempt = 0;
        boolean accepted = false;
        String currentVAT = "";
        String vatNumberLabel = "Ondernemingsnummer";


        while (!accepted && currentAttempt < maxAttempts) {
            currentVAT = CustomerRandomDataGenerator.generateVat("generator:vat:BEL");

            seleniumDriver.waitForRequestsToFinish();
            parameterProvider.put("inputValue", currentVAT);
            Map<String, String> options = new HashMap<>();
            options.put("label", vatNumberLabel);
            options.put("value", currentVAT);

            executeJavascriptTest(JS_BASE_FORM_INPUT, options);
            seleniumDriver.waitForRequestsToFinish();
            accepted = isCompanyAccepted();
            logger().debug("Accepted? " + accepted);
            currentAttempt++;
        }
    }

    private boolean isCompanyAccepted() {
        String accepted = "Geaccepteerd";
        String currentStatus = new NonEditableImpl().getValue("Perform customer acceptance check", "Klantacceptatie");
        logger().debug("Company VAT status found: " + currentStatus);
        return StringUtils.equalsIgnoreCase(currentStatus, accepted);
    }

    @And("^Company name is random$")
    public void generateRandomCompanyName(){
        seleniumDriver.waitForRequestsToFinish();
        parameterProvider.put("company-name", generateCompanyName());
    }

    @And("^Company address is$")
    public void initCustomerAddress(final DataTable address){
        List<Map<String,String>> addresses = address.asMaps(String.class, String.class);
        new CompanyDetailsAddressPage().setAddressNewDatatable(addresses);
    }


    @And("^Company contact info is generated$")
    public void generateContactInfo() {
        CustomerDetails customerDetails = (CustomerDetails) parameterProvider.getValueOrParameter("parameter:suitecrm-company-account");
        new ContactDetailsPage(customerDetails).fillInFormData();
    }

    @Override
    @After("@DWP or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }


    private String generateCompanyName() {
        CustomerDetails customer = new CustomerDetails();
        Map<String, String> customerName = CustomerRandomDataGenerator.createCompanyAccountName();
        String firstName = customerName.get("firstName");
        String lastName = customerName.get("lastName");
        String email = firstName + "." + lastName + "@company.com";
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        parameterProvider.put("suitecrm-company-account", customer);
        parameterProvider.put("contact-person-first-name", customer.getFirstName());
        parameterProvider.put("contact-person-last-name", customer.getLastName());
        return customer.getFirstName() + " " + customer.getLastName();
    }
}
