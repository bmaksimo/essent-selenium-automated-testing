package stepdefinitions.quote.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;

import java.io.IOException;

public abstract class AbstractAPI {

    protected Cookies cookie = null;

    protected Cookies getCookie() {
        return cookie;
    }

    public abstract String createPayload(String username, String password);
    public abstract Cookies restPOST(String payload,String apiPath, Integer expectedResponseCode) throws IOException;
}


