package stepdefinitions.dwp.end_of_contract;

import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EndOfContract extends com.essent.testing.dwp.pageobject.b2b_regression.EndOfContract {

    @Before("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL, @B2B_REGRESSION")
    public void SetupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @When("^Click on ([^\"]*)$")
    public void clickOn(String element) {
        clickOnElement(element.toLowerCase());
    }

    @And("^Open Select Contractline$")
    public void openSelectContractline() {
        webDriver.findElementOrNull(By.id("id-field")).click();
    }

    @And("^Assert is true$")
    public void assertIsTrue() {
        Assert.assertTrue(webDriver.findElementOrNull(By.id("search-input")).isDisplayed());
    }

    @And("^Search field input is \"([^\"]*)\"$")
    public void searchFieldInputIs(String input) {
        searchInputField(input);
    }

    @And("^Click Select Contractline$")
    public void clickSelectContractline() {
        simpleExecuteJavaScript("TrSelectContractline");
    }

    @And("^EAN check box$")
    public void eanCheckBox() {
        boolean success = new EanCheckBox().test("");
        assertThat(String.format("JavaScript file TrEanCheckBox is undefined."),
            success, is(true));
    }

    @When("^Break Point$")
    public void breakPoint() {
        System.out.println("Break Point!");
    }

    @And("^Submit button$")
    public void submitButton() {
        boolean success = new SubmitContractLine().test( "");
        assertThat(String.format("Java Script file TrSubmitButton is undefined."),
            success, is(true));
    }
}
