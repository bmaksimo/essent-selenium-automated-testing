package stepdefinitions.quote.api;

import org.apache.log4j.Logger;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import stepdefinitions.quote.api.model.ContractDetails;
import stepdefinitions.quote.api.model.ContractsOnAccount;

/**
 * @author n.grkavac
 *
 */
public class ContractDetailsAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(ContractDetailsAPI.class);

    private static String ean = ConfigProvider.getProperty(ConfigKey.EAN_NUMBER);

    public ContractDetails getContractDetails(Cookies cookie, String recordId) throws JsonProcessingException {
    RequestHelper helper = new RequestHelper();
    String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
        + ConfigProvider.getProperty(ConfigKey.CRM_CONTRACTS_ON_ACCOUNT_URL);
    String payload = createContractPayload(recordId);

    Response contractResponse = helper.postRequest(STATUS_OK, cookie, payload, path);

    ContractDetails contractDetails = new ContractDetails();

    LOGGER.info("Contract created");
    String contractRecordId = contractResponse.jsonPath().getString("data.rows[0].id");
    contractDetails.setContractRecordId(contractRecordId);
    LOGGER.info("Contract record ID: " + contractRecordId);
    String contractNumber = contractResponse.jsonPath().getString("data.rows[0].cells[3].options.line1");
    contractDetails.setContractNumber(contractNumber);
    LOGGER.info("Contract number: " + contractNumber);
    String aosProductsId = contractResponse.jsonPath().getString("data.rows[0].cells[5].options.params.recordId");
    contractDetails.setAosProductsId(aosProductsId);
    LOGGER.info("Aos Products ID: " + aosProductsId);

    return contractDetails;

    }

    public boolean checkIfEanExists(Cookies cookie, String recordId) throws JsonProcessingException {
    RequestHelper helper = new RequestHelper();
    String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
        + ConfigProvider.getProperty(ConfigKey.CRM_CONTRACTED_EANS_ON_ACCOUNT_URL);
    String payload = createContractPayload(recordId);

    Response statusResponse = helper.postRequest(STATUS_OK, cookie, payload, path);

    boolean eanExists = false;

    LOGGER.info("Quotelines retrieved");
    eanExists = statusResponse.jsonPath().getString("data.rows[0].rowData.ean_c").contains(ean);
    LOGGER.info("EAN: " + ean + " exists in Quotelines: " + eanExists);

    return eanExists;

    }

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

    public boolean getContractStatus(Cookies cookie, String contractRecordId)
	    throws JsonProcessingException, InterruptedException {
	RequestHelper helper = new RequestHelper();
	String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
		+ ConfigProvider.getProperty(ConfigKey.CRM_CONTRACT_DETAILS_URL) + "/" + contractRecordId + "/"
		+ "readOnly";
	PayloadMapper mapper = new PayloadMapper();
	String payload = mapper.createPayload();

	Response contractDetailsResponse = helper.postRequest(STATUS_OK, cookie, payload, path);

	String contractStatus = null;

	LOGGER.info("Contract details retreived");
	contractStatus = contractDetailsResponse.jsonPath().getString("data.model.start_contract_status_c");
	LOGGER.info("Contract status: " + contractStatus);

	return "success".equals(contractStatus);

    }

    private String createContractPayload(String recordId) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    ContractsOnAccount contracts = new ContractsOnAccount();
    contracts.setRecordId(recordId);
    contracts.setPage(1);

    return mapper.writeValueAsString(contracts);
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
