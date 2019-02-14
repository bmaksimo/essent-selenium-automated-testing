/**
 * 
 */
package stepdefinitions.quote.api;

import org.apache.log4j.Logger;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;

import io.restassured.http.Cookies;
import io.restassured.response.Response;

/**
 * @author mkolarov
 *
 */
public class QuoteBTCAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(QuoteBTCAPI.class);

    public String getTariffSheetID(Cookies cookie) {
	String tariffSheetID = null;
	Integer expectedResponseCode = STATUS_OK;
	String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
		+ ConfigProvider.getProperty(ConfigKey.CRM_B2CCQ_URL);
	String payload = "";

	RequestHelper helper = new RequestHelper();
	Response tsResponse = helper.postRequest(expectedResponseCode, cookie, payload, path);

	if (tsResponse.getStatusCode() == expectedResponseCode) {
	    tariffSheetID = tsResponse.jsonPath()
		    .getString("'data.model.accounts|aos_quotes|aos_products_quotes|tariffsheet_id'");
	    LOGGER.info("TariffSheetID is: " + tariffSheetID);
	} else {
	    LOGGER.error("Cannot retrieve tariffSheetID");
	}

	return tariffSheetID;
    }

}
