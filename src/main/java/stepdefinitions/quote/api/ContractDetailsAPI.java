package stepdefinitions.quote.api;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import stepdefinitions.quote.api.helper.PayloadMapper;
import stepdefinitions.quote.api.helper.RequestHelper;
import stepdefinitions.quote.api.model.ContractDetails;
import stepdefinitions.quote.api.model.ContractsOnAccount;
import stepdefinitions.quote.api.model.QuoteDetails;
import stepdefinitions.quote.api.model.getOrderDetailsRequest;

import java.io.IOException;

/**
 * @author n.grkavac
 *
 */
public class ContractDetailsAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(ContractDetailsAPI.class);

    //private static String ean = ConfigProvider.getProperty(ConfigKey.EAN_NUMBER);

    public ContractDetails getContractDetails(Cookies cookie, QuoteDetails quoteDetails) throws IOException {
    RequestHelper helper = new RequestHelper();
    String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
        + ConfigProvider.getProperty(ConfigKey.CRM_CONTRACTS_ON_ACCOUNT_URL);
    String payload = createContractPayload(quoteDetails.getRecordId());

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
        String contractStartDate = contractResponse.jsonPath().getString("data.rows[0].rowData");
        contractStartDate = findContractStartDate(contractStartDate);
    contractDetails.setContractStartDate(contractStartDate);
    LOGGER.info("contractStartDate: " + contractStartDate);

    return contractDetails;

    }

    public boolean checkIfEanExists(Cookies cookie, QuoteDetails quoteDetails) throws IOException {

    RequestHelper helper = new RequestHelper();
    String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_CONTRACTED_EANS_ON_ACCOUNT_URL);

    String payload = createContractPayload(quoteDetails.getRecordId());

    Response statusResponse = helper.postRequest(STATUS_OK, cookie, payload, path);

    boolean eanExists = false;

    LOGGER.info("Quotelines retrieved");
    eanExists = statusResponse.jsonPath().getString("data.rows[0].rowData.ean_c").contains(quoteDetails.getEan());
    LOGGER.info("EAN: " + quoteDetails.getEan() + " exists in Quotelines: " + eanExists);

    return eanExists;

    }

    public String getPaymentDetails(Cookies cookie, String quoteId, ContractDetails contractDetails) throws IOException {
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
    contractDetails.setjBillingId(jbillingId);

	return jbillingId;

    }

    public boolean getContractStatus(Cookies cookie, String contractRecordId)
        throws IOException, InterruptedException {
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


    public boolean getOrderDetails(Cookies cookie, QuoteDetails quoteDetails, ContractDetails contractDetails ) throws IOException {
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.BILLING_ORDER_DETAILS_URL);

        String payload = createXMLPayload4OrderDetails(quoteDetails, contractDetails);

        Response getOrderDetailsResponse = helper.postXMLRequest(STATUS_OK, cookie, payload, path);
        //check for errors
        //check for "UserNotFoundException"
        //not contains <result>false</result>
        boolean resultStatus = false;
        String resultStatusStr;

        resultStatusStr = getOrderDetailsResponse.xmlPath().getString("//getOrderDetailsResponse/result");

        if (resultStatusStr.startsWith("true") ){resultStatus = true;}
        return resultStatus;
    }

    public String createXMLPayload4OrderDetails(QuoteDetails quoteDetails, ContractDetails contractDetails) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();

        xmlMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        getOrderDetailsRequest orderDetailsPayload = new getOrderDetailsRequest();

        orderDetailsPayload.setBillingId(contractDetails.getjBillingId());
        orderDetailsPayload.setEan(quoteDetails.getEan());
        orderDetailsPayload.setAskDate(contractDetails.getContractStartDate());
        orderDetailsPayload.setIncludeSettlement("true");
        orderDetailsPayload.setSettlementStatus("1");

        String xml = xmlMapper.writeValueAsString(orderDetailsPayload);
        return xml;
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

    private String findContractStartDate(String part) {
        String startDate = null;

        String[] s = part.split("\\|");
        for (String str : s) {
            if (str.contains("up_start_date_c")) {
                String result = str.split(":")[1];
                if (result.contains(",")) {
                    startDate = result.substring(0, result.indexOf(","));
                } else {
                    startDate = result;
                }
            }
        }
        return startDate;
    }

}
