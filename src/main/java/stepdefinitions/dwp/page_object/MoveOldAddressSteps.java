package stepdefinitions.dwp.page_object;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.guidance_mode.CreateMoveOAPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.details.UpdateBillingCustomerPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;

public class MoveOldAddressSteps extends DwpScenario {
    @Before("@DWP, @REGRESSION")
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
    public void getMeterReadingPlusKwl(int num, int rate){
        CreateMoveOAPage cmoa = new CreateMoveOAPage();
        String oldMeterReading = cmoa.getPreviousMeterReading(rate);
        int newMeterReading = Integer.parseInt(oldMeterReading)+num;
        parameterProvider.put("meterstand",newMeterReading);
    }

    @And("^Change house number by \"(\\d+)\"$")
    public void changeHouseNumber(int num) {
        UpdateBillingCustomerPage ubcp = new UpdateBillingCustomerPage();
//        String oldHouseNum = ubcp.getCurrentHouseNumber();
//        System.out.println("-----------------------"+oldHouseNum);
//        int newHouseNum = Integer.parseInt(oldHouseNum);
//        System.out.println("-----------------------"+newHouseNum);
//        System.out.println("-----------------------"+newHouseNum+num);
//        ubcp.changeHouseNumber(newHouseNum+num);
        ubcp.changeHouseNumber(4);
    }

    @And("^Datum meteropname low date is \"([^\"]*)\"$")
    public void datumMeteropnameDateIs(String value){
        CreateMoveOAPage cmoa = new CreateMoveOAPage();
        String inputValue = toDwpDate(parameterProvider.getValueOrParameterAsString(value));
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(2);
        cmoa.chooseMeterReadingDate(inputValue);
        Sleeper.sleepTightInSeconds(1);
    }

    @And("^Meterstand low input is \"([^\"]*)\"$")
    public void meterstandLowInputIs(String value) {
        CreateMoveOAPage cmoa = new CreateMoveOAPage();
        String meterReading = parameterProvider.getValueOrParameterAsString(value);
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(2);
        cmoa.setMeterReading(meterReading);
        Sleeper.sleepTightInSeconds(1);

    }
}
