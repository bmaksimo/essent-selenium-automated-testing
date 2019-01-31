package stepdefinitions.quote.api;

import com.billinghouse.cucumber.runtime.parameter.ParameterProvider;
//import com.essent.testing.context.ContextService;
import com.essent.testing.restassured.create_contract.constants.ApiPathsContract;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.log4j.Logger;

import java.io.IOException;

public class iWelcomeLogin {

    String username;
    String password;

    private static final Logger logger = Logger.getLogger(iWelcomeLogin.class);
    private ParameterProvider parameterProvider;

    //private String CRMusername = ConfigProvider.getProperty(ConfigKey.DWP_USER_SOAPUI_B2C);
    //private String CRMpassword = ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_SOAPUI_B2C);

    protected Gson gson;
    protected Cookies cookie = null;

    public iWelcomeLogin(String username_m, String password_m) {

        username = username_m;
        password = password_m;
    }


    //  getters and setters
    public void setUsername(String username_m) {
        username = username_m;
    }

    public void setPassword(String password_m) {
        password = password_m;
    }

    public Cookies getCookie() {
        return cookie;
    }

   
    public void login2iWelcome() throws IOException {
     
        String jsonBody = serializePayloadForiWelcome(username,password);

      
        cookie = (Cookies) RestAssured
            .given()
            .contentType(ContentType.JSON)
            .when()
            .body(jsonBody)
            .post(ApiPathsContract.API_LOGIN_CRM)
            .then()
            .statusCode(200)
            .extract()
            .response()
            .getDetailedCookies();


    }


    private String serializePayloadForiWelcome(String username_m, String password_m) {
        PayloadForiWelcome payloadForiWelcome_m = new PayloadForiWelcome(username_m,password_m);
        gson = new Gson();
        return gson.toJson(payloadForiWelcome_m);

    }
}
