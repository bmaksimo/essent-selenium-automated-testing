package stepdefinitions.quote.api;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.restassured.create_contract.helper.PrepareDataForContract;
import com.essent.testing.util.resource.ResourceUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import stepdefinitions.quote.api.helper.PayloadMapper;
import stepdefinitions.quote.api.helper.RequestHelper;
import stepdefinitions.quote.api.model.QuoteDetails;
import stepdefinitions.quote.api.model.QuoteLines;
import stepdefinitions.quote.api.model.QuotesOnAccount;
import stepdefinitions.quote.api.model.dto.PayloadDTO;
import stepdefinitions.quote.api.model.dto.QuoteDetailsDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//import java.text.DateFormat;

/**
 * @author n.grkavac
 *
 */
public class QuoteDetailsAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(QuoteDetailsAPI.class);


    private static String PATH_TO_QUOTE = ConfigProvider.getProperty(ConfigKey.CRM_PATH_TO_QUOTE);
    private static String PATH_TO_PAYLOAD = ConfigProvider.getProperty(ConfigKey.CRM_PATH_TO_PAYLOAD);

    public String getTariffSheetID(Cookies cookie, String startedFlowName) throws IOException {
    String tariffSheetID = null;
    Integer expectedResponseCode = STATUS_OK;
    String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
        + ConfigProvider.getProperty(ConfigKey.CRM_B2CCQ_URL);
    String payload = "";

    RequestHelper helper = new RequestHelper();
    Response tsResponse = helper.postRequest(expectedResponseCode, cookie, payload, path);

    tariffSheetID = getTarrifIDFromResponse(tsResponse);
    // tariffSheetID= tsResponse.jsonPath()
    // .getString("'data.model.accounts|aos_quotes|aos_products_quotes|tariffsheet_id'");
    LOGGER.info("TariffSheetID is: " + tariffSheetID);

    return tariffSheetID;
    }

    public QuoteDetails getQuoteDetails(Cookies cookie, String tariffSheetId, String startedFlowName)
	    throws JsonParseException, JsonMappingException, IOException {

        String ean = null;
        String dateOfBirth = null;
	RequestHelper helper = new RequestHelper();
	String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
		+ ConfigProvider.getProperty(ConfigKey.CRM_B2CCQ_URL);


    synchronized(this) {
        ean = PrepareDataForContract.generateEAN();
    }
        synchronized(this) {
            dateOfBirth = PrepareDataForContract.generateDOBForAnAdult();
        }

    QuoteDetails quoteDetails = new QuoteDetails();
    quoteDetails.setEan(ean);

        Map<String,String> generatedNames4account = new HashMap<String, String>();
        generatedNames4account = createAccountName(startedFlowName);
        quoteDetails.setFirstName(generatedNames4account.get("firstName"));
        quoteDetails.setLastName(generatedNames4account.get("lastName"));
        quoteDetails.setAccountName(generatedNames4account.get("accountName"));

    LOGGER.info("Generated EAN: " + ean);
        quoteDetails.setDateOfBirth(dateOfBirth);
    LOGGER.info("Generated date of birth: " + dateOfBirth);

	String payload = createQuotePayload(tariffSheetId, ean, dateOfBirth, generatedNames4account.get("firstName"),generatedNames4account.get("lastName"));

	Response quoteResponse = helper.postRequest(STATUS_CREATED, cookie, payload, path);

	LOGGER.info("Quote created");
	String recordId = quoteResponse.jsonPath().getString("data.arguments.params.recordId");
	quoteDetails.setRecordId(recordId);
	LOGGER.info("Record ID: " + recordId);
	String accountNumber = quoteResponse.jsonPath().getString("data.params.Account.account_number");
	quoteDetails.setAccountNumber(accountNumber);
	LOGGER.info("Account number: " + accountNumber);
	String accountId = quoteResponse.jsonPath().getString("data.relatedBeans.Account[0]");
	quoteDetails.setAccountId(accountId);
	LOGGER.info("Account ID: " + accountId);
	String quoteNumber = quoteResponse.jsonPath().getString("data.params.AOS_Quotes.quote_number");
	quoteDetails.setQuoteNumber(quoteNumber);
	LOGGER.info("Quote Number: " + quoteNumber);
	String quoteId = quoteResponse.jsonPath().getString("data.relatedBeans.AOS_Quotes[0]");
	quoteDetails.setQuoteId(quoteId);
	LOGGER.info("Quote Id: " + quoteId);


	return quoteDetails;

    }

    public String getQuoteNumber(Cookies cookie, String recordId) throws IOException {
	RequestHelper helper = new RequestHelper();
	String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
		+ ConfigProvider.getProperty(ConfigKey.CRM_QUOTES_ON_ACCOUNT_URL);
	String payload = createListQuotePayload(recordId);

	Response listQuoteResponse = helper.postRequest(STATUS_OK, cookie, payload, path);

	String quoteId = null;

	LOGGER.info("Quote list retrieved");
	quoteId = listQuoteResponse.jsonPath().getString("data.rows[0].rowData.number");
	LOGGER.info("Quote ID: " + quoteId);

	return quoteId;
    }

    public String checkStatus(Cookies cookie, String quoteNumber) throws IOException {
    String status = null;
    Response response = quoteStatus(cookie, quoteNumber);

    LOGGER.info("Quote status retrieved");
    status = response.jsonPath().getString("data.model.ca_status_c");
    LOGGER.info("Status: " + status);

    return status;
    }

    public String getStatus(Cookies cookie, String quoteId) throws IOException {
    RequestHelper helper = new RequestHelper();
    String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
        + ConfigProvider.getProperty(ConfigKey.CRM_QUOTELINES_URL);
    String payload = createQuoteLinesPayload(quoteId);

    Response statusResponse = helper.postRequest(STATUS_OK, cookie, payload, path);

    String status = null;

    LOGGER.info("Quotelines retrieved");
    status = statusResponse.jsonPath().getString("data.rows[0].cells[1].options.line1");
    LOGGER.info("Quotelinestatus is : " + status);

    return status;

    }

    public String checkStageStatus(Cookies cookie, String quoteNumber) throws IOException {
    String status = null;
    Response response = quoteStatus(cookie, quoteNumber);

    LOGGER.info("Quote stage status retrieved");
    status = response.jsonPath().getString("data.model.stage");
    LOGGER.info("Stage Status: " + status);

        return status;
    }

    public boolean checkIfEANexists(Cookies cookie, QuoteDetails quoteDetails) throws IOException {
    RequestHelper helper = new RequestHelper();
    String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
        + ConfigProvider.getProperty(ConfigKey.CRM_QUOTELINES_URL);
    String payload = createQuoteLinesPayload(quoteDetails.getQuoteId());

    Response statusResponse = helper.postRequest(STATUS_OK, cookie, payload, path);

    boolean eanExists = false;

    LOGGER.info("Quotelines retrieved");
    eanExists = statusResponse.jsonPath().getString("data.rows[0].rowData.ean_c").contains(quoteDetails.getEan());
    LOGGER.info("EAN: " + quoteDetails.getEan() + " exists in Quotelines: " + eanExists);

    return eanExists;

    }

    private String createQuotePayload(String tariffSheetId, String ean, String dateOfBirth, String firstName, String lastName)
	    throws JsonParseException, JsonMappingException, IOException {
	ObjectMapper mapper = new ObjectMapper();

	String pathToQuote = ResourceUtil.toPath(PATH_TO_QUOTE);
	String jsonQuote = new String(Files.readAllBytes(Paths.get(pathToQuote)));
	QuoteDetailsDTO quote = mapper.readValue(jsonQuote, QuoteDetailsDTO.class);

	String pathToPayload = ResourceUtil.toPath(PATH_TO_PAYLOAD);
	String jsonPayload = new String(Files.readAllBytes(Paths.get(pathToPayload)));
	PayloadDTO payload = mapper.readValue(jsonPayload, PayloadDTO.class);
	payload.setTariffsheetId(tariffSheetId);
	payload.setEan(ean);
	quote.getModel().setBirthdate(dateOfBirth);
        quote.getModel().setFirstName(firstName);
        quote.getModel().setLastName(lastName);
	quote.getModel().getPayloadWrapper().setPayload(payload);


	return mapper.writeValueAsString(quote);
    }

    private String createListQuotePayload(String recordId) throws JsonProcessingException {
	ObjectMapper mapper = new ObjectMapper();

	QuotesOnAccount quotes = new QuotesOnAccount();
	quotes.setRecordId(recordId);
	quotes.setRecordType("Accounts");
	quotes.setPage(1);

	return mapper.writeValueAsString(quotes);
    }

    private Response quoteStatus(Cookies cookie, String quoteNumber) throws IOException {
    RequestHelper helper = new RequestHelper();
    String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
        + ConfigProvider.getProperty(ConfigKey.CRM_QUOTE_STATUS_URL) + "/" + quoteNumber + "/" + "readOnly";
    PayloadMapper mapper = new PayloadMapper();
    String payload = mapper.createPayload();

    Response response = helper.postRequest(STATUS_OK, cookie, payload, path);
    return response;
    }

    private String createQuoteLinesPayload(String quoteId) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    QuoteLines quoteLines = new QuoteLines();
    quoteLines.setRecordId(quoteId);
    quoteLines.setPage(1);

    return mapper.writeValueAsString(quoteLines);
    }

    private String getTarrifIDFromResponse(Response tsResponse) {
    String id = null;
    String part = tsResponse.jsonPath().getString("data.model");
    String[] s = part.split("\\|");
    for (String str : s) {
        if (str.contains("tariffsheet_id")) {
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


    private String getCurrentDateTime() {
        Date now = new Date();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy_HHmmSS");
        return dateFormat.format(now);
    }
    private Map<String,String> createAccountName(String startedFlowName){

        String firstName = "default";
        String lastName = "default";
        String accountName = "API_" + startedFlowName + "_BasicQuoteB2C_TC1_YMR_MoveIn";
        String reversedLastName = "";
        Map<String,String> generatedNames = new HashMap<String, String>();

        accountName = accountName + getCurrentDateTime();

        int nameLength = accountName.length();
        if (nameLength > 35){
            firstName = accountName.substring(0,35);
            lastName = "L" + accountName.substring(35,nameLength);
            if (lastName.length() > 35){
                StringBuilder sb=new StringBuilder(lastName);
                reversedLastName = String.valueOf(sb.reverse());
                lastName = reversedLastName.substring(35,nameLength);
                StringBuilder sb2 = new StringBuilder(lastName);
                lastName = String.valueOf(sb2);
            }
        }else{
            firstName = accountName.substring(0,nameLength-2);
            lastName = "L" + accountName.substring(nameLength-2,nameLength);
        }

        generatedNames.put("firstName",firstName);
        generatedNames.put("lastName", lastName);
        generatedNames.put("accountName", accountName);



        LOGGER.info("First name: " + generatedNames.get("firstName"));
        LOGGER.info("Last name: " + generatedNames.get("lastName"));
        LOGGER.info("Account name: " + generatedNames.get("accountName"));

        return generatedNames;

    }
}
