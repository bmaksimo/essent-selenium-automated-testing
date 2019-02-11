package stepdefinitions.quote.api;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.http.Cookies;

public interface BaseAPI {

    public String createPayload(String username, String password) throws JsonProcessingException;
    public Cookies restPOST(String payload,String apiPath, Integer expectedResponseCode) throws IOException;
}


