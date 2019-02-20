package stepdefinitions.quote.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.apache.log4j.Logger;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.util.resource.ResourceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import stepdefinitions.quote.api.model.QuoteDetails;
import stepdefinitions.quote.api.model.dto.QuoteSignatureDTO;

public class QuoteSignatureAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(QuoteSignatureAPI.class);

    public void setSignatureReceived(Cookies cookie, QuoteDetails quoteDetails) throws IOException {
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_SIGNATURE_RECEIVED_URL);
        String payload = createSignaturePayload(quoteDetails);

        Response signatureResponse =  helper.postRequest(STATUS_CREATED, cookie, payload, path);

        if (signatureResponse.getStatusCode() == STATUS_OK) {
            LOGGER.info("Quote status retrieved");
        } else {
            LOGGER.error("Cannot retrieve quote status");
        }
    }

    private String createSignaturePayload(QuoteDetails quoteDetails) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String pathToSignPayload = ResourceUtil.toPath("/data/restassured/payload_for_signature_received.json");
        String jsonSignPayload = new String(Files.readAllBytes(Paths.get(pathToSignPayload)));
        QuoteSignatureDTO signature = mapper.readValue(jsonSignPayload, QuoteSignatureDTO.class);

        signature.getModel().setAccountsId(quoteDetails.getRecordId());
        signature.getModel().setId(quoteDetails.getQuoteId());

        LocalDate currentDate = LocalDate.now();
        LocalDate validUntilDate = currentDate.plusDays(15);
        signature.getModel().setSignatureReceivedDate(currentDate.toString());
        signature.getModel().setValidUntil(validUntilDate.toString());

        return mapper.writeValueAsString(signature);
    }


}
