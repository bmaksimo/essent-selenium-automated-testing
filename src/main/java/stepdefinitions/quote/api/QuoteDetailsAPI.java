package stepdefinitions.quote.api;

import com.billinghouse.test_automation.util.random.CustomerRandomDataGenerator;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.restassured.create_contract.helper.PrepareDataForContract;
import com.essent.testing.util.resource.ResourceUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import stepdefinitions.dwp.tables.CustomerDetails;
import stepdefinitions.quote.api.builders.QuoteDetailsBuilder;
import stepdefinitions.quote.api.builders.QuoteDetailsDTOBuilder;
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

public class QuoteDetailsAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(QuoteDetailsAPI.class);

    private static String PATH_TO_QUOTE = ConfigProvider.getProperty(ConfigKey.CRM_PATH_TO_QUOTE);
    private static String PATH_TO_PAYLOAD = ConfigProvider.getProperty(ConfigKey.CRM_PATH_TO_PAYLOAD);
    private static String PATH_TO_PAYLOAD_SUPPLIER_SWITCH = ConfigProvider.getProperty(ConfigKey.CRM_PATH_TO_PAYLOAD_SUPPLIER_SWITCH);

    public String getTariffSheetID(Cookies cookie, String startedFlowName) throws IOException {
        String payload = "";
        Response response = new RequestHelper().postRequest(STATUS_OK, cookie, payload, buildCreateQuotePath());

        String tariffSheetID = getTarrifIDFromResponse(response);
        LOGGER.debug("TariffSheetID is: " + tariffSheetID);

        return tariffSheetID;
    }

    public QuoteDetails getQuoteDetails(Cookies cookie, String tariffSheetId, String startedFlowName, String meterOpen, String signInDate, String contactPreference) throws IOException {
        String ean = PrepareDataForContract.generateEAN();
        String birthDate = PrepareDataForContract.generateDOBForAnAdult();
        String ibanBE = PrepareDataForContract.getValidIbanBE();
        String companyNumber = PrepareDataForContract.generateValidBECompanyNumber();
        Map<String, String> accountNames = CustomerRandomDataGenerator.createAccountName(startedFlowName);

        QuoteDetailsDTO dto = buildQuoteDetailsDTO(tariffSheetId, meterOpen, signInDate, ean, birthDate, ibanBE, accountNames, contactPreference);
        String payload = new ObjectMapper().writeValueAsString(dto);
        Response quoteResponse = new RequestHelper().postRequest(STATUS_CREATED, cookie, payload, buildCreateQuotePath());

        QuoteDetails quoteDetails = buildQuoteDetails(ean, birthDate, ibanBE, companyNumber, accountNames, quoteResponse);
        LOGGER.debug("Quote details: " + quoteDetails.toString());
        return quoteDetails;
    }

    private QuoteDetailsDTO buildQuoteDetailsDTO(String tariffSheetId, String meterOpen, String signInDate, String ean, String birthDate, String ibanBE, Map<String, String> generatedNames4account, String contactPreference) throws IOException {
        return new QuoteDetailsDTOBuilder()
                .withBirthdate(birthDate)
                .withFirstName(generatedNames4account.get("firstName"))
                .withLastName(generatedNames4account.get("lastName"))
                .withGeneralChannel(contactPreference)
                .withIban(ibanBE)
                .withSignInDate(signInDate)
                .withPayload(meterOpen, tariffSheetId, ean)
                .build();
    }

    private QuoteDetails buildQuoteDetails(String ean, String dateOfBirth, String ibanBE, String companyNumber, Map<String, String> generatedNames4account, Response quoteResponse) {
        JsonPath jsonPath = quoteResponse.jsonPath();
        return new QuoteDetailsBuilder()
                .withEan(ean)
                .withFirstName(generatedNames4account.get("firstName"))
                .withLastName(generatedNames4account.get("lastName"))
                .withAccountName(generatedNames4account.get("accountName"))
                .withIban(ibanBE)
                .withCompanyNumber(companyNumber)
                .withDateOfBirth(dateOfBirth)
                .withRecordId(jsonPath.getString("data.arguments.params.recordId"))
                .withAccountNumber(jsonPath.getString("data.params.Account.account_number"))
                .withAccountId(jsonPath.getString("data.relatedBeans.Account[0]"))
                .withQuoteNumber(jsonPath.getString("data.params.AOS_Quotes.quote_number"))
                .withQuoteId(jsonPath.getString("data.relatedBeans.AOS_Quotes[0]"))
                .build();
    }

    public String getQuoteNumber(Cookies cookie, String recordId) throws IOException {
        String path = buildRetrieveQuotesPath(ConfigKey.CRM_QUOTES_ON_ACCOUNT_URL);
        String payload = createListQuotePayload(recordId);

        Response listQuoteResponse = new RequestHelper().postRequest(STATUS_OK, cookie, payload, path);

        LOGGER.debug("Quote list retrieved");
        String quoteId = listQuoteResponse.jsonPath().getString("data.rows[0].rowData.number");
        LOGGER.debug("Quote ID: " + quoteId);

        return quoteId;
    }

    private String buildCreateQuotePath() {
        return ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_B2CCQ_URL);
    }

    private String buildRetrieveQuotesPath(ConfigKey crmQuotesOnAccountUrl) {
        return ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(crmQuotesOnAccountUrl);
    }

    public String checkStatus(Cookies cookie, String quoteNumber) throws IOException {
        Response response = quoteStatus(cookie, quoteNumber);
        LOGGER.debug("Quote status retrieved");
        String status = response.jsonPath().getString("data.model.ca_status_c");
        LOGGER.debug("Quote Status: " + status);
        return status;
    }

    public String getStatus(Cookies cookie, String quoteId) throws IOException {
        String path = buildRetrieveQuotesPath(ConfigKey.CRM_QUOTELINES_URL);
        String payload = createQuoteLinesPayload(quoteId);

        Response statusResponse = new RequestHelper().postRequest(STATUS_OK, cookie, payload, path);

        LOGGER.debug("Quotelines retrieved");
        String status = statusResponse.jsonPath().getString("data.rows[0].cells[1].options.line1");
        LOGGER.debug("Quotelinestatus is : " + status);
        return status;
    }

    public String checkStageStatus(Cookies cookie, String quoteNumber) throws IOException {
        Response response = quoteStatus(cookie, quoteNumber);
        LOGGER.debug("Quote stage status retrieved");
        String status = response.jsonPath().getString("data.model.stage");
        LOGGER.debug("Stage Status: " + status);

        return status;
    }

    public boolean checkIfEANexists(Cookies cookie, QuoteDetails quoteDetails) throws IOException {
        RequestHelper helper = new RequestHelper();
        String path = buildRetrieveQuotesPath(ConfigKey.CRM_QUOTELINES_URL);
        String payload = createQuoteLinesPayload(quoteDetails.getQuoteId());

        Response statusResponse = helper.postRequest(STATUS_OK, cookie, payload, path);

        boolean eanExists = false;

        LOGGER.debug("Quotelines retrieved");
        eanExists = statusResponse.jsonPath().getString("data.rows[0].rowData.ean_c").contains(quoteDetails.getEan());
        LOGGER.debug("EAN: " + quoteDetails.getEan() + " exists in Quotelines: " + eanExists);

        return eanExists;
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
