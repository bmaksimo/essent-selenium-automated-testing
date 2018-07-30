package stepdefinitions.dwp.change_pay_method;

import com.essent.testing.dwp.DwpScenario;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import stepdefinitions.dwp.NavigationElements;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ChangePayMethod extends DwpScenario {
    private String locator = "//dwp-app/div[3]/focus-mode/focus-mode-content[@title='Van Hauwaert Steven']//div[@class='col-1-1']//gridlr[@class='']//list/div/div[@class='list__actions']/div[2]/a[2]";
    private String selectContracline = "/html//div[@id='id-field']//button[@type='button']";
    private String EAN = "541448860014827666";
    private String searchInputField = "search-input";
    private String btnSearch = "/section[@class='view__modal']/div[@class='modal__container modal__wide']/div[@class='modal__content']//input[@value='Search']";
    private String checkBox = "//select-with-search-modal/section[@class='view__modal']/div[@class='modal__container modal__wide']/div[@class='modal__content']/div[@class='cols']//div[@class='multi-select__results']/ul[2]//span[@class='icon-checkmark']";
    private String btnSubmit = "/html//select-with-search-modal/section[@class='view__modal']//a[@href='']";

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
        clickOnElement(element.toUpperCase());
//        webDriver.findElementOrNull(By.xpath(locator)).click();
    }

    private void clickOnElement(String element) {
        boolean success = new ClickOnElement().test(element);
        assertThat(String.format("Top Menu item %s was not available.", element),
            success, is(true));
    }

    @And("^Open Select Contractline$")
    public void openSelectContractline() {
        webDriver.findElementOrNull(By.id("id-field")).click();
    }

    @And("^Input EAN in search field$")
    public void inputEANInSearchField() throws InterruptedException {
        webDriver.findElementOrNull(By.id(searchInputField)).sendKeys(EAN);
        webDriver.findElementOrNull(By.xpath(btnSearch)).click();
        webDriver.findElementOrNull(By.xpath(checkBox)).click();
        webDriver.findElementOrNull(By.xpath(btnSubmit)).click();
        Thread.sleep(15000);
    }

    private class ClickOnElement implements Predicate<String> {
        @Override
        public boolean test(String element) {
            Map<String, String> options = new HashMap<>();
            options.put("element", element);
            boolean success = executeJavascriptTest("TrClickOnElement", options);
            return success;
        }
    }
}
