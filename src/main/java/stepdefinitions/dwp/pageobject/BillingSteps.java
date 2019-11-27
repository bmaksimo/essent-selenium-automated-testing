package stepdefinitions.dwp.pageobject;

import com.essent.testing.dwp.pageobject.salesmarketing.customerdashboard.billing.BillingPage;
import com.essent.testing.dwp.pageobject.salesmarketing.customerdashboard.billing.CreateFreeTextInvoicePage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class BillingSteps extends DwpScenario {
    @Before("@DWP or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@DWP or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @And("^Click select product code$")
    public void clickSelectProductCode() {
        new CreateFreeTextInvoicePage().clickOnSelectButtonPlaceholder();
    }

    @And("^Select \"([^\"]*)\" product code$")
    public void selectProductCode(String productCode) {
        new CreateFreeTextInvoicePage().selectProductCode(productCode);
    }

    @Then("^Transaction is created with TYPE \"([^\"]*)\"$")
    public void transactiesIsCreatedWithTYPE(String type) {
        Assert.assertTrue("Type does not mach", new BillingPage().selectProductCode().equalsIgnoreCase(type));
    }

    @And("^Send$")
    public void send() {
        new BillingPage().clickOnSendButton();
    }

    @And("^Copy invoice amount from the first invoice$")
    public void copyInvoiceAmountFromTheFirstInvoice(){
        BillingPage bp = new BillingPage();
        parameterProvider.put("invoiceAmount",bp.getInvoiceAmount());


    }

    @And("^CNW and VKW invoice have same invoice amount$")
    public void cnwAndVKWInvoiceHaveSameInvoiceAmount() {
        String amountCNW = new BillingPage().getInvoiceAmount().replace("-", "");
        String amountVKW = parameterProvider.getValueOrParameterAsString("parameter:invoiceAmount");
        Assert.assertTrue(String.format("cnw \"%s\" and vkw \"%s\" does not have the same invice amount", amountCNW, amountVKW), amountCNW.equalsIgnoreCase(amountVKW));
    }
}
