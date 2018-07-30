package com.essent.testing.restassured;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;



public class CreateQuoteTC1B2B {

	private static final String PATH_TO_JSON_FILES_QUOTE_TC1_B2B = "./src/test/resources/data/payloads_create_quote_b2b/";

	
	private static final String API_LOGIN_CRM = "https://devint01.nova.essent.be/nova-crm/Api/V8/login";
	private static final String API_CREATE_QUOTE_B2B_TC1 = "https://devint01.nova.essent.be/nova-crm/Api/V8_Custom/Flow/B2B_CQ_TC1";
	private static final String API_QUOTES_ON_ACCOUNT = "https://devint01.nova.essent.be/nova-crm/Api/V8_Custom/List/QuotesOnAccount";
	private static final String API_MODAL_TO_GF_QUOTE_SEND_TO_CUSTOMER = "https://devint01.nova.essent.be/nova-crm/Api/V8_Custom/Action/modal_to_gf_quote_send_to_customer";
	private static final String API_MODAL_TO_GF_QUOTE_SEND_TO_CUSTOMER_AND_RELOAD_LIST = "https://devint01.nova.essent.be/nova-crm/Api/V8_Custom/Action/modal_to_gf_quote_send_to_customer_and_reload_list";
	private static final String API_GF_QUOTE_SEND_TO_CUSTOMER = "https://devint01.nova.essent.be/nova-crm/Api/V8_Custom/Flow/gf_quote_send_to_customer";
	private static final String API_GF_QUOTE_SIGNATURE_RECEIVED = "https://devint01.nova.essent.be/nova-crm/Api/V8_Custom/Flow/gf_quote_signatureReceived";
	private static final String API_FILE_UPLOAD = "https://devint01.nova.essent.be/nova-crm/Api/V8_Custom/fileupload";
	private static final String API_SIGN_QUOTE_MODAL_TC1 = "https://devint01.nova.essent.be/nova-crm/Api/V8_Custom/Flow/sign_quote_modal_tc1";
	private static final String API_VERIFY_CONTRACT_CREATED = "https://devint01.nova.essent.be/nova-crm/Api/V8_Custom/List/ContractsOnAccount";
	private static final String API_LIST_BILLING_CUSTOMER_ACCOUNT = "https://devint01.nova.essent.be/nova-crm/Api/V8_Custom/List/BillingCustomerOnaccount";
	private static final String API_MODAL_TO_GF_SIGN_MANDATE_PAPER = "https://devint01.nova.essent.be/nova-crm/Api/V8_Custom/Action/Modal_to_gf_sign_mandate_paper";
	private static final String API_GF_SIGN_MANDATE_PAPER = "https://devint01.nova.essent.be/nova-crm/Api/V8_Custom/Flow/gf_sign_mandate_paper";

	
	private String recordId = "";
	private String rowId = "";
	private String contractId = "";
	private String model = "";
	private String docId = "";
	private String bilingCustomerId = "";
	private String modelMandatePaper = "";
	private String upload = "";
	
	private String pricingDate = "2017-11-01 12:22:00";
	private String priceValidUntilDate = "2017-11-01 12:22:00";
	private String signatureReceivedDate = "2017-11-01 12:22:00";		
	
	private Cookies cookie;


	public void login() {

		cookie = RestAssured.given().contentType(ContentType.JSON).when()
				.body("{ \"username\": \"soapui_b2b\", \"password\": \"504pu17357\" }")
				.post(API_LOGIN_CRM).then().statusCode(200).extract()
				.response().getDetailedCookies();
	}

	public void createQuoteTC1B2B() throws IOException {

		String jsonBody = generateStringFromResource(PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "create_quote_b2b_tc1.json");

		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.when().body(jsonBody).post(API_CREATE_QUOTE_B2B_TC1).then().statusCode(201).extract().response();

		recordId = new JsonPath(response.getBody().asString()).get("data.arguments.params.recordId");
		
		listOfQuotesOnAccount();

	}

	public void sendToCustomer() throws IOException {

		modalSendToCustomer();

		String payloadModalQuoteSendCustomerReloadList = PATH_TO_JSON_FILES_QUOTE_TC1_B2B
				+ "modal_to_gf_quote_send_to_customer_and_reload_list.json.template";

		updatePayloadJson(payloadModalQuoteSendCustomerReloadList, "${rowId}", rowId);

		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(payloadModalQuoteSendCustomerReloadList).when().post(API_MODAL_TO_GF_QUOTE_SEND_TO_CUSTOMER_AND_RELOAD_LIST).then()
				.statusCode(200).extract().response();

		model = new JsonPath(response.getBody().asString()).get("data.arguments.model");

		model = model.replaceAll("\": ", "\":");
		model = model.replace("sendemailtocust\":false", "sendemailtocust\": true");
		model = model.replace("sendemailtome\":null", "sendemailtome\":true");
		model = model.replace("send_quote_to_c\":null", "send_quote_to_c\":\"BOTH\"");
		model = model.replace("matchedCondition\":\"default\"", "matchedCondition\":\"model['dwp|sendemailtome'] && model['dwp|sendemailtome']\"");

		String payloadQuoteSendCustomer = PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "gf_quote_send_to_customer.json.template";

		updatePayloadJson(payloadQuoteSendCustomer, "${model}", model);

		RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(payloadQuoteSendCustomer).when().post(API_GF_QUOTE_SEND_TO_CUSTOMER).then().statusCode(201);

	}

	public void signatureReceived() throws IOException {

		String payloadQuoteSignatureReceived = PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "gf_quote_signatureReceived.json.template";

		//TODO
		//pricingDate, rowId, recordId, priceValidUntilDate, signatureReceivedDate
		HashMap<String, String> testMap = new HashMap<String, String>();
		testMap.put("${pricingDate}", pricingDate);
		testMap.put("${priceValidUntilDate}", priceValidUntilDate);
		testMap.put("${signatureReceivedDate}", signatureReceivedDate);
		testMap.put("${rowId}", rowId);
		
		updatePayloadJson(payloadQuoteSignatureReceived, testMap);

		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(payloadQuoteSignatureReceived).when().post(API_GF_QUOTE_SIGNATURE_RECEIVED).then().statusCode(201)
				.extract().response();
		
		
		response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON)
				.multiPart("test.pdf", new File("./src/test/resources/data/dwp/signed-document.pdf"))
				.formParams(createFormParamsMap()).when().post(API_FILE_UPLOAD).then().statusCode(200).extract()
				.response();
		
		docId = new JsonPath(response.getBody().asString()).get("data.id");

	}
	
	public void confirmSigning() throws IOException {
		String payloadConfirmSigning = PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "sign_quote_modal_tc1.json.template";

		//TODO
		//rowId, docId, date
		HashMap<String, String> testMap = new HashMap<String, String>();
		testMap.put("${rowId}", rowId);
		testMap.put("${docId}", docId);
		testMap.put("${date}", getYesterdayDay());
		
		updatePayloadJson(payloadConfirmSigning, testMap);

		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(payloadConfirmSigning).when().post(API_SIGN_QUOTE_MODAL_TC1).then().statusCode(201)
				.extract().response();

		String payloadContractsOnAccount = PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "contracts_on_account.json.template";
		
		updatePayloadJson(payloadContractsOnAccount, "${recordId}", recordId);
		
		RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(payloadContractsOnAccount).when().post(API_VERIFY_CONTRACT_CREATED).then().statusCode(200);
		
		
	}
	
	//Only required if we want to have payment method: DOM
	public void signMandatePaper() throws IOException {
		
		String payloadBillingCustomerOnAccount = PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "billing_customer_on_account.json.template";

		updatePayloadJson(payloadBillingCustomerOnAccount, "${recordId}", recordId);
		
		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(payloadBillingCustomerOnAccount).when().post(API_LIST_BILLING_CUSTOMER_ACCOUNT).then().statusCode(200)
				.extract().response();
		
		bilingCustomerId = new JsonPath(response.getBody().asString()).get("data.rows[0].id");
		
		
		//TODO
		//billingCustomerId, recordId
		HashMap<String, String> testMap = new HashMap<String, String>();
		testMap.put("${billingCustomerId}", bilingCustomerId);
		testMap.put("${recordId}", recordId);
		
		updatePayloadJson(payloadBillingCustomerOnAccount, testMap);
		
		String payloadSignMandatePaper = PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "modal_to_gf_sign_mandate_paper.json.template";
		
		response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(payloadSignMandatePaper).when().post(API_MODAL_TO_GF_SIGN_MANDATE_PAPER).then().statusCode(200)
				.extract().response();
		
		modelMandatePaper = new JsonPath(response.getBody().asString()).get("data.arguments.model");
		
		response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON)
				.multiPart("test.pdf", new File("./src/test/resources/data/dwp/signed-document.pdf"))
				.formParams(createFormParamsMapMandatePaper()).when().post(API_FILE_UPLOAD).then().statusCode(200).extract()
				.response();
		
		upload = new JsonPath(response.getBody().asString()).get("data");
		
		//modelMandatePaper = modelMandatePaper.replaceFirst(/\[\]/,"["+upload+"]");
		
		//RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
		//		.body(payloadQuotesOnAccount).when().post(API_GF_SIGN_MANDATE_PAPER).then().statusCode(201);
		
	}
	
	
	
	
	private static Map<String, String> createFormParamsMap(){
		Map<String, String> formParams = new HashMap<>();
		formParams.put("model[id]", "${rowId}");
		formParams.put("model[dwp|id]", "${rowId}");
		formParams.put("model[accounts|name]", "${accountNameStatic}");
		formParams.put("model[dwp|recordType]", "AOS_Quotes");
		formParams.put("model[accounts|company_number_c]", "${companyNumber}");
		formParams.put("model[stage]", "SIGNED");
		formParams.put("model[accounts|id]", "${recordId}");
		formParams.put("model[sign_date_c]", "${date}");
		formParams.put("model[recordTypeOfRecordId]", "AOS_Quotes");
		formParams.put("model[baseModule]", "AOS_Quotes");
		formParams.put("model[assigned_user_id][0][key]", "1");
		formParams.put("model[assigned_user_id][0][label]", "Administrator");
		formParams.put("model[primary_group_id][0][key]", "abeba670-1428-dbca-6a0b-57d11cbdb3bd");
		formParams.put("model[primary_group_id][0][label]", "TDS");
		formParams.put("model[dwp|mainDocType]", "");
		formParams.put("model[dwp|subDocType]", "");
		formParams.put("model[accounts|record_type]", "B2B");
		formParams.put("model[aos_products_quotes|id]", "${aosProductsQuotesId}");
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
	
	
	private static Map<String, String> createFormParamsMapMandatePaper(){
		Map<String, String> formParams = new HashMap<>();
		
		formParams.put("model[recordTypeOfRecordId]", "Paym_Details");
		formParams.put("model[recordId]", "${bilingCustomerId}");
		formParams.put("model[baseModule]", "Paym_Details");
		formParams.put("model[dwp|recordType]", "Accounts");
		formParams.put("model[accounts|id]", "${recordId}");
		formParams.put("model[dwp|id]", "${recordId}");
		formParams.put("model[id]", "${bilingCustomerId}");
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
	
	

	private String generateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}

	// TODO originalValue can be array of strings. Find solution to implement that
	// and not only one String
	private void updatePayloadJson(String path, String replaceString, String originalValue) throws IOException {
		File file = new File(path);
		String fileContext = FileUtils.readFileToString(file);
		fileContext = fileContext.replace(replaceString, originalValue);
		FileUtils.write(file, fileContext);
	}
	
	private void updatePayloadJson(String path, HashMap<String, String> mapValues) throws IOException {
		File file = new File(path);
		String fileContext = FileUtils.readFileToString(file);
		
		for (Entry<String, String> entry : mapValues.entrySet()) {
			if(fileContext.contains(entry.getKey())) {
				fileContext = fileContext.replace(entry.getKey(), entry.getValue());
			}
		}
		
		FileUtils.write(file, fileContext);
	}
	
	private void listOfQuotesOnAccount() throws IOException {

		String payloadQuotesOnAccount = PATH_TO_JSON_FILES_QUOTE_TC1_B2B + "quotes_on_account.json.template";

		updatePayloadJson(payloadQuotesOnAccount, "${recordId}", recordId);

		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(payloadQuotesOnAccount).when().post(API_QUOTES_ON_ACCOUNT).then().statusCode(200).extract()
				.response();

		rowId = new JsonPath(response.getBody().asString()).get("data.rows[0].id");

	}

	private void modalSendToCustomer() throws IOException {

		String payloadModalQuoteSentToCustomer = PATH_TO_JSON_FILES_QUOTE_TC1_B2B
				+ "modal_to_gf_quote_send_to_customer.json.template";

		updatePayloadJson(payloadModalQuoteSentToCustomer, "${rowId}", rowId);

		Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(payloadModalQuoteSentToCustomer).when().post(API_MODAL_TO_GF_QUOTE_SEND_TO_CUSTOMER).then().statusCode(200)
				.extract().response();

		contractId = new JsonPath(response.getBody().asString()).get("data.arguments.model.contacts|id");
	}
	
	private String getYesterdayDay() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}
}
