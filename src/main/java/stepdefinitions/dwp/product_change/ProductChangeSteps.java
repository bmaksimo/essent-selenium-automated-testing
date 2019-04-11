package stepdefinitions.dwp.product_change;

import com.essent.testing.dwp.pageobject.impl.product_change.ProductChangePageObjectImpl;
import com.essent.testing.dwp.pageobject.product_change.ProcuctChangePageObject;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;

public class ProductChangeSteps extends DwpScenario {

    @Before("@DWP, @E2E, @REGRESSION, @B2C")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("Tariefkaart has value of 1st item from list$")
    public void selectFirstItemFromList() throws Throwable {
        //stabilizing agreement regarding waitForRequestToFinish() is encapsulated in PO
        ProcuctChangePageObject po = new ProductChangePageObjectImpl();
        po.selectFirstItemFromList();
    }

    @Override
    @After("@DWP, @E2E, @REGRESSION, @B2C")
    public void tearDown() {
        super.tearDown();
    }

}
