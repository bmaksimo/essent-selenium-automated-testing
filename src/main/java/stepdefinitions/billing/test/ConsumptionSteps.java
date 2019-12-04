package stepdefinitions.billing.test;

import com.billinghouse.testautomation.util.dsl.DateExpressionsUtil;
import com.billinghouse.testautomation.util.file.FileUtil;
import com.essent.be.api.config.BillingServiceFactory;
import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.belgium.energycomm.ws_to_bo.BasePayload;
import com.essent.restclients.BillingEnergyCommRest;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.util.resource.ResourceUtil;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import static com.billinghouse.testautomation.javascript.testrunner.JsTestRegistry.JS_TR_CHECK_TABLE_CELL_VALUE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConsumptionSteps extends DwpScenario {

    private static final Logger logger = Logger.getLogger(ConsumptionSteps.class);

    private static final String PATH = "/xml/";
    private static final String CONSUMPTION_FILE = "consumption.xml";

    @Autowired
    private BillingServiceFactory billingServiceFactory;

    @Autowired
    private ConsumptionService consumptionService;

    @Before("@DWP or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @When("^Consumption at current deliverypointid with \"([^\"]*)\" hourly-tariff is generated from now until \"([^\"]*)\" months after$")
    public void generateConsumption(String hourlyTariff, String months) throws Exception {
        String deliveryPointId = parameterProvider.getValueOrParameterAsString("parameter:EAN-code");
        String consumptionData = getConsumptionRequest(deliveryPointId, hourlyTariff, null, months);

        BasePayload msg = generatePayloadFromString(consumptionData);

        BillingEnergyCommRest bERest = new BillingEnergyCommRest();
        RestResponse resp = bERest.postEnergyCommMessage(msg);
        Assert.isTrue(resp.getResult(), resp.getMsg());
    }

    @When("^Consumption at deliverypointid \"([^\"]*)\" with \"([^\"]*)\" hourly-tariff is generated from now until \"([^\"]*)\" months after$")
    public void generateConsumptionatDeliveryPoint(String deliveryPoint, String hourlyTariff, String months) throws Exception {
        String deliveryPointId = parameterProvider.getValueOrParameterAsString(deliveryPoint);
        String consumptionData = getConsumptionRequest(deliveryPointId, hourlyTariff, null, months);
        BasePayload msg = generatePayloadFromString(consumptionData);

        BillingEnergyCommRest bERest = new BillingEnergyCommRest();
        RestResponse resp = bERest.postEnergyCommMessage(msg);
        Assert.isTrue(resp.getResult(), resp.getMsg());
    }

    @When("^Consumption at deliverypointid \"([^\"]*)\" with \"([^\"]*)\" hourly-tariff is generated from \"([^\"]*)\" until \"([^\"]*)\" months after$")
    public void generateConsumptionatDeliveryPoint(String deliveryPoint, String hourlyTariff, String dateFrom, String months) throws Exception {
        String deliveryPointId = parameterProvider.getValueOrParameterAsString(deliveryPoint);
        String consumptionData = getConsumptionRequest(deliveryPointId, hourlyTariff, dateFrom, months);
        BasePayload msg = generatePayloadFromString(consumptionData);

        BillingEnergyCommRest bERest = new BillingEnergyCommRest();
        RestResponse resp = bERest.postEnergyCommMessage(msg);
        Assert.isTrue(resp.getResult(), resp.getMsg());
    }

    @When("^Consumption at deliverypointid \"([^\"]*)\" is generated from now until \"([^\"]*)\"$")
    public void generateConsumptionUntilDate(String deliveryPoint, String dateTo) throws Exception {
        String deliveryPointId = parameterProvider.getValueOrParameterAsString(deliveryPoint);
        parameterProvider.put("billrun-date", DateExpressionsUtil.toDwpDate(dateTo));

        RestResponse resp = postConsumption(deliveryPointId, null, dateTo);
        Assert.isTrue(resp.getResult(), resp.getMsg());
    }

    @When("^Consumption at deliverypointid \"([^\"]*)\" is generated until \"([^\"]*)\"$")
    public void generateConsumptionUntilRelativeDate(String deliveryPoint, String dateTo) throws Exception {
        String deliveryPointId = parameterProvider.getValueOrParameterAsString(deliveryPoint);
        String inputValue = toDwpDate(parameterProvider.getValueOrParameterAsString(dateTo));
        parameterProvider.put("billrun-date", inputValue);

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime frenchFormatDate = formatter.parseDateTime(inputValue);
        dateTo = frenchFormatDate.toString("yyyy-MM-dd");

        postConsumptionTypeElectricityMeterTypeTotalHour(deliveryPointId, null, dateTo);
    }

    @When("^Consumption at deliverypointid \"([^\"]*)\" is generated from \"([^\"]*)\" until \"([^\"]*)\" months after$")
    public void generateConsumptionUntilRelativeDate(String deliveryPoint, String dateFrom, String months) throws Exception {
        String deliveryPointId = parameterProvider.getValueOrParameterAsString(deliveryPoint);
        String fromDate = toDwpDate(parameterProvider.getValueOrParameterAsString(dateFrom));
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime frenchFormatDateFrom = formatter.parseDateTime(fromDate);
        fromDate = frenchFormatDateFrom.toString("yyyy-MM-dd");
        String toDate = DateTime.parse(fromDate).plusMonths(Integer.parseInt(months)).toString("yyyy-MM-dd");

        RestResponse resp = postConsumption(deliveryPointId, fromDate, toDate);
        Assert.isTrue(resp.getResult(), resp.getMsg());
    }

    @Given("generate consumptions")
    public void sendEANsToJBilling() throws Exception {
        File file = new File(FileUtil.JBILLING_CONSUMPTION_LOCATION + "eans_consumption.csv");
        InputStream inputStream = new FileInputStream(file);

        Iterable<CSVRecord> records = CSVFormat.DEFAULT
            .withHeader("EAN","start date","end date", "TYPE", "Meter Type")
            .withRecordSeparator(",")
            .withTrailingDelimiter(true)
            .withFirstRecordAsHeader()
            .parse(new InputStreamReader(inputStream));

        records.forEach(record -> consumptionService.postConsumption(buildConsumptionRecords(record)));
    }

    private ConsumptionRecord buildConsumptionRecords(CSVRecord record) {
        String ean = record.get("EAN");
        String startDate = record.get("start date");
        String endDate = record.get("end date");
        String type = record.get("TYPE");
        String meterTypeValue = record.get("Meter Type");
        String meterType = StringUtils.isNotBlank(meterTypeValue) ? meterTypeValue : type;
        return new ConsumptionRecord(ean, type, meterType, startDate, endDate);
    }

    private RestResponse postConsumption(String deliveryPointId, String dateFrom, String dateTo) throws Exception {
        String consumptionData = getConsumptionRequest(deliveryPointId, dateFrom, dateTo);
        BasePayload msg = generatePayloadFromString(consumptionData);

        return billingServiceFactory.createEnergyCommService().doRequest(msg);
    }

    private void postConsumptionTypeElectricityMeterTypeTotalHour(String deliveryPointId, String dateFrom, String dateTo) throws Exception {
        ConsumptionRecord consumptionRecord = new ConsumptionRecord(deliveryPointId, "Electricity", "TOTAL_HOUR", dateFrom, dateTo);
        consumptionService.postConsumption(consumptionRecord);
    }

    private String getConsumptionRequest(String deliveryPoint, String dateFrom, String dateTo) {
        UUID uuid = UUID.randomUUID();
        String fromDate = StringUtils.isBlank(dateFrom) ? DateTime.now().toString("yyyy-MM-dd") : dateFrom;

        parameterProvider.put("fromDate", fromDate);
        parameterProvider.put("toDate", dateTo);

        Map<String, String> consumptionData = new HashMap<>();
        consumptionData.put("uuid", String.valueOf(uuid));
        consumptionData.put("fromDate", fromDate);
        consumptionData.put("toDate", dateTo);
        consumptionData.put("deliveryPoint", deliveryPoint);

        return getConsumptionRequestFromTemplate(consumptionData);
    }

    private String getConsumptionRequest(String deliveryPoint, String hourlyTariff, String fromDate, String months) {
        UUID uuid = UUID.randomUUID();
        String dateFrom = StringUtils.isBlank(fromDate) ? DateTime.now().toString("yyyy-MM-dd") : fromDate;
        String toDate = DateTime.parse(dateFrom).plusMonths(Integer.parseInt(months)).toString("yyyy-MM-dd");

        parameterProvider.put("fromDate", dateFrom);
        parameterProvider.put("toDate", toDate);

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
            logger.error("Something went wrong while creating consumption request.");
        }

        return null;
    }

    @Then("^Consumption is available at \"([^\"]*)\" row in \"([^\"]*)\" column$")
    public void checkCreatedConsumption(String ordinal, String column) {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        String fromDate = parameterProvider.getValueOrParameterAsString("parameter:fromDate");
        String toDate = parameterProvider.getValueOrParameterAsString("parameter:toDate");

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
            return executeJavascriptTest(JS_TR_CHECK_TABLE_CELL_VALUE, options);
        }
    }

    @Override
    @After("@DWP or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
