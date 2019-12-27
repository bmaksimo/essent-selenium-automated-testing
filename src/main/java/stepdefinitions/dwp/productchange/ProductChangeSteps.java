package stepdefinitions.dwp.productchange;

import com.essent.testing.dwp.pageobject.impl.productchange.ProductChangePageObjectImpl;
import com.essent.testing.dwp.pageobject.productchange.ProductChangePageObject;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;

public class ProductChangeSteps extends DwpScenario {

  @Before("@DWP or @E2E or @REGRESSION or @B2C")
  public void setupTest(Scenario scenario) {
    registerActiveScenario(scenario);
  }

  @And("^Tariff card has value of 1st item from list$")
  public void selectFirstItemFromList() {
    seleniumDriver.waitForRequestsToFinish();
    ProductChangePageObject po = new ProductChangePageObjectImpl();
    po.selectFirstItemFromList();
    seleniumDriver.waitForRequestsToFinish();
  }

  @Override
  @After("@DWP or @E2E or @REGRESSION or @B2C")
  public void tearDown() {
    super.tearDown();
  }
}
