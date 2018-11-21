package com.essent.testing.restassured.create_contract;

import com.essent.testing.restassured.create_contract.constants.ContractStatus;

public interface QuoteCreator {

	void setPreconditions(String accountName, String contractStartDate, String contractEndDate) throws Exception;

	void login();

	void createQuote(String path, String pathJsonFile, String pathApiPath) throws Exception;

	void sendToCustomer(String path) throws Exception;

	void signatureReceived(String path, String pricingDate, String priceValidUntilDate, String signatureReceivedDate) throws Exception;

	void confirmSigning(String path, String pathJsonFileSignQuote, String apiPathSignQuote) throws Exception;

	//Only required if we want to have payment method: DOM
	void signMandatePaper(String path) throws Exception;

	String createContract() throws Exception;

    String createQuoteWithoutSignature() throws Exception;

	String createContractAndCheckContractStatus() throws Exception;

	ContractStatus checkContractIsActive(String path) throws Exception;

	void verifyContractCreated(String path, String quoteStage, String quoteStatus) throws Exception;
}
