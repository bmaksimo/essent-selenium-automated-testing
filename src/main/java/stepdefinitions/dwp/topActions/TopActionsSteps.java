package stepdefinitions.dwp.topActions;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.modal.ModalBase;
import com.essent.testing.dwp.pageobject.impl.navigation.DwpLeftMenu;
import com.essent.testing.dwp.pageobject.impl.navigation.DwpTopMenu;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import stepdefinitions.dwp.menu.TopMenuActions;
import stepdefinitions.dwp.navigation.NavigationElements;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class TopActionsSteps extends NavigationElements {

    private static final String TOP_FILTER_BUTTON = "Filters";
    private static final String TOP_MENU_KLANTEN = "Klanten";
    private static final String FILTER_MENU_REQUIRED_ELEMENT = "record-type-default-value-field";

    @Before("@DWP or @CORE or @E2E or @REGRESSION or @API")
    public void setupTest(Scenario scenario){
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
            seleniumDriver.waitForRequestsToFinish();
            Sleeper.sleepTightInSeconds(4);
            seleniumDriver.findElement(By.name(TOP_FILTER_BUTTON)).click();
            elementVisible = isFilterExpectedElementVisible();
            if (!elementVisible) {
                seleniumDriver.waitForRequestsToFinish();
                new DwpLeftMenu().clickOnLeftElement(sideMenu);
                seleniumDriver.waitForRequestsToFinish();
                new DwpTopMenu().findAndClickTopMenu(TOP_MENU_KLANTEN);
            }
        }
    }

    private boolean isFilterExpectedElementVisible() {
        try {
            return seleniumDriver.findElementWhenPresent(By.id(FILTER_MENU_REQUIRED_ELEMENT)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @When("^Top action is \"([^\"]*)\"$")
    public void checkTopAction(String action){
        clickTopAction(action);
    }

    @When("^Top action is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void checkTopAction(String action, int waitingTime){
        clickTopAction(action, waitingTime);
    }

    @And("^Top arrow button is \"([^\"]*)\"$")
    public void clickTopArrowButton(String arrow){
        super.clickTopArrow(arrow);
    }

    @And("Click on top menu button UP")
    public void clickTopMenuUp() {
        new TopMenuActions().clickUpButton();
    }

    @And("^Click on top menu button PLUS and navigate to \"([^\"]*)\"$")
    public void clickPlusButton(String path) throws Exception {
        new TopMenuActions().clickPlusButton(path);
    }

    @And("Click on top menu button PREVIOUS")
    public void clickPreviousButton() {
        new TopMenuActions().clickPreviousButton();
    }

    @And("^Top arrow button is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void clickTopArrowButton(String arrow, int waitingTime){
        super.clickTopArrow(arrow.toLowerCase(), waitingTime);
    }

    @And("Changes are confirmed")
    public void confirmChange() {
        seleniumDriver.waitForRequestsToFinish();
        boolean success = new ModalBase().confirm(parameterProvider.getScenarioInfo());
        assertThat(String.format("Button %s was not available.", ""), success, is(true));
    }

    @And("^Changes are confirmed waiting for (\\d+) seconds$")
    public void confirmChange(int waitingTime) {
        Sleeper.sleepTightInSeconds(waitingTime);
        boolean success = new ModalBase().confirmNow(parameterProvider.getScenarioInfo(), waitingTime);
        assertThat(String.format("Button %s was not available.", ""), success, is(true));
    }

    @Override
    @After("@DWP or @CORE or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
