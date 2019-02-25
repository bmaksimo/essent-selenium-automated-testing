package stepdefinitions.quote.api;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import stepdefinitions.quote.api.model.QuoteLines;


public class QuoteLineAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(QuoteStatusAPI.class);
    private static String ean = ConfigProvider.getProperty(ConfigKey.EAN_NUMBER);

    public boolean checkIfEANexists(Cookies cookie, String quoteId) throws JsonProcessingException {
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_QUOTELINES_URL);
        String payload = createQuoteLinesPayload(quoteId);

        Response statusResponse =  helper.postRequest(STATUS_OK, cookie, payload, path);


        boolean eanExists = false;


        if (statusResponse.getStatusCode() == STATUS_OK) {
            LOGGER.info("Quotelines retrieved");
            eanExists = statusResponse.jsonPath().getString("data.rows[0].rowData.ean_c").contains(ean);
            LOGGER.info("EAN: " + ean + " exists in Quotelines: " + eanExists);
        } else {
            LOGGER.error("Cannot retrieve quotelines");
        }

        return eanExists;

    }

    private String createQuoteLinesPayload(String quoteId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        QuoteLines quoteLines = new QuoteLines();
        quoteLines.setRecordId(quoteId);
        quoteLines.setPage(1);

        return mapper.writeValueAsString(quoteLines);
    }

    public String getStatus(Cookies cookie, String quoteId) throws JsonProcessingException {
	 RequestHelper helper = new RequestHelper();
	        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_QUOTELINES_URL);
	        String payload = createQuoteLinesPayload(quoteId);

	        Response statusResponse =  helper.postRequest(STATUS_OK, cookie, payload, path);


	        String status = null;


	        if (statusResponse.getStatusCode() == STATUS_OK) {
	            LOGGER.info("Quotelines retrieved");
	            status = statusResponse.jsonPath().getString("data.rows[0].cells[1].options.line1");
	            LOGGER.info("Quotelinestatus is : " + status);
	        } else {
	            LOGGER.error("Cannot retrieve quotelines");
	        }

	        return status;

    }

}
