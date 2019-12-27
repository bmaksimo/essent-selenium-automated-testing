package stepdefinitions.dwp.pageobject;

import com.essent.testing.dwp.pageobject.impl.servicecontracting.EndOfContractPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import org.junit.Assert;
import org.openqa.selenium.By;

public class EndOfContractSteps extends DwpScenario {

  @Before("@DWP or @E2E or @REGRESSION")
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
    String inputValue = parameterProvider.getValueOrParameterAsString(input);
    new EndOfContractPage().searchInputField(inputValue);
  }

  @And("^Search field input is \"([^\"]*)\" waiting for (\\d+) seconds$")
  public void searchFieldInputIs(String input, int waitingTime) {
    String inputValue = parameterProvider.getValueOrParameterAsString(input);
    new EndOfContractPage().searchInputFieldNow(inputValue, waitingTime);
  }

  @And("^Click Select Contractline$")
  public void clickSelectContractline() {
    new EndOfContractPage().clickOnSelectButtonPlaceholder();
  }

  @Override
  @After("@DWP or @E2E or @REGRESSION")
  public void tearDown() {
    super.tearDown();
  }
}
