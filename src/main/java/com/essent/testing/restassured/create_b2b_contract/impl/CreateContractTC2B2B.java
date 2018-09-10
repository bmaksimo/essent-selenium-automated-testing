package com.essent.testing.restassured.create_b2b_contract.impl;

import java.io.IOException;

import com.essent.testing.restassured.create_b2b_contract.CreateQuoteB2B;
import com.essent.testing.restassured.create_b2b_contract.CreateQuoteB2BBase;
import com.essent.testing.restassured.create_b2b_contract.constants.ApiPaths;
import com.essent.testing.restassured.create_b2b_contract.constants.Constants;
import com.essent.testing.restassured.create_b2b_contract.constants.ContractStatus;

public class CreateContractTC2B2B extends CreateQuoteB2BBase implements CreateQuoteB2B {

	public CreateContractTC2B2B() {
		super();
		
		// These dates are set, because for this date we have tariff, tariff prices, ...
		pricingDate = "2016-11-01 12:22:00";
		priceValidUntilDate = "2016-12-12 11:00:00";
		signatureReceivedDate = "2016-11-01 12:22:00";

		//This is address from SOAPUI
		/*
		 * private String addressNumber = "175240"; private String addressStreet =
		 * "Stadsveld"; private String addressPostalCode = "3012"; private String
		 * addressCity = "WILSELE";
		 */

		// This address params are located in src/test/resources/data/contract_b2b/address_b2b/adress_b2B.XLSX
		addressNumber = "36";
		addressStreet = "Heiken";
		addressPostalCode = "2960";
		addressCity = "Brecht";

		// This date is from SOAPUI.
		// Each time new contract is created this date should be incremented by 1, and
		// normally a customer switch can only be sent 30 days in the future or in the
		// past. On devint01 we do not have this validation, for UAT* I do not know

		// Contract 1: I should set some date in file: create_quote_b2b_tc2.json. Once I
		// started with creation of new contract, I should read upStartDate from file,
		// and increment by 1, and so on
		upStartDate = "2017-03-22";
		upEndDate = "2018-10-31";

		// This ean should be any from src/test/resources/data/contract_b2b/address_b2b/adress_b2B.XLSX which correspond appropriate address
		ean_c = "541448810000057966";
		paymentMethod = "DOM"; // DOM or OV
		legalCommunicationBy = "POST"; // POST or EMAIL
	}
	
	@Override
	public void setPreconditions(String accountName, String upStartDate) throws Exception{
		super.setPreconditions(accountName, upStartDate);
	}

	@Override
	public String createContractB2B() throws Exception {
		setPreconditions(Constants.ACCOUNT_NAME_PREFIX_TC2_B2B, upStartDate);
		login();
		createQuoteB2B(Constants.PATH_TO_JSON_FILES_QUOTE_TC2_B2B, Constants.PATH_TO_JSON_FILES_CREATE_QUOTE_B2B_TC2, ApiPaths.API_CREATE_QUOTE_B2B_TC2_UP);
		verifyQuoteStatus(Constants.PATH_TO_JSON_FILES_QUOTE_TC2_B2B, Constants.PRICED_EN.toUpperCase(), Constants.ACCEPTED_EN.toUpperCase());
		sendToCustomer(Constants.PATH_TO_JSON_FILES_QUOTE_TC2_B2B);
		verifyQuoteStatus(Constants.PATH_TO_JSON_FILES_QUOTE_TC2_B2B, Constants.SENT_TO_CUSTOMER_EN.toUpperCase(), Constants.ACCEPTED_EN.toUpperCase());
		signatureReceived(Constants.PATH_TO_JSON_FILES_QUOTE_TC2_B2B, pricingDate, priceValidUntilDate, signatureReceivedDate);
		verifyQuoteStatus(Constants.PATH_TO_JSON_FILES_QUOTE_TC2_B2B, Constants.SIGNATURE_RECEIVED_EN.toUpperCase(), Constants.ACCEPTED_EN.toUpperCase());
		confirmSigning(Constants.PATH_TO_JSON_FILES_QUOTE_TC2_B2B, Constants.PATH_TO_JSON_FILES_SIGN_QUOTE_MODAL_TC2_UP, ApiPaths.API_SIGN_QUOTE_MODAL_TC2_UP);
		signMandatePaper(Constants.PATH_TO_JSON_FILES_QUOTE_TC2_B2B);
		verifyContractCreated(Constants.PATH_TO_JSON_FILES_QUOTE_TC2_B2B, Constants.SIGNED_EN.toUpperCase(), Constants.ACCEPTED_EN.toUpperCase());
		
		//Should be checked is this should be removed or not after we start to use CREATING OF CONTRACTS from jenkins job
		/*
		if (numberOfAttempts < Constants.MAX_NUMBER_OF_ATTEMPTS_TO_FIND_APPROPRIATE_START_CONTRACT_DATE) {
			
			ContractStatus contractStatus = checkContractIsActive(Constants.PATH_TO_JSON_FILES_QUOTE_TC2_B2B);
			
			switch (contractStatus) {
				case TO_BE_ACTIVATED:
				{
					++numberOfAttempts;
					createContractB2B();
					break;
				}
				case ACTIVE:
				{
					assertTrue(true, "Contract status is: " + contractStatus);
					break;
				}
				default:
				{
					assertFalse(false, "Contract status is not ACTIVE and it is: " + contractStatus);
					break;
				}
			}
		}*/
		
		return getAccountNumber(recordId, cookie);
	}
	
	@Override
	public ContractStatus checkContractIsActive(String path) throws Exception {
		return super.checkContractIsActive(path);
	}

	@Override
	public void login() {
		super.login();
	}
	
	@Override
	public void createQuoteB2B(String path, String pathJsonFile, String pathApiPath) throws IOException {
		super.createQuoteB2B(path, pathJsonFile, pathApiPath);
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
