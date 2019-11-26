package stepdefinitions.dwp.b2b;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.navigation.DwpTopMenu;
import com.essent.testing.dwp.pageobject.salesmarketing.customer_dashboard.service.ServicePage;
import com.essent.testing.dwp.pageobject.salesmarketing.customer_dashboard.workflows.MarketMessagesPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.awaitility.Duration.TEN_SECONDS;


public class Navigation extends DwpScenario {


    @Before("@DWP or @REGRESSION")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);

    }

    //TODO
    //This method should be moved from "Navigation" to ServiceSteps
    @And("^\"([^\"]*)\" is clicked$")
    public void isClicked(String srt) {
        ServicePage sp = new ServicePage();
        sp.clickOnNewCase();
    }

    @Override
    @After("@DWP or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    //TODO
    //This method should be moved from "Navigation"
    //It is distracting to maintain the methods placed to logical structure
    //without any relation to this structure.
    @Then("^Verify status is \"([^\"]*)\" and \"([^\"]*)\"$")
    public void verifyStatusIsAnd(String external, String status) {
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(10);
        if (status.equalsIgnoreCase("Normaal") || (status.equalsIgnoreCase("Normal"))) {
            Assert.assertTrue(checkStatusIsNormal(external));
        } else {
            Assert.assertTrue(checkStatusValidation(external, status));
        }
    }

    private boolean checkStatusIsNormal(String external) {
        seleniumDriver.waitForRequestsToFinish();
        String externalFromPage = seleniumDriver.findElementWhenVisible(By.xpath("//gridlr[@class='']//blue-sidebar/div/div[2]")).getText();
        return externalFromPage.equalsIgnoreCase(external);
    }

    private boolean checkStatusValidation(String external, String status) {
        seleniumDriver.waitForRequestsToFinish();
        List<WebElement> allElements = seleniumDriver.findElements(By.xpath("//div[@class='card__message']"));
        List<String> statuses = new ArrayList<String>();
        for (WebElement element : allElements){
            statuses.add(element.getText());
        }
        return statuses.contains(external) && statuses.contains(status);
    }

    @And("^Go back to home screen$")
    public void goBackToHomeScreen() {
        DwpTopMenu tm = new DwpTopMenu();
        tm.goBackToHomePage();
    }

    @When("^Refresh \"([^\"]*)\" till \"([^\"]*)\" is visible in table$")
    public void refreshTillIsVisible(String name, String status) {
        seleniumDriver.waitForRequestsToFinish();
        MarketMessagesPage mp = new MarketMessagesPage();
        if (seleniumDriver.findElement(By.xpath("//tr[1]//list-link-bold-top-two-liner-cell/div/a/h5")).isDisplayed()) {
            By selector = By.xpath("//tr[1]//list-simple-two-liner-cell/p/span[1]");
            given().await()
                .pollInterval(TEN_SECONDS)
                .atMost(new org.awaitility.Duration(600, SECONDS))
                .until(()-> mp.isRefreshedByName(name)
                    && seleniumDriver.findElementWhenVisible(selector).getText().equalsIgnoreCase(status));
        } else if (seleniumDriver.findElement(By.xpath("//tr[3]//list-link-bold-top-two-liner-cell/div/a/h5")).isDisplayed()) {
            By selector = By.xpath("//tr[3]//list-simple-two-liner-cell/p/span[1]");
            given().await()
                .pollInterval(TEN_SECONDS)
                .atMost(new org.awaitility.Duration(600, SECONDS))
                .until(()-> mp.isRefreshedByName(name)
                    && seleniumDriver.findElementWhenVisible(selector).getText().equalsIgnoreCase(status));
        }
    }
}
