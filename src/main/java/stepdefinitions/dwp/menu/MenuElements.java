package stepdefinitions.dwp.menu;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.navigation.DwpLeftMenu;
import com.essent.testing.dwp.pageobject.impl.navigation.DwpTopMenu;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import stepdefinitions.dwp.navigation.NavigationElements;

public class MenuElements extends NavigationElements {

  @Before("@DWP, @CORE, @E2E, @REGRESSION")
  public void setupTest(Scenario scenario) throws Throwable {
    registerActiveScenario(scenario);
  }

  @When("^Left menu is \"([^\"]*)\"$")
  public void clickLeftMenuItem(String tabName) throws Throwable {
    seleniumDriver.waitForRequestsToFinish();
    DwpLeftMenu lm = new DwpLeftMenu();
    lm.clickOnLeftElement(tabName);
  }

  @When("^Left menu is \"([^\"]*)\" waiting for (\\d+) seconds$")
  public void clickLeftMenuItemFixedWait(String tabName, int waitingTime) throws Throwable {
    DwpLeftMenu lm = new DwpLeftMenu();
    lm.clickOnLeftElement(tabName, waitingTime);
  }

  @When("^Left Tab is \"([^\"]*)\"$")
  public void clickLeftTab(String itemName) throws Throwable {
    clickLeftMenuItem(itemName);
  }

  @When("^Top menu item is \"([^\"]*)\"$")
  public void clickTopMenuItem(String tabName) throws Throwable {
    seleniumDriver.waitForRequestsToFinish();
    DwpTopMenu tm = new DwpTopMenu();
    tm.findAndClickTopMenu(tabName);
  }

  @When("^Top menu item is \"([^\"]*)\" waiting for (\\d+) seconds$")
  public void clickTopMenuItemFixedWait(String tabName, int waitingTime) throws Throwable {
    Sleeper.sleepTightInSeconds(waitingTime);
    DwpTopMenu tm = new DwpTopMenu();
    tm.findAndClickTopMenuNow(tabName);
  }

  @Then("^Sleep for (\\d+) seconds$")
  public void sleepForSeconds(int seconds) {
    Sleeper.sleepTightInSeconds(seconds);
  }

  @Override
  @After("@DWP, @CORE, @E2E, @REGRESSION")
  public void tearDown() {
    super.tearDown();
  }
}
