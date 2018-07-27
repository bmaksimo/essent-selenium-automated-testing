package com.essent.testing.restassured;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateQuoteTC1B2B {
	
	private static final String API_CREATE_QUOTE_B2B_TC1 = "https://devint01.nova.essent.be/nova-crm/Api/V8_Custom/Flow/B2B_CQ_TC1";
	private static final String PATH_TO_JSON_FILES_QUOTE_TC1_B2B = "./src/test/resources/data/payloads_create_quote_b2b/";
	private static final String API_QUOTES_ON_ACCOUNT = "https://devint01.nova.essent.be/nova-crm/Api/V8_Custom/List/QuotesOnAccount";
	
	
	private String recordId = "";
	
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
		
		String jsonBody = generateStringFromResource(PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "create_quote_b2b_tc1.json");		
		
		Response response = RestAssured.given().
				cookies(cookie).
				contentType(ContentType.JSON).
				accept(ContentType.JSON).
		when().
				body(jsonBody).
		     	post(API_CREATE_QUOTE_B2B_TC1).
		then().
				statusCode(201).
				extract().response();
		
		recordId = new JsonPath(response.getBody().asString()).get("data.arguments.params.recordId");
		
	}
	
	public void listOfQuotesOnAccount() throws IOException{
		
		String payloadQuotesOnAccount = PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "quotes_on_account.json.template";
		
		update(payloadQuotesOnAccount, recordId);
		
		Response response = RestAssured.given().
			cookies(cookie).
			contentType(ContentType.JSON).
			accept(ContentType.JSON).
			body(payloadQuotesOnAccount).
		when().
			post(API_QUOTES_ON_ACCOUNT).
		then().
			statusCode(200).
			extract().response();
		
	}
	
	private String generateStringFromResource(String path) throws IOException {
	    return new String(Files.readAllBytes(Paths.get(path)));
	}
	
	//TODO replaceString can be array of strings. Find solution to implement that and not only one String
	private void update(String path, String replaceString) throws IOException{
        File file = new File(path);
        String fileContext = FileUtils.readFileToString(file);
        fileContext = fileContext.replace("${recordId}", recordId);
        FileUtils.write(file, fileContext);
 }
}
