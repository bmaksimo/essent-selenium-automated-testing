package stepdefinitions.dwp.menu;

import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.belgium.energycomm.ws_to_bo.BasePayload;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.Assert;
import stepdefinitions.dwp.navigation.NavigationElements;
import stepdefinitions.dwp.tables.plus.Item;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MenuElements extends NavigationElements {

    private static final String YMR_MEASUREMENT_TEMPLATE = "/data/templates/ymr_measure.xml";

    public enum DirectionType {
        PRODUCTION, CONSUMPTION
    }

    public enum STEP {
        DAILY, MONTHLY
    }

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd";
    public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormat.forPattern(DEFAULT_FORMAT);


    @When("^Consumption is generated$")
    public void generateConsumption() throws Exception {

        String consumptionData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Consumption xmlns=\"EnergyComm-out-bo\">\n" +
            "    <messageid>1000378945</messageid>\n" +
            "    <sender>5414488000608</sender>\n" +
            "    <recipient>5499764826400</recipient>\n" +
            "    <transactionid>20003</transactionid>\n" +
            "    <externalmessageid>EdielTransactionId[envid=378,txnbr=1]</externalmessageid>\n" +
            "    <externaltimestamp>2012-02-27T15:30:00+01:00</externaltimestamp>\n" +
            "    <gsrn>541448860014901878</gsrn>\n" +
            "    <consumptionid>79</consumptionid>\n" +
            "    <release>1</release>\n" +
            "    <continuous>false</continuous>\n" +
            "    <readingfrequency>YEAR</readingfrequency>\n" +
            "    <measurementfrequency>YEARLY</measurementfrequency>\n" +
            "    <fromdate>2016-09-01+01:00</fromdate>\n" +
            "    <todate>2017-06-11+01:00</todate>\n" +
            "    <rectification>false</rectification>\n" +
            "    <historical>false</historical>\n" +
            "    <measurementperiod>1</measurementperiod>\n" +
            "    <context>PERIODIC_METERING</context>\n" +
            "    <register>\n" +
            "        <measurementnature>ACTIVE_ENERGY</measurementnature>\n" +
            "        <timeframe>TOTAL_HOUR</timeframe>\n" +
            "        <direction>CONSUMPTION</direction>\n" +
            "        <values>\n" +
            "            <number>1</number>\n" +
            "            <value>4321.0</value>\n" +
            "            <quality>DEFAULT</quality>\n" +
            "        </values>\n" +
            "    </register>\n" +
            "</Consumption>\n";

        String deliveryPointId = "541448820045083585";
        DirectionType direction = DirectionType.CONSUMPTION;
        DateTime from = DateTime.now();
        DateTime to = new DateTime().plusMonths(6);
        String templatePath = YMR_MEASUREMENT_TEMPLATE;

        Objects.requireNonNull(deliveryPointId, "Delivery-point id cannot be null.");
        Objects.requireNonNull(direction, "Direction cannot be null.");
        Objects.requireNonNull(from, "From date cannot be null.");
        Objects.requireNonNull(to, "To date cannot be null.");
        Objects.requireNonNull(templatePath, "Template path cannot be null.");

        Map<String, Object> data = new HashMap<>();
        UUID randomUUID = UUID.randomUUID();
        data.put("messageid", randomUUID.toString());
        data.put("direction", direction.name());
        data.put("deliverypoint", deliveryPointId);
        data.put("fromdate", DEFAULT_FORMATTER.print(from));
        data.put("todate", DEFAULT_FORMATTER.print(to));
        data.put("historical", String.valueOf(false));

        BasePayload msg = generatePayloadFromString(consumptionData);

        // Send to billing
        BillingEnergyCommRest bERest = new BillingEnergyCommRest();
        RestResponse resp = bERest.postEnergyCommMessage(msg);
        Assert.isTrue(resp.getResult(), resp.getMsg());
    }

    private DateTime incrementDateByStep(DateTime date, STEP step) {
        switch (step) {
            case DAILY:
                return date.plusDays(1);
            case MONTHLY:
                return date.plusMonths(1);
            default:
                throw new RuntimeException("Incrementing step " + step + " is not supported.");
        }
    }

    private BasePayload generatePayload(String templatePath, Map<String, Object> data) throws Exception {

        Template template = freemarker.getTemplate(templatePath);

        BasePayload msg = null;
        try (StringWriter out = new StringWriter()) {
            template.process(data, out);

            if (TestSettings.verbose()) {
                LOG.info("About to send payload: " + out.toString());
            }

            EnergyCommUtil eUtil = new EnergyCommUtil();
            msg = eUtil.readData(out);
        }

        return msg;
    }

    private BasePayload generatePayloadFromString(String data) throws Exception {
        StringReader sr = new StringReader(data);
        JAXBContext jaxbContext = JAXBContext.newInstance(BasePayload.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BasePayload response = (BasePayload) unmarshaller.unmarshal(sr);
//        BasePayload msg = null;
//            EnergyCommUtil eUtil = new EnergyCommUtil();
//            msg = eUtil.readData(out);

        return response;
    }


    @Before("@SMOKE, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Available left menu items are:$")
    public void checkMenuItemLink(DataTable menuItems) throws Throwable {
        List<String> failedMenuItems = menuItems.asList(String.class).stream()
            .filter(
                menuItem -> {
                    Map<String, String> jsOptions = new HashMap<>();
                    jsOptions.put("menu", "left");
                    jsOptions.put("item", menuItem);
                    return !executeJavascriptTest("TrCheckMenuItem", jsOptions);
                })
            .collect(Collectors.toList());
        assertThat("The following left menu items were not available: "
            + StringUtils.join(failedMenuItems, ", "), failedMenuItems.isEmpty(), is(true));

    }

    @And("^Available top menu items are:$")
    public void checkAvailableTopItems(DataTable menuItems) throws Throwable {
        final List<String> failedUpperItems = menuItems.asList(String.class)
            .stream()
            .filter(
                menuItem -> {
                    Map<String, String> options = new HashMap<>();
                    options.put("item", menuItem);
                    options.put("menu", "top");
                    return !executeJavascriptTest("TrCheckMenuItem", options);
                })
            .collect(Collectors.toList());
        assertThat("The following left menu items were not available: "
            + StringUtils.join(failedUpperItems, ", "), failedUpperItems.isEmpty(), is(true));
    }

    @When("^Left menu is ([^\"]*)$")
    public void clickLeftMenuItem(String tabName) throws Throwable {
        super.visitLeftMenuItem(tabName);
    }

    @When("^Top menu item is ([^\"]*)$")
    public void clickTopMenuItem(String tabName) throws Throwable {
        super.visitTopMenuItem(tabName);
    }

    @When("^Left Tab is ([^\"]*)$")
    public void check_left_menu_item(String itemName) throws Throwable {
        clickLeftMenuItem(itemName);
    }

    @When("^Top Tab is ([^\"]*)$")
    public void check_top_menu_item(String itemName) throws Throwable {
        clickTopMenuItem(itemName);
    }

    @And("^The following Plus menu items are available at positions:$")
    public void checkPositionsOfPlusMenuItems(DataTable plusItems) throws Throwable {
        final List<String> failedItems = plusItems.asList(Item.class)
            .stream()
            .filter(
             item -> {
                 Map<String, Object> options = new HashMap<>();
                 options.put("item", item.getItem());
                 options.put("position", item.getPosition());
                 return !executeJavascriptTest("TrPlusMenuHasItem", options);
             })
            .map(item -> {
                return item.getItem();
            }).collect(Collectors.toList());
        assertThat("The following left menu items were not available: "
            + StringUtils.join(failedItems, ", "), failedItems.isEmpty(), is(true));
    }

    @Override
    @After("@SMOKE, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
