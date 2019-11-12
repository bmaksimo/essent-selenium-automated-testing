package stepdefinitions.dwp.overview;

import com.essent.automation.util.Sleeper;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.navigation.NavigationElements;

public class DashboardMenu extends NavigationElements {

    private static final String REPLACEMENT_KEY = "replacement_key";
    private static final String DASHBOARD_PATH = "//a[@class='col-1-4 nav-item']//small[normalize-space()='${" + REPLACEMENT_KEY + "}']/parent::a";

    @Before("@DWP or @CORE or @E2E or @REGRESSION or @API")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @When("^Dashboard menu is \"([^\"]*)\"$")
    public void goToDashboardMenuItem(String menuItem) {
        seleniumDriver.waitForRequestsToFinish();
        String currentDashboardMenu = createQuery(DASHBOARD_PATH, REPLACEMENT_KEY, menuItem);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(currentDashboardMenu)));
        parameterProvider.put("navigation", "back");
        parameterProvider.put("dashboard-menu", menuItem);
    }

    @When("^Dashboard menu is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void goToDashboardMenuItem(String menuItem, int waitingTime){
        Sleeper.sleepTightInSeconds(waitingTime);
        String currentDashboardMenu = createQuery(DASHBOARD_PATH, REPLACEMENT_KEY, menuItem);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(currentDashboardMenu)));
        parameterProvider.put("navigation", "back");
        parameterProvider.put("dashboard-menu", menuItem);
    }

    @Override
    @After("@DWP or @CORE or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
