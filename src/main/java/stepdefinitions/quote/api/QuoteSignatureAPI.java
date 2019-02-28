package stepdefinitions.quote.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.util.resource.ResourceUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import stepdefinitions.quote.api.model.QuoteDetails;
import stepdefinitions.quote.api.model.dto.QuoteSignatureDTO;
import stepdefinitions.quote.api.model.dto.SignContractDTO;

/**
 * @author n.grkavac
 *
 */
public class QuoteSignatureAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(QuoteSignatureAPI.class);

    public void setSignatureReceived(Cookies cookie, QuoteDetails quoteDetails) throws IOException {
	RequestHelper helper = new RequestHelper();
	String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
		+ ConfigProvider.getProperty(ConfigKey.CRM_SIGNATURE_RECEIVED_URL);
	String payload = createSignaturePayload(quoteDetails);

	helper.postRequest(STATUS_CREATED, cookie, payload, path);
	LOGGER.info("Quote signature retrieved request is sent");
    }

    public String uploadSignature(Cookies cookie, QuoteDetails quoteDetails) {
    RequestHelper helper = new RequestHelper();
    String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
        + ConfigProvider.getProperty(ConfigKey.CRM_SIGNATURE_UPLOAD_URL);
    Map<String, String> payload = createUploadPayload(quoteDetails);

    Response signatureResponse = helper.postMultipartRequest(STATUS_OK, cookie, payload, path);
    String docId = null;

    LOGGER.info("Signature response retrieved");
    docId = signatureResponse.jsonPath().get("data.id");
    LOGGER.info("Document ID: " + docId);

    return docId;
    }

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

    private Map<String, String> createUploadPayload(QuoteDetails quoteDetails) {
    Map<String, String> uploadMap = new HashMap<>();
    uploadMap.put("model[id]", quoteDetails.getQuoteId());
    uploadMap.put("model[dwp|id]", quoteDetails.getQuoteId());
    // hardcoded firstName + lastName from model
    uploadMap.put("model[accounts|name]", "vvz Electricity_Fix_TC1_YMR_CUPQ_B2 LC_Power2B_MoveIn 0204 110343");
    uploadMap.put("model[dwp|recordType]", "AOS_Quotes");
    uploadMap.put("model[accounts|company_number_c]", "BE0177446949");
    uploadMap.put("model[stage]", "SIGNED");
    uploadMap.put("model[accounts|id]", quoteDetails.getRecordId());
    uploadMap.put("model[sign_date_c]", getYesterdaysDate().toString());
    uploadMap.put("model[recordTypeOfRecordId]", "AOS_Quotes");
    uploadMap.put("model[baseModule]", "AOS_Quotes");
    uploadMap.put("model[assigned_user_id][0][key]", "1");
    uploadMap.put("model[assigned_user_id][0][label]", "Administrator");
    uploadMap.put("model[primary_group_id][0][key]", "abeba670-1428-dbca-6a0b-57d11cbdb3bd");
    uploadMap.put("model[primary_group_id][0][label]", "TDS");
    uploadMap.put("model[accounts|record_type]", "B2C");
    // hardcoded product_id from payload
    uploadMap.put("model[aos_products_quotes|id]", "c0f94c2f-72e0-51b9-ce93-58930799ecf1");
    uploadMap.put("fieldGuid", "4d16155e-cbf1-e650-35b2-57a30ce14302");

    return uploadMap;
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

    private LocalDate getYesterdaysDate() {
    return LocalDate.now().minusDays(1);
    }

}
