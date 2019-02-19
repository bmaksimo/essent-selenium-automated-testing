package stepdefinitions.quote.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.util.resource.ResourceUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import stepdefinitions.quote.api.model.QuotesOnAccount;
import stepdefinitions.quote.api.model.dto.PayloadDTO;
import stepdefinitions.quote.api.model.dto.QuoteDetailsDTO;

public class QuoteDetailsAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(QuoteDetailsAPI.class);

    public String getRecordId(Cookies cookie, String tariffSheetId) throws JsonParseException, JsonMappingException, IOException {
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_B2CCQ_URL);
        String payload = createQuotePayload(tariffSheetId);

        Response quoteResponse =  helper.postRequest(STATUS_CREATED, cookie, payload, path);

        String recordId = null;

        if (quoteResponse.getStatusCode() == STATUS_CREATED) {
            LOGGER.info("Quote created");
            recordId  = quoteResponse.jsonPath().getString("data.arguments.params.recordId");
            LOGGER.info("Record ID: " + recordId);
            String accountNumber  = quoteResponse.jsonPath().getString("data.params.Account.account_number");
            LOGGER.info("Account number: " + accountNumber);
            String accountId  = quoteResponse.jsonPath().getString("data.relatedBeans.Account");
            LOGGER.info("Account ID: " + accountId);
            String quoteId  = quoteResponse.jsonPath().getString("data.params.AOS_Quotes.quote_number");
            LOGGER.info("Quote ID: " + quoteId);
            String quoteNumber  = quoteResponse.jsonPath().getString("data.relatedBeans.AOS_Quotes");
            LOGGER.info("Quote number: " + quoteNumber);

        } else {
            LOGGER.error("Cannot create quote");
        }

        return recordId;

    }

    public String getQuoteId(Cookies cookie, String recordId) throws JsonProcessingException {
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_QUOTES_ON_ACCOUNT_URL);
        String payload = createListQuotePayload(recordId);

        Response listQuoteResponse = helper.postRequest(STATUS_OK, cookie, payload, path);

        String quoteId = null;

        if (listQuoteResponse.getStatusCode() == STATUS_OK) {
            LOGGER.info("Quote list retrieved");
            quoteId = listQuoteResponse.jsonPath().getString("data.rows.rowData.number");
            LOGGER.info("Quote ID: " + quoteId);
        } else {
            LOGGER.error("Cannot retrieve quote list");
        }

        return quoteId;
    }

    private String createQuotePayload(String tariffSheetId) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        String pathToQuote = ResourceUtil.toPath("/data/restassured/model_for_create_quote.json");
        String jsonQuote = new String(Files.readAllBytes(Paths.get(pathToQuote)));
        QuoteDetailsDTO quote = mapper.readValue(jsonQuote, QuoteDetailsDTO.class);

        String pathToPayload = ResourceUtil.toPath("/data/restassured/payload_for_create_quote.json");
        String jsonPayload = new String(Files.readAllBytes(Paths.get(pathToPayload)));
        PayloadDTO payload = mapper.readValue(jsonPayload, PayloadDTO.class);
        payload.setTariffsheetId(tariffSheetId);

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


}
