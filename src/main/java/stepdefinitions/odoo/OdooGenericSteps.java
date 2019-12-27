package stepdefinitions.odoo;

import static org.junit.Assert.assertNotNull;

import com.essent.roles.UserRoles;
import com.essent.testing.odoo.pageobject.Window;
import com.essent.testing.odoo.pageobject.impl.modal.login.OdooLogin;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

public class OdooGenericSteps extends OdooScenario {
  @Before("@ODOO or @E2E or @REGRESSION")
  public void setupTest(Scenario scenario) {
    registerActiveScenario(scenario);
  }

  @Given("^I logged in to Odoo as \"([^\"]*)\"$")
  public void login(String username) throws Throwable {
    logger().info("Logging into Odoo as " + username);
    setUpWebDriver();
    isOdooRunning();
    UserRoles odooUser = UserRoles.get(username);
    Window application = new OdooLogin().login(odooUser.getUsername(), odooUser.getPassword());
    assertNotNull("DWP application did not appear after a login", application);
    awaitOdooRequestToFinish(180);
  }

  @Given("^I renew login to Odoo as \"([^\"]*)\"$")
  public void renewLoginAs(String username) throws Throwable {
    tearDown();
    login(username);
  }

  @After("@ODOO or @E2E or @REGRESSION")
  public void tearDown() {
    super.tearDown();
  }
}
