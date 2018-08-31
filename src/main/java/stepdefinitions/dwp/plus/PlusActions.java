package stepdefinitions.dwp.plus;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import org.openqa.selenium.By;
import stepdefinitions.dwp.navigation.NavigationElements;

public class PlusActions extends NavigationElements {

    @Before("@SMOKE, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Plus menu is \"([^\"]*)\"$")
    public void checkPlusMenu(String path) throws Throwable {
        clickPlusAction(path);
    }

    @And("^List plus action is ([^\"]*)$")
    public void checkPlusAction(String item) throws Exception {
        clickListPlusAction(item);
    }

    @Override
    @After("@SMOKE, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @And("^Selenium click on plus$")
    public void seleniumClickOnPlus() throws Throwable {
        webDriver.findElementOrNull(By.xpath("//table[@safeclass~'\\blist__content\\b']/tbody[@id='rows']//list-plus-cell[@smartid='bdaac-e']/?/?/a[@safeclass~'\\bicon-plus\\b.*\\bshow-actions\\b']")).click();
        webDriver.findElementOrNull(By.className("icon-edit")).click();
    }
}
