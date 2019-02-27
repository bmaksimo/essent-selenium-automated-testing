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
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_BILLING_DETAILS_URL) + "/" + quoteId + "/" + "readOnly";
        PayloadMapper mapper = new PayloadMapper();
        String payload = mapper.createPayload();

        Response quoteDetailsResponse =  helper.postRequest(STATUS_OK, cookie, payload, path);

        String jbillingId = null;

        if (quoteDetailsResponse.getStatusCode() == STATUS_OK) {
            LOGGER.info("Billing details retrieved");
            jbillingId  = getBillingIdFromResponse(quoteDetailsResponse);
            LOGGER.info("JBilling ID: " + jbillingId);
        } else {
            LOGGER.error("Cannot retrieve Billing details");
        }

        return jbillingId;

    }

    public String getContractStatus(Cookies cookie, String contractRecordId) throws JsonProcessingException {
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_CONTRACT_DETAILS_URL) + "/" + contractRecordId + "/" + "readOnly";
        PayloadMapper mapper = new PayloadMapper();
        String payload = mapper.createPayload();

        Response contractDetailsResponse = helper.postRequest(STATUS_OK, cookie, payload, path);

        String contractStatus = null;

        if (contractDetailsResponse.getStatusCode() == STATUS_OK) {
            LOGGER.info("Contract details retreived");
            contractStatus = contractDetailsResponse.jsonPath().getString("data.model.start_contract_status_c");
            LOGGER.info("Contract status: " + contractStatus);
        } else {
            LOGGER.error("Cannot retrieve contract status");
        }

        return contractStatus;
    }

    private String getBillingIdFromResponse(Response response) {
        String id = null;
        String part = response.jsonPath().getString("data.model");
        String[] s = part.split("\\|");
        for (String str : s) {
            if (str.contains("billingcustomerid")){
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
