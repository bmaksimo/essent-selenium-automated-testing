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
        pageObject.next();
    }

    @And("^Company VAT number is random$")
    public void generateRandomUser(){
        String vat = generateVat("generator:vat:BEL");
        parameterProvider.put("VAT", vat);
    }

    @And("^Company name is random$")
    public void generateRandomCompanyName(){
        String companyName = generateCompanyName();
        parameterProvider.put("company-name", companyName);
    }

    @And("^Company address is$")
    public void initCustomerAddress(final DataTable address){
        CompanyDetailsAddressPage companyDetailsAddressPage = new CompanyDetailsAddressPage();
        List<Map<String,String>> addresses = address.asMaps(String.class, String.class);
        companyDetailsAddressPage.setAddressNewDatatable(addresses);
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
