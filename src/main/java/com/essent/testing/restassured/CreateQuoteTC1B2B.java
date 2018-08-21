package com.essent.testing.restassured;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.essent.testing.restassured.constants.ApiPaths;
import com.essent.testing.restassured.constants.Constants;
import com.essent.testing.restassured.constants.ContractStatus;
import com.essent.testing.restassured.helper.ContractUtil;
import com.essent.testing.restassured.helper.GenerateDataForQuote;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;


public class CreateQuoteTC1B2B {

	//TODO This fields should be maybe defined in separate class. See how to handle this
	private String recordId = "";
	private String rowId = "";
	private String aosProductsQuotesId = "";
	private HashMap model = null;
	private String docId = "";
	private String bilingCustomerId = "";
	private HashMap modelMandatePaper = null;
	private HashMap upload = null;
	private String accountName = "";
	private String companyNumber = "";
	private String yesterdayDate = "";
	private String todayDate = "";
	private String generatedIban = "";
	
	// It is set this date, because for this date we have tariff, tariff prices, ...
	private String pricingDate = "2017-11-01 12:22:00";
	private String priceValidUntilDate = "2017-11-02 12:22:00";
	private String signatureReceivedDate = "2017-11-01 12:22:00";	

	// This date is from SOAPUI. 
	// Each time new contract is created this date should be incremented by 1, and
	// normally a customer switch can only be sent 30 days in the future or in the past. On devint01 we do not have this validation, for UAT* I do not know
	
	//Contract 1: I should set some date in file: create_quote_b2b_tc1.json. Once I started with creation of new contract, I should read upStartDate from file, and increment by 1, and so on
	private String upStartDate = "2018-01-01";  
	
	
	// This address params should be any from src/test/resources/data/contract_b2b/address_b2b/adress_b2B.XLSX
	private String addressNumber = "54";
	private String addressStreet = "Nijverheidsstraat";
	private String addressPostalCode = "2160";
	private String addressCity = "Wommelgem";
	
	/* This is address from SOAPUI
	private String addressNumber = "195457";
	private String addressStreet = "Pierre Marchandstraat";
	private String addressPostalCode = "1970";
	private String addressCity = "Wezembeek-Oppem";*/
	
	// This ean should be any from src/test/resources/data/contract_b2b/address_b2b/adress_b2B.XLSX which correspond appropriate address
	private String ean_c = "541448258188316404";
	
	
	private String paymentMethod = "DOM"; // DOM or OV
	private String legalCommunicationBy = "POST"; //POST or EMAIL
	
	private int numberOfAttempts;

	
	private Cookies cookie = null;
	
	private Gson gson = null;
	
	public CreateQuoteTC1B2B() {
		gson = new Gson();
		numberOfAttempts = 0;
	}
	
	private void setPreconditions() throws ParseException {
		
		accountName = GenerateDataForQuote.setAccountName("B2B_TC1 ");
		companyNumber = GenerateDataForQuote.generateValidBECompanyNumber();
		yesterdayDate = GenerateDataForQuote.getYesterdayDate();
		todayDate = GenerateDataForQuote.getTodayDate();
		generatedIban = GenerateDataForQuote.getValidIbanBE();
		upStartDate = ContractUtil.getRandomStartContractDate(upStartDate, todayDate);
		
	}
	
	public void createContractB2BTC1() throws IOException, Exception {
		setPreconditions();
		login();
		createQuoteTC1B2B();
		verifyQuoteAfterCreating();
		sendToCustomer();
		verifyQuoteAfterSending();
		signatureReceived();
		verifyQuoteAfterSignatureReceived();
		confirmSigning();
		signMandatePaper();
		checkContractIsActive();
	}
	
	public void checkContractIsActive() throws Exception {
		
		String payloadContractedEansOnAccount = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "contracted_eans_on_account.json.template";
		String originalPayloadContractedEansOnAccount = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "contracted_eans_on_account.json";
				
		String jsonBody = GenerateDataForQuote.createRequestJsonPayload(payloadContractedEansOnAccount, originalPayloadContractedEansOnAccount, "${recordId}", recordId);
		
		String jsonPathFromResponse = "data.rows[0].rowData.contract_line_status_c";
		
		//ContractUtil.waitUntilStringFoundInResponse(cookie, ApiPaths.API_CONTRACTED_EAN, jsonBody, ContractStatus.ACTIVE, jsonPathFromResponse, 12);
		
		if (numberOfAttempts < Constants.MAX_NUMBER_OF_ATTEMPTS_TO_FIND_APPROPRIATE_START_CONTRACT_DATE) {
			if (ContractUtil.waitUntilStringFoundInResponse(cookie, ApiPaths.API_CONTRACTED_EAN, jsonBody,
					ContractStatus.ACTIVE, jsonPathFromResponse, 12).equals(ContractStatus.TO_BE_ACTIVATED.getContractStatus())) {
				++numberOfAttempts;
				createContractB2BTC1();
			}
		} else {
			assertFalse(false, "Contract is not being ACTIVE");
		}

		assertTrue(true, "Contract is ACTIVE");
	}
	
	//TODO Is this should be public, or only this class should see this login
	public void login() {

		cookie = RestAssured.given().contentType(ContentType.JSON).when()
				.body("{ \"username\": \"soapui_b2b\", \"password\": \"504pu17357\" }")
				.post(ApiPaths.API_LOGIN_CRM).then().statusCode(200).extract()
				.response().getDetailedCookies();
	}

	//TODO Is this should be public, or only this class should see this login. Probably it should be private
	public void createQuoteTC1B2B() throws IOException {
		
		// Combined Customer Switch
		//"move_in_c" : true, "switchtype_c":"ACTIVATION_REQUEST", "dwp|mig_module_c":"START ACCESS", "dwp|mig_label_c":"Combined Customer Switch"
		
		// Supplier Switch
		// "move_in_c":false, "switchtype_c":"SUPPLY_START_REQUEST", "dwp|mig_module_c":"START ACCESS", "dwp|mig_label_c":"Supplier Switch"
		
		// normally a customer switch can only be sent 30 days in the future or in the past
		// Customer Switch
		// "move_in_c":true, "switchtype_c":"CUSTOMER_SWITCH_NOTIFICATION", "dwp|mig_module_c":"START ACCESS", "dwp|mig_label_c":"Customer Switch"
		
		String payloadCreateQuoteB2BTC1 = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B
				+ "create_quote_b2b_tc1.json.template";
		String originalPayloadCreateQuoteB2BTC1 = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B
				+ "create_quote_b2b_tc1.json";
		
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

		String jsonBody = GenerateDataForQuote.createRequestJsonPayload(payloadCreateQuoteB2BTC1, originalPayloadCreateQuoteB2BTC1, testMap);
		
		//accountName, companyNumber, pricingDate, addressStreet, addressNumber, addressPostalCode, addressCity, legalCommunicationBy,paymentMethod,generatedIban, ean_c
		Response response = RestAssured.given().log().all().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.when().body(jsonBody).post(ApiPaths.API_CREATE_QUOTE_B2B_TC1).then().log().all().statusCode(201).body("data.arguments.errors", equalTo(null)).extract().response();

		recordId = new JsonPath(response.getBody().asString()).get("data.arguments.params.recordId");
		
		listOfQuotesOnAccount();
	}

	public void sendToCustomer() throws IOException {

		modalSendToCustomer();

		String payloadModalQuoteSendCustomerReloadList = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B
				+ "modal_to_gf_quote_send_to_customer_and_reload_list.json.template";
		String originalPayloadModalQuoteSendCustomerReloadList = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B
				+ "modal_to_gf_quote_send_to_customer_and_reload_list.json";
		
		String jsonBody = GenerateDataForQuote.createRequestJsonPayload(payloadModalQuoteSendCustomerReloadList, originalPayloadModalQuoteSendCustomerReloadList, "${rowId}", rowId);

		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_MODAL_TO_GF_QUOTE_SEND_TO_CUSTOMER_AND_RELOAD_LIST).then().log().all()
				.statusCode(200).extract().response();

		
		model = new JsonPath(response.getBody().asString()).get("data.arguments.model");
		model.put("dwp|sendemailtocust", true);
		model.put("dwp|sendemailtome", false);
		model.put("send_quote_to_c", "BOTH");
		model.put("dwp|send_quote_to_c|matchedCondition", "model['dwp|sendemailtome'] && model['dwp|sendemailtome']");
		String payload = gson.toJson(model); 
		
		String payloadQuoteSendCustomer = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "gf_quote_send_to_customer.json.template";
		String originalPayloadQuoteSendCustomer = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "gf_quote_send_to_customer.json";
		
		jsonBody = GenerateDataForQuote.createRequestJsonPayload(payloadQuoteSendCustomer, originalPayloadQuoteSendCustomer, "${model}", payload);

		RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_GF_QUOTE_SEND_TO_CUSTOMER).then().log().all().statusCode(201).body("data.arguments.errors", equalTo(null));
		
	}

	public void signatureReceived() throws IOException {

		String payloadQuoteSignatureReceived = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "gf_quote_signatureReceived.json.template";
		String originalPayloadQuoteSignatureReceived = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "gf_quote_signatureReceived.json";

		//pricingDate, rowId, recordId, priceValidUntilDate, signatureReceivedDate
		HashMap testMap = new HashMap();
		testMap.put("${pricingDate}", pricingDate);
		testMap.put("${priceValidUntilDate}", priceValidUntilDate);
		testMap.put("${signatureReceivedDate}", signatureReceivedDate);
		testMap.put("${rowId}", rowId);
		testMap.put("${recordId}", recordId);
		
		String jsonBody = GenerateDataForQuote.createRequestJsonPayload(payloadQuoteSignatureReceived, originalPayloadQuoteSignatureReceived, testMap);
		
		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_GF_QUOTE_SIGNATURE_RECEIVED).then().log().all().statusCode(201)
				.extract().response();
		
		response = RestAssured.given().log().all().cookies(cookie).contentType("multipart/form-data")
				.multiPart("file", new File(Constants.PATH_TO_PDF), "application/pdf")
				.formParams(createFormParamsMap()).when().post(ApiPaths.API_FILE_UPLOAD).then().log().all().statusCode(200).extract()
				.response();
		
		docId = new JsonPath(response.getBody().asString()).get("data.id");

	}
	
	public void confirmSigning() throws IOException {
		String payloadConfirmSigning = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "sign_quote_modal_tc1.json.template";
		String originalPayloadConfirmSigning = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "sign_quote_modal_tc1.json";

		//rowId, docId, date
		HashMap testMap = new HashMap();
		testMap.put("${rowId}", rowId);
		testMap.put("${docId}", docId);
		testMap.put("${date}", yesterdayDate);
		
		String jsonBody = GenerateDataForQuote.createRequestJsonPayload(payloadConfirmSigning, originalPayloadConfirmSigning, testMap);

		RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_SIGN_QUOTE_MODAL_TC1).then().log().all().statusCode(201)
				.extract().response();

		String payloadContractsOnAccount = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "contracts_on_account.json.template";
		String originalPayloadContractsOnAccount = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "contracts_on_account.json";
		
		jsonBody = GenerateDataForQuote.createRequestJsonPayload(payloadContractsOnAccount, originalPayloadContractsOnAccount, "${recordId}", recordId);
		
		RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_VERIFY_CONTRACT_CREATED).then().log().all().statusCode(200);
		
	}
	
	//Only required if we want to have payment method: DOM
	public void signMandatePaper() throws IOException {
		
		String payloadBillingCustomerOnAccount = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "billing_customer_on_account.json.template";
		String originalPayloadBillingCustomerOnAccount = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "billing_customer_on_account.json";

		String jsonBody = GenerateDataForQuote.createRequestJsonPayload(payloadBillingCustomerOnAccount, originalPayloadBillingCustomerOnAccount, "${recordId}", recordId);
		
		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_LIST_BILLING_CUSTOMER_ACCOUNT).then().statusCode(200)
				.extract().response();
		
		bilingCustomerId = new JsonPath(response.getBody().asString()).get("data.rows[0].id");
		
		String payloadModalSignMandatePaper = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "modal_to_gf_sign_mandate_paper.json.template";
		String originalModalPayloadSignMandatePaper = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "modal_to_gf_sign_mandate_paper.json";
		
		//billingCustomerId, recordId
		HashMap<String, String> testMap = new HashMap<String, String>();
		testMap.put("${billingCustomerId}", bilingCustomerId);
		testMap.put("${recordId}", recordId);
		
		jsonBody = GenerateDataForQuote.createRequestJsonPayload(payloadModalSignMandatePaper, originalModalPayloadSignMandatePaper, testMap);
		
		response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_MODAL_TO_GF_SIGN_MANDATE_PAPER).then().statusCode(200)
				.extract().response();
		
		modelMandatePaper = new JsonPath(response.getBody().asString()).get("data.arguments.model");
		
		response = RestAssured.given().cookies(cookie).contentType("multipart/form-data")
				.multiPart("file", new File(Constants.PATH_TO_PDF), "application/pdf")
				.formParams(createFormParamsMapMandatePaper()).when().post(ApiPaths.API_FILE_UPLOAD).then().statusCode(200).extract()
				.response();
		
		upload = new JsonPath(response.getBody().asString()).get("data");
		String payloadUpload = gson.toJson(upload);
		modelMandatePaper.put("dwp|attachment", payloadUpload);
		String payloadModelMandatePaper = gson.toJson(modelMandatePaper);
		
		String payloadSignMandatePaper = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "gf_sign_mandate_paper.json.template";
		String originalPayloadSignMandatePaper = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "gf_sign_mandate_paper.json";
		
		String jsonBodyPayloadSignMandatePaper = GenerateDataForQuote.createRequestJsonPayload(payloadSignMandatePaper, originalPayloadSignMandatePaper, "${modelMandatePaper}", payloadModelMandatePaper);
		
		RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBodyPayloadSignMandatePaper).when().post(ApiPaths.API_GF_SIGN_MANDATE_PAPER).then().statusCode(201);
	}
	

	private void listOfQuotesOnAccount() throws IOException {

		String payloadQuotesOnAccount = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "quotes_on_account.json.template";
		String originalPayloadQuotesOnAccount = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "quotes_on_account.json";
		
		String jsonBody = GenerateDataForQuote.createRequestJsonPayload(payloadQuotesOnAccount, originalPayloadQuotesOnAccount, "${recordId}", recordId);

		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_QUOTES_ON_ACCOUNT).then().statusCode(200).extract()
				.response();
		
		rowId = new JsonPath(response.getBody().asString()).get("data.rows[0].id");

	}

	private void modalSendToCustomer() throws IOException {

		String payloadModalQuoteSentToCustomer = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B
				+ "modal_to_gf_quote_send_to_customer.json.template";
		String originalPayloadModalQuoteSentToCustomer = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B
				+ "modal_to_gf_quote_send_to_customer.json";

		String jsonBody = GenerateDataForQuote.createRequestJsonPayload(payloadModalQuoteSentToCustomer, originalPayloadModalQuoteSentToCustomer, "${rowId}", rowId);

		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_MODAL_TO_GF_QUOTE_SEND_TO_CUSTOMER).then().log().all().statusCode(200)
				.extract().response();
		
		HashMap model = new JsonPath(response.getBody().asString()).get("data.arguments.model");
		aosProductsQuotesId = (String) model.get("aos_products_quotes|id");
	}
	
	private Map<String, String> createFormParamsMap(){
		Map<String, String> formParams = new HashMap<>();
		
		formParams.put("model[id]", rowId);
		formParams.put("model[dwp|id]", rowId);
		formParams.put("model[accounts|name]", accountName);
		formParams.put("model[dwp|recordType]", "AOS_Quotes");
		formParams.put("model[accounts|company_number_c]", companyNumber);
		formParams.put("model[stage]", "SIGNED");
		formParams.put("model[accounts|id]", recordId);
		formParams.put("model[sign_date_c]", yesterdayDate);
		formParams.put("model[recordTypeOfRecordId]", "AOS_Quotes");
		formParams.put("model[baseModule]", "AOS_Quotes");
		formParams.put("model[assigned_user_id][0][key]", "1");
		formParams.put("model[assigned_user_id][0][label]", "Administrator");
		formParams.put("model[primary_group_id][0][key]", "abeba670-1428-dbca-6a0b-57d11cbdb3bd");
		formParams.put("model[primary_group_id][0][label]", "TDS");
		formParams.put("model[dwp|mainDocType]", "");
		formParams.put("model[dwp|subDocType]", "");
		formParams.put("model[accounts|record_type]", "B2B");
		formParams.put("model[aos_products_quotes|id]", aosProductsQuotesId);
		formParams.put("fieldGuid", "4d16155e-cbf1-e650-35b2-57a30ce14302");
		formParams.put("model[complete]", "");
		formParams.put("model[do_auto_communication_c]", "");
		formParams.put("model[flag|confirmCommandKey]", "");
		formParams.put("model[payment_details|com_prefs(type='MANDATE')|channel]", "");
		formParams.put("model[payment_details|com_prefs(type='MANDATE')|id]", "");
		formParams.put("model[payment_details|id]", "");
		formParams.put("model[payment_details|payment_methods(valid_to is null)|id]", "");
		formParams.put("model[payment_details|payment_methods(valid_to is null)|payment_method]", "");
		formParams.put("model[recordId]", "");
		formParams.put("model[tasks(parent_type = 'AOS_Quotes')|id]", "");
		formParams.put("model[tasks(parent_type = 'AOS_Quotes')|status]", "");
		
		return formParams;
		
	}
	
	
	private Map<String, String> createFormParamsMapMandatePaper(){
		Map<String, String> formParams = new HashMap<>();
		
		formParams.put("model[recordTypeOfRecordId]", "Paym_Details");
		formParams.put("model[recordId]", bilingCustomerId);
		formParams.put("model[baseModule]", "Paym_Details");
		formParams.put("model[dwp|recordType]", "Accounts");
		formParams.put("model[accounts|id]", recordId);
		formParams.put("model[dwp|id]", recordId);
		formParams.put("model[id]", bilingCustomerId);
		formParams.put("fieldGuid", "2bee8c38-dfdc-bbcb-b373-59d488de9d5e");
		formParams.put("model[accounts|name]", "");
		formParams.put("model[accounts|company_number_c]", "");
		formParams.put("model[stage]", "");
		formParams.put("model[sign_date_c]", "");
		formParams.put("model[assigned_user_id][0][key]", "");
		formParams.put("model[assigned_user_id][0][label]", "");
		formParams.put("model[primary_group_id][0][key]", "");
		formParams.put("model[primary_group_id][0][label]", "");
		formParams.put("model[dwp|mainDocType]", "");
		formParams.put("model[dwp|subDocType]", "");
		formParams.put("model[accounts|record_type]", "");
		formParams.put("model[aos_products_quotes|id]", "");
		formParams.put("model[complete]", "");
		formParams.put("model[do_auto_communication_c]", "");
		formParams.put("model[flag|confirmCommandKey]", "");
		formParams.put("model[payment_details|com_prefs(type='MANDATE')|channel]", "");
		formParams.put("model[payment_details|com_prefs(type='MANDATE')|id]", "");
		formParams.put("model[payment_details|id]", "");
		formParams.put("model[payment_details|payment_methods(valid_to is null)|id]", "");
		formParams.put("model[payment_details|payment_methods(valid_to is null)|payment_method]", "");
		formParams.put("model[tasks(parent_type = 'AOS_Quotes')|id]", "");
		formParams.put("model[tasks(parent_type = 'AOS_Quotes')|status]", "");
		
		return formParams;
		
	}
	
	private void verifyQuoteAfterSignatureReceived() throws IOException {
		String payloadVerifyQuotesOnAccount = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "verify_quotes_on_account.json.template";
		String originalPayloadVerifyQuotesOnAccount = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "verify_quotes_on_account.json";
		
		String jsonBody = GenerateDataForQuote.createRequestJsonPayload(payloadVerifyQuotesOnAccount, originalPayloadVerifyQuotesOnAccount, "${recordId}", recordId);
		
		Response response = RestAssured.given().log().all().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.when().body(jsonBody).post(ApiPaths.API_QUOTES_ON_ACCOUNT).then().log().all().statusCode(200).body("data.rows[0].cells[2].options.line2", equalTo(Constants.STATUS_QUOTE_AFTER_SIGNATURE_RECEIVED_EN)).extract().response();

		
	}

	private void verifyQuoteAfterSending() throws IOException {
		String payloadVerifyQuotesOnAccount = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "verify_quotes_on_account.json.template";
		String originalPayloadVerifyQuotesOnAccount = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "verify_quotes_on_account.json";
		
		String jsonBody = GenerateDataForQuote.createRequestJsonPayload(payloadVerifyQuotesOnAccount, originalPayloadVerifyQuotesOnAccount, "${recordId}", recordId);
		
		Response response = RestAssured.given().log().all().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.when().body(jsonBody).post(ApiPaths.API_QUOTES_ON_ACCOUNT).then().log().all().statusCode(200).body("data.rows[0].cells[2].options.line2", equalTo(Constants.STATUS_QUOTE_AFTER_SENDING_EN)).extract().response();

		
	}

	private void verifyQuoteAfterCreating() throws IOException {
		String payloadVerifyQuotesOnAccount = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "verify_quotes_on_account.json.template";
		String originalPayloadVerifyQuotesOnAccount = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "verify_quotes_on_account.json";
		
		String jsonBody = GenerateDataForQuote.createRequestJsonPayload(payloadVerifyQuotesOnAccount, originalPayloadVerifyQuotesOnAccount, "${recordId}", recordId);
		
		Response response = RestAssured.given().log().all().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.when().body(jsonBody).post(ApiPaths.API_QUOTES_ON_ACCOUNT).then().log().all().statusCode(200).body("data.rows[0].cells[2].options.line2", equalTo(Constants.STATUS_QUOTE_AFTER_CREATING_TC1_EN)).extract().response();

		
	}
	
}
