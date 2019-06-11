package stepdefinitions.quote.api;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import stepdefinitions.quote.api.helper.RequestHelper;
import stepdefinitions.quote.api.model.IWelcomeLogin;

/**
 * @author n.grkavac
 *
 */
public class IWelcomeLoginAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(IWelcomeLoginAPI.class);

    public String createPayload(String username, String password) throws JsonProcessingException {
        String payload_m = serializePayloadForiWelcome(username, password);
        return payload_m;
    }

    private String serializePayloadForiWelcome(String username_m, String password_m) throws JsonProcessingException {
        IWelcomeLogin iWelcome_Login_m = new IWelcomeLogin(username_m, password_m);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(iWelcome_Login_m);
    }

    public Cookies getCookie(String username, String password) throws JsonProcessingException {
        Cookies cookie = null;
        Integer expectedResponseCode = STATUS_OK;
        String payload = createPayload(username, password);
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
            + ConfigProvider.getProperty(ConfigKey.CRM_LOGIN_URL);
        Response iWelcomeResponse = helper.simplePostRequest(expectedResponseCode, payload, path);

        cookie = (Cookies) iWelcomeResponse.getDetailedCookies();
        LOGGER.debug("Cookie is: " + cookie);

        return cookie;
    }
}
