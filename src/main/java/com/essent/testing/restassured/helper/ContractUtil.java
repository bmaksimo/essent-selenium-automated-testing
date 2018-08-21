package com.essent.testing.restassured.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.essent.testing.restassured.constants.ContractStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ContractUtil {
	
	public static String waitUntilStringFoundInResponse(Cookies cookie, String apiPath, String payload, ContractStatus findMe, String jsonPathFromResponse, int TIMEOUT) throws Exception {
	    String contractStatus = "";
	    int i = 0;
	    while (i < TIMEOUT) {
	    	
	    	Response response = RestAssured.given().log().all().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
	    			.when().body(payload).post(apiPath).then().log().all().statusCode(200).extract().response();
	    			
	    	contractStatus = new JsonPath(response.getBody().asString()).get(jsonPathFromResponse);
	    	
	        if (contractStatus.contains(findMe.getContractStatus())) {
	            break;
	        } else {
	        	if(i == 0) {
	        		TimeUnit.MINUTES.sleep(3);
	        	}else {
	        		TimeUnit.SECONDS.sleep(15);
	        	}
	            ++i;
	            if (i == TIMEOUT) {
	            	return contractStatus;
	                //throw new TimeoutException("Timed out after waiting for " + i + " seconds");
	            }
	        }
	    }
	    return contractStatus;
	}
	
	public static String getRandomStartContractDate(String startContractDate, String todayDate) throws ParseException {

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(startContractDate);
		Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(todayDate);

		long random = ThreadLocalRandom.current().nextLong(date1.getTime(), date2.getTime());
		Date date = new Date(random);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);

	}

}
