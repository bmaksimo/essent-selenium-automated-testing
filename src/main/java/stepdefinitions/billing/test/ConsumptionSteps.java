package stepdefinitions.billing.test;

import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.belgium.energycomm.ws_to_bo.BasePayload;
import com.essent.restclients.BillingEnergyCommRest;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class ConsumptionSteps extends DwpScenario {

    @When("^Consumption at deliverypointid ([^\"]*) with ([^\"]*) hourly-tariff is generated from now until ([^\"]*) months after$")
    public void generateConsumption(String deliveryPointId, String hourlyTariff, String months) throws Exception {
        String consumptionData = getConsumptionRequest(deliveryPointId, hourlyTariff, months);

        BasePayload msg = generatePayloadFromString(consumptionData);

        BillingEnergyCommRest bERest = new BillingEnergyCommRest();
        RestResponse resp = bERest.postEnergyCommMessage(msg);
        Assert.isTrue(resp.getResult(), resp.getMsg());
    }

    private String getConsumptionRequest(String deliveryPointId, String hourlyTariff, String months) {
        UUID randomUUID = UUID.randomUUID();
        String now = DateTime.now().toString("yyyy-mm-dd");
        String toDate = DateTime.now().plusMonths(Integer.parseInt(months)).toString("yyyy-mm-dd");

        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<ConsumptionSteps xmlns=\"EnergyComm-out-bo\">\n" +
            "    <messageid>" + randomUUID + "</messageid>\n" +
            "    <sender>5414488000608</sender>\n" +
            "    <recipient>5499764826400</recipient>\n" +
            "    <transactionid>" + randomUUID + "</transactionid>\n" +
            "    <externalmessageid>EdielTransactionId[envid=378,txnbr=1]</externalmessageid>\n" +
            "    <externaltimestamp>2012-02-27T15:30:00+01:00</externaltimestamp>\n" +
            "    <gsrn>" + deliveryPointId + "</gsrn>\n" +
            "    <consumptionid>79</consumptionid>\n" +
            "    <release>1</release>\n" +
            "    <continuous>false</continuous>\n" +
            "    <direction>CONSUMPTION</direction>\n" +
            "    <readingfrequency>YEAR</readingfrequency>\n" +
            "    <measurementfrequency>YEARLY</measurementfrequency>\n" +
            "    <fromdate>" + now + "</fromdate>\n" +
            "    <todate>" + toDate + "</todate>\n" +
            "    <rectification>false</rectification>\n" +
            "    <historical>false</historical>\n" +
            "    <measurementperiod>1</measurementperiod>\n" +
            "    <context>PERIODIC_METERING</context>\n" +
            "    <register>\n" +
            "        <measurementnature>" + hourlyTariff + "</measurementnature>\n" +
            "        <timeframe>TOTAL_HOUR</timeframe>\n" +
            "        <direction>CONSUMPTION</direction>\n" +
            "        <values>\n" +
            "            <number>1</number>\n" +
            "            <value>4321.0</value>\n" +
            "            <quality>DEFAULT</quality>\n" +
            "        </values>\n" +
            "    </register>\n" +
            "</ConsumptionSteps>\n";

    }

    @Then("^Consumption ending in ([^\"]*) months is available$")
    public void checkConsumptionCreated(String months) {
        String toDate = DateTime.now().plusMonths(Integer.parseInt(months)).toString("yyyy-mm-dd");

    }

    private BasePayload generatePayloadFromString(String data) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(BasePayload.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (BasePayload) unmarshaller.unmarshal(new StringReader(data));
    }

    private class ConsumptionGenerator implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrCheckGeneratedConsumption", options);
        }
    }
}
