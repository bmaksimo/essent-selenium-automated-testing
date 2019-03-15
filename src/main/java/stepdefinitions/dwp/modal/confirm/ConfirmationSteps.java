package stepdefinitions.dwp.modal.confirm;

import com.essent.testing.dwp.pageobject.impl.modal.confirm.ConfirmSignatureDialogImpl;
import com.essent.testing.dwp.pageobject.modal.confirm.ConfirmSignatureDialog;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import stepdefinitions.dwp.navigation.NavigationElements;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConfirmationSteps extends NavigationElements {

    private class CheckModalDialog implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrCheckModalDialog", options);
        }
    }

    @Before("@DWP, @CORE, @E2E, @REGRESSION")
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


    @And("^Contract signature is confirmed$")
    public void contractSignatureIsConfirmed() throws Throwable {
        ConfirmSignatureDialog dialog = new ConfirmSignatureDialogImpl();
        boolean success = dialog.confirm();
        assertThat("Contract signature was not confirmed.",
            success,
            is(true));
    }

    @When("^Modal \"([^\"]*)\" is displayed$")
    public void checkModalDialogOpen(String headerText) {
        Map<String, String> options = new HashMap<>();
        options.put("headerText", headerText);
        boolean success = new CheckModalDialog().test(options);
        assertThat(String.format("Action row %s was not found", headerText), success, is(true));
    }

    @Override
    @After("@DWP, @CORE, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
