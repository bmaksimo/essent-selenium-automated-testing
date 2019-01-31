package stepdefinitions.quote.api;

import com.billinghouse.cucumber.runtime.parameter.ParameterProvider;
import com.essent.testing.restassured.create_contract.constants.ApiPathsContract;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;

import java.io.IOException;
//package stepdefinitions.quote.api;


public class IWelcomeLoginImpl extends AbstractAPI {

        String username;
        String password;

        //private static final Logger logger = Logger.getLogger(stepdefinitions.quote.api.iWelcomeLogin.class);
        private ParameterProvider parameterProvider;

        //private String CRMusername = ConfigProvider.getProperty(ConfigKey.DWP_USER_SOAPUI_B2C);
        //private String CRMpassword = ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_SOAPUI_B2C);

        protected Gson gson;
        protected Cookies cookie = null;


        public Cookies getCookie() {
            return cookie;
        }

        @Override
        public String createPayload(String username, String password){
          String payload_m =  serializePayloadForiWelcome(username,password);
          return payload_m;

        }

        @Override
        public Cookies restPOST(String payload, String apiPath, Integer expectedResponseCode) throws IOException {


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



        private String serializePayloadForiWelcome(String username_m, String password_m) {
            PayloadForiWelcome payloadForiWelcome_m = new PayloadForiWelcome(username_m,password_m);
            gson = new Gson();
            return gson.toJson(payloadForiWelcome_m);

        }
    }




