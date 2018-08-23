package com.essent.testing.restassured.create_b2b_contract.constants;

public class Constants {
	
	public static final int TIMEOUT_SET_CONTRACT_ACTIVE = 12;
	
	public static final int MAX_NUMBER_OF_ATTEMPTS_TO_FIND_APPROPRIATE_START_CONTRACT_DATE = 5;
	
	public static final String PATH_TO_PDF = "./src/test/resources/data/contract_b2b/pdf/customer-signature.pdf";
	public static final String PATH_TO_PRICES = "./src/test/resources/data/contract_b2b/soapui_tests_prices";
	
	public static final String PATH_TO_JSON_FILES_QUOTE_TC1_B2B = "./src/test/resources/data/contract_b2b/payloads_create_quote_contract_b2b_tc1/";
	public static final String PATH_TO_JSON_FILES_QUOTE_TC2_B2B = "./src/test/resources/data/contract_b2b/payloads_create_quote_contract_b2b_tc2/";
	public static final String PATH_TO_JSON_FILES_QUOTE_UP_B2B = "./src/test/resources/data/contract_b2b/payloads_create_quote_contract_b2b_up/";
	
	public static final String ACCOUNT_NAME_PREFIX_TC1_B2B = "B2B_TC1_";
	public static final String ACCOUNT_NAME_PREFIX_TC2_B2B = "B2B_TC2_";
	public static final String ACCOUNT_NAME_PREFIX_UP_B2B = "B2B_UP_";
	
	public static final String SENT_TO_CUSTOMER_EN = "Sent to customer";
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
	
	public static final String STATUS_QUOTE_AFTER_CREATING_TC2_UP_EN = PRICED_EN + " - " + ACCEPTED_EN;
	public static final String STATUS_QUOTE_AFTER_CREATING_TC2_UP_NL = PRICED_NL + " - " + ACCEPTED_NL;
	public static final String STATUS_QUOTE_AFTER_CREATING_TC2_UP_FR = PRICED_FR + " - " + ACCEPTED_FR;
	
	public static final String STATUS_QUOTE_AFTER_CREATING_TC1_EN = SENT_TO_CUSTOMER_EN + " - " + ACCEPTED_EN;
	public static final String STATUS_QUOTE_AFTER_CREATING_TC1_NL = SENT_TO_CUSTOMER_NL + " - " + ACCEPTED_NL;
	public static final String STATUS_QUOTE_AFTER_CREATING_TC1_FR = SENT_TO_CUSTOMER_FR + " - " + ACCEPTED_FR;
	
	public static final String STATUS_QUOTE_AFTER_SENDING_EN = SENT_TO_CUSTOMER_EN + " - " + ACCEPTED_EN;
	public static final String STATUS_QUOTE_AFTER_SENDING_NL = SENT_TO_CUSTOMER_NL + " - " + ACCEPTED_NL;
	public static final String STATUS_QUOTE_AFTER_SENDING_FR = SENT_TO_CUSTOMER_FR + " - " + ACCEPTED_FR;
			
	public static final String STATUS_QUOTE_AFTER_SIGNATURE_RECEIVED_EN = SIGNATURE_RECEIVED_EN + " - " + ACCEPTED_EN;
	public static final String STATUS_QUOTE_AFTER_SIGNATURE_RECEIVED_NL = SIGNATURE_RECEIVED_NL + " - " + ACCEPTED_NL;
	public static final String STATUS_QUOTE_AFTER_SIGNATURE_RECEIVED_FR = SIGNATURE_RECEIVED_FR + " - " + ACCEPTED_FR;
	
}
