package com.essent.testing.restassured.create_contract;

import static org.hamcrest.Matchers.equalTo;

import com.billinghouse.cucumber.runtime.parameter.ParameterProvider;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.context.ContextService;
import com.essent.testing.restassured.create_contract.constants.ApiPathsContract;
import com.essent.testing.restassured.create_contract.constants.ContractConstants;
import com.essent.testing.restassured.create_contract.constants.ContractStatus;
import com.essent.testing.restassured.create_contract.constants.SwitchTypes;
import com.essent.testing.restassured.create_contract.helper.ContractUtil;
import com.essent.testing.restassured.create_contract.helper.PrepareDataForContract;
import com.google.gson.Gson;
import cucumber.runtime.CucumberException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.junit.Assert;

public class QuoteCreatorB2CBase {

  private static final Logger logger = Logger.getLogger(QuoteCreatorB2CBase.class);

  private ParameterProvider parameterProvider;

  private String CRMusername = ConfigProvider.getProperty(ConfigKey.DWP_USER_SOAPUI_B2C);
  private String CRMpassword = ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_SOAPUI_B2C);

  protected Gson gson;

  protected Cookies cookie = null;

  protected String recordId = "";
  protected String rowId = "";
  protected String aosProductsQuotesId = "";
  protected String docId = "";
  protected String bilingCustomerId = "";
  protected String accountName = "";
  protected String yesterdayDate = "";
  protected String todayDate = "";
  protected String generatedIban = "";
  protected String paymentDetailsId = "";

  // It is set this date, because for this date we have tariff, tariff prices, ...
  protected String pricingDate = "";
  protected String priceValidUntilDate = "";
  protected String signatureReceivedDate = "";

  // Start contract date
  protected String upStartDate = "";

  // End contract date
  protected String upEndDate = "";

  // This address params should be any from
  // src/test/resources/data/contract/address_b2b/adress_b2B.XLSX
  protected String addressNumber = "";
  protected String addressStreet = "";
  protected String addressPostalCode = "";
  protected String addressCity = "";

  // This ean should be any from adress_b2B.XLSX which correspond appropriate address
  protected String ean_c = "";

  protected String paymentMethod = ""; // DOM or OV
  protected String legalCommunicationBy = ""; // POST or EMAIL

  protected String moveIn = "";
  protected String switchType = "";
  protected String migLabel = "";

  protected String migModul = "";
  protected String meterOpen = "";
  protected String isFakeAddress = "";

  public QuoteCreatorB2CBase() {
    gson = new Gson();

    parameterProvider =
        ((ParameterProvider) ContextService.getContext().getBean("parameterProvider"))
            .consumingNullValues(true);
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  protected void setPreconditions(
      String path, String accountName, String contractStartDate, String contractEndDate)
      throws Exception {

    this.accountName = PrepareDataForContract.setAccountName(accountName);
    yesterdayDate = PrepareDataForContract.getYesterdayDate();
    todayDate = PrepareDataForContract.getTodayDate();
    generatedIban = PrepareDataForContract.getValidIbanBE();

    if (!this.isFakeAddress.equals("FAKE")) {
      // Set appropriate start contract date in create quote page of DWP (only use if addresses are
      // real)
      String currentContractStartDateInDWP = getCurrentContractStartDateFromDWP(path);
      this.upStartDate =
          PrepareDataForContract.setStartContractDate(
              path, contractStartDate, contractEndDate, currentContractStartDateInDWP);
      if (this.upStartDate.equals("NOT_VALID")) {
        logger.warn(
            "ALL START CONTRACT DATES ARE USED FOR ADDRESS STREET: "
                + addressStreet
                + " EAN: "
                + ean_c
                + "; PLEASE USE ANOTHER ADDRESS AND EAN");
        throw new CucumberException(
            "ALL START CONTRACT DATES ARE USED FOR ADDRESS STREET: "
                + addressStreet
                + " EAN: "
                + ean_c
                + "; PLEASE USE ANOTHER ADDRESS AND EAN");
      }
    }
  }

  protected void login() {

    cookie =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .when()
            .body(
                "{ \"username\": \"" + CRMusername + "\", \"password\": \"" + CRMpassword + "\" }")
            .post(ApiPathsContract.API_LOGIN_CRM)
            .then()
            .statusCode(200)
            .extract()
            .response()
            .getDetailedCookies();
  }

  protected void verifyQuoteStatus(String path, String quoteStage, String quoteStatus)
      throws IOException {
    String payloadVerifyQuotesOnAccount = path + "verify_quotes_on_account.json.template";
    String originalPayloadVerifyQuotesOnAccount = path + "verify_quotes_on_account.json";

    String jsonBody =
        PrepareDataForContract.createRequestJsonPayload(
            payloadVerifyQuotesOnAccount,
            originalPayloadVerifyQuotesOnAccount,
            "${recordId}",
            recordId);

    RestAssured.given()
        .cookies(cookie)
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .when()
        .body(jsonBody)
        .post(ApiPathsContract.API_QUOTES_ON_ACCOUNT)
        .then()
        .statusCode(200)
        .body("data.rows[0].rowData.stage", equalTo(quoteStage))
        .body("data.rows[0].rowData.ca_status_c", equalTo(quoteStatus))
        .extract()
        .response();
  }

  protected void verifyContractCreated(String path, String quoteStage, String quoteStatus)
      throws IOException {
    String payloadVerifyContractOnAccount = path + "verify_contract_on_account.json.template";
    String originalPayloadVerifyContractOnAccount = path + "verify_contract_on_account.json";

    String jsonBody =
        PrepareDataForContract.createRequestJsonPayload(
            payloadVerifyContractOnAccount,
            originalPayloadVerifyContractOnAccount,
            "${recordId}",
            recordId);

    RestAssured.given()
        .cookies(cookie)
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .when()
        .body(jsonBody)
        .post(ApiPathsContract.API_CONTRACT_ON_ACCOUNT)
        .then()
        .statusCode(200)
        .body("data.rows[0].rowData.status", equalTo(quoteStage))
        .body("data.rows[0].rowData.ca_status_c", equalTo(quoteStatus))
        .extract()
        .response();
  }

  protected void sendToCustomer(String path) throws IOException {

    modalSendToCustomer(path);

    String payloadModalQuoteSendCustomerReloadList =
        path + "modal_to_gf_quote_send_to_customer_and_reload_list.json.template";
    String originalPayloadModalQuoteSendCustomerReloadList =
        path + "modal_to_gf_quote_send_to_customer_and_reload_list.json";

    String jsonBody =
        PrepareDataForContract.createRequestJsonPayload(
            payloadModalQuoteSendCustomerReloadList,
            originalPayloadModalQuoteSendCustomerReloadList,
            "${rowId}",
            rowId);

    Response response =
        RestAssured.given()
            .cookies(cookie)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(jsonBody)
            .when()
            .post(ApiPathsContract.API_MODAL_TO_GF_QUOTE_SEND_TO_CUSTOMER_AND_RELOAD_LIST)
            .then()
            .statusCode(200)
            .extract()
            .response();

    Map<String, Object> model =
        new JsonPath(response.getBody().asString()).get("data.arguments.model");
    model.put("dwp|sendemailtocust", true);
    model.put("dwp|sendemailtome", false);
    model.put("send_quote_to_c", "BOTH");
    model.put(
        "dwp|send_quote_to_c|matchedCondition",
        "model['dwp|sendemailtome'] && model['dwp|sendemailtome']");
    String payload = gson.toJson(model);

    String payloadQuoteSendCustomer = path + "gf_quote_send_to_customer.json.template";
    String originalPayloadQuoteSendCustomer = path + "gf_quote_send_to_customer.json";

    jsonBody =
        PrepareDataForContract.createRequestJsonPayload(
            payloadQuoteSendCustomer, originalPayloadQuoteSendCustomer, "${model}", payload);

    RestAssured.given()
        .cookies(cookie)
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body(jsonBody)
        .when()
        .post(ApiPathsContract.API_GF_QUOTE_SEND_TO_CUSTOMER)
        .then()
        .statusCode(201)
        .body("data.arguments.errors", equalTo(null));
  }

  protected void signatureReceived(
      String path, String pricingDate, String priceValidUntilDate, String signatureReceivedDate)
      throws IOException {

    String payloadQuoteSignatureReceived = path + "gf_quote_signatureReceived.json.template";
    String originalPayloadQuoteSignatureReceived = path + "gf_quote_signatureReceived.json";

    // pricingDate, rowId, recordId, priceValidUntilDate, signatureReceivedDate
    HashMap<String, String> testMap = new HashMap<>();
    testMap.put("${pricingDate}", pricingDate);
    testMap.put("${priceValidUntilDate}", priceValidUntilDate);
    testMap.put("${signatureReceivedDate}", signatureReceivedDate);
    testMap.put("${rowId}", rowId);
    testMap.put("${recordId}", recordId);

    String jsonBody =
        PrepareDataForContract.createRequestJsonPayload(
            payloadQuoteSignatureReceived, originalPayloadQuoteSignatureReceived, testMap);

    Response response =
        RestAssured.given()
            .cookies(cookie)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(jsonBody)
            .when()
            .post(ApiPathsContract.API_GF_QUOTE_SIGNATURE_RECEIVED)
            .then()
            .statusCode(201)
            .extract()
            .response();

    response =
        RestAssured.given()
            .cookies(cookie)
            .contentType("multipart/form-data")
            .multiPart("file", new File(ContractConstants.PATH_TO_PDF), "application/pdf")
            .formParams(
                createFormParamsMap(
                    rowId, accountName, recordId, yesterdayDate, aosProductsQuotesId))
            .when()
            .post(ApiPathsContract.API_FILE_UPLOAD)
            .then()
            .statusCode(200)
            .extract()
            .response();

    docId = new JsonPath(response.getBody().asString()).get("data.id");
  }

  protected void confirmSigning(String path, String pathJsonFileSignQuote, String apiPathSignQuote)
      throws IOException {
    String payloadConfirmSigning = path + pathJsonFileSignQuote + ".template";
    String originalPayloadConfirmSigning = path + pathJsonFileSignQuote;

    // rowId, docId, date
    HashMap<String, String> testMap = new HashMap<>();
    testMap.put("${rowId}", rowId);
    testMap.put("${docId}", docId);
    testMap.put("${date}", yesterdayDate);
    testMap.put("${paymentDetailsId}", paymentDetailsId);

    String jsonBody =
        PrepareDataForContract.createRequestJsonPayload(
            payloadConfirmSigning, originalPayloadConfirmSigning, testMap);

    RestAssured.given()
        .cookies(cookie)
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body(jsonBody)
        .when()
        .post(apiPathSignQuote)
        .then()
        .statusCode(201)
        .extract()
        .response();

    String payloadContractsOnAccount = path + "contracts_on_account.json.template";
    String originalPayloadContractsOnAccount = path + "contracts_on_account.json";

    jsonBody =
        PrepareDataForContract.createRequestJsonPayload(
            payloadContractsOnAccount, originalPayloadContractsOnAccount, "${recordId}", recordId);

    RestAssured.given()
        .cookies(cookie)
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body(jsonBody)
        .when()
        .post(ApiPathsContract.API_VERIFY_CONTRACT_CREATED)
        .then()
        .statusCode(200);
  }

  protected void signMandatePaper(String path) throws IOException {

    String payloadBillingCustomerOnAccount = path + "billing_customer_on_account.json.template";
    String originalPayloadBillingCustomerOnAccount = path + "billing_customer_on_account.json";

    String jsonBody =
        PrepareDataForContract.createRequestJsonPayload(
            payloadBillingCustomerOnAccount,
            originalPayloadBillingCustomerOnAccount,
            "${recordId}",
            recordId);

    Response response =
        RestAssured.given()
            .cookies(cookie)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(jsonBody)
            .when()
            .post(ApiPathsContract.API_LIST_BILLING_CUSTOMER_ACCOUNT)
            .then()
            .statusCode(200)
            .extract()
            .response();

    bilingCustomerId = new JsonPath(response.getBody().asString()).get("data.rows[0].id");

    String payloadModalSignMandatePaper = path + "modal_to_gf_sign_mandate_paper.json.template";
    String originalModalPayloadSignMandatePaper = path + "modal_to_gf_sign_mandate_paper.json";

    // billingCustomerId, recordId
    HashMap<String, String> testMap = new HashMap<>();
    testMap.put("${billingCustomerId}", bilingCustomerId);
    testMap.put("${recordId}", recordId);

    jsonBody =
        PrepareDataForContract.createRequestJsonPayload(
            payloadModalSignMandatePaper, originalModalPayloadSignMandatePaper, testMap);

    response =
        RestAssured.given()
            .cookies(cookie)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(jsonBody)
            .when()
            .post(ApiPathsContract.API_MODAL_TO_GF_SIGN_MANDATE_PAPER)
            .then()
            .statusCode(200)
            .extract()
            .response();

    Map<String, Object> modelMandatePaper =
        new JsonPath(response.getBody().asString()).get("data.arguments.model");

    response =
        RestAssured.given()
            .cookies(cookie)
            .contentType("multipart/form-data")
            .multiPart("file", new File(ContractConstants.PATH_TO_PDF), "application/pdf")
            .formParams(createFormParamsMapMandatePaper(bilingCustomerId, recordId))
            .when()
            .post(ApiPathsContract.API_FILE_UPLOAD)
            .then()
            .statusCode(200)
            .extract()
            .response();

    Map<String, Object> upload = new JsonPath(response.getBody().asString()).get("data");
    String payloadUpload = gson.toJson(upload);
    modelMandatePaper.put("dwp|attachment", payloadUpload);
    String payloadModelMandatePaper = gson.toJson(modelMandatePaper);

    String payloadSignMandatePaper = path + "gf_sign_mandate_paper.json.template";
    String originalPayloadSignMandatePaper = path + "gf_sign_mandate_paper.json";

    String jsonBodyPayloadSignMandatePaper =
        PrepareDataForContract.createRequestJsonPayload(
            payloadSignMandatePaper,
            originalPayloadSignMandatePaper,
            "${modelMandatePaper}",
            payloadModelMandatePaper);

    RestAssured.given()
        .cookies(cookie)
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body(jsonBodyPayloadSignMandatePaper)
        .when()
        .post(ApiPathsContract.API_GF_SIGN_MANDATE_PAPER)
        .then()
        .statusCode(201);
  }

  private void listOfQuotesOnAccount(String path) throws IOException {

    String payloadQuotesOnAccount = path + "quotes_on_account.json.template";
    String originalPayloadQuotesOnAccount = path + "quotes_on_account.json";

    String jsonBody =
        PrepareDataForContract.createRequestJsonPayload(
            payloadQuotesOnAccount, originalPayloadQuotesOnAccount, "${recordId}", recordId);

    Response response =
        RestAssured.given()
            .cookies(cookie)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(jsonBody)
            .when()
            .post(ApiPathsContract.API_QUOTES_ON_ACCOUNT)
            .then()
            .statusCode(200)
            .extract()
            .response();

    rowId = new JsonPath(response.getBody().asString()).get("data.rows[0].id");
  }

  protected String getAccountNumber(String recordId, Cookies cookie) {

    Response response =
        RestAssured.given()
            .cookies(cookie)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .when()
            .get(ApiPathsContract.API_GET_ACCOUNT_NUMBER, recordId)
            .then()
            .statusCode(200)
            .extract()
            .response();

    return new JsonPath(response.getBody().asString()).get("data.number");
  }

  protected void createQuoteB2C(String path, String pathJsonFile, String pathApiPath)
      throws IOException {

    // Combined Customer Switch
    // "move_in_c" : true, "switchtype_c":"ACTIVATION_REQUEST", "dwp|mig_module_c":"START ACCESS",
    // "dwp|mig_label_c":"Combined Customer Switch"

    // Supplier Switch
    // "move_in_c":false, "switchtype_c":"SUPPLY_START_REQUEST", "dwp|mig_module_c":"START ACCESS",
    // "dwp|mig_label_c":"Supplier Switch"

    // normally a customer switch can only be sent 30 days in the future or in the past
    // Customer Switch
    // "move_in_c":true, "switchtype_c":"CUSTOMER_SWITCH_NOTIFICATION", "dwp|mig_module_c":"START
    // ACCESS", "dwp|mig_label_c":"Customer Switch"

    String payloadCreateQuoteB2C = path + pathJsonFile + ".template";
    String originalPayloadCreateQuoteB2C = path + pathJsonFile;

    HashMap<String, String> testMap = new HashMap<>();
    testMap.put("${accountName}", accountName);
    testMap.put("${pricingDate}", pricingDate);
    testMap.put("${addressStreet}", addressStreet);
    testMap.put("${addressNumber}", addressNumber);
    testMap.put("${addressPostalCode}", addressPostalCode);
    testMap.put("${addressCity}", addressCity);
    testMap.put("${legalCommunicationBy}", legalCommunicationBy);
    testMap.put("${paymentMethod}", paymentMethod);
    testMap.put("${generatedIban}", generatedIban);
    testMap.put("${ean_c}", ean_c);
    testMap.put("${up_start_date}", upStartDate);
    testMap.put("${upEndDate}", upEndDate);
    testMap.put("${moveIn}", moveIn);
    testMap.put("${switchType}", switchType);
    testMap.put("${migLabel}", migLabel);
    testMap.put("${uuid}", "1" + (long) (Math.random() * (99999 - 10000) + 10000));
    testMap.put("${migModul}", migModul);
    testMap.put("${meterOpen}", meterOpen);

    String jsonBody =
        PrepareDataForContract.createRequestJsonPayload(
            payloadCreateQuoteB2C, originalPayloadCreateQuoteB2C, testMap);

    Response response =
        RestAssured.given()
            .cookies(cookie)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .when()
            .body(jsonBody)
            .post(pathApiPath)
            .then()
            .statusCode(201)
            .body("data.arguments.errors", equalTo(null))
            .extract()
            .response();

    recordId = new JsonPath(response.getBody().asString()).get("data.arguments.params.recordId");

    if (path != ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C) {
      paymentDetailsId =
          new JsonPath(response.getBody().asString()).get("data.relatedBeans.Paym_Details[0]");
    }

    listOfQuotesOnAccount(path);
  }

  protected ContractStatus checkContractIsActive(String path) throws Exception {

    String payloadContractedEansOnAccount = path + "contracted_eans_on_account.json.template";
    String originalPayloadContractedEansOnAccount = path + "contracted_eans_on_account.json";

    String jsonBody =
        PrepareDataForContract.createRequestJsonPayload(
            payloadContractedEansOnAccount,
            originalPayloadContractedEansOnAccount,
            "${recordId}",
            recordId);

    String jsonPathFromResponse = "data.rows[0].rowData.contract_line_status_c";

    String contractStatus =
        ContractUtil.waitUntilStringFoundInResponse(
            cookie,
            ApiPathsContract.API_CONTRACTED_EAN,
            jsonBody,
            ContractStatus.ACTIVE,
            jsonPathFromResponse,
            ContractConstants.TIMEOUT_SET_CONTRACT_ACTIVE);

    return ContractStatus.fromString(contractStatus);
  }

  private void modalSendToCustomer(String path) throws IOException {

    String payloadModalQuoteSentToCustomer =
        path + "modal_to_gf_quote_send_to_customer.json.template";
    String originalPayloadModalQuoteSentToCustomer =
        path + "modal_to_gf_quote_send_to_customer.json";

    String jsonBody =
        PrepareDataForContract.createRequestJsonPayload(
            payloadModalQuoteSentToCustomer,
            originalPayloadModalQuoteSentToCustomer,
            "${rowId}",
            rowId);

    Response response =
        RestAssured.given()
            .cookies(cookie)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(jsonBody)
            .when()
            .post(ApiPathsContract.API_MODAL_TO_GF_QUOTE_SEND_TO_CUSTOMER)
            .then()
            .statusCode(200)
            .extract()
            .response();

    Map<String, Object> model =
        new JsonPath(response.getBody().asString()).get("data.arguments.model");
    aosProductsQuotesId = (String) model.get("aos_products_quotes|id");
  }

  private Map<String, String> createFormParamsMap(
      String rowId,
      String accountName,
      String recordId,
      String yesterdayDate,
      String aosProductsQuotesId) {
    Map<String, String> formParams = new HashMap<>();

    formParams.put("model[id]", rowId);
    formParams.put("model[dwp|id]", rowId);
    formParams.put("model[accounts|name]", accountName);
    formParams.put("model[dwp|recordType]", "AOS_Quotes");
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
    formParams.put("model[accounts|record_type]", "B2C");
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

  private Map<String, String> createFormParamsMapMandatePaper(
      String bilingCustomerId, String recordId) {

    Map<String, String> formParams = new HashMap<>();

    formParams.put("model[recordTypeOfRecordId]", "Paym_Details");
    formParams.put("model[recordId]", bilingCustomerId);
    formParams.put("model[baseModule]", "Paym_Details");
    formParams.put("model[dwp|recordType]", "AccountsAsQuotes");
    formParams.put("model[accounts|id]", recordId);
    formParams.put("model[dwp|id]", recordId);
    formParams.put("model[id]", bilingCustomerId);
    formParams.put("fieldGuid", "2bee8c38-dfdc-bbcb-b373-59d488de9d5e");
    formParams.put("model[accounts|name]", "");
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

  protected void getQuoteProperties(String path, String isFakeAddress, String switchType)
      throws FileNotFoundException, IOException {
    this.isFakeAddress = isFakeAddress;

    Properties prop = ContractUtil.loadProperties(path);

    pricingDate = prop.getProperty("pricing_date");
    priceValidUntilDate = prop.getProperty("price_valid_until_date");
    signatureReceivedDate = prop.getProperty("signature_received_date");

    upStartDate = prop.getProperty("up_start_date");
    upEndDate = prop.getProperty("up_end_date");

    paymentMethod = prop.getProperty("payment_method");
    legalCommunicationBy = prop.getProperty("legal_communication_by");

    migLabel = prop.getProperty("mig_label_c");

    if (!isFakeAddress.equals("FAKE")) {
      addressNumber = prop.getProperty("address_number");
      addressStreet = prop.getProperty("address_street");
      addressPostalCode = prop.getProperty("address_postal_code");
      addressCity = prop.getProperty("address_city");
      ean_c = prop.getProperty("ean_c");
      parameterProvider.put("EAN-code", ean_c);
      moveIn = prop.getProperty("move_in_c");
      switchType = prop.getProperty("switchtype_c");
      migLabel = prop.getProperty("mig_label_c");
      migModul = prop.getProperty("mig_modul_c");
      meterOpen = prop.getProperty("meter_open");
    } else getAddressEANSwitchType(prop, switchType);
  }

  private String getCurrentContractStartDateFromDWP(String path) throws IOException {

    String upStartDate = "";

    String payloadFilterByEan = path + "filter_quotes_by_ean.json.template";
    String originalpayloadFilterByEan = path + "filter_quotes_by_ean.json";

    String jsonBody =
        PrepareDataForContract.createRequestJsonPayload(
            payloadFilterByEan, originalpayloadFilterByEan, "${ean_c}", ean_c);

    Response response =
        RestAssured.given()
            .cookies(cookie)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(jsonBody)
            .when()
            .post(ApiPathsContract.API_LIST_QUOTES)
            .then()
            .statusCode(200)
            .extract()
            .response();

    Map<String, Map<String, Object>> isContractExist =
        new JsonPath(response.getBody().asString()).get("data.rows[0]");
    if (isContractExist != null) {
      Map<String, Object> rowData = isContractExist.get("rowData");
      upStartDate = (String) rowData.get("aos_products_quotes|up_start_date_c");
    }

    return upStartDate;
  }

  private void getAddressEANSwitchType(Properties prop, String typeSwitch) {

    addressStreet = prop.getProperty("fake_address_street");
    addressPostalCode = prop.getProperty("fake_address_postal_code");
    addressCity = prop.getProperty("fake_address_city");
    addressNumber = PrepareDataForContract.getRandomAddressNumber();

    ean_c = PrepareDataForContract.generateEAN();
    parameterProvider.put("EAN-code", ean_c);

    SwitchTypes switchTypeStatus = SwitchTypes.fromString(typeSwitch);

    switch (switchTypeStatus) {
      case SUPPLIER_SWITCH:
        {
          moveIn = "false";
          switchType = "SUPPLY_START_REQUEST";
          migLabel = "Supplier Switch";
          migModul = "START ACCESS";
          meterOpen = "true";
          break;
        }
      case MOVE_IN:
        {
          moveIn = "false";
          switchType = "ACTIVATION_REQUEST";
          migLabel = "Move In";
          migModul = "MOVE IN";
          meterOpen = "false";
          break;
        }
      case CUSTOMER_SWITCH:
        {
          moveIn = "true";
          switchType = "CUSTOMER_SWITCH_NOTIFICATION";
          migLabel = "Customer Switch";
          migModul = "START ACCESS";
          meterOpen = "true";
          break;
        }
      case COMBINED_CUSTOMER_SWITCH:
        {
          moveIn = "true";
          switchType = "ACTIVATION_REQUEST";
          migLabel = "Combined Customer Switchh";
          migModul = "START ACCESS";
          meterOpen = "true";
          break;
        }
      default:
        logger.error(
            "Switch type: " + typeSwitch + " doesn't exist. Please use another switch type.");
        Assert.fail(
            "Switch type: " + typeSwitch + " doesn't exist. Please use another switch type.");
    }
  }
}
