package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;

public class ContractenSteps extends DwpScenario {

    private String eanCode;

    @Before("@SMOKE, @QUOTE, @RENEWAL, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@SMOKE, @QUOTE, @RENEWAL, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }


    @And("^Search for ean code$")
    public void searchForEanCode() throws Throwable {
        searchForEanCode(eanCode);
    }

    @When("^Input in ([^\"]*) is \"([^\"]*)\"$")
    public void inputInModuleIs(String label, String input) throws Throwable {
        fieldDropDownLabel(label, input);
    }

    @And("^Check toggle \"([^\"]*)\"$")
    public void checkToggle(String label) throws Throwable {
        turnOnCheckBox(label);
    }

    @When("^Find \"([^\"]*)\" contract$")
    public void findContract(String input) throws Throwable {
        eanCode = findActiveContract(input);
    }

    @Then("^Confirm task was \"([^\"]*)\"$")
    public void confirmTaskWas(String input) throws Throwable {
        confirmTaskStatus(input);
    }

    private String findActiveContract(String input) throws InterruptedException {
        webDriver.waitForRequestsToFinish();
        int counter = 2;
        String eanCode;
        String action = webDriver.findElementWhenVisible(By.xpath("(//h6)[" + counter + "]")).getText();
        while (!action.equalsIgnoreCase(input)) {
            counter = counter + 2;
            action = webDriver.findElementWhenVisible(By.xpath("(//h6)[.='" + counter + "'][1]")).getText();
        }
        counter--;
        eanCode = webDriver.findElementWhenVisible(By.xpath("(//h5)[" + counter + "]")).getText();

        return eanCode;
    }

    private void searchForEanCode(String eanCode) {
        webDriver.waitForRequestsToFinish();
        webDriver.findElementWhenVisible(By.id("search-input")).clear();
        webDriver.findElementWhenVisible(By.id("search-input")).sendKeys(eanCode);
        webDriver.findElementWhenVisible(By.xpath("//input[@value='Search']")).click();
        webDriver.waitForRequestsToFinish();
        webDriver.findElementWhenVisible(By.xpath("//div[@class='multi-select__results']//ul[2]")).click();
        webDriver.waitForRequestsToFinish();
        webDriver.findElementWhenVisible(By.xpath("//section[@class='view__modal']//a[@href='']")).click();
    }

    private void fieldDropDownLabel(String label, String input) {
        webDriver.waitForRequestsToFinish();
        webDriver.findElementWhenVisible(By.xpath("//select[@id='dwp-mig-" + label.toLowerCase() + "-c-field']/option[@label='" + input + "']")).click();
    }

    private void turnOnCheckBox(String label) {
        if (label.equalsIgnoreCase("Testing")) {
            webDriver.findElementWhenVisible(By.id("dwp|toggle_testing")).click();
        } else if (label.equalsIgnoreCase("Market mock")) {
            webDriver.findElementWhenVisible(By.id("aos_products_quotes|market_mock_c")).click();
        }
    }

    private void confirmTaskStatus(String input) {
        webDriver.waitForRequestsToFinish();
        Assert.assertTrue(webDriver.findElementWhenVisible(By.xpath("(//h6)[.='" + input + "']")).isDisplayed());
    }
}
