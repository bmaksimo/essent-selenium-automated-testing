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
	String status = null;
	Response response = quoteStatus(cookie, quoteNumber);

	LOGGER.info("Quote status retrieved");
	status = response.jsonPath().getString("data.model.ca_status_c");
	LOGGER.info("Status: " + status);

	return status;
    }

    public String checkStageStatus(Cookies cookie, String quoteNumber) throws JsonProcessingException {
	String status = null;
	Response response = quoteStatus(cookie, quoteNumber);

	LOGGER.info("Quote stage status retrieved");
	status = response.jsonPath().getString("data.model.stage");
	LOGGER.info("Stage Status: " + status);

        return status;
    }

    private Response quoteStatus(Cookies cookie, String quoteNumber) throws JsonProcessingException {
	RequestHelper helper = new RequestHelper();
	String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
		+ ConfigProvider.getProperty(ConfigKey.CRM_QUOTE_STATUS_URL) + "/" + quoteNumber + "/" + "readOnly";
	PayloadMapper mapper = new PayloadMapper();
	String payload = mapper.createPayload();

	Response response = helper.postRequest(STATUS_OK, cookie, payload, path);
	return response;
    }

}
