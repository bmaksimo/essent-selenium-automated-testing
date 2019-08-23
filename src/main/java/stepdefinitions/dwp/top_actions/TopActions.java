package stepdefinitions.dwp.top_actions;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.navigation.DwpLeftMenu;
import com.essent.testing.dwp.pageobject.impl.navigation.DwpTopMenu;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import stepdefinitions.dwp.navigation.NavigationElements;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class TopActions extends NavigationElements {

    private static final String TOP_FILTER_BUTTON = "Filters";
    private static final String TOP_MENU_KLANTEN = "Klanten";
    private static final String FILTER_MENU_REQUIRED_ELEMENT = "record-type-default-value-field";

    @Before("@DWP or @CORE or @E2E or @REGRESSION or @API")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Top action is Filter from \"([^\"]*)\" menu retrying ([^\"]*) times")
    public void checkTopActionFilterWithWaiting(String sideMenu, int maxAttempts) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        boolean elementVisible = false;
        int currentAttempt = 0;

        while (!elementVisible && currentAttempt <= maxAttempts) {
            currentAttempt++;
            Sleeper.sleepTightInSeconds(1);
            seleniumDriver.findElement(By.name(TOP_FILTER_BUTTON)).click();
            elementVisible = isFilterExpectedElementVisible();
            if (!elementVisible) {
                new DwpLeftMenu().clickOnLeftElement(sideMenu);
                new DwpTopMenu().findAndClickTopMenu(TOP_MENU_KLANTEN);
            }
        }
    }

    private boolean isFilterExpectedElementVisible() {
        try {
            return seleniumDriver
                .findElementWhenPresent(By.id(FILTER_MENU_REQUIRED_ELEMENT))
                .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @When("^Top action is \"([^\"]*)\"$")
    public void checkTopAction(String action) throws Throwable {
        clickTopAction(action);
    }

    @When("^Top action is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void checkTopAction(String action, int waitingTime) throws Throwable {
        clickTopAction(action, waitingTime);
    }

    @And("^Top arrow button is \"([^\"]*)\"$")
    public void clickTopArrowButton(String arrow) throws Throwable {
        super.clickTopArrow(arrow);
    }

    @And("^Top arrow button is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void clickTopArrowButton(String arrow, int waitingTime) throws Throwable {
        super.clickTopArrow(arrow.toLowerCase(), waitingTime);
    }

    @When("^Cockpit item is \"([^\"]*)\"$")
    public void checkCockpitItem(String item) throws Throwable {
        clickCockpitItem(item);
    }

    @And("^Changes are confirmed$")
    public void confirmChange() {
        seleniumDriver.waitForRequestsToFinish();
        boolean success = new ClickConfirm().test("");
        assertThat(String.format("Button %s was not available.", ""), success, is(true));
    }

    @And("^Changes are confirmed waiting for (\\d+) seconds$")
    public void confirmChange(int waitingTime) {
        Sleeper.sleepTightInSeconds(waitingTime);
        boolean success = new ClickConfirm().testNow("");
        assertThat(String.format("Button %s was not available.", ""), success, is(true));
    }

    @And("^Search input is \"([^\"]*)\"$")
    public void input(String inputName) throws Throwable {
        String name = parameterProvider.getValueOrParameterAsString(inputName);
        Map<String, String> customerName = new HashMap<>();
        customerName.put("name", name);
        boolean success = new SearchCustomer().test(name);
        assertThat(String.format("Customer %s was not found.", name),
            success, is(true));
    }

    @And("^Customer \"([^\"]*)\" is found$")
    public void customerFind(String inputName) throws Throwable {
        String name = parameterProvider.getValueOrParameterAsString(inputName);
        Map<String, String> customerName = new HashMap<>();
        String Inputname = parameterProvider.getValueOrParameterAsString(name);
        customerName.put("name", Inputname);
        boolean success = new ValidateCustomer().test(customerName);
        assertThat(String.format("View list did not contain customer '%s'", inputName),
            success, is(true));
    }

    @And("Intermittent Alert window is confirmed")
    public void handleAlert() {
        boolean actualAlert = isAlertPresent();
        if (actualAlert)
        {
            seleniumDriver.getDriver().switchTo().alert().accept();
        }
    }

    @Override
    @After("@DWP or @CORE or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
