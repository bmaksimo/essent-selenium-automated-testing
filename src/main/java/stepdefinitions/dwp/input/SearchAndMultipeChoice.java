package stepdefinitions.dwp.input;

import com.essent.testing.dwp.pageobject.impl.modal.search.SearchAndMultipeChoiceModalDialogImpl;
import com.essent.testing.dwp.pageobject.modal.search.SearchAndMultipeChoiceModalDialog;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SearchAndMultipeChoice extends DwpScenario {

  /**
   * Cucumber-JVM Before- hook
   *
   * @param scenario Gherkin scenario descriptor
   * @throws Throwable
   */
  @Before("@DWP or @B2C or @CORE or @E2E or @REGRESSION")
  public void setupTest(Scenario scenario) throws Throwable {
    registerActiveScenario(scenario);
  }

  @When("^Search option is \"([^\"]*)\"$")
  public void setSearchOption(String searchOption) throws Throwable {
    String inputValue = parameterProvider.getValueOrParameterAsString(searchOption);
    SearchAndMultipeChoiceModalDialog searchDialog = new SearchAndMultipeChoiceModalDialogImpl();
    searchDialog.setSearchOption(inputValue);
  }

  @And("^Search button with label \"([^\"]*)\" is clicked$")
  public void clickSearchButton(String searchButtonLabel) throws Throwable {
    SearchAndMultipeChoiceModalDialog searchDialog = new SearchAndMultipeChoiceModalDialogImpl();
    searchDialog.search(searchButtonLabel);
  }

  @And("^First search result matching \"([^\"]*)\" is checked$")
  public void checkFirstSearchResult(String match) throws Throwable {
    String input = parameterProvider.getValueOrParameterAsString(match);
    SearchAndMultipeChoiceModalDialog searchDialog = new SearchAndMultipeChoiceModalDialogImpl();
    assertThat(searchDialog.checkSearchResult(input), is(true));
  }

  @And("^Submit search results button \"([^\"]*)\" is clicked$")
  public void submitSearchResult(String submitButtonLabel) {
    SearchAndMultipeChoiceModalDialog searchDialog = new SearchAndMultipeChoiceModalDialogImpl();
    searchDialog.submitSearchResult(submitButtonLabel);
  }

  /** Cucumber-JVM After- hook */
  @Override
  @After("@DWP or @CORE or @B2C or @E2E or @REGRESSION")
  public void tearDown() {
    super.tearDown();
  }
}
