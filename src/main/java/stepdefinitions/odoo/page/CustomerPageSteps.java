package stepdefinitions.odoo.page;

import com.essent.testing.odoo.pageobject.impl.pageObject.CustomerPage;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @And("^Odoo click on account block button$")
    public void odooClickOnAccountBlockButton(){
        CustomerPage cp = new CustomerPage();
        cp.clickOnAccountBlockButton();
    }

    @Then("^Check account blocks data$")
    public void checkAccountBlocksData(final DataTable dbTable) throws Exception{
        List<List<String>> data = dbTable.asLists();
        CustomerPage cp = new CustomerPage();
        String startDate =data.get(1).get(1).replace("-","/");
        String endDate = data.get(1).get(2).replace("-","/");
        Assert.assertThat("Reason is not as expected", cp.getAccountBlockReason().equalsIgnoreCase(data.get(1).get(0)), is(true));
        Assert.assertThat("Start date is not as expected", cp.getAccountBlockStartDate().equalsIgnoreCase(String.valueOf(DateTimeFormatter.ofPattern("MM/dd/yyyy").parse(startDate))), is(true));
        Assert.assertThat("End date is not as expected", cp.getAccountBlockEndDate().equalsIgnoreCase(DateTimeFormatter.ofPattern("MM/dd/yyyy").parse(endDate).toString()), is(true));
        Assert.assertThat("Active status is not as expected", cp.getAccountBlockActiveStatus().equalsIgnoreCase(data.get(1).get(3)), is(true));


    }
}
