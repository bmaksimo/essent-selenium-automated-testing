package stepdefinitions.quote.api;

import org.apache.log4j.Logger;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.http.Cookies;
import io.restassured.response.Response;

public class ContractDetailsAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(ContractDetailsAPI.class);

    public String getPaymentDetails(Cookies cookie, String quoteId) throws JsonProcessingException {
	RequestHelper helper = new RequestHelper();
	String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
		+ ConfigProvider.getProperty(ConfigKey.CRM_BILLING_DETAILS_URL) + "/" + quoteId + "/" + "readOnly";
	PayloadMapper mapper = new PayloadMapper();
	String payload = mapper.createPayload();

	Response quoteDetailsResponse = helper.postRequest(STATUS_OK, cookie, payload, path);

	String jbillingId = null;

	LOGGER.info("Billing details retrieved");
	jbillingId = getBillingIdFromResponse(quoteDetailsResponse);
	LOGGER.info("JBilling ID: " + jbillingId);

	return jbillingId;

    }

    private String getBillingIdFromResponse(Response response) {
	String id = null;
	String part = response.jsonPath().getString("data.model");
	String[] s = part.split("\\|");
	for (String str : s) {
	    if (str.contains("billingcustomerid")) {
		String result = str.split(":")[1];
		if (result.contains(",")) {
		    id = result.substring(0, result.indexOf(","));
		} else {
		    id = result;
		}
	    }
	}

	return id;
    }

}
