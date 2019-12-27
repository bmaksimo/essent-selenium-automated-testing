package stepdefinitions.jbilling.Page;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.essent.testing.jbilling.pageobject.impl.page.OrdersPage;
import com.essent.testing.jbilling.scenario.JBillingScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

public class Orders extends JBillingScenario {

  @Before("@JBILLING or @B2B or @REGRESSION")
  public void setupTest(Scenario scenario) {
    registerActiveScenario(scenario);
  }

  @When("^Order table is not empty$")
  public void checkOrderTableNotEmpty() {
    OrdersPage ordersPage = new OrdersPage();
    boolean success = ordersPage.checkOrderTableNotEmpty();

    assertThat("Rows in order table are empty", success, is(true));
  }

  @When("^JBilling Value next to label \"([^\"]*)\" is \"([^\"]*)\" in Inner Table$")
  public void checkValueNextToLabel(String label, String expectedValue) {
    OrdersPage orderPage = new OrdersPage();
    String actualResult = orderPage.checkValueNextToLabel(label);
    assertThat(
        "Value " + expectedValue + " is not shown next to label " + label,
        actualResult.equalsIgnoreCase(expectedValue),
        is(true));
  }

  @Override
  @After("@JBILLING or @B2B or @REGRESSION")
  public void tearDown() {
    super.tearDown();
  }
}
