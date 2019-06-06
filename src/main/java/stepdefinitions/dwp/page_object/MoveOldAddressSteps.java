package stepdefinitions.dwp.page_object;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.guidance_mode.CreateMoveOAPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.details.UpdateBillingCustomerPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;

public class MoveOldAddressSteps extends DwpScenario {
    @Before("@DWP or @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Communication channel is \"([^\"]*)\"$")
    public void communicationChannelIs(String channel) {
        CreateMoveOAPage cmoa = new CreateMoveOAPage();
        cmoa.chooseCommunicationChannel(channel);

    }

    @And("^Reason of move \"([^\"]*)\"$")
    public void reasonOfMove(String reason) {
        CreateMoveOAPage cmoa = new CreateMoveOAPage();
        cmoa.chooseReasonOfMove(reason);
    }

    @And("^Move date is \"([^\"]*)\"$")
    public void moveDateIs(String value) {
        CreateMoveOAPage cmoa = new CreateMoveOAPage();
        String inputValue = toDwpDate(parameterProvider.getValueOrParameterAsString(value));
        cmoa.chooseMoveDate(inputValue);
    }

    @And("^Get meter reading plus \"(\\d+)\"kwl from \"(\\d+)\"$")
    public void getMeterReadingPlusKwl(int num, String rate){
        CreateMoveOAPage cmoa = new CreateMoveOAPage();
        String oldMeterReading = cmoa.getPreviousMeterReading(rate);
        int newMeterReading = Integer.parseInt(oldMeterReading)+num;
        parameterProvider.put("meterstand",newMeterReading);
    }

    @And("^Change house number to \"(\\d+)\"$")
    public void changeHouseNumber(int num) {
        UpdateBillingCustomerPage ubcp = new UpdateBillingCustomerPage();
        ubcp.changeHouseNumber(num);
    }

    @And("^Low date meter reading date is \"([^\"]*)\"$")
    public void lowDateMeterReadingDateIs(String value){
        CreateMoveOAPage cmoa = new CreateMoveOAPage();
        String inputValue = toDwpDate(parameterProvider.getValueOrParameterAsString(value));
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(2);
        cmoa.chooseMeterReadingDate(inputValue);
        Sleeper.sleepTightInSeconds(1);
    }

    @And("^Low meter reading input is \"([^\"]*)\"$")
    public void lowMeterRreadingInputIs(String value) {
        CreateMoveOAPage cmoa = new CreateMoveOAPage();
        String meterReading = parameterProvider.getValueOrParameterAsString(value);
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(2);
        cmoa.setMeterReading(meterReading);
        Sleeper.sleepTightInSeconds(1);

    }
}
