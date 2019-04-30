package stepdefinitions.dwp.page_object;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.service_contracting.EndOfContractPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import org.junit.Assert;
import org.openqa.selenium.By;

import static com.billinghouse.MatcherAssert.assertThat;
import static com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry.JS_TR_SELECT_CONTRACTLINE;
import static org.hamcrest.Matchers.is;

public class EndOfContractSteps extends DwpScenario {

  @Before("@DWP, @E2E, @REGRESSION")
  public void setupTest(Scenario scenario) {
    registerActiveScenario(scenario);
  }

  @And("^Open Select Contractline$")
  public void openSelectContractline() {
    seleniumDriver.findElementWhenPresent(By.id("id-field")).click();
  }

  @And("^Assert is true$")
  public void assertIsTrue() {
    Assert.assertTrue(seleniumDriver.findElementWhenPresent(By.id("search-input")).isDisplayed());
  }

  @And("^Search field input is \"([^\"]*)\"$")
  public void searchFieldInputIs(String input) {
    EndOfContractPage endOfContractPage = new EndOfContractPage();
    String inputValue = parameterProvider.getValueOrParameterAsString(input);
    endOfContractPage.searchInputField(inputValue);
  }

  @And("^Search field input is \"([^\"]*)\" waiting for (\\d+) seconds$")
  public void searchFieldInputIs(String input, int waitingTime) {
    EndOfContractPage endOfContractPage = new EndOfContractPage();
    String inputValue = parameterProvider.getValueOrParameterAsString(input);
    Sleeper.sleepTightInSeconds(waitingTime);
    endOfContractPage.searchInputFieldNow(inputValue);
  }

  @And("^Click Select Contractline$")
  public void clickSelectContractline() {
    seleniumDriver.waitForRequestsToFinish();
    EndOfContractPage endOfContractPage = new EndOfContractPage();
    endOfContractPage.simpleExecuteJavaScript(JS_TR_SELECT_CONTRACTLINE);
  }

  @And("^EAN check box$")
  public void eanCheckBox() {
    EndOfContractPage endOfContractPage = new EndOfContractPage();
    boolean success = endOfContractPage.checkEanCheckBox();
    assertThat(String.format("JavaScript file TrEanCheckBox is undefined."), success, is(true));
  }

  @Override
  @After("@DWP, @E2E, @REGRESSION")
  public void tearDown() {
    super.tearDown();
  }
}
