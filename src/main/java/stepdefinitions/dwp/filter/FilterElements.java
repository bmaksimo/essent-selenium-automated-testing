package stepdefinitions.dwp.filter;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.model.FilterElementConverter;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import stepdefinitions.dwp.NavigationElements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.essent.automation.autocrat.Action.CLICK;
import static com.essent.automation.autocrat.Action.REQUIRE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FilterElements extends NavigationElements {

    private static final String FILTER_BUTTON_ELEMENT = "FILTER_BUTTON_ELEMENT";
    private static final String FILTER_BUTTON_ELEMENT_QUERY = ".icon-filters";


    @Before("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL")
    public void SetupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    private class ApplySingleFilter implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrApplyFilterInput", options);
        }
    }

    private class TogggleFilterMode implements Predicate<FilterElements> {

        @Override
        public boolean test(FilterElements param) {
            Model.Execution execution = newExecution().element(FILTER_BUTTON_ELEMENT, new Model.Element().search("SELECTOR").query(FILTER_BUTTON_ELEMENT_QUERY));
            execution
                .flow()
                .step((new Model.Step().action(CLICK).element(FILTER_BUTTON_ELEMENT)))
                .step(new Model.Step().action(Action.SLEEP).sleepInMillis(2500));
            return execute(webDriver.getDriver(), execution);
        }
    }

    private class VerifyFilterElements implements Function<DataTable, List<String>> {
        @Override
        public List<String> apply(DataTable filterElements) {
            List<Model.Element> elements = FilterElementConverter.get().getElements(filterElements);
            List<String> failures = elements.stream().filter(element ->
            {
                Model.Execution execution = newExecution().element("LABEL", element);
                execution.flow().step((new Model.Step().action(REQUIRE).element("LABEL")));
                return !execute(webDriver.getDriver(), execution);
            })
                .map(element->{ return element.getName() + ", " + element.query;}).collect(Collectors.toList());
            return failures;
        }
    }


    @When("^I click on the filter button$")
    public void i_click_on_filter_button() throws Throwable {
        TogggleFilterMode togggleFilterMode = new TogggleFilterMode();
        togggleFilterMode.test(this);
        boolean success = togggleFilterMode.test(this);
        assertThat("DWP Main Filter mode off was expected to be checked successfully, actualy there were verification errors.", success, is(true));

    }

    @When("^Available filters are:$")
    public void visitLeftMenuItemFilter(DataTable filterElements) throws Throwable {
        TogggleFilterMode togggleFilterMode = new TogggleFilterMode();
        togggleFilterMode.test(this);
        List<String> failingElements = new VerifyFilterElements().apply(filterElements);
        boolean success = failingElements.isEmpty();
        togggleFilterMode.test(this);
        assertThat(String.format("Filter elements: {%s} were not available.",
            StringUtils.join(failingElements, ";")), success, is(true));
    }

    @And("^Filter element \"([^\"]*)\" input is \"([^\"]*)\"$")
    public void setFilterInput(String label, String value) throws Throwable {
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", value);
        boolean success = new ApplySingleFilter().test(options);
        assertThat(String.format("Filter element %s is undefined.", label),
            success, is(true));
    }

    @And("^Filter element \"([^\"]*)\" date input is \"([^\"]*)\"$")
    public void setFilterDateInput(String label, String value) throws Throwable {
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("value", convertToDwpDate(value));
        boolean success = new ApplySingleFilter().test(options);
        assertThat(String.format("Filter element %s is undefined.", label),
            success, is(true));
    }

    @And("^Filter element \"([^\"]*)\" selection is \"([^\"]*)\"$")
    public void setFilterSelection(String label, String value) throws Throwable {
        setFilterInput(label, String.format("string:%s", value));
    }

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
