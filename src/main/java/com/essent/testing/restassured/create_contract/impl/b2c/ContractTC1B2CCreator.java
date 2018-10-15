package com.essent.testing.restassured.create_contract.impl.b2c;

import com.essent.testing.restassured.create_contract.QuoteCreator;
import com.essent.testing.restassured.create_contract.QuoteCreatorB2CBase;
import com.essent.testing.restassured.create_contract.constants.ApiPathsContract;
import com.essent.testing.restassured.create_contract.constants.ContractConstants;
import com.essent.testing.restassured.create_contract.constants.ContractStatus;
import com.essent.testing.restassured.create_contract.constants.SwitchTypes;
import com.essent.testing.restassured.create_contract.helper.PrepareDataForContract;
import org.apache.log4j.Logger;
import org.junit.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;


public class ContractTC1B2CCreator extends QuoteCreatorB2CBase implements QuoteCreator {

	private static final Logger logger = Logger.getLogger(ContractTC1B2CCreator.class);

	public ContractTC1B2CCreator() throws FileNotFoundException, IOException {
		super();
		getQuoteProperties(ContractConstants.PATH_TO_PROPERTIES_FILE_CREATE_QUOTE_B2B_TC1, "FAKE", SwitchTypes.MOVE_IN.getSwitchTypes());
	}

	@Override
	public void setPreconditions(String accountName, String contractStartDate, String contractEndDate) throws Exception{
		logger.info("Set preconditions before starting: " + this.getClass().getSimpleName());
		super.setPreconditions(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2B, accountName, contractStartDate, contractEndDate);
		logger.info("Set preconditions after starting: " + this.getClass().getSimpleName() + " - PASSED");
	}

	@Override
	public void login() {
		logger.info("login: " + this.getClass().getSimpleName());
		super.login();
		logger.info("login: " + this.getClass().getSimpleName() + " - PASSED");
	}


	@Override
	public String createContract() throws Exception {
		logger.info("createContract: " + this.getClass().getSimpleName());

		login();
        setPreconditions(ContractConstants.ACCOUNT_NAME_PREFIX_TC1_B2C, upStartDate, PrepareDataForContract.getTodayDate());
        createQuote(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C, ContractConstants.PATH_TO_JSON_FILES_CREATE_QUOTE_B2C_TC1, ApiPathsContract.API_CREATE_QUOTE_B2C_TC1);
        verifyQuoteStatus(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C, ContractConstants.SENT_TO_CUSTOMER_EN.toUpperCase(), ContractConstants.ACCEPTED_EN.toUpperCase());
        sendToCustomer(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C);
        verifyQuoteStatus(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C, ContractConstants.SENT_TO_CUSTOMER_EN.toUpperCase(), ContractConstants.ACCEPTED_EN.toUpperCase());
        signatureReceived(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C, pricingDate, priceValidUntilDate, signatureReceivedDate);
        verifyQuoteStatus(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C, ContractConstants.SIGNATURE_RECEIVED_EN.toUpperCase(), ContractConstants.ACCEPTED_EN.toUpperCase());
        confirmSigning(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C, ContractConstants.PATH_TO_JSON_FILES_SIGN_QUOTE_MODAL_TC1, ApiPathsContract.API_SIGN_QUOTE_MODAL_TC1);
        signMandatePaper(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C);
        verifyContractCreated(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C, ContractConstants.SIGNED_EN.toUpperCase(), ContractConstants.ACCEPTED_EN.toUpperCase());

        logger.info("createContract: " + this.getClass().getSimpleName() + " - PASSED");

		logger.info("createContract: " + this.getClass().getSimpleName() + " - PASSED");

		return getAccountNumber(recordId, cookie);
	}

	@Override
	public ContractStatus checkContractIsActive(String path) throws Exception {

		logger.info("checkContractIsActive: " + this.getClass().getSimpleName());

		return super.checkContractIsActive(path);
	}

	@Override
	public void createQuote(String path, String pathJsonFile, String pathApiPath) throws IOException {
		logger.info("createQuote: " + this.getClass().getSimpleName());
		super.createQuoteB2C(path, pathJsonFile, pathApiPath);
		logger.info("createQuote: " + this.getClass().getSimpleName() + " - PASSED");
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

	@Override
	public void verifyContractCreated(String path, String quoteStage, String quoteStatus) throws IOException {
		logger.info("verifyContractCreated: " + this.getClass().getSimpleName());
		super.verifyContractCreated(path, quoteStage, quoteStatus);
		logger.info("verifyContractCreated: " + this.getClass().getSimpleName() + " - PASSED");
	}


	@Override
	public String createContractAndCheckContractStatus() throws Exception {
		logger.info("createContractAndCheckContractStatus: " + this.getClass().getSimpleName());

        login();
        setPreconditions(ContractConstants.ACCOUNT_NAME_PREFIX_TC1_B2C, upStartDate, PrepareDataForContract.getTodayDate());
        createQuote(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C, ContractConstants.PATH_TO_JSON_FILES_CREATE_QUOTE_B2C_TC1, ApiPathsContract.API_CREATE_QUOTE_B2C_TC1);
        verifyQuoteStatus(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C, ContractConstants.SENT_TO_CUSTOMER_EN.toUpperCase(), ContractConstants.ACCEPTED_EN.toUpperCase());
        sendToCustomer(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C);
        verifyQuoteStatus(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C, ContractConstants.SENT_TO_CUSTOMER_EN.toUpperCase(), ContractConstants.ACCEPTED_EN.toUpperCase());
        signatureReceived(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C, pricingDate, priceValidUntilDate, signatureReceivedDate);
        verifyQuoteStatus(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C, ContractConstants.SIGNATURE_RECEIVED_EN.toUpperCase(), ContractConstants.ACCEPTED_EN.toUpperCase());
        confirmSigning(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C, ContractConstants.PATH_TO_JSON_FILES_SIGN_QUOTE_MODAL_TC1, ApiPathsContract.API_SIGN_QUOTE_MODAL_TC1);
        signMandatePaper(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C);
        verifyContractCreated(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C, ContractConstants.SIGNED_EN.toUpperCase(), ContractConstants.ACCEPTED_EN.toUpperCase());

		ContractStatus contractStatus = null;

		// Checking is contract ACTIVE, because once contract is created a lot of stuff is triggered in jbilling, bpm and odoo and after that contract become ACTIVE
		if (numberOfAttempts < ContractConstants.MAX_NUMBER_OF_ATTEMPTS_TO_FIND_APPROPRIATE_START_CONTRACT_DATE) {

			contractStatus = checkContractIsActive(ContractConstants.PATH_TO_JSON_FILES_QUOTE_TC1_B2C);

			switch (contractStatus) {
				case TO_BE_ACTIVATED:
				{
					++numberOfAttempts;
					createContractAndCheckContractStatus();
					break;
				}
				case ACTIVE:
				{
					Assert.assertTrue(contractStatus == ContractStatus.ACTIVE);
					break;
				}
				default:
				{
					Assert.fail("Contract status is not ACTIVE and it status is: " + contractStatus);
					break;
				}
			}
		}

		if(contractStatus != ContractStatus.ACTIVE) {
			logger.error("Contract status is not ACTIVE and it status is: " + contractStatus);
			Assert.fail("Contract status is not ACTIVE and it status is: " + contractStatus);
		}

		logger.info("createContractAndCheckContractStatus: " + this.getClass().getSimpleName() + " - PASSED");

		return getAccountNumber(recordId, cookie);
	}

}
