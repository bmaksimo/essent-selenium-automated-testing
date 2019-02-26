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

    public String checkStatus(Cookies cookie, String quoteNumber) throws JsonProcessingException {
	String status = null;
	Response response = quoteStatus(cookie, quoteNumber);
	if (response.getStatusCode() == STATUS_OK) {
	    LOGGER.info("Quote status retrieved");
	    status = response.jsonPath().getString("data.model.ca_status_c");
	    LOGGER.info("Status: " + status);
	} else {
	    LOGGER.error("Cannot retrieve quote status");
	}

	return status;

    }

    public String checkStageStatus(Cookies cookie, String quoteNumber) throws JsonProcessingException {
	String status = null;
	Response response = quoteStatus(cookie, quoteNumber);
	if (response.getStatusCode() == STATUS_OK) {
	    LOGGER.info("Quote stage status retrieved");
	    status = response.jsonPath().getString("data.model.stage");
	    LOGGER.info("Stage Status: " + status);
	} else {
	    LOGGER.error("Cannot retrieve quote stage status");
	}

	return status;
    }

    private Response quoteStatus(Cookies cookie, String quoteNumber) throws JsonProcessingException {
	RequestHelper helper = new RequestHelper();
	String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
		+ ConfigProvider.getProperty(ConfigKey.CRM_QUOTE_STATUS_URL) + "/" + quoteNumber + "/" + "readOnly";
	String payload = createCheckPayload();

	Response response = helper.postRequest(STATUS_OK, cookie, payload, path);
	return response;
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
