package stepdefinitions.dwp.quote.b2c;

import com.essent.testing.dwp.pageobject.impl.quote.ConnectionDetailsPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import stepdefinitions.dwp.tables.plus.SwitchState;

public class MarketMockSwitchSteps extends DwpScenario {

  @Before("@DWP or @E2E or @REGRESSION")
  public void setupTest(Scenario scenario){
    registerActiveScenario(scenario);
  }

  @When("^Electricity market mock mode is switched ([^\"]*) on \"([^\"]*)\" card$")
  public void switchMarketMockOnOnCard(SwitchState switchState, String card){
    ConnectionDetailsPage connectionDetailsPage = new ConnectionDetailsPage();
    connectionDetailsPage.toggleElectricityMarketMockTest(switchState, card);
    connectionDetailsPage.isElectricityMarketMockOn(card);
  }

//  @When("^Gas market mock mode is switched ([^\"]*) on \"([^\"]*)\" card$")
//  public void marketMockModeIsSwitchedOnOnCard(SwitchState switchState, String card){
//    ConnectionDetailsPage connectionDetailsPage = new ConnectionDetailsPage();
//    connectionDetailsPage.toggleGasMarketMockTest(switchState, card);
//    connectionDetailsPage.isGasMarketMockOn(card);
//  }

  @Override
  @After("@DWP or @E2E or @REGRESSION")
  public void tearDown() {
    super.tearDown();
  }
}
