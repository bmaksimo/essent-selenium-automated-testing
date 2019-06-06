package stepdefinitions.dwp.modal.confirm;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.modal.confirm.ConfirmSignatureDialogImpl;
import com.essent.testing.dwp.pageobject.modal.confirm.ConfirmSignatureDialog;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import stepdefinitions.dwp.navigation.NavigationElements;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry.JS_TR_CHECK_MODAL_DIALOG;
import static org.hamcrest.Matchers.is;

public class ConfirmationSteps extends NavigationElements {

  private class CheckModalDialog implements Predicate<Map> {
    @Override
    public boolean test(Map options) {
      return executeJavascriptTest(JS_TR_CHECK_MODAL_DIALOG, options);
    }
  }

    @Before("@DWP or @CORE or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Modal dialog is \"([^\"]*)\"$")
    public void verifyDialogue(String title) {
        ConfirmSignatureDialog dialog = new ConfirmSignatureDialogImpl(title);
        boolean success = dialog.isShown();
        assertThat("Contract signature dialog was not shown.",
            success,
            is(true));
    }

    @And("^Modal dialog \"([^\"]*)\" is not shown$")
    public void isDialogShown(String title) {
        ConfirmSignatureDialog dialog = new ConfirmSignatureDialogImpl(title);
        assertThat("Contract signature dialog was not shown.",
            dialog.isShown(),
            is(false));
    }

  @Then("^Modal dialogue is confirmed$")
  public void confirm() throws Throwable {
    confirmModalDialogue();
  }

  @Then("^Contract signature is confirmed$")
  public void contractSignatureIsConfirmed() throws Throwable {
    confirmModalDialogue();
  }

  private void confirmModalDialogue() {
    Sleeper.sleepTightInSeconds(3);
    ConfirmSignatureDialog dialog = new ConfirmSignatureDialogImpl();
    dialog.confirm();
    boolean shown = dialog.isShown();
    assertThat("Modal dialogue was not confirmed.", shown, is(false));
  }

    @When("^Modal \"([^\"]*)\" is displayed$")
    public void checkModalDialogOpen(String headerText) {
        Map<String, String> options = new HashMap<>();
        options.put("headerText", headerText);
        boolean success = new CheckModalDialog().test(options);
        assertThat(String.format("Action row %s was not found", headerText), success, is(true));
    }

  @When("^Modal dialog contains \"([^\"]*)\" in action list$")
  public void hasActionInActionList(String match) throws Throwable {
    seleniumDriver.waitForRequestsToFinish();
    String textToLookup = parameterProvider.getValueOrParameterAsString(match);
    ConfirmSignatureDialog dialog = new ConfirmSignatureDialogImpl();
    assertThat(
        String.format("Dialogue doesn't contain given text \"%s\"", textToLookup),
        dialog.isInActionList(textToLookup),
        is(true));
  }

  @Override
  @After("@DWP or @CORE or @E2E or @REGRESSION")
  public void tearDown() {
    super.tearDown();
  }
}
