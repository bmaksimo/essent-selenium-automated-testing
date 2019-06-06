package stepdefinitions.dwp.quote.b2b;

import com.billinghouse.random.RandomUser;
import com.essent.testing.dwp.pageobject.impl.quote.CompanyDetailsAddressPage;
import com.essent.testing.dwp.pageobject.impl.quote.ContactDetailsPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import io.cucumber.datatable.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import stepdefinitions.dwp.page_object.CustomerAcceptance;
import stepdefinitions.dwp.tables.CustomerAddress;

import java.util.List;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CompanySteps extends DwpScenario {

    private class InitialiseCompanyAddress implements Predicate<CustomerAddress> {
        @Override
        public boolean test(CustomerAddress customerAddress) {
            return setAddress(customerAddress);
        }

        private boolean setAddress(CustomerAddress customerAddress) {
            CompanyDetailsAddressPage companyAddressView = new CompanyDetailsAddressPage();
            companyAddressView.setAddress(customerAddress);
            return companyAddressView.setAddress();
        }
    }

    @Before("@DWP or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Customer acceptance checks page is confirmed$")
    public void confirmCustomerAcceptanceChecksPage() {
        CustomerAcceptance pageObject = new CustomerAcceptance();
        pageObject.next();
    }

    @And("^Company VAT number is random$")
    public void generateRandomUser() throws Throwable {
        String vat = generateVat("generator:vat:BEL");
        parameterProvider.put("VAT", vat);
    }

    @And("^Company name is random$")
    public void generateRandomCompanyName() throws Throwable {
        String companyName = generateCompanyName();
        parameterProvider.put("company-name", companyName);
    }

    @And("^Company address is$")
    public void initCustomerAddress(final DataTable address) throws Throwable {
        List<CustomerAddress> list = address.asList(CustomerAddress.class);
        CustomerAddress customerAddress = list.get(0);
        boolean success = new InitialiseCompanyAddress().test(customerAddress);
        assertThat("Company Address data wasn't initialised.", success, is(true));
    }


    @And("^Company contact info is generated$")
    public void generateContactInfo() {
        RandomUser genetaredUser = (RandomUser)parameterProvider.getValueOrParameter("parameter:suitecrm-company-account");
        ContactDetailsPage contactDetailsPage = new ContactDetailsPage();
        contactDetailsPage.setRandomUser(genetaredUser);
        contactDetailsPage.fillInFormData();
    }

    @Override
    @After("@DWP or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }


}
