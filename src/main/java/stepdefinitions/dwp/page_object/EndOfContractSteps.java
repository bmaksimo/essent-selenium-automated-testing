package stepdefinitions.dwp.page_object;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.service_contracting.EndOfContractPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;



import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class EndOfContractSteps extends DwpScenario {

    @Before("@DWP, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }


    @When("^Click on ([^\"]*)$")
    public void clickOn(String element) {
        clickOnElement(element.toLowerCase());
    }

    @And("^Clicked on sign X")
    public void clickedOn() {
        webDriver.waitAndClick(webDriver.findElementWhenVisible(By.xpath("//div[@class = 'modal__header']/a")));
        webDriver.waitForRequestsToFinish();

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
       EndOfContractPage endOfContractPage = new EndOfContractPage(webDriver);
       String inputValue = parameterProvider.getValueOrParameterAsString(input);
       endOfContractPage.searchInputField(inputValue);
    }

    @And("^Click Select Contractline$")
    public void clickSelectContractline() {
        EndOfContractPage endOfContractPage = new EndOfContractPage(webDriver);
        endOfContractPage.simpleExecuteJavaScript("TrSelectContractline");
    }

    @And("^EAN check box$")
    public void eanCheckBox() {
        EndOfContractPage endOfContractPage = new EndOfContractPage(webDriver);
        boolean success = endOfContractPage.checkEanCheckBox();
        assertThat(String.format("JavaScript file TrEanCheckBox is undefined."),
            success, is(true));
    }

    private void clickOnElement(String element) {
        EndOfContractPage endOfContractPage = new EndOfContractPage(webDriver);
        boolean success = endOfContractPage.startNewMarketSection(element);
        assertThat(String.format("Top Menu item %s was not available.", element),
            success, is(true));
    }

    @Override
    @After("@DWP, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
