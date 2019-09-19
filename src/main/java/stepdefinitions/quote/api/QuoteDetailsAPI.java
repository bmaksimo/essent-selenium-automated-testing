package stepdefinitions.quote.api;

import com.billinghouse.test_automation.util.random.CustomerRandomDataGenerator;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.restassured.create_contract.helper.PrepareDataForContract;
import com.essent.testing.util.resource.ResourceUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.Map;


/**
 * @author n.grkavac
 *
 */
public class QuoteDetailsAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(QuoteDetailsAPI.class);

    private static String PATH_TO_QUOTE = ConfigProvider.getProperty(ConfigKey.CRM_PATH_TO_QUOTE);
    private static String PATH_TO_PAYLOAD = ConfigProvider.getProperty(ConfigKey.CRM_PATH_TO_PAYLOAD);
    private static String PATH_TO_PAYLOAD_SUPPLIER_SWITCH = ConfigProvider.getProperty(ConfigKey.CRM_PATH_TO_PAYLOAD_SUPPLIER_SWITCH);

    public String getTariffSheetID(Cookies cookie, String startedFlowName) throws IOException {
        String tariffSheetID = null;
        Integer expectedResponseCode = STATUS_OK;
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
            + ConfigProvider.getProperty(ConfigKey.CRM_B2CCQ_URL);
        String payload = "";

        RequestHelper helper = new RequestHelper();
        Response tsResponse = helper.postRequest(expectedResponseCode, cookie, payload, path);

        tariffSheetID = getTarrifIDFromResponse(tsResponse);
        LOGGER.debug("TariffSheetID is: " + tariffSheetID);

        return tariffSheetID;
    }

    public QuoteDetails getQuoteDetails(Cookies cookie, String tariffSheetId, String startedFlowName, String meterOpen, String signInDate) throws IOException {

        String ean = null;
        String dateOfBirth = null;
        String ibanBE = null;
        String companyNumber = null;

        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_B2CCQ_URL);


        synchronized(this) {
            ean = PrepareDataForContract.generateEAN();
        }
        synchronized(this) {
            dateOfBirth = PrepareDataForContract.generateDOBForAnAdult();
        }

        synchronized(this) {
            ibanBE = PrepareDataForContract.getValidIbanBE();
        }

        synchronized(this) {
            companyNumber = PrepareDataForContract.generateValidBECompanyNumber();
        }

        QuoteDetails quoteDetails = new QuoteDetails();
        quoteDetails.setEan(ean);

        Map<String,String> generatedNames4account;
        generatedNames4account = CustomerRandomDataGenerator.createAccountName(startedFlowName);
        quoteDetails.setFirstName(generatedNames4account.get("firstName"));
        quoteDetails.setLastName(generatedNames4account.get("lastName"));
        quoteDetails.setAccountName(generatedNames4account.get("accountName"));
        quoteDetails.setiBan(ibanBE);
        quoteDetails.setCompanyNumber(companyNumber);

        LOGGER.debug("Generated EAN: " + ean);
        quoteDetails.setDateOfBirth(dateOfBirth);
        LOGGER.debug("Generated date of birth: " + dateOfBirth);

        String payload = createQuotePayload(tariffSheetId, ean, dateOfBirth, generatedNames4account.get("firstName"),generatedNames4account.get("lastName"), ibanBE, companyNumber, meterOpen, signInDate);

        Response quoteResponse = helper.postRequest(STATUS_CREATED, cookie, payload, path);

        LOGGER.debug("Quote created");
        String recordId = quoteResponse.jsonPath().getString("data.arguments.params.recordId");
        quoteDetails.setRecordId(recordId);
        LOGGER.debug("Record ID: " + recordId);
        String accountNumber = quoteResponse.jsonPath().getString("data.params.Account.account_number");
        quoteDetails.setAccountNumber(accountNumber);
        LOGGER.debug("Account number: " + accountNumber);
        String accountId = quoteResponse.jsonPath().getString("data.relatedBeans.Account[0]");
        quoteDetails.setAccountId(accountId);
        LOGGER.debug("Account ID: " + accountId);
        String quoteNumber = quoteResponse.jsonPath().getString("data.params.AOS_Quotes.quote_number");
        quoteDetails.setQuoteNumber(quoteNumber);
        LOGGER.debug("Quote Number: " + quoteNumber);
        String quoteId = quoteResponse.jsonPath().getString("data.relatedBeans.AOS_Quotes[0]");
        quoteDetails.setQuoteId(quoteId);
        LOGGER.debug("Quote Id: " + quoteId);

        return quoteDetails;
    }

    public String getQuoteNumber(Cookies cookie, String recordId) throws IOException {
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
            + ConfigProvider.getProperty(ConfigKey.CRM_QUOTES_ON_ACCOUNT_URL);
        String payload = createListQuotePayload(recordId);

        Response listQuoteResponse = helper.postRequest(STATUS_OK, cookie, payload, path);

        String quoteId = null;

        LOGGER.debug("Quote list retrieved");
        quoteId = listQuoteResponse.jsonPath().getString("data.rows[0].rowData.number");
        LOGGER.debug("Quote ID: " + quoteId);

        return quoteId;
    }

    public String checkStatus(Cookies cookie, String quoteNumber) throws IOException {
        String status = null;
        Response response = quoteStatus(cookie, quoteNumber);

        LOGGER.debug("Quote status retrieved");
        status = response.jsonPath().getString("data.model.ca_status_c");
        LOGGER.debug("Quote Status: " + status);

        return status;
    }

    public String getStatus(Cookies cookie, String quoteId) throws IOException {
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
            + ConfigProvider.getProperty(ConfigKey.CRM_QUOTELINES_URL);
        String payload = createQuoteLinesPayload(quoteId);

        Response statusResponse = helper.postRequest(STATUS_OK, cookie, payload, path);

        String status = null;

        LOGGER.debug("Quotelines retrieved");
        status = statusResponse.jsonPath().getString("data.rows[0].cells[1].options.line1");
        LOGGER.debug("Quotelinestatus is : " + status);

        return status;

    }

    public String checkStageStatus(Cookies cookie, String quoteNumber) throws IOException {
        String status = null;
        Response response = quoteStatus(cookie, quoteNumber);

        LOGGER.debug("Quote stage status retrieved");
        status = response.jsonPath().getString("data.model.stage");
        LOGGER.debug("Stage Status: " + status);

        return status;
    }

    public boolean checkIfEANexists(Cookies cookie, QuoteDetails quoteDetails) throws IOException {
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
            + ConfigProvider.getProperty(ConfigKey.CRM_QUOTELINES_URL);
        String payload = createQuoteLinesPayload(quoteDetails.getQuoteId());

        Response statusResponse = helper.postRequest(STATUS_OK, cookie, payload, path);

        boolean eanExists = false;

        LOGGER.debug("Quotelines retrieved");
        eanExists = statusResponse.jsonPath().getString("data.rows[0].rowData.ean_c").contains(quoteDetails.getEan());
        LOGGER.debug("EAN: " + quoteDetails.getEan() + " exists in Quotelines: " + eanExists);

        return eanExists;

    }

    private String createQuotePayload(String tariffSheetId, String ean, String dateOfBirth, String firstName, String lastName, String iBan, String companyNumber, String meterOpen, String signInDate)
	    throws  IOException {
        ObjectMapper mapper = new ObjectMapper();

        String pathToQuote = ResourceUtil.toPath(PATH_TO_QUOTE);
        String jsonQuote = new String(Files.readAllBytes(Paths.get(pathToQuote)));
        QuoteDetailsDTO quote = mapper.readValue(jsonQuote, QuoteDetailsDTO.class);

        String pathToPayload = ResourceUtil.toPath(PATH_TO_PAYLOAD);

        if (meterOpen.equals("On")) {
                pathToPayload = ResourceUtil.toPath(PATH_TO_PAYLOAD_SUPPLIER_SWITCH);
            }

        String jsonPayload = new String(Files.readAllBytes(Paths.get(pathToPayload)));
        PayloadDTO payload = mapper.readValue(jsonPayload, PayloadDTO.class);
        payload.setTariffsheetId(tariffSheetId);
        payload.setEan(ean);
        quote.getModel().setBirthdate(dateOfBirth);
        quote.getModel().setFirstName(firstName);
        quote.getModel().setLastName(lastName);
        quote.getModel().setIban(iBan);
        quote.getModel().setSignDateC(signInDate);
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
}
