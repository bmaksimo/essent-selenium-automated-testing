package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.salesmarketing.focuse_mode.PricingToolDashboardPage;
import com.essent.testing.dwp.scenario.DwpScenario;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import org.junit.Assert;

public class PricingToolDashboardSteps extends DwpScenario {
    @Before("@DWP or @REGRESSION")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @And("^Table price calculation is not empty$")
    public void tablePriceCalculationIsNotEmpty(){
        PricingToolDashboardPage ptdp = new PricingToolDashboardPage();
        Assert.assertTrue("Purchase price column is not displayed", ptdp.getPurchasePriceHeader().isDisplayed());
        Assert.assertTrue("Purchase price column first row is not displayed",ptdp.getPurchasePriceFirstRow().isDisplayed());
    }

    @And("^Indexatieparameter is not empty$")
    public void indexatieparameterIsNotEmpty(){
        PricingToolDashboardPage ptdp = new PricingToolDashboardPage();
        Assert.assertTrue("Index Parameter is empty", ptdp.GetIndexParameter().getText() != null);
    }

    @And("^Huidige waarde is not empty$")
    public void huidigeWaardeIsNotEmpty(){
        PricingToolDashboardPage ptdp = new PricingToolDashboardPage();
        Assert.assertTrue("Current value is empty", ptdp.getCurrentValue().getText() != null);
    }

}
