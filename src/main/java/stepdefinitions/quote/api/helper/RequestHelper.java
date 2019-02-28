package stepdefinitions.quote.api.helper;

import static io.restassured.RestAssured.expect;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.UUID;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.response.Response;

import org.apache.log4j.Logger;

import com.essent.testing.util.resource.ResourceUtil;

/**
 * Helper class with rest assured requests. Add new request or refactor existing
 * ones if they don't suit your needs.
 *
 * @author mi.kolarov
 *
 */
public class RequestHelper {

    private final static Logger LOGGER = Logger.getLogger(RequestHelper.class);
    private Header trackingHeader = new Header("X-LOG-ID", UUID.randomUUID().toString());

    /**
     * POST request with JSON set in header, without Cookie
     *
     * @param statusCode
     * @param body
     * @param path
     * @return response
     */
    public Response simplePostRequest(Integer expectedStatusCode, String body, String path) {

	Response response = expect().given().header(trackingHeader).contentType(ContentType.JSON).body(body).when()
		.post(path);

	Integer responseStatusCode = getResponseStatusCode(response, path, expectedStatusCode);
	
	if (!responseStatusCode.equals(expectedStatusCode)) {
	    LOGGER.info("JSON body which was sent in the request is: " + body);
	    LOGGER.error("RESPONSE IS: " + response.body().asString());
	}

//	assertEquals(expectedStatusCode, responseStatusCode);	
	assertThat(responseStatusCode, is(equalTo(expectedStatusCode)));	
	return response;
    }

    public Response postRequest(Integer expectedStatusCode, Cookies cookie, String payload, String path) {

	Response response = expect().given().header(trackingHeader).cookies(cookie).contentType(ContentType.JSON)
		.body(payload).when().post(path);

	Integer responseStatusCode = getResponseStatusCode(response, path, expectedStatusCode);

	if (!responseStatusCode.equals(expectedStatusCode)) {
	    LOGGER.info("JSON body which was sent in the request is: " + payload);
	    LOGGER.error("RESPONSE IS: " + response.body().asString());
	}

//	assertEquals(exectedStatusCode, responseStatusCode);
	assertThat(responseStatusCode, is(equalTo(expectedStatusCode)));
	return response;
    }

    public Response postMultipartRequest(Integer expectedStatusCode, Cookies cookie, Map<String, String> payload, String path) {
	
	String pathToFile = ResourceUtil.toPath("/data/restassured/upload/fileupload.txt");
	File file = new File(pathToFile);
	Response response = expect().given().header(trackingHeader).cookies(cookie).multiPart("file", file)
		.formParams(payload).when().post(path);

	Integer responseStatusCode = getResponseStatusCode(response, path, expectedStatusCode);

	if (!responseStatusCode.equals(expectedStatusCode)) {
	    LOGGER.info("JSON body which was sent in the request is: " + payload);
	    LOGGER.error("RESPONSE IS: " + response.body().asString());
	}

//	assertEquals(expectedStatusCode, responseStatusCode);
	assertThat(responseStatusCode, is(equalTo(expectedStatusCode)));
	return response;
    }

    private Integer getResponseStatusCode(Response response, String path, Integer exectedStatusCode) {
	
	Integer responseStatusCode = new Integer(response.statusCode());
	LOGGER.info("POST " + path + " status : " + responseStatusCode + " (expected: " + exectedStatusCode + ")");
	LOGGER.info("X-LOG-ID tracking header: " + trackingHeader.getValue());
	logResponseTimeDuration(response, "for "+ path +" ");
	return responseStatusCode;
    }
    
    private void logResponseTimeDuration(Response response, String Description) {
	    Long actualResponseTime = response.time();
	    LOGGER.info("Measured response time, " + Description + "is : "
	        + new SimpleDateFormat("ss.SSS").format(actualResponseTime) + " sec");
	  }

}
