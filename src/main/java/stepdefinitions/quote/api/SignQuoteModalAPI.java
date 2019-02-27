package stepdefinitions.quote.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.util.resource.ResourceUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import stepdefinitions.quote.api.model.dto.SignContractDTO;

public class SignQuoteModalAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(QuoteDetailsAPI.class);


    public String confirmSigning(Cookies cookie, String rowId, String docId) throws IOException {
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_SIGN_QUOTE_MODAL);
        String payload = signQuoteModalPayload(rowId, docId);

        Response signQuoteResponse = helper.postRequest(STATUS_CREATED, cookie, payload, path);
        String confirmSigning = null;

        if (signQuoteResponse.getStatusCode() == STATUS_CREATED) {
            LOGGER.info("Quote signed");
            confirmSigning = signQuoteResponse.getBody().toString();
            LOGGER.info("");
        } else {
            LOGGER.error("Cannot retrieve quote list");
        }

        return confirmSigning;
    }

    private String signQuoteModalPayload(String rowId, String docId) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        String pathToPayload = ResourceUtil.toPath("/data/restassured/payload_for_sign_quote_modal.json");
        String jsonPayload = new String(Files.readAllBytes(Paths.get(pathToPayload)));
        SignContractDTO signContract = mapper.readValue(jsonPayload, SignContractDTO.class);
        signContract.getContractModeDTO().setRecordId(rowId);
        signContract.getContractModeDTO().setDwpId(rowId);
        signContract.getContractModeDTO().setId(rowId);
        signContract.getContractModeDTO().setSignDate(getTodaysDate().toString());
        signContract.getContractModeDTO().setSignedcContractDocguid(docId);
        List<String> signedContractDocId = new ArrayList<>();

        signContract.getContractModeDTO().setSignedContractDocguidC(signedContractDocId);

        return mapper.writeValueAsString(signContract);
    }

    private LocalDate getTodaysDate() {
        return LocalDate.now();
    }
}
