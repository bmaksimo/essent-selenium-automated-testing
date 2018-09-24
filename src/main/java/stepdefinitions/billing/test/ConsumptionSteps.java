package stepdefinitions.billing.test;

import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.belgium.energycomm.ws_to_bo.BasePayload;
import com.essent.restclients.BillingEnergyCommRest;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.util.SharedPropertiesSingleton;
import com.essent.testing.util.resource.ResourceUtil;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.util.Assert;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConsumptionSteps extends DwpScenario {

    private static final Logger logger = Logger.getLogger(ConsumptionSteps.class);

    private static final String PATH = "/xml/";
    private static final String CONSUMPTION_FILE = "consumption.xml";

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

    public static void main(String[] args) {
        ConsumptionSteps consumptionSteps = new ConsumptionSteps();
        String request = consumptionSteps.getConsumptionRequest("12455", "NIGHTLY", "6");
        System.out.println(request);
    }

    private String getConsumptionRequest(String deliveryPoint, String hourlyTariff, String months) {
        UUID uuid = UUID.randomUUID();
        String fromDate = DateTime.now().toString("yyyy-MM-dd");
        String toDate = DateTime.now().plusMonths(Integer.parseInt(months)).toString("yyyy-MM-dd");

        SharedPropertiesSingleton.getInstance().getSharedProperties().put("fromDate", fromDate);
        SharedPropertiesSingleton.getInstance().getSharedProperties().put("toDate", toDate);

        Map<String, String> consumptionData = new HashMap<>();
        consumptionData.put("uuid", String.valueOf(uuid));
        consumptionData.put("fromDate", fromDate);
        consumptionData.put("toDate", toDate);
        consumptionData.put("deliveryPoint", deliveryPoint);
        consumptionData.put("hourlyTariff", hourlyTariff);

        return getConsumptionRequestFromTemplate(consumptionData);
    }

    private String getConsumptionRequestFromTemplate(Map<String, String> data) {
        String consumptionTemplatePath = ResourceUtil.toPath(PATH + CONSUMPTION_FILE);
        File consumptionTemplateFile = new File(consumptionTemplatePath);
        try {
            String consumptionRequest = FileUtils.readFileToString(consumptionTemplateFile, Charset.defaultCharset());
            StrSubstitutor substitutor = new StrSubstitutor(data);
            consumptionRequest = substitutor.replace(consumptionRequest);

            return consumptionRequest;
        } catch (IOException e) {
            logger.warn("Something went wrong while creating consumption request.");
        }

        return null;
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
