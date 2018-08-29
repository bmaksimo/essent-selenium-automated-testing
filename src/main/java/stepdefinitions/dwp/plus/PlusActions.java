package stepdefinitions.dwp.plus;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import org.openqa.selenium.By;
import stepdefinitions.dwp.navigation.NavigationElements;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PlusActions extends NavigationElements {

    @Before("@SMOKE, @QUOTE, @QUOTE_MI, @QUOTE_SS")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Plus menu is \"([^\"]*)\"$")
    public void checkPlusMenu(String path) throws Throwable {
        clickPlusAction(path);
    }

    @Override
    @After("@SMOKE, @QUOTE, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @And("^\"([^\"]*)\" list item at ([^\"]*) plus action$")
    public void listItemAtStringPlusAction(String headerName, String ordinal) {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        Map<String, String> params = new HashMap<>();
        params.put("headerName", headerName);
        params.put("ordinal", rowIndex);
        boolean success = new ClickOnPlusAction().test(params);
        assertThat(String.format("View list did not contain header '%s'", headerName),
            success, is(true));
    }

    @And("^Selenium click on plus$")
    public void seleniumClickOnPlus() throws Throwable {
        webDriver.findElementOrNull(By.xpath("//table[@safeclass~'\\blist__content\\b']/tbody[@id='rows']//list-plus-cell[@smartid='bdaac-e']/?/?/a[@safeclass~'\\bicon-plus\\b.*\\bshow-actions\\b']")).click();
        webDriver.findElementOrNull(By.className("icon-edit")).click();
    }

    @And("^List Plus Action is ([^\"]*)$")
    public void checkPlusAction(String item) throws Exception {
        clickListPlusAction(item);
    }
}
