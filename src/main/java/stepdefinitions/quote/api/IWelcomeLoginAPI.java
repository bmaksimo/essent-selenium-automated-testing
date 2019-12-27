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

/** @author n.grkavac */
public class IWelcomeLoginAPI extends AbstractAPI {

  private static final Logger LOGGER = Logger.getLogger(IWelcomeLoginAPI.class);

  public String createPayload(String username, String password) throws JsonProcessingException {
    return serializePayloadForiWelcome(username, password);
  }

  private String serializePayloadForiWelcome(String username, String password)
      throws JsonProcessingException {
    IWelcomeLogin iWelcomeLogin = new IWelcomeLogin(username, password);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(iWelcomeLogin);
  }

  public Cookies getCookie(String username, String password) throws JsonProcessingException {
    Cookies cookie;
    Integer expectedResponseCode = STATUS_OK;
    String payload = createPayload(username, password);
    RequestHelper helper = new RequestHelper();
    String path =
        ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI)
            + ConfigProvider.getProperty(ConfigKey.CRM_LOGIN_URL);
    Response iWelcomeResponse = helper.simplePostRequest(expectedResponseCode, payload, path);

    cookie = iWelcomeResponse.getDetailedCookies();
    LOGGER.debug("Cookie is: " + cookie);

    return cookie;
  }
}
