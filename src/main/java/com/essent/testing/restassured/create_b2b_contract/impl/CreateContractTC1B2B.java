package com.essent.testing.restassured.create_b2b_contract.impl;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.essent.testing.restassured.create_b2b_contract.CreateQuoteB2B;
import com.essent.testing.restassured.create_b2b_contract.CreateQuoteB2BBase;
import com.essent.testing.restassured.create_b2b_contract.constants.ApiPathsContractB2B;
import com.essent.testing.restassured.create_b2b_contract.constants.ConstantsContractB2B;
import com.essent.testing.restassured.create_b2b_contract.constants.ContractStatus;
import com.essent.testing.restassured.create_b2b_contract.helper.PrepareDataForB2BContract;


public class CreateContractTC1B2B extends CreateQuoteB2BBase implements CreateQuoteB2B {
	
	private static final Logger logger = Logger.getLogger(CreateContractTC1B2B.class);

	public CreateContractTC1B2B() throws FileNotFoundException, IOException {
		super();
		getQuoteProperties(ConstantsContractB2B.PATH_TO_PROPERTIES_FILE_CREATE_QUOTE_B2B_TC1);
	}
	
	@Override
	public void setPreconditions(String accountName, String contractStartDate, String contractEndDate) throws Exception{
		logger.info("Set preconditions before starting: " + this.getClass().getSimpleName());
		super.setPreconditions(ConstantsContractB2B.PATH_TO_JSON_FILES_QUOTE_TC1_B2B, accountName, contractStartDate, contractEndDate);
		logger.info("Set preconditions after starting: " + this.getClass().getSimpleName() + " - PASSED");
	}
	
	@Override
	public void login() {
		logger.info("login: " + this.getClass().getSimpleName());
		super.login();
		logger.info("login: " + this.getClass().getSimpleName() + " - PASSED");
	}
	
	
	@Override
	public String createContractB2B() throws Exception {
		
		logger.info("createContractB2B: " + this.getClass().getSimpleName());
		
		setPreconditions(ConstantsContractB2B.ACCOUNT_NAME_PREFIX_TC1_B2B, upStartDate, PrepareDataForB2BContract.getTodayDate());
		login();
		createQuoteB2B(ConstantsContractB2B.PATH_TO_JSON_FILES_QUOTE_TC1_B2B, ConstantsContractB2B.PATH_TO_JSON_FILES_CREATE_QUOTE_B2B_TC1, ApiPathsContractB2B.API_CREATE_QUOTE_B2B_TC1);		
		verifyQuoteStatus(ConstantsContractB2B.PATH_TO_JSON_FILES_QUOTE_TC1_B2B, ConstantsContractB2B.SENT_TO_CUSTOMER_EN.toUpperCase(), ConstantsContractB2B.ACCEPTED_EN.toUpperCase());
		sendToCustomer(ConstantsContractB2B.PATH_TO_JSON_FILES_QUOTE_TC1_B2B);
		verifyQuoteStatus(ConstantsContractB2B.PATH_TO_JSON_FILES_QUOTE_TC1_B2B, ConstantsContractB2B.SENT_TO_CUSTOMER_EN.toUpperCase(), ConstantsContractB2B.ACCEPTED_EN.toUpperCase());
		signatureReceived(ConstantsContractB2B.PATH_TO_JSON_FILES_QUOTE_TC1_B2B, pricingDate, priceValidUntilDate, signatureReceivedDate);
		verifyQuoteStatus(ConstantsContractB2B.PATH_TO_JSON_FILES_QUOTE_TC1_B2B, ConstantsContractB2B.SIGNATURE_RECEIVED_EN.toUpperCase(), ConstantsContractB2B.ACCEPTED_EN.toUpperCase());
		confirmSigning(ConstantsContractB2B.PATH_TO_JSON_FILES_QUOTE_TC1_B2B, ConstantsContractB2B.PATH_TO_JSON_FILES_SIGN_QUOTE_MODAL_TC1, ApiPathsContractB2B.API_SIGN_QUOTE_MODAL_TC1);
		signMandatePaper(ConstantsContractB2B.PATH_TO_JSON_FILES_QUOTE_TC1_B2B);
		verifyContractCreated(ConstantsContractB2B.PATH_TO_JSON_FILES_QUOTE_TC1_B2B, ConstantsContractB2B.SIGNED_EN.toUpperCase(), ConstantsContractB2B.ACCEPTED_EN.toUpperCase());
		
		logger.info("createContractB2B: " + this.getClass().getSimpleName() + " - PASSED");
		//Should be checked: is this should be removed or not, after we start to use CREATING OF CONTRACTS from jenkins job
		/*if (numberOfAttempts < ConstantsContractB2B.MAX_NUMBER_OF_ATTEMPTS_TO_FIND_APPROPRIATE_START_CONTRACT_DATE) {
			
			ContractStatus contractStatus = checkContractIsActive(ConstantsContractB2B.PATH_TO_JSON_FILES_QUOTE_TC1_B2B);
			
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
		
		logger.info("checkContractIsActive: " + this.getClass().getSimpleName());
		
		return super.checkContractIsActive(path);
	}
	
	@Override
	public void createQuoteB2B(String path, String pathJsonFile, String pathApiPath) throws IOException {
		logger.info("createQuoteB2B: " + this.getClass().getSimpleName());
		super.createQuoteB2B(path, pathJsonFile, pathApiPath);
		logger.info("createQuoteB2B: " + this.getClass().getSimpleName() + " - PASSED");
	}

	@Override
	public void sendToCustomer(String path) throws IOException {
		logger.info("sendToCustomer: " + this.getClass().getSimpleName());
		super.sendToCustomer(path);
		logger.info("sendToCustomer: " + this.getClass().getSimpleName() + " - PASSED");
	}

	@Override
	public void signatureReceived(String path, String pricingDate, String priceValidUntilDate, String signatureReceivedDate) throws IOException {
		logger.info("signatureReceived: " + this.getClass().getSimpleName());
		super.signatureReceived(path, pricingDate, priceValidUntilDate, signatureReceivedDate);
		logger.info("signatureReceived: " + this.getClass().getSimpleName() + " - PASSED");
	}
	
	@Override
	public void confirmSigning(String path, String pathJsonFileSignQuote, String apiPathSignQuote) throws IOException {
		logger.info("confirmSigning: " + this.getClass().getSimpleName());
		super.confirmSigning(path, pathJsonFileSignQuote, apiPathSignQuote);
		logger.info("confirmSigning: " + this.getClass().getSimpleName() + " - PASSED");
	}
	
	//Only required if we want to have payment method: DOM
	@Override
	public void signMandatePaper(String path) throws IOException {
		logger.info("signMandatePaper: " + this.getClass().getSimpleName());
		super.signMandatePaper(path);
		logger.info("signMandatePaper: " + this.getClass().getSimpleName() + " - PASSED");
	}
	
}
