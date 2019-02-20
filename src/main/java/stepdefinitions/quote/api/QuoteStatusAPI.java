package stepdefinitions.quote.api;

import org.apache.log4j.Logger;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import stepdefinitions.quote.api.model.StatusCheck;

public class QuoteStatusAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(QuoteStatusAPI.class);

    public String checkStatus(Cookies cookie, String quoteId) throws JsonProcessingException {
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_QUOTE_STATUS_URL) + "/" + quoteId + "/" + "readOnly";
        String payload = createCheckPayload();

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

    private String createCheckPayload() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // this is needed because we need to pass an empty model
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        StatusCheck check = new StatusCheck();
        check.setModel(new StatusCheck.Model());
        return mapper.writeValueAsString(check);
    }



}
