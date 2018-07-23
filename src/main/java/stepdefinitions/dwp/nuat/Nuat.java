package stepdefinitions.dwp.nuat;

import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteStepView;
import com.essent.testing.dwp.pageobject.quote.impl.QuoteOverviewView;
import com.essent.testing.dwp.pageobject.quote.impl.SelectQuoteTypeView;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import stepdefinitions.dwp.NavigationElements;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class Nuat extends NavigationElements {

    @OutputParameter(name="contractor")
    private String contractor;

    @When("^Set Contractor \"([^\"]*)\"$")
    public void setContractor(String contractor) throws Throwable {
        logger().info("STEP:");
        logger().info(" - ACTION: SET_OUTPUT_PARAM");
        logger().info(" - NAME: contractor");
        logger().info(" - VALUE: " + contractor);
        this.contractor = contractor;

        tapConfirm();
    }

    private void tapConfirm() {
        SelectQuoteTypeView selectQuoteTypeView = new SelectQuoteTypeView(webDriver);
        CreateQuoteStepView confirm = selectQuoteTypeView.confirm();
        assertThat("Failure accepting the standard quote type.", confirm,
            notNullValue());
    }



}
