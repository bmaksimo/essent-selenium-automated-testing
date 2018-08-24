package com.essent.testing.restassured.create_b2b_contract;

import com.essent.testing.restassured.create_b2b_contract.constants.ContractStatus;

public interface CreateQuoteB2B {
	
	public void setPreconditions(String accountName, String upStartDate) throws Exception;
	
	public void login();
	
	public void createQuoteB2B(String path, String pathJsonFile, String pathApiPath) throws Exception;

	public void sendToCustomer(String path) throws Exception;

	public void signatureReceived(String path, String pricingDate, String priceValidUntilDate, String signatureReceivedDate) throws Exception;
	
	public void confirmSigning(String path, String pathJsonFileSignQuote, String apiPathSignQuote) throws Exception;
	
	//Only required if we want to have payment method: DOM
	public void signMandatePaper(String path) throws Exception;
	
	public String createContractB2B() throws Exception;
	
	public ContractStatus checkContractIsActive(String path) throws Exception;
	
}
