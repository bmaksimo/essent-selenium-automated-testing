package com.essent.testing.restassured.create_contract.constants;

import com.essent.testing.util.resource.ResourceUtil;

public class ContractConstants {

  public static final int TIMEOUT_SET_CONTRACT_ACTIVE = 15;

  // Paths to the pdf files
  public static final String PATH_TO_PDF =
      ResourceUtil.toPath("/data/contract/pdf/customer-signature.pdf");
  public static final String PATH_TO_PRICES =
      ResourceUtil.toPath("/data/contract/soapui_tests_prices");

  // Paths to the json request payloads
  public static final String PATH_TO_JSON_FILES_QUOTE_TC1_B2B =
      ResourceUtil.toPath("/data/contract/b2b/payloads_create_quote_contract_b2b_tc1/");
  public static final String PATH_TO_JSON_FILES_QUOTE_TC1_B2C =
      ResourceUtil.toPath("/data/contract/b2c/payloads_create_quote_contract_b2c_tc1/");
  public static final String PATH_TO_JSON_FILES_QUOTE_TC2_B2B =
      ResourceUtil.toPath("/data/contract/b2b/payloads_create_quote_contract_b2b_tc2/");
  public static final String PATH_TO_JSON_FILES_QUOTE_UP_B2B =
      ResourceUtil.toPath("/data/contract/b2b/payloads_create_quote_contract_b2b_up/");

  public static final String PATH_TO_PROPERTIES_FILE_CREATE_QUOTE_TC1 =
      ResourceUtil.toPath("/data/contract/specific_quote_properties/quote_tc1.properties");
  public static final String PATH_TO_PROPERTIES_FILE_CREATE_QUOTE_TC2 =
      ResourceUtil.toPath("/data/contract/specific_quote_properties/quote_tc2.properties");
  public static final String PATH_TO_PROPERTIES_FILE_CREATE_QUOTE_UP =
      ResourceUtil.toPath("/data/contract/specific_quote_properties/quote_up.properties");

  public static final String PATH_TO_JSON_FILES_CREATE_QUOTE_B2B_TC1 = "create_quote_b2b_tc1.json";
  public static final String PATH_TO_JSON_FILES_CREATE_QUOTE_B2C_TC1 = "create_quote_b2c_tc1.json";
  public static final String PATH_TO_JSON_FILES_CREATE_QUOTE_B2B_TC2 = "create_quote_b2b_tc2.json";
  public static final String PATH_TO_JSON_FILES_CREATE_QUOTE_B2B_UP = "create_quote_b2b_up.json";

  public static final String PATH_TO_JSON_FILES_SIGN_QUOTE_MODAL_TC1 = "sign_quote_modal_tc1.json";
  public static final String PATH_TO_JSON_FILES_SIGN_QUOTE_MODAL_TC2_UP =
      "sign_quote_modal_tc2_up.json";

  // Account names for TC1, TC2, UP contracts
  public static final String ACCOUNT_NAME_PREFIX_TC1_B2B = "B2B_TC1_";
  public static final String ACCOUNT_NAME_PREFIX_TC1_B2C = "B2C_TC1_";
  public static final String ACCOUNT_NAME_PREFIX_TC2_B2B = "B2B_TC2_";
  public static final String ACCOUNT_NAME_PREFIX_UP_B2B = "B2B_UP_";

  // Different quote statuses after creating, sending, signature receiving quote (in different
  // languages: Dutch, English, French)
  public static final String SENT_TO_CUSTOMER_EN = "Send to customer";
  public static final String SENT_TO_CUSTOMER_NL = "Verstuurd naar de klant";
  public static final String SENT_TO_CUSTOMER_FR = "Envoyé au client";

  public static final String PRICED_EN = "Priced";
  public static final String PRICED_NL = "Geprijsd";
  public static final String PRICED_FR = "Tarifé";

  public static final String SIGNATURE_RECEIVED_EN = "Signature received";
  public static final String SIGNATURE_RECEIVED_NL = "Handtekening ontvangen";
  public static final String SIGNATURE_RECEIVED_FR = "Signature reçue";

  public static final String SIGNED_EN = "Signed";
  public static final String SIGNED_NL = "Getekend";
  public static final String SIGNED_FR = "Signé";

  public static final String ACCEPTED_EN = "Accepted";
  public static final String ACCEPTED_NL = "Geaccepteerd";
  public static final String ACCEPTED_FR = "Accepté";

  public static final String STATUS_QUOTE_AFTER_CREATING_TC2_UP_EN =
      PRICED_EN + " - " + ACCEPTED_EN;
  public static final String STATUS_QUOTE_AFTER_CREATING_TC2_UP_NL =
      PRICED_NL + " - " + ACCEPTED_NL;
  public static final String STATUS_QUOTE_AFTER_CREATING_TC2_UP_FR =
      PRICED_FR + " - " + ACCEPTED_FR;

  public static final String STATUS_QUOTE_AFTER_CREATING_TC1_EN =
      SENT_TO_CUSTOMER_EN + " - " + ACCEPTED_EN;
  public static final String STATUS_QUOTE_AFTER_CREATING_TC1_NL =
      SENT_TO_CUSTOMER_NL + " - " + ACCEPTED_NL;
  public static final String STATUS_QUOTE_AFTER_CREATING_TC1_FR =
      SENT_TO_CUSTOMER_FR + " - " + ACCEPTED_FR;

  public static final String STATUS_QUOTE_AFTER_SENDING_EN =
      SENT_TO_CUSTOMER_EN + " - " + ACCEPTED_EN;
  public static final String STATUS_QUOTE_AFTER_SENDING_NL =
      SENT_TO_CUSTOMER_NL + " - " + ACCEPTED_NL;
  public static final String STATUS_QUOTE_AFTER_SENDING_FR =
      SENT_TO_CUSTOMER_FR + " - " + ACCEPTED_FR;

  public static final String STATUS_QUOTE_AFTER_SIGNATURE_RECEIVED_EN =
      SIGNATURE_RECEIVED_EN + " - " + ACCEPTED_EN;
  public static final String STATUS_QUOTE_AFTER_SIGNATURE_RECEIVED_NL =
      SIGNATURE_RECEIVED_NL + " - " + ACCEPTED_NL;
  public static final String STATUS_QUOTE_AFTER_SIGNATURE_RECEIVED_FR =
      SIGNATURE_RECEIVED_FR + " - " + ACCEPTED_FR;
}
