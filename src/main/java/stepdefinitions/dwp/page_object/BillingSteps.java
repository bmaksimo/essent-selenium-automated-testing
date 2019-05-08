package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.billing.BillingPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.billing.CreateFreeTextInvoicePage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class BillingSteps extends DwpScenario {
    @Before("@DWP, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@DWP, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @And("^Click select product code$")
    public void clickSelectProductCode() {
        CreateFreeTextInvoicePage cftip = new CreateFreeTextInvoicePage();
        cftip.clickOnSelectProductCodeButton();
    }

    @And("^Select \"([^\"]*)\" product code$")
    public void selectProductCode(String productCode) {
        CreateFreeTextInvoicePage cftip = new CreateFreeTextInvoicePage();
        cftip.selectProductCode(productCode);
    }

    @Then("^Transaction is created with TYPE \"([^\"]*)\"$")
    public void transactiesIsCreatedWithTYPE(String type){
        BillingPage bp = new BillingPage();
        Assert.assertEquals("Type does not mach", bp.selectProductCode(),type);

    }
}
