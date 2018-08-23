package com.essent.testing.restassured.create_b2b_contract.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import com.essent.testing.restassured.create_b2b_contract.CreateQuoteB2B;
import com.essent.testing.restassured.create_b2b_contract.CreateQuoteB2BBase;
import com.essent.testing.restassured.create_b2b_contract.constants.ApiPaths;
import com.essent.testing.restassured.create_b2b_contract.constants.Constants;
import com.essent.testing.restassured.create_b2b_contract.constants.ContractStatus;
import com.essent.testing.restassured.create_b2b_contract.helper.ContractUtil;
import com.essent.testing.restassured.create_b2b_contract.helper.PrepareDataForQuote;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


//TODO This class and class CreateQuoteTC2B2B should be in one class because APIs are almost the same
public class CreateQuoteUPB2B extends CreateQuoteB2BBase implements CreateQuoteB2B{

	public CreateQuoteUPB2B() {
		super();
		
		pricingDate = "2016-11-01 12:22:00";
		priceValidUntilDate = "2016-11-02 12:22:00";
		signatureReceivedDate = "2016-11-01 12:22:00";
		
		// This address params are located in src/test/resources/data/contract_b2b/address_b2b/adress_b2B.XLSX
		addressNumber = "325";
		addressStreet = "Diksmuidsesteenweg";
		addressPostalCode = "8800";
		addressCity = "Roeselare";
		
		
		// This is address from SOAPUI
		//addressNumber = "118805";
		//addressStreet = "Stadsvest";
		//addressPostalCode = "3012";
		//addressCity = "WILSELE";
		
		// This date is from SOAPUI. 
		// Each time new contract is created this date should be incremented by 1, and
		// normally a customer switch can only be sent 30 days in the future or in the past. On devint01 we do not have this validation, for UAT* I do not know
		
		//Contract 1: I should set some date in file: create_quote_b2b_up.json. Once I started with creation of new contract, I should read upStartDate from file, and increment by 1, and so on
		upStartDate = "2015-11-01"; 
		upEndDate = "2018-10-31";
		
		ean_c = "541448810000084849";
		
		paymentMethod = "DOM"; // DOM or OV
		legalCommunicationBy = "POST"; //POST or EMAIL
	}
	
	@Override
	public void setPreconditions(String accountName, String upStartDate) throws Exception{
		super.setPreconditions(accountName, upStartDate);
	}
	
	@Override
	public String createContractB2B() throws Exception {
		setPreconditions(Constants.ACCOUNT_NAME_PREFIX_UP_B2B, upStartDate);
		login();
		createQuoteB2B();
		verifyQuoteStatus(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B, Constants.STATUS_QUOTE_AFTER_CREATING_TC2_UP_EN);
		sendToCustomer(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B);
		verifyQuoteStatus(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B, Constants.STATUS_QUOTE_AFTER_SENDING_EN);
		signatureReceived(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B, pricingDate, priceValidUntilDate, signatureReceivedDate);
		verifyQuoteStatus(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B, Constants.STATUS_QUOTE_AFTER_SIGNATURE_RECEIVED_EN);
		confirmSigning(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B, "sign_quote_modal_tc2_up.json", ApiPaths.API_SIGN_QUOTE_MODAL_TC2_UP);
		signMandatePaper(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B);
		checkContractIsActive();
		return getAccountNumber(recordId, cookie);
	}
	
	@Override
	public void checkContractIsActive() throws Exception {
		
		String payloadContractedEansOnAccount = Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B + "contracted_eans_on_account.json.template";
		String originalPayloadContractedEansOnAccount = Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B + "contracted_eans_on_account.json";
				
		String jsonBody = PrepareDataForQuote.createRequestJsonPayload(payloadContractedEansOnAccount, originalPayloadContractedEansOnAccount, "${recordId}", recordId);
		
		String jsonPathFromResponse = "data.rows[0].rowData.contract_line_status_c";
		
		//ContractUtil.waitUntilStringFoundInResponse(cookie, ApiPaths.API_CONTRACTED_EAN, jsonBody, ContractStatus.ACTIVE, jsonPathFromResponse, 12);
		
		if (numberOfAttempts < Constants.MAX_NUMBER_OF_ATTEMPTS_TO_FIND_APPROPRIATE_START_CONTRACT_DATE) {
			if (ContractUtil.waitUntilStringFoundInResponse(cookie, ApiPaths.API_CONTRACTED_EAN, jsonBody,
					ContractStatus.ACTIVE, jsonPathFromResponse, Constants.TIMEOUT_SET_CONTRACT_ACTIVE).equals(ContractStatus.TO_BE_ACTIVATED.getContractStatus())) {
				++numberOfAttempts;
				createContractB2B();
			}
		} else {
			assertFalse(false, "Contract is not being ACTIVE");
		}

		assertTrue(true, "Contract is ACTIVE");
	}

	@Override
	public void login() {
		super.login();
	}

	@Override
	public void createQuoteB2B() throws Exception {
		
		// Combined Customer Switch
		//"move_in_c" : true, "switchtype_c":"ACTIVATION_REQUEST", "dwp|mig_module_c":"START ACCESS", "dwp|mig_label_c":"Combined Customer Switch"
		
		// Supplier Switch
		// "move_in_c":false, "switchtype_c":"SUPPLY_START_REQUEST", "dwp|mig_module_c":"START ACCESS", "dwp|mig_label_c":"Supplier Switch"
		
		// normally a customer switch can only be sent 30 days in the future or in the past
		// Customer Switch
		// "move_in_c":true, "switchtype_c":"CUSTOMER_SWITCH_NOTIFICATION", "dwp|mig_module_c":"START ACCESS", "dwp|mig_label_c":"Customer Switch"
		
		String payloadCreateQuoteB2BUP = Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B
				+ "create_quote_b2b_up.json.template";
		
		String originalPayloadCreateQuoteB2BUP = Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B
				+ "create_quote_b2b_up.json";
		
		HashMap testMap = new HashMap();
		testMap.put("${accountName}", accountName);
		testMap.put("${companyNumber}", companyNumber);
		testMap.put("${pricingDate}", pricingDate);
		testMap.put("${addressStreet}", addressStreet);
		testMap.put("${addressNumber}", addressNumber);
		testMap.put("${addressPostalCode}", addressPostalCode);
		testMap.put("${addressCity}", addressCity);
		testMap.put("${legalCommunicationBy}", legalCommunicationBy);
		testMap.put("${paymentMethod}", paymentMethod);
		testMap.put("${generatedIban}", generatedIban);
		testMap.put("${ean_c}", ean_c);
		testMap.put("${upStartDate}", upStartDate);
		testMap.put("${upEndDate}", upEndDate);
		
		String jsonBody = PrepareDataForQuote.createRequestJsonPayload(payloadCreateQuoteB2BUP, originalPayloadCreateQuoteB2BUP, testMap);

		//accountName, companyNumber, pricingDate, addressStreet, addressNumber, addressPostalCode, addressCity, legalCommunicationBy,paymentMethod,generatedIban, ean_c
		Response response = RestAssured.given().log().all().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.when().body(jsonBody).post(ApiPaths.API_CREATE_QUOTE_B2B_TC2_UP).then().log().all().statusCode(201).body("data.arguments.errors", equalTo(null)).extract().response();

		recordId = new JsonPath(response.getBody().asString()).get("data.arguments.params.recordId");
		paymentDetailsId  = new JsonPath(response.getBody().asString()).get("data.relatedBeans.Paym_Details[0]");
		
		listOfQuotesOnAccount(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B);
	}
	
	@Override
	public void sendToCustomer(String path) throws IOException {
		super.sendToCustomer(path);
	}
	
	@Override
	public void signatureReceived(String path, String pricingDate, String priceValidUntilDate, String signatureReceivedDate) throws IOException {
		super.signatureReceived(path, pricingDate, priceValidUntilDate, signatureReceivedDate);
	}
	
	@Override
	public void confirmSigning(String path, String pathJsonFileSignQuote, String apiPathSignQuote) throws IOException {
		super.confirmSigning(path, pathJsonFileSignQuote, apiPathSignQuote);
	}

	// Only required if we want to have payment method: DOM
	@Override
	public void signMandatePaper(String path) throws IOException {
		super.signMandatePaper(path);
	}
	
}
