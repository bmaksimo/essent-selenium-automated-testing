package stepdefinitions.quote.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;

import java.io.IOException;
//package stepdefinitions.quote.api;


public class BaseApiImpl implements BaseAPI {

        //private static final Logger logger = Logger.getLogger(stepdefinitions.quote.api.iWelcomeLogin.class);

        //private String CRMusername = ConfigProvider.getProperty(ConfigKey.DWP_USER_SOAPUI_B2C);
        //private String CRMpassword = ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_SOAPUI_B2C);

        public String createPayload(String username, String password) throws JsonProcessingException{
          String payload_m =  serializePayloadForiWelcome(username,password);
          return payload_m;
        }

        public Cookies restPOST(String payload, String apiPath, Integer expectedResponseCode) throws IOException {

            Cookies cookie = null;

            cookie = (Cookies) RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .body(payload)
                .post(apiPath)
                .then()
                .statusCode(expectedResponseCode)
                .extract()
                .response()
                .getDetailedCookies();

            System.out.println(cookie);
            return cookie;

        }

        private String serializePayloadForiWelcome(String username_m, String password_m) throws JsonProcessingException {
            iWelcomeLogin iWelcome_Login_m = new iWelcomeLogin(username_m,password_m);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(iWelcome_Login_m);
        }
    }




