package stepdefinitions.dwp;

import com.essent.automation.core.WebDriverWait;
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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.test.context.ContextConfiguration;

import java.time.Duration;

import static org.junit.Assert.assertNotNull;
@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")
public class GenericSteps extends DwpScenario {


    @Before("@DWP, @CORE, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    private void login(String username) throws Throwable {
        isDwpRunning();
        UserRoles dwpUser = UserRoles.get(username);
        Window application = new LoginAction(getDwpWebDriver()).doLogin(dwpUser.getUsername(), dwpUser.getPassword());
        assertNotNull("DWP application did not appear after a login", application);
        injectJavaScriptTestRunner();
        discardPreviousFlow();
    }

    @Given("^I logged in to DWP as \"([^\"]*)\"$")
    public void loginAs(String username) throws Throwable {
        setUpWebDriver();
        login(username);
    }

    @Given("^I renew login to DWP as \"([^\"]*)\"$")
    public void renewLoginAs(String username) throws Throwable {
        setUpWebDriver();
        login(username);
    }

    private void discardPreviousFlow() throws Throwable {
        WebElement cancelWebElement = webDriver.findElementOrNull(By.id("cancel-button"), Duration.ofSeconds(1), Duration.ofMillis(50));
        if(cancelWebElement != null) {
            (new WebDriverWait(webDriver.getDriver(), 2)).until(ExpectedConditions.elementToBeClickable(cancelWebElement));
            Button cancelButton = new ButtonImpl(cancelWebElement);
            cancelButton.click();
        }

    }


    @After("@DWP, @CORE, @E2E, @REGRESSION")
    public void tearDown() {
        tidyUp();
    }

}
