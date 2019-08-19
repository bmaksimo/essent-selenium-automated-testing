package stepdefinitions.billing.test;

import com.essent.be.api.config.BillingServiceFactory;
import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.belgium.energycomm.ws_to_bo.BasePayload;
import com.essent.testing.util.resource.ResourceUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ConsumptionService {

    private static final Logger logger = Logger.getLogger(ConsumptionService.class);

    private static final String PATH = "/xml/";
    private static final String ELECTRICITY_LOW_HIGH = "electricity_high_low.xml";
    private static final String ELECTRICITY_LOW_HIGH_EXCLUSIVE_NIGHT = "electricity_high_low_exclusivenight.xml";
    private static final String ELECTRICITY_TOTAL_HOUR_EXCLUSIVE_NIGHT = "electricity_totalhour_exclusivenight.xml";
    private static final String ELECTRICITY_TOTAL_HOUR = "electricity_totalhours.xml";
    private static final String ELECTRICITY_LOW_HIGH_TOTAL_HOUR = "electricity_high_low_total_hours.xml";
    private static final String GAS = "gas.xml";

    @Autowired
    private BillingServiceFactory billingServiceFactory;

    public void postConsumption(ConsumptionRecord consumptionRecord) {
        Optional<String> consumptionData = getConsumptionRequest(consumptionRecord);
        try {
            if (consumptionData.isPresent()) {
                BasePayload msg = generatePayloadFromString(consumptionData.get());
                logger.debug("postConsumption: " + consumptionRecord.toString());
                RestResponse response = billingServiceFactory.createEnergyCommService().doRequest(msg);
                logger.debug(response.getMsg());
            }
        } catch (Exception e) {
            logger.error("Error while posting consumption");
        }
    }

    private Optional<String>  getConsumptionRequest(ConsumptionRecord consumptionRecord) {
        UUID uuid = UUID.randomUUID();
        String fromDate = StringUtils.isBlank(consumptionRecord.getStartDate()) ? DateTime.now().toString("yyyy-MM-dd") : consumptionRecord.getStartDate();

        Map<String, String> consumptionData = new HashMap<>();
        consumptionData.put("uuid", String.valueOf(uuid));
        consumptionData.put("fromDate", fromDate);
        consumptionData.put("toDate", consumptionRecord.getEndDate());
        consumptionData.put("deliveryPoint", consumptionRecord.getEan());

        return getConsumptionRequestFromTemplate(consumptionData, consumptionRecord);
    }

    private Optional<String> getConsumptionRequestFromTemplate(Map<String, String> data, ConsumptionRecord consumptionRecord) {
        try {
            File consumptionTemplateFile = new File(chooseTemplate(consumptionRecord));
            String consumptionRequest = FileUtils.readFileToString(consumptionTemplateFile, Charset.defaultCharset());
            StrSubstitutor substitutor = new StrSubstitutor(data);
            consumptionRequest = substitutor.replace(consumptionRequest);

            return Optional.ofNullable(consumptionRequest);
        } catch (IOException e) {
            logger.error("Something went wrong while creating consumption request: " + e.getMessage());
        }
        return Optional.empty();
    }

    private String chooseTemplate(ConsumptionRecord consumptionRecord) {
        String meterType = consumptionRecord.getMeterType();
        String consumptionFile = getFileTypeMapping().get(meterType);
        logger.debug("Template chosen: " + consumptionFile);
        return ResourceUtil.toPath(PATH + consumptionFile);
    }

    private Map<String, String> getFileTypeMapping() {
        Map<String, String> fileTypeMapping = new HashMap<>();
        fileTypeMapping.put("Gas", GAS);
        fileTypeMapping.put(ConsumptionMeterType.LOW_HIGH.value, ELECTRICITY_LOW_HIGH);
        fileTypeMapping.put(ConsumptionMeterType.LOW_HIGH_NIGHT_EXCLUSIVE.value, ELECTRICITY_LOW_HIGH_EXCLUSIVE_NIGHT);
        fileTypeMapping.put(ConsumptionMeterType.TOTAL_HOUR.value, ELECTRICITY_TOTAL_HOUR);
        fileTypeMapping.put(ConsumptionMeterType.TOTAL_HOUR_NIGHT_EXCLUSIVE.value, ELECTRICITY_TOTAL_HOUR_EXCLUSIVE_NIGHT);
        fileTypeMapping.put(ConsumptionMeterType.LOW_HIGH_TOTAL_HOUR.value, ELECTRICITY_LOW_HIGH_TOTAL_HOUR);
        return fileTypeMapping;
    }

    private BasePayload generatePayloadFromString(String data) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(BasePayload.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (BasePayload) unmarshaller.unmarshal(new StringReader(data));
    }
}
