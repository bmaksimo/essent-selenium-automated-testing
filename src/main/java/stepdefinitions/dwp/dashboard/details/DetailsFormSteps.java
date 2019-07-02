package stepdefinitions.dwp.dashboard.details;

import com.essent.testing.dwp.pageobject.dashboard.AccountDetails;
import com.essent.testing.dwp.pageobject.impl.dashboard.AccountDetailsImpl;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.details.DetailsPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DetailsFormSteps extends DwpScenario {

    @Before("@DWP or @CORE or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Then("^\"([^\"]*)\" field value is checked$")
    public void checkFieldValue(String label) {
        AccountDetails details = new AccountDetailsImpl();
        String value = details.getNonEdtableValue(label);
        assertThat(String.format("'%s' field was empty, was expected to have value", label), StringUtils.isNotEmpty(value), is(true));
        parameterProvider.put(label, value);
    }

    @Then("^\"([^\"]*)\" switch value is checked$")
    public void checkToggleSwitchValue(String label){
        AccountDetails details = new AccountDetailsImpl();
        boolean value = details.isToggleSwitchEnabled(label);
        parameterProvider.put(label, value);
    }


    @Override
    @After("@DWP or @CORE or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @And("^Customer type is \"([^\"]*)\"$")
    public void customerTypeIs(String type){
        DetailsPage dp = new DetailsPage();        ;
        Assert.assertTrue("Customer type does not match expected value", dp.getCustomerType().equalsIgnoreCase(type));
    }

    @Then("^Customer bank number is \"([^\"]*)\" and payment method is \"([^\"]*)\"$")
    public void customerBankNumberIsAndPaymentMethodIs(String iban, String method) {
        DetailsPage dp = new DetailsPage();

        System.out.println("-----------------------"+dp.getIban());
        System.out.println("++++++++++++++++++++++"+parameterProvider.getValueOrParameterAsString(iban));

        if (dp.getIban().equalsIgnoreCase(parameterProvider.getValueOrParameterAsString(iban))) {System.out.println("ok--------------------");
            if (dp.getIban().equals(parameterProvider.getValueOrParameterAsString(iban))) {System.out.println("ok--------------------");}}else{
        Assert.assertEquals("Customer iban does not match expected value", dp.getIban(), parameterProvider.getValueOrParameterAsString(iban));
        Assert.assertTrue("Customer payment method does not match expected value", dp.getPaymentMethod().equalsIgnoreCase(method));}

    }
}
