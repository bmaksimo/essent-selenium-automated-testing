package com.essent.testing.restassured.create_b2b_contract.impl;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import com.essent.testing.restassured.create_b2b_contract.CreateQuoteB2B;
import com.essent.testing.restassured.create_b2b_contract.CreateQuoteB2BBase;
import com.essent.testing.restassured.create_b2b_contract.constants.ApiPaths;
import com.essent.testing.restassured.create_b2b_contract.constants.Constants;
import com.essent.testing.restassured.create_b2b_contract.constants.ContractStatus;

public class CreateContractUPB2B extends CreateQuoteB2BBase implements CreateQuoteB2B{

	public CreateContractUPB2B() {
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
		
		// This ean should be any from src/test/resources/data/contract_b2b/address_b2b/adress_b2B.XLSX which correspond appropriate address
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
		createQuoteB2B(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B, Constants.PATH_TO_JSON_FILES_CREATE_QUOTE_B2B_UP, ApiPaths.API_CREATE_QUOTE_B2B_TC2_UP);
		verifyQuoteStatus(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B, Constants.STATUS_QUOTE_AFTER_CREATING_TC2_UP_EN);
		sendToCustomer(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B);
		verifyQuoteStatus(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B, Constants.STATUS_QUOTE_AFTER_SENDING_EN);
		signatureReceived(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B, pricingDate, priceValidUntilDate, signatureReceivedDate);
		verifyQuoteStatus(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B, Constants.STATUS_QUOTE_AFTER_SIGNATURE_RECEIVED_EN);
		confirmSigning(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B, Constants.PATH_TO_JSON_FILES_SIGN_QUOTE_MODAL_TC2_UP, ApiPaths.API_SIGN_QUOTE_MODAL_TC2_UP);
		signMandatePaper(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B);
		
		if (numberOfAttempts < Constants.MAX_NUMBER_OF_ATTEMPTS_TO_FIND_APPROPRIATE_START_CONTRACT_DATE) {
			
			ContractStatus contractStatus = checkContractIsActive(Constants.PATH_TO_JSON_FILES_QUOTE_UP_B2B);
			
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
		}
		
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
