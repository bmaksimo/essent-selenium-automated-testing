package stepdefinitions.quote.api;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import stepdefinitions.quote.api.model.QuoteDetails;

public class UploadSignatureAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(UploadSignatureAPI.class);

    public void uploadSignature(Cookies cookie, QuoteDetails quoteDetails) {
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_SIGNATURE_UPLOAD_URL);
        Map<String, String> payload = createUploadPayload(quoteDetails);

        Response signatureResponse =  helper.postMultipartRequest(STATUS_OK, cookie, payload, path);

        if (signatureResponse.getStatusCode() == STATUS_OK) {
            LOGGER.info("Signature response retrieved");
        } else {
            LOGGER.error("Cannot retrieve signature response");
        }
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

    private LocalDate getYesterdaysDate() {
        return LocalDate.now().minusDays(1);
    }

}
