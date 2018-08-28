package com.essent.testing.restassured.create_b2b_contract.impl;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import com.essent.testing.restassured.create_b2b_contract.CreateQuoteB2B;
import com.essent.testing.restassured.create_b2b_contract.CreateQuoteB2BBase;
import com.essent.testing.restassured.create_b2b_contract.constants.ApiPaths;
import com.essent.testing.restassured.create_b2b_contract.constants.Constants;
import com.essent.testing.restassured.create_b2b_contract.constants.ContractStatus;


public class CreateContractTC1B2B extends CreateQuoteB2BBase implements CreateQuoteB2B {

	public CreateContractTC1B2B() {
		super();
		
		pricingDate = "2018-04-01 12:22:00";
		priceValidUntilDate = "2018-04-02 12:22:00";
		signatureReceivedDate = "2018-04-01 12:22:00";	
		
		upStartDate = "2018-01-01";  
		upEndDate = "2019-12-31";
		
		addressNumber = "54";
		addressStreet = "Nijverheidsstraat";
		addressPostalCode = "2160";
		addressCity = "Wommelgem";
		
		ean_c = "541448258188316404";
		paymentMethod = "DOM"; // DOM or OV
		legalCommunicationBy = "POST"; //POST or EMAIL
	}
	
	@Override
	public void setPreconditions(String accountName, String upStartDate) throws Exception{
		super.setPreconditions(accountName, upStartDate);
	}
	
	@Override
	public void login() {
		super.login();
	}
	
	@Override
	public String createContractB2B() throws Exception {
		setPreconditions(Constants.ACCOUNT_NAME_PREFIX_TC1_B2B, upStartDate);
		login();
		createQuoteB2B(Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B, Constants.PATH_TO_JSON_FILES_CREATE_QUOTE_B2B_TC1, ApiPaths.API_CREATE_QUOTE_B2B_TC1);
		verifyQuoteStatus(Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B, Constants.STATUS_QUOTE_AFTER_CREATING_TC1_EN);
		sendToCustomer(Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B);
		verifyQuoteStatus(Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B, Constants.STATUS_QUOTE_AFTER_SENDING_EN);
		signatureReceived(Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B, pricingDate, priceValidUntilDate, signatureReceivedDate);
		verifyQuoteStatus(Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B, Constants.STATUS_QUOTE_AFTER_SIGNATURE_RECEIVED_EN);
		confirmSigning(Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B, Constants.PATH_TO_JSON_FILES_SIGN_QUOTE_MODAL_TC1, ApiPaths.API_SIGN_QUOTE_MODAL_TC1);
		signMandatePaper(Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B);
		
		if (numberOfAttempts < Constants.MAX_NUMBER_OF_ATTEMPTS_TO_FIND_APPROPRIATE_START_CONTRACT_DATE) {
			
			ContractStatus contractStatus = checkContractIsActive(Constants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B);
			
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
	
	//Only required if we want to have payment method: DOM
	@Override
	public void signMandatePaper(String path) throws IOException {
		super.signMandatePaper(path);
	}
	
}
