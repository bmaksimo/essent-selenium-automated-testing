package stepdefinitions.dwp.dialog;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import com.essent.testing.util.SharedPropertiesSingleton;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SearchDialog extends Component {

    public SearchDialog(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    @When("^Dialog search input is current \"([^\"]*)\"$")
    public void runDialogSearch(String searchInput) {

        String currentSearchInputValue = (String) SharedPropertiesSingleton.getInstance().getSharedProperties().get(searchInput);

        WebElement searchField = seleniumDriver.findElementOrNull(By.xpath("//input[@id='search-input']"));
        searchField.sendKeys(currentSearchInputValue);

        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//input[@type='submit']")));

        //wait for search results to load
        Sleeper.sleepTightInSeconds(5);

        List<WebElement> checkboxes = seleniumDriver.findElements(By.xpath("//input[@type='checkbox']"));
        assertThat(String.format("Search term %s was not found.", currentSearchInputValue),
            checkboxes.size() > 1, is(true));
        checkboxes.get(1).click();

        WebElement sendButton = seleniumDriver.findElements(By.xpath("//a[@class='button']")).get(1);
        seleniumDriver.waitAndClick(sendButton);
    }

    @When("^Confirm is clicked$")
    public void clickConfirmButton() {
        //wait confirm button to be enabled
        Sleeper.sleepTightInSeconds(5);
        seleniumDriver.waitAndClick(seleniumDriver.findElementOrNull(By.id("confirm-button")));
    }
}
