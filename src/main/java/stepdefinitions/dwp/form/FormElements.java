package stepdefinitions.dwp.form;

import com.essent.testing.dwp.pageobject.elements.NonEditable;
import com.essent.testing.dwp.pageobject.elements.NonEditableInput;
import com.essent.testing.dwp.pageobject.impl.elements.NonEditableImpl;
import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.apache.commons.lang3.StringUtils;
import org.awaitility.Duration;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.awaitility.Duration.FIVE_HUNDRED_MILLISECONDS;
import static org.awaitility.Duration.ONE_SECOND;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FormElements extends DwpScenario {

    @Before("@DWP, @CORE, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    private class CheckFormHeader implements Predicate<String> {
        @Override
        public boolean test(String header) {
            int sec = 7;
            Map<String, Object> options = new HashMap<>();
            options.put("schedule_seconds", sec);
            options.put("header", header);
            boolean success = executeJavascriptTest("TrCheckFormHeader", options);
            return success;
        }
    }

    @Then("^Form header is \"([^\"]*)\"$")
    public void checkFormHeader(String formHeader) throws Throwable {
        given().await()
            .pollInterval(FIVE_HUNDRED_MILLISECONDS)
            .pollDelay(ONE_SECOND)
            .atMost(new Duration(30, SECONDS)).until(() -> new CheckFormHeader().test(formHeader));
    }

    @And("^\"([^\"]*)\" field value is \"([^\"]*)\"$")
    public void setFieldValue(String label, String expectedValue) throws Throwable {
        NonEditable field = new NonEditableImpl();
        FluentWait<NonEditable> waiter = waiter(field, 20, 5);
        waiter.until((NonEditable p) -> {
            String actualValue = p.getValue(label);
            seleniumDriver.getDriver().getCurrentUrl();
            String assertionMessage = String.format("Actual value of \"%s\" was \"%s\" differs from expected \"%s\"", label, actualValue, expectedValue);
            waiter.withMessage(assertionMessage);
            return StringUtils.equals(expectedValue, actualValue);
        });
    }

    @And("^Value at \"([^\"]*)\" in the card \"([^\"]*)\" is \"([^\"]*)\"$")
    public void checkValueInCard(String label, String cardName, String expectedValue) {
        NonEditable card = new NonEditableImpl();
        String nonEditableValue = card.getValue(cardName, label);
        boolean result = nonEditableValue.matches(expectedValue);
        assertThat("The expected value differs from the real value", result, is(true));
    }

    @Override
    @After("@DWP, @CORE, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

}
