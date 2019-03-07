package stepdefinitions.dwp.dashboard.details;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.dashboard.AccountDetails;
import com.essent.testing.dwp.pageobject.impl.dashboard.AccountDetailsImpl;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.tables.plus.SwitchState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DetailsFormSteps extends DwpScenario {

    @Before("@DWP, @CORE, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Then("^\"([^\"]*)\" field value is checked$")
    public void checkFieldValue(String label) throws Throwable {
        AccountDetails details = new AccountDetailsImpl();
        String value = details.getNonEdtableValue(label);
        assertThat(String.format("'%s' field has is empty, was expected to have value", label),StringUtils.isNotEmpty(value), is(true));
        parameterProvider.put(label, value);
    }

    @Then("^\"([^\"]*)\" switch value is checked$")
    public void checkToggleSwitchValue(String label) throws Throwable {
        AccountDetails details = new AccountDetailsImpl();
        boolean value = details.isToggleSwitchEnabled(label);
        parameterProvider.put(label, value);
    }


    @Override
    @After("@DWP, @CORE, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
