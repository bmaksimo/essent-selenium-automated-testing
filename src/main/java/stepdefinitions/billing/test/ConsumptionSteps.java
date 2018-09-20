package stepdefinitions.billing.test;

import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.belgium.energycomm.ws_to_bo.BasePayload;
import com.essent.restclients.BillingEnergyCommRest;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.util.SharedPropertiesSingleton;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.joda.time.DateTime;
import org.springframework.util.Assert;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConsumptionSteps extends DwpScenario {

    @Before("@SMOKE, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Consumption at deliverypointid ([^\"]*) with ([^\"]*) hourly-tariff is generated from now until ([^\"]*) months after$")
    public void generateConsumption(String deliveryPointId, String hourlyTariff, String months) throws Exception {
        String consumptionData = getConsumptionRequest(deliveryPointId, hourlyTariff, months);

        BasePayload msg = generatePayloadFromString(consumptionData);

        BillingEnergyCommRest bERest = new BillingEnergyCommRest();
        RestResponse resp = bERest.postEnergyCommMessage(msg);
        Assert.isTrue(resp.getResult(), resp.getMsg());
    }

    @When("^Consumption at current deliverypointid with ([^\"]*) hourly-tariff is generated from now until ([^\"]*) months after$")
    public void generateConsumption(String hourlyTariff, String months) throws Exception {
        String deliveryPointId = (String) SharedPropertiesSingleton.getInstance().getSharedProperties().get("EAN-code");
        String consumptionData = getConsumptionRequest(deliveryPointId, hourlyTariff, months);

        BasePayload msg = generatePayloadFromString(consumptionData);

        BillingEnergyCommRest bERest = new BillingEnergyCommRest();
        RestResponse resp = bERest.postEnergyCommMessage(msg);
        Assert.isTrue(resp.getResult(), resp.getMsg());
    }

    private String getConsumptionRequest(String deliveryPointId, String hourlyTariff, String months) {
        UUID randomUUID = UUID.randomUUID();
        String now = DateTime.now().toString("yyyy-MM-dd");
        String toDate = DateTime.now().plusMonths(Integer.parseInt(months)).toString("yyyy-MM-dd");

        SharedPropertiesSingleton.getInstance().getSharedProperties().put("fromDate", now);
        SharedPropertiesSingleton.getInstance().getSharedProperties().put("toDate", toDate);

        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Consumption xmlns=\"EnergyComm-out-bo\">\n" +
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
            "        <measurementnature>ACTIVE_ENERGY</measurementnature>\n" +
            "        <timeframe>" + hourlyTariff + "</timeframe>\n" +
            "        <direction>CONSUMPTION</direction>\n" +
            "        <deliverypoint>" + deliveryPointId + "</deliverypoint>\n" +
            "        <values>\n" +
            "            <number>1</number>\n" +
            "            <value>4321.0</value>\n" +
            "            <quality>DEFAULT</quality>\n" +
            "        </values>\n" +
            "    </register>\n" +
            "</Consumption>\n";
    }

    @Then("^Consumption is available at ([^\"]*) row in ([^\"]*) column$")
    public void checkCreatedConsumption(String ordinal, String column) throws Throwable {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        String fromDate = (String) SharedPropertiesSingleton.getInstance().getSharedProperties().get("fromDate");
        String toDate = (String) SharedPropertiesSingleton.getInstance().getSharedProperties().get("toDate");

        DateTime dateFromDate = DateTime.parse(fromDate);
        DateTime dateToDate = DateTime.parse(toDate);
        fromDate = dateFromDate.toString("dd-MM-yyyy");
        toDate = dateToDate.toString("dd-MM-yyyy");

        Map<String, String> options = new HashMap<>();
        options.put("data", fromDate + " " + toDate);
        options.put("column", column);
        options.put("index", rowIndex);

        boolean success = new TableCellValueChecker().test(options);
        assertThat(String.format("Consumption not available at row %s header '%s'", ordinal, column),
            success, is(true));
    }

    private BasePayload generatePayloadFromString(String data) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(BasePayload.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (BasePayload) unmarshaller.unmarshal(new StringReader(data));
    }

    private class TableCellValueChecker implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrCheckTableCellValue", options);
        }
    }
}
