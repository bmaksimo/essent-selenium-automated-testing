package stepdefinitions.dwp.quote.b2b;

import com.billinghouse.test_automation.util.random.CustomerRandomDataGenerator;
import com.essent.testing.dwp.pageobject.impl.quote.CompanyDetailsAddressPage;
import com.essent.testing.dwp.pageobject.impl.quote.ContactDetailsPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import io.cucumber.datatable.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import stepdefinitions.dwp.page_object.CustomerAcceptance;
import stepdefinitions.dwp.tables.CustomerDetails;

import java.util.List;
import java.util.Map;

public class CompanySteps extends DwpScenario {

    @Before("@DWP or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @And("^Customer acceptance checks page is confirmed$")
    public void confirmCustomerAcceptanceChecksPage() {
        CustomerAcceptance pageObject = new CustomerAcceptance();
        pageObject.next(parameterProvider.getScenarioInfo());
    }

    @And("^Company VAT number is random$")
    public void generateRandomUser(){
        String vat = CustomerRandomDataGenerator.generateVat("generator:vat:BEL");
        parameterProvider.put("VAT", vat);
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
