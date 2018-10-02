package stepdefinitions.dwp.input;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.util.SharedPropertiesSingleton;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.tools.ant.taskdefs.Sleep;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MultipleInputElements extends DwpScenario {

    @Before("@SMOKE, @QUOTE, @BILLING")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    private class MultipleInputDialog implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrOpenMultipleInputDialog", options);
        }
    }

    private class ApplyMultipleInput implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrApplyMultipleFilterInput", options);
        }
    }

    @When("^Multiple product input selected is \"([^\"]*)\"$")
    public void setMultipleInput(String product) {

        Map<String, String> options = new HashMap<>();
        options.put("searchInput", product);

        boolean openedDialog = new MultipleInputDialog().test(options);
        assertThat("Dialog could not be opened.",
            openedDialog, is(true));

        boolean success = new ApplyMultipleInput().test(options);
        assertThat(String.format("Product %s is undefined.", product),
            success, is(true));
    }

    @When("^Dialog search input is current \"([^\"]*)\"$")
    public void runDialogSearch(String searchInput) {

        String currentSearchInputValue = (String) SharedPropertiesSingleton.getInstance().getSharedProperties().get(searchInput);

        WebElement searchField = webDriver.findElementOrNull(By.xpath("//input[@id='search-input']"));
        searchField.sendKeys(currentSearchInputValue);

        webDriver.waitAndClick(webDriver.findElementWhenVisible(By.xpath("//input[@type='submit']")));

        Sleeper.sleepTightInSeconds(5);

        List<WebElement> checkboxes = webDriver.findElements(By.xpath("//input[@type='checkbox']"));
        assertThat(String.format("Search term %s was not found.", currentSearchInputValue),
            checkboxes.size() > 1, is(true));
        checkboxes.get(1).click();

        Sleeper.sleepTightInSeconds(5);

        WebElement sendButton = webDriver.findElements(By.xpath("//a[@class='button']")).get(1);
        webDriver.waitAndClick(sendButton);
    }

    @Then("^Confirm is clicked$")
    public void clickConfirmButton() {
        Sleeper.sleepTightInSeconds(5);
        webDriver.waitAndClick(webDriver.findElementOrNull(By.id("confirm-button")));
    }

    @Override
    @After("@SMOKE, @QUOTE, @BILLING")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
