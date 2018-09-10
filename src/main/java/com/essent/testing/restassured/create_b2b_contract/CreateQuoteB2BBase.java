package com.essent.testing.restassured.create_b2b_contract;

import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.restassured.create_b2b_contract.constants.ApiPaths;
import com.essent.testing.restassured.create_b2b_contract.constants.Constants;
import com.essent.testing.restassured.create_b2b_contract.constants.ContractStatus;
import com.essent.testing.restassured.create_b2b_contract.helper.ContractUtil;
import com.essent.testing.restassured.create_b2b_contract.helper.PrepareDataForQuote;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateQuoteB2BBase {
	
	private String CRMusername = ConfigProvider.getProperty(ConfigKey.DWP_USER_SOAPUI_B2B);
	private String CRMpassword = ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_SOAPUI_B2B);
	
	protected Gson gson;
	
	protected Cookies cookie = null;
	
	protected String recordId = "";
	protected String rowId = "";
	protected String aosProductsQuotesId = "";
	protected String docId = "";
	protected String bilingCustomerId = "";
	protected String accountName = "";
	protected String companyNumber = "";
	protected String yesterdayDate = "";
	protected String todayDate = "";
	protected String generatedIban = "";
	protected String paymentDetailsId = "";
	protected int numberOfAttempts;
	
	
	// It is set this date, because for this date we have tariff, tariff prices, ...
	protected String pricingDate = "";
	protected String priceValidUntilDate = "";
	protected String signatureReceivedDate = "";	

	// This date is from SOAPUI. 
	// Each time new contract is created this date should be incremented by 1, and
	// normally a customer switch can only be sent 30 days in the future or in the past. On devint01 we do not have this validation, for UAT* I do not know
	
	//Contract 1: I should set some date in file: create_quote_b2b_tc1.json. Once I started with creation of new contract, I should read upStartDate from file, and increment by 1, and so on
	protected String upStartDate = "";  
	protected String upEndDate = "";
	
	
	// This address params should be any from src/test/resources/data/contract_b2b/address_b2b/adress_b2B.XLSX
	protected String addressNumber = "";
	protected String addressStreet = "";
	protected String addressPostalCode = "";
	protected String addressCity = "";
	
	/* This is address from SOAPUI
	private String addressNumber = "195457";
	private String addressStreet = "Pierre Marchandstraat";
	private String addressPostalCode = "1970";
	private String addressCity = "Wezembeek-Oppem";*/
	
	// This ean should be any from src/test/resources/data/contract_b2b/address_b2b/adress_b2B.XLSX which correspond appropriate address
	protected String ean_c = "";
	
	protected String paymentMethod = ""; // DOM or OV
	protected String legalCommunicationBy = ""; //POST or EMAIL

	public CreateQuoteB2BBase() {
		gson = new Gson();
	}
	
	protected void setPreconditions(String accountName, String upStartDate) throws Exception{
		
		numberOfAttempts = 0;
		
		this.accountName = PrepareDataForQuote.setAccountName(accountName);
		companyNumber = PrepareDataForQuote.generateValidBECompanyNumber();
		yesterdayDate = PrepareDataForQuote.getYesterdayDate();
		todayDate = PrepareDataForQuote.getTodayDate();
		generatedIban = PrepareDataForQuote.getValidIbanBE();
		this.upStartDate = PrepareDataForQuote.getRandomStartContractDate(upStartDate, todayDate);
		
	}
	
	protected void login() {
				
		cookie = RestAssured.given().log().all().contentType(ContentType.JSON).when()
				.body("{ \"username\": \"" + CRMusername + "\", \"password\": \"" + CRMpassword + "\" }")
				.post(ApiPaths.API_LOGIN_CRM).then().log().all().statusCode(200).extract()
				.response().getDetailedCookies();
	}
	
	protected void verifyQuoteStatus(String path, String quoteStage, String quoteStatus) throws IOException {
		String payloadVerifyQuotesOnAccount = path + "verify_quotes_on_account.json.template";
		String originalPayloadVerifyQuotesOnAccount = path + "verify_quotes_on_account.json";
		
		String jsonBody = PrepareDataForQuote.createRequestJsonPayload(payloadVerifyQuotesOnAccount, originalPayloadVerifyQuotesOnAccount, "${recordId}", recordId);
		
		RestAssured.given().log().all().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
			.when().body(jsonBody).post(ApiPaths.API_QUOTES_ON_ACCOUNT).then().log().all().statusCode(200).body("data.rows[0].rowData.stage", equalTo(quoteStage)).body("data.rows[0].rowData.ca_status_c", equalTo(quoteStatus)).extract().response();
		
	}
	
	protected void verifyContractCreated(String path, String quoteStage, String quoteStatus) throws IOException {
		String payloadVerifyContractOnAccount = path + "verify_contract_on_account.json.template";
		String originalPayloadVerifyContractOnAccount = path + "verify_contract_on_account.json";
		
		String jsonBody = PrepareDataForQuote.createRequestJsonPayload(payloadVerifyContractOnAccount, originalPayloadVerifyContractOnAccount, "${recordId}", recordId);
		
		RestAssured.given().log().all().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
			.when().body(jsonBody).post(ApiPaths.API_CONTRACT_ON_ACCOUNT).then().log().all().statusCode(200).body("data.rows[0].rowData.status", equalTo(quoteStage)).body("data.rows[0].rowData.ca_status_c", equalTo(quoteStatus)).extract().response();
		
	}
	
	protected void sendToCustomer(String path) throws IOException {

		modalSendToCustomer(path);

		String payloadModalQuoteSendCustomerReloadList = path
				+ "modal_to_gf_quote_send_to_customer_and_reload_list.json.template";
		String originalPayloadModalQuoteSendCustomerReloadList = path
				+ "modal_to_gf_quote_send_to_customer_and_reload_list.json";
		
		String jsonBody = PrepareDataForQuote.createRequestJsonPayload(payloadModalQuoteSendCustomerReloadList, originalPayloadModalQuoteSendCustomerReloadList, "${rowId}", rowId);

		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_MODAL_TO_GF_QUOTE_SEND_TO_CUSTOMER_AND_RELOAD_LIST).then().log().all()
				.statusCode(200).extract().response();

		
		HashMap model = new JsonPath(response.getBody().asString()).get("data.arguments.model");
		model.put("dwp|sendemailtocust", true);
		model.put("dwp|sendemailtome", false);
		model.put("send_quote_to_c", "BOTH");
		model.put("dwp|send_quote_to_c|matchedCondition", "model['dwp|sendemailtome'] && model['dwp|sendemailtome']");
		String payload = gson.toJson(model); 
		
		String payloadQuoteSendCustomer = path + "gf_quote_send_to_customer.json.template";
		String originalPayloadQuoteSendCustomer = path + "gf_quote_send_to_customer.json";
		
		jsonBody = PrepareDataForQuote.createRequestJsonPayload(payloadQuoteSendCustomer, originalPayloadQuoteSendCustomer, "${model}", payload);

		RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_GF_QUOTE_SEND_TO_CUSTOMER).then().log().all().statusCode(201).body("data.arguments.errors", equalTo(null));
		
	}
	
	
	protected void signatureReceived(String path, String pricingDate, String priceValidUntilDate, String signatureReceivedDate) throws IOException {

		String payloadQuoteSignatureReceived = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "gf_quote_signatureReceived.json.template";
		String originalPayloadQuoteSignatureReceived = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "gf_quote_signatureReceived.json";

		//pricingDate, rowId, recordId, priceValidUntilDate, signatureReceivedDate
		HashMap<String, String> testMap = new HashMap<>();
		testMap.put("${pricingDate}", pricingDate);
		testMap.put("${priceValidUntilDate}", priceValidUntilDate);
		testMap.put("${signatureReceivedDate}", signatureReceivedDate);
		testMap.put("${rowId}", rowId);
		testMap.put("${recordId}", recordId);
		
		String jsonBody = PrepareDataForQuote.createRequestJsonPayload(payloadQuoteSignatureReceived, originalPayloadQuoteSignatureReceived, testMap);
		
		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_GF_QUOTE_SIGNATURE_RECEIVED).then().log().all().statusCode(201)
				.extract().response();
		
		response = RestAssured.given().log().all().cookies(cookie).contentType("multipart/form-data")
				.multiPart("file", new File(Constants.PATH_TO_PDF), "application/pdf")
				.formParams(createFormParamsMap(rowId, accountName, companyNumber, recordId, yesterdayDate, aosProductsQuotesId)).when().post(ApiPaths.API_FILE_UPLOAD).then().log().all().statusCode(200).extract()
				.response();
		
		docId = new JsonPath(response.getBody().asString()).get("data.id");

	}
	
	protected void confirmSigning(String path, String pathJsonFileSignQuote, String apiPathSignQuote) throws IOException {
		String payloadConfirmSigning = path + pathJsonFileSignQuote + ".template";
		String originalPayloadConfirmSigning = path + pathJsonFileSignQuote;

		//rowId, docId, date
		HashMap<String, String> testMap = new HashMap<>();
		testMap.put("${rowId}", rowId);
		testMap.put("${docId}", docId);
		testMap.put("${date}", yesterdayDate);
		testMap.put("${paymentDetailsId}", paymentDetailsId);
		
		String jsonBody = PrepareDataForQuote.createRequestJsonPayload(payloadConfirmSigning, originalPayloadConfirmSigning, testMap);

		RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(apiPathSignQuote).then().log().all().statusCode(201)
				.extract().response();

		String payloadContractsOnAccount = path + "contracts_on_account.json.template";
		String originalPayloadContractsOnAccount = path + "contracts_on_account.json";
		
		jsonBody = PrepareDataForQuote.createRequestJsonPayload(payloadContractsOnAccount, originalPayloadContractsOnAccount, "${recordId}", recordId);
		
		RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_VERIFY_CONTRACT_CREATED).then().log().all().statusCode(200);
		
	}
	
	protected void signMandatePaper(String path) throws IOException {
		
		String payloadBillingCustomerOnAccount = path + "billing_customer_on_account.json.template";
		String originalPayloadBillingCustomerOnAccount = path + "billing_customer_on_account.json";

		String jsonBody = PrepareDataForQuote.createRequestJsonPayload(payloadBillingCustomerOnAccount, originalPayloadBillingCustomerOnAccount, "${recordId}", recordId);
		
		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_LIST_BILLING_CUSTOMER_ACCOUNT).then().statusCode(200)
				.extract().response();
		
		bilingCustomerId = new JsonPath(response.getBody().asString()).get("data.rows[0].id");
		
		String payloadModalSignMandatePaper = path + "modal_to_gf_sign_mandate_paper.json.template";
		String originalModalPayloadSignMandatePaper = path + "modal_to_gf_sign_mandate_paper.json";
		
		//billingCustomerId, recordId
		HashMap<String, String> testMap = new HashMap<>();
		testMap.put("${billingCustomerId}", bilingCustomerId);
		testMap.put("${recordId}", recordId);
		
		jsonBody = PrepareDataForQuote.createRequestJsonPayload(payloadModalSignMandatePaper, originalModalPayloadSignMandatePaper, testMap);
		
		response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_MODAL_TO_GF_SIGN_MANDATE_PAPER).then().statusCode(200)
				.extract().response();
		
		HashMap modelMandatePaper = new JsonPath(response.getBody().asString()).get("data.arguments.model");
		
		response = RestAssured.given().cookies(cookie).contentType("multipart/form-data")
				.multiPart("file", new File(Constants.PATH_TO_PDF), "application/pdf")
				.formParams(createFormParamsMapMandatePaper(bilingCustomerId, recordId)).when().post(ApiPaths.API_FILE_UPLOAD).then().statusCode(200).extract()
				.response();
		
		HashMap upload = new JsonPath(response.getBody().asString()).get("data");
		String payloadUpload = gson.toJson(upload);
		modelMandatePaper.put("dwp|attachment", payloadUpload);
		String payloadModelMandatePaper = gson.toJson(modelMandatePaper);
		
		String payloadSignMandatePaper = path + "gf_sign_mandate_paper.json.template";
		String originalPayloadSignMandatePaper = path + "gf_sign_mandate_paper.json";
		
		String jsonBodyPayloadSignMandatePaper = PrepareDataForQuote.createRequestJsonPayload(payloadSignMandatePaper, originalPayloadSignMandatePaper, "${modelMandatePaper}", payloadModelMandatePaper);
		
		RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBodyPayloadSignMandatePaper).when().post(ApiPaths.API_GF_SIGN_MANDATE_PAPER).then().statusCode(201);
	}
	
	private void listOfQuotesOnAccount(String path) throws IOException {

		String payloadQuotesOnAccount = path + "quotes_on_account.json.template";
		String originalPayloadQuotesOnAccount = path + "quotes_on_account.json";
		
		String jsonBody = PrepareDataForQuote.createRequestJsonPayload(payloadQuotesOnAccount, originalPayloadQuotesOnAccount, "${recordId}", recordId);

		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_QUOTES_ON_ACCOUNT).then().statusCode(200).extract()
				.response();
		
		rowId = new JsonPath(response.getBody().asString()).get("data.rows[0].id");

	}
	
	protected String getAccountNumber(String recordId, Cookies cookie) {

		Response response = RestAssured.given().log().all().cookies(cookie).contentType(ContentType.JSON)
				.accept(ContentType.JSON).when().get(ApiPaths.API_GET_ACCOUNT_NUMBER, recordId).then().log().all()
				.statusCode(200).extract().response();

		return new JsonPath(response.getBody().asString()).get("data.number");
	}
	
	protected void createQuoteB2B(String path, String pathJsonFile, String pathApiPath) throws IOException {
		
		// Combined Customer Switch
		//"move_in_c" : true, "switchtype_c":"ACTIVATION_REQUEST", "dwp|mig_module_c":"START ACCESS", "dwp|mig_label_c":"Combined Customer Switch"
		
		// Supplier Switch
		// "move_in_c":false, "switchtype_c":"SUPPLY_START_REQUEST", "dwp|mig_module_c":"START ACCESS", "dwp|mig_label_c":"Supplier Switch"
		
		// normally a customer switch can only be sent 30 days in the future or in the past
		// Customer Switch
		// "move_in_c":true, "switchtype_c":"CUSTOMER_SWITCH_NOTIFICATION", "dwp|mig_module_c":"START ACCESS", "dwp|mig_label_c":"Customer Switch"
		
		String payloadCreateQuoteB2B = path
				+ pathJsonFile + ".template";
		String originalPayloadCreateQuoteB2B = path
				+ pathJsonFile;
		
		HashMap<String, String> testMap = new HashMap<>();
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

		String jsonBody = PrepareDataForQuote.createRequestJsonPayload(payloadCreateQuoteB2B, originalPayloadCreateQuoteB2B, testMap);
		
		//accountName, companyNumber, pricingDate, addressStreet, addressNumber, addressPostalCode, addressCity, legalCommunicationBy,paymentMethod,generatedIban, ean_c
		Response response = RestAssured.given().log().all().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.when().body(jsonBody).post(pathApiPath).then().log().all().statusCode(201).body("data.arguments.errors", equalTo(null)).extract().response();

		recordId = new JsonPath(response.getBody().asString()).get("data.arguments.params.recordId");
		
		if(path != Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B) {
			paymentDetailsId  = new JsonPath(response.getBody().asString()).get("data.relatedBeans.Paym_Details[0]");
		}
		
		listOfQuotesOnAccount(path);
	}
	
	protected ContractStatus checkContractIsActive(String path) throws Exception {
		
		String payloadContractedEansOnAccount = path + "contracted_eans_on_account.json.template";
		String originalPayloadContractedEansOnAccount = path + "contracted_eans_on_account.json";
				
		String jsonBody = PrepareDataForQuote.createRequestJsonPayload(payloadContractedEansOnAccount, originalPayloadContractedEansOnAccount, "${recordId}", recordId);
		
		String jsonPathFromResponse = "data.rows[0].rowData.contract_line_status_c";
		
		String contractStatus = ContractUtil.waitUntilStringFoundInResponse(cookie, ApiPaths.API_CONTRACTED_EAN, jsonBody,
				ContractStatus.ACTIVE, jsonPathFromResponse, Constants.TIMEOUT_SET_CONTRACT_ACTIVE);
		
		return ContractStatus.fromString(contractStatus);
		
	}


	private void modalSendToCustomer(String path) throws IOException {

		String payloadModalQuoteSentToCustomer = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B
				+ "modal_to_gf_quote_send_to_customer.json.template";
		String originalPayloadModalQuoteSentToCustomer = Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B
				+ "modal_to_gf_quote_send_to_customer.json";

		String jsonBody = PrepareDataForQuote.createRequestJsonPayload(payloadModalQuoteSentToCustomer, originalPayloadModalQuoteSentToCustomer, "${rowId}", rowId);

		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jsonBody).when().post(ApiPaths.API_MODAL_TO_GF_QUOTE_SEND_TO_CUSTOMER).then().log().all().statusCode(200)
				.extract().response();
		
		HashMap model = new JsonPath(response.getBody().asString()).get("data.arguments.model");
		aosProductsQuotesId = (String) model.get("aos_products_quotes|id");
	}
	
	private Map<String, String> createFormParamsMap(String rowId, String accountName, String companyNumber, String recordId,String yesterdayDate, String aosProductsQuotesId){
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
	
	private Map<String, String> createFormParamsMapMandatePaper(String bilingCustomerId, String recordId){
		
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
	
}


