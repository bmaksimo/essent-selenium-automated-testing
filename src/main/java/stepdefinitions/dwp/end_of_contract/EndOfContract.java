package stepdefinitions.dwp.end_of_contract;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EndOfContract extends com.essent.testing.dwp.pageobject.impl.EndOfContract {

    @Before("@SMOKE, @QUOTE, @RENEWAL, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@SMOKE, @QUOTE, @RENEWAL, @B2B_REGRESSION")
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

    @And("^Select button$")
    public void selectButton() {
        boolean success = new SelectButton().test("");
        assertThat(String.format("Java Script file TrSelectButton is undefined."),
            success, is(true));
    }

    public void clickOnElement(String element) {
        boolean success = new ClickOnElement().test(element);
        assertThat(String.format("Top Menu item %s was not available.", element),
            success, is(true));
    }
}
