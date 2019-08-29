package stepdefinitions.odoo.page;

import com.essent.testing.odoo.pageobject.impl.pageObject.CustomerPage;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.is;

public class CustomerPageSteps extends OdooScenario {

    @Before("@ODOO or @B2B or @REGRESSION")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @Override
    @After("@ODOO or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @And("^Odoo click on account number$")
    public void odooClickOnAccountNumber(){
        CustomerPage cp = new CustomerPage();
        cp.clickOnBankAccountElement();
    }

    @Then("^Odoo check if bank account checkbox 'active' is checked$")
    public void odooBankAccountCheckboxActiveIsChecked(){
        CustomerPage cp = new CustomerPage();
        Assert.assertTrue("Active checkbox is not checked",cp.isActiveCheckboxElementChecked());
    }

    @And("^Odoo check if format is \"([^\"]*)\"$")
    public void odooFormatIs(String format){
        CustomerPage cp = new CustomerPage();
        Assert.assertThat("Format is not as expected", cp.getDirectDebitFormat().equalsIgnoreCase(format), is(true));
    }

    @And("^Odoo check if send to customer checkbox is checked$")
    public void odooSendToCustomerCheckboxIsChecked(){
        CustomerPage cp = new CustomerPage();
        Assert.assertThat("Sent to customer checkbox is not checked", cp.isSentToCustomerChecked(), is(true));
    }

    @And("^Odoo check if send date is today$")
    public void odooSendDateIs(){
        CustomerPage cp = new CustomerPage();
        Assert.assertThat("Sent date is not as expected", cp.getDirectDebitSentDate().equalsIgnoreCase(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))), is(true));
    }
}
