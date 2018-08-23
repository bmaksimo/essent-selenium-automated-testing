package com.essent.testing.restassured.create_b2b_contract;

import io.restassured.http.Cookies;

public interface CreateQuoteB2B {
	
	public void setPreconditions(String accountName, String upStartDate) throws Exception;
	
	public void login();
	
	//TODO Is this should be public, or only this class should see this login. Probably it should be private
	public void createQuoteB2B() throws Exception;

	public void sendToCustomer(String path) throws Exception;

	public void signatureReceived(String path, String pricingDate, String priceValidUntilDate, String signatureReceivedDate) throws Exception;
	
	public void confirmSigning(String path, String pathJsonFileSignQuote, String apiPathSignQuote) throws Exception;
	
	//Only required if we want to have payment method: DOM
	public void signMandatePaper(String path) throws Exception;
	
	public String createContractB2B() throws Exception;
	
	public void checkContractIsActive() throws Exception;
	
}
