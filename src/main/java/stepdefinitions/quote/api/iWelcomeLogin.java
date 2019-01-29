package stepdefinitions.quote.api;

import com.billinghouse.cucumber.runtime.parameter.ParameterProvider;
import com.essent.testing.context.ContextService;
import com.essent.testing.restassured.create_contract.constants.ApiPathsContract;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.log4j.Logger;

import java.io.IOException;

public class iWelcomeLogin {

    String userId;
    String password;

    private static final Logger logger = Logger.getLogger(iWelcomeLogin.class);
    private ParameterProvider parameterProvider;

    //private String CRMusername = ConfigProvider.getProperty(ConfigKey.DWP_USER_SOAPUI_B2C);
    //private String CRMpassword = ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_SOAPUI_B2C);

    protected Gson gson;
    protected Cookies cookie = null;

    public iWelcomeLogin(String userId_m, String password_m) {

        userId = userId_m;
        password = password_m;
    }


    //  getters and setters
    public void setUserId(String userId_m) {
        userId = userId_m;
    }

    public void setPassword(String password_m) {
        password = password_m;
    }

    public Cookies getCookie() {
        return cookie;
    }

    //parameterProvider = ((ParameterProvider) ContextService.getContext().getBean("parameterProvider")).consumingNullValues(true);
		//RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    public void login2iWelcome() throws IOException {
        //String jsonBody = PrepareDataForContract.createRequestJsonPayload(payloadConfirmSigning, originalPayloadConfirmSigning, testMap);
        String jsonBody = serializePayloadForiWelcome(userId,password);

        //RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
            //.body(jsonBody).when().post(apiPathiWelcome).then().statusCode(201) = .extract().response();

        cookie = RestAssured.given().contentType(ContentType.JSON).when()
            .body(jsonBody)
            .post(ApiPathsContract.API_LOGIN_CRM).then().statusCode(200).extract()
            .response().getDetailedCookies();
    }


    private String serializePayloadForiWelcome(String userId_m, String password_m) {
        PayloadForiWelcome payloadForiWelcome_m = new PayloadForiWelcome(userId_m,password_m);
        gson = new Gson();
        return gson.toJson(payloadForiWelcome_m);

    }
}
