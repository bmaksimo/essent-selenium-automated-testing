package stepdefinitions.dwp;

import com.essent.roles.UserRoles;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.elements.Button;
import com.essent.testing.dwp.pageobject.impl.elements.ButtonImpl;
import com.essent.testing.dwp.pageobject.impl.modal.login.LoginAction;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;

import java.time.Duration;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")
public class GenericSteps extends DwpScenario {


    @Before("@DWP or @CORE or @E2E or @REGRESSION or @API")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Given("^I logged in to DWP as \"([^\"]*)\"$")
    public void loginAs(String username) throws Throwable {
        setUpWebDriver();
        isDwpRunning();
        UserRoles dwpUser = UserRoles.get(username);
        Window application = new LoginAction().doLogin(dwpUser.getUsername(), dwpUser.getPassword());
        assertNotNull("DWP application did not appear after a login", application);
        injectJavaScriptTestRunner();
        discardPreviousFlow();
    }

    @Given("^I renew login to DWP as \"([^\"]*)\"$")
    public void renewLoginAs(String username) throws Throwable {
        tearDown();
        loginAs(username);
    }

    private void discardPreviousFlow() throws Throwable {
        try {
            WebElement cancelWebElement = seleniumDriver.findElementWhenPresent(By.id("cancel-button"), Duration.ofSeconds(1), Duration.ofMillis(100));
            Button cancelButton = new ButtonImpl(cancelWebElement);
            cancelButton.click();
            logger().debug("Unfinished guidance flow cancelled.");

        } catch(TimeoutException te) {
            logger().debug("No unfinished guidance flow to cancel.");
        }

    }

    @After("@DWP or @CORE or @E2E or @REGRESSION")
    public void tearDown() {
        tidyUp(seleniumDriver);
    }

}
