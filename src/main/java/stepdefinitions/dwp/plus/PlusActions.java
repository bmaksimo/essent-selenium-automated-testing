package stepdefinitions.dwp.plus;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import stepdefinitions.dwp.navigation.NavigationElements;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PlusActions extends NavigationElements {

    @Before("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Plus Menu is \"([^\"]*)\"$")
    public void checkPlusMenu(String path) throws Throwable {
        clickPlusAction(path);
    }

    @And("^List Plus Action is ([^\"]*)$")
    public void checkPlusAction(String item) throws Exception {
        clickListPlusAction(item);
    }

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @And("^\"([^\"]*)\" list item at ([^\"]*) plus action$")
    public void listItemAtStringPlusAction(String headerName, String ordinal) {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        Map<String, String> ListOptions = new HashMap<>();
        ListOptions.put("headerName", headerName);
        ListOptions.put("index", rowIndex);
        boolean success = new ClickOnPlusAction().test(ListOptions);
        assertThat(String.format("View list did not contain header '%s'", ""),
            success, is(true));
    }


}
