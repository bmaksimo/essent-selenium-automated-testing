package stepdefinitions.quote.api;

import org.apache.log4j.Logger;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.http.Cookies;
import io.restassured.response.Response;

public class QuoteStatusAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(QuoteStatusAPI.class);

    public String checkStatus(Cookies cookie, String quoteNumber) throws JsonProcessingException {
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_QUOTE_STATUS_URL) + "/" + quoteNumber + "/" + "readOnly";
        PayloadMapper mapper = new PayloadMapper();
        String payload = mapper.createPayload();

        Response statusResponse =  helper.postRequest(STATUS_OK, cookie, payload, path);

        String status = null;

        if (statusResponse.getStatusCode() == STATUS_OK) {
            LOGGER.info("Quote status retrieved");
            status  = statusResponse.jsonPath().getString("data.model.ca_status_c");
            LOGGER.info("Status: " + status);
        } else {
            LOGGER.error("Cannot retrieve quote status");
        }

        return status;

    }

}
