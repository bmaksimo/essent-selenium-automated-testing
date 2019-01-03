package stepdefinitions.dwp.input;

import com.essent.automation.autocrat.Model;
import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import org.apache.commons.lang.text.StrSubstitutor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import stepdefinitions.dwp.tables.plus.SwitchState;

import java.util.HashMap;
import java.util.Map;

import static com.essent.automation.autocrat.Action.SELECT;
import static com.essent.automation.autocrat.Action.TYPING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class InputElements extends DwpScenario {

    private static final String INPUT_ELEMENT_LOCATOR_TEMPLATE = "//div[@class='input' | @class='input label-inline' and label/text()='${label}']//${action}[1]";

    @Before("@DWP, @CORE, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    private class ApplyInput {
        public boolean test(String label, String value) {
            Map<String, String> map = new HashMap<>();
            map.put("label", label);
            map.put("action", "input");
            StrSubstitutor substitutor = new StrSubstitutor(map);
            String query = substitutor.replace(INPUT_ELEMENT_LOCATOR_TEMPLATE);
            String elementName = "INPUT_FIELD";
            Model.Execution initializeField = createExecution();
            initializeField
                .element(new Model.Element().search("XPATH").query(query).key(elementName))
                .step(createStep(TYPING).element(elementName).value(value).timeoutInSeconds(4), 100);
            webDriver.waitForRequestsToFinish();
            return execute(initializeField);
        }
    }

    private class ApplySelection {
        public boolean test(String label, String value) {
            Map<String, String> map = new HashMap<>();
            map.put("label", label);
            map.put("action", "select");
            StrSubstitutor substitutor = new StrSubstitutor(map);
            String query = substitutor.replace(INPUT_ELEMENT_LOCATOR_TEMPLATE);
            String elementName = "DROPDOWN";
            Model.Execution initializeField = createExecution();
            initializeField
                .element(new Model.Element().search("XPATH").query(query).key(elementName))
                .step(createStep(SELECT).element(elementName).value(value).timeoutInSeconds(4), 100);
            webDriver.waitForRequestsToFinish();
            boolean success =  execute(initializeField);
            webDriver.waitForRequestsToFinish();
            return success;
        }
    }


    @And("^\"([^\"]*)\" input is \"([^\"]*)\"$")
    public void setInput(String label, String value) throws Throwable {
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        parameterProvider.put("inputValue", inputValue);
        boolean success = new ApplyInput().test(label, inputValue);
        assertThat(String.format("Input '%s' = '$s' failed.", label, inputValue),
            success, is(true));
    }

    @And("^Label input for \"([^\"]*)\" is \"([^\"]*)\"$")
    public void setLabelInput(String label, String value) throws Throwable {
        webDriver.waitForRequestsToFinish();
        setInput(label, "string:"+value);
        webDriver.waitForRequestsToFinish();
    }

    @And("^\"([^\"]*)\" date is \"([^\"]*)\"$")
    public void setDateInput(String label, String value) throws Throwable {
        String inputValue = toDwpDate(parameterProvider.getValueOrParameterAsString(value));
        boolean success = new ApplyInput().test(label, inputValue);
        assertThat(String.format("Input '%s' = '$s' failed.", label, inputValue),
            success, is(true));
    }

    @And("^\"([^\"]*)\" selection is \"([^\"]*)\"$")
    public void setSelection(String label, String value) throws Throwable {
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        boolean success = new ApplySelection().test(label, inputValue);
        assertThat(String.format("Selection %s is undefined.", label),
            success, is(true));
        Sleeper.sleepTightInSeconds(3);
    }

    @And("^Option \"([^\"]*)\" is ([^\"]*)$")
    public void switchOption(String option, SwitchState state) throws Throwable {
        Map<String, String> options = new HashMap<>();
        options.put("label", option);
        boolean success = executeJavascriptTest("TrClickToggleInput", options);
        assertThat(String.format("Option %s is undefined.", option),
            success, is(true));
    }

    @And("^Form is submitted$")
    public void formIsSubmitted() throws Throwable {
        Map<String, String> options = new HashMap<>();
        boolean success = executeJavascriptTest("TrSubmitForm", options);
    }

    @And("^Field \"([^\"]*)\" input is \"([^\"]*)\"$")
    public void setInputByPlaceholder(String placeholder, String value) {
        WebElement placeHolderInputElement = webDriver.findElement(By.xpath("//input[@placeholder='"+placeholder+"']"));
        boolean placeHolderWasFound = placeHolderInputElement != null;
        assertThat(String.format("Placeholder element '%s' was not found.", placeholder), placeHolderWasFound, is(true));
        placeHolderInputElement.sendKeys(value);
    }

    @Override
    @After("@DWP, @CORE, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
