package stepdefinitions.quote.api;

import static io.restassured.RestAssured.expect;
import static org.junit.Assert.*;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;


import org.apache.log4j.Logger;

/**
 * Helper class with rest assured requests. Add new request or refactor existing
 * ones if they don't suit your needs.
 * 
 * @author mi.kolarov
 *
 */
public class RequestHelper {

    private final static Logger LOGGER = Logger.getLogger(RequestHelper.class);

    /**
     * POST request with JSON set in header, without Cookie
     * 
     * @param statusCode
     * @param body
     * @param path
     * @return response
     */
    public  Response simplePostRequest(Integer statusCode, String body, String path) {

	Response response = expect().given().contentType(ContentType.JSON).body(body).when().post(path);

	Integer responseStatusCode = new Integer(response.statusCode());
	LOGGER.info("POST " + path + " status : " + responseStatusCode + " (expected: " + statusCode + ")");

	if (!responseStatusCode.equals(statusCode)) {
	    LOGGER.info("JSON body which was sent in the request is: " + body);
	    LOGGER.error("RESPONSE IS: " + response.body().asString());
	}

	assertEquals(responseStatusCode, statusCode);
	return response;
    }

    public  Response postRequest(Integer statusCode, Cookies cookie, String payload,String path) {

   	Response response = expect().given().cookies(cookie).contentType(ContentType.JSON).body(payload).when().post(path);

   	Integer responseStatusCode = new Integer(response.statusCode());
   	LOGGER.info("POST " + path + " status : " + responseStatusCode + " (expected: " + statusCode + ")");

   	if (!responseStatusCode.equals(statusCode)) {
   	    LOGGER.info("JSON body which was sent in the request is: " + payload);
   	    LOGGER.error("RESPONSE IS: " + response.body().asString());
   	}

   	assertEquals(statusCode, responseStatusCode);
   	return response;
       }

}
