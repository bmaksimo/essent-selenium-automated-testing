package stepdefinitions.quote.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.util.resource.ResourceUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import stepdefinitions.quote.api.model.Payload;
import stepdefinitions.quote.api.model.QuoteDetails;

public class QuoteDetailsAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(QuoteDetailsAPI.class);

    public String createPayload() throws JsonParseException, JsonMappingException, IOException {
        String path = ResourceUtil.toPath("/data/restassured/payload_for_create_quote.json");
        String jsonPayload = new String(Files.readAllBytes(Paths.get(path)));
        ObjectMapper mapper = new ObjectMapper();

        Payload payload = mapper.readValue(jsonPayload, Payload.class);
        String pathToQuote = ResourceUtil.toPath("/data/restassured/model_for_create_quote.json");
        String jsonQuote = new String(Files.readAllBytes(Paths.get(pathToQuote)));
        QuoteDetails quote = mapper.readValue(jsonQuote, QuoteDetails.class);
        quote.getModel().getPayloadWrapper().setPayload(payload);
        return mapper.writeValueAsString(quote);
//        return jsonQuote;
    }

    public Response getResponse(Cookies cookie) throws JsonParseException, JsonMappingException, IOException {
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)+ConfigProvider.getProperty(ConfigKey.CRM_B2CCQ_URL);
        String payload = createPayload();
        Response quoteResponse =  helper.postRequest(STATUS_CREATED, cookie, payload, path);

        if (quoteResponse.getStatusCode() == STATUS_CREATED) {
            LOGGER.info("Quote created");
        } else {
            LOGGER.error("Cannot create quote");
        }

        return quoteResponse;

    }


}
