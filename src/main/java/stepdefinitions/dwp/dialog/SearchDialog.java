package stepdefinitions.dwp.dialog;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.util.SharedPropertiesSingleton;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SearchDialog extends DwpScenario {

    @When("^Dialog search input is current \"([^\"]*)\"$")
    public void runDialogSearch(String searchInput) {

        String currentSearchInputValue = (String) SharedPropertiesSingleton.getInstance().getSharedProperties().get(searchInput);

        WebElement searchField = webDriver.findElementOrNull(By.xpath("//input[@id='search-input']"));
        searchField.sendKeys(currentSearchInputValue);

        webDriver.waitAndClick(webDriver.findElementWhenVisible(By.xpath("//input[@type='submit']")));

        //wait for search results to load
        Sleeper.sleepTightInSeconds(5);

        List<WebElement> checkboxes = webDriver.findElements(By.xpath("//input[@type='checkbox']"));
        assertThat(String.format("Search term %s was not found.", currentSearchInputValue),
            checkboxes.size() > 1, is(true));
        checkboxes.get(1).click();

        WebElement sendButton = webDriver.findElements(By.xpath("//a[@class='button']")).get(1);
        webDriver.waitAndClick(sendButton);
    }

    @When("^Confirm is clicked$")
    public void clickConfirmButton() {
        //wait confirm button to be enabled
        Sleeper.sleepTightInSeconds(5);
        webDriver.waitAndClick(webDriver.findElementOrNull(By.id("confirm-button")));
    }

    @Before("@SMOKE, @QUOTE, @BILLING")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@SMOKE, @QUOTE, @BILLING")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
