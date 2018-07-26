package com.essent.testing.restassured;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;

public class CreateQuoteTC1B2B {
	
	private String ROOT_URI = "https://devint01.nova.essent.be/nova-crm/Api/V8_Custom/Flow/B2B_CQ_TC1";
	private Cookies cookie;
	
	public void login() {
		
		cookie = RestAssured.given()
 					.contentType(ContentType.JSON)
	            .when()
	            	.body("{ \"username\": \"soapui_b2b\", \"password\": \"504pu17357\" }")
	            	.post("https://devint01.nova.essent.be/nova-crm/Api/V8/login")
	            .then()
	            	.statusCode(200)
	            .extract()
	            .response()
	            	.getDetailedCookies();
	}
	
	public void createQuoteTC1B2B() throws IOException{
		
		String PATH_QUOTE_TC1_B2B = "./src/test/resources/data/create_quote_b2b/";
		String jsonBody = generateStringFromResource(PATH_QUOTE_TC1_B2B + "create_quote_b2b_tc1.json");
		
		login();
		
		RestAssured.given().
				cookies(cookie).
				contentType(ContentType.JSON).
				accept(ContentType.JSON).
				body(jsonBody).
		when().
				post(ROOT_URI).
		then().
				statusCode(201);
		
	}
	
	private String generateStringFromResource(String path) throws IOException {
	    return new String(Files.readAllBytes(Paths.get(path)));
	}
}
