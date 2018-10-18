package stepdefinitions.dwp.contracts.b2b;

import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.restassured.create_b2b_contract.CreateQuoteB2B;
import com.essent.testing.restassured.create_b2b_contract.impl.CreateContractTC1B2B;
import com.essent.testing.restassured.create_b2b_contract.impl.CreateContractTC2B2B;
import com.essent.testing.restassured.create_b2b_contract.impl.CreateContractUPB2B;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import stepdefinitions.dwp.contracts.b2b.product_types.B2BProductTypes;

public class ContractB2BScenario extends DwpScenario {

    @OutputParameter(name="account-nr")
    private String accountNumber;

	@Before("@DWP, @CORE, @E2E, @REGRESSION, @SALES-MARKETING, @CONTRACTING-SWITCHING, @BUSINESS-DESK, @BILLING")
	public void setupTest(Scenario scenario) throws Throwable {
		registerActiveScenario(scenario);
	}

	 /**
	   * This method is used to create B2B contract without checking is contract ACTIVE or not.
	   * @param productType This is product type and values of productType can be: UP, TC1, TC2
	   * @param isFakeAddress  This is used to select fake or real addresses during creation of B2B quote.
	   * Values can be: FAKE and any other value except FAKE. FAKE means we will use FAKE address to create B2B contract
	   * @param switchType This is switch type which we select during creation of B2B quote and values of switchType
	   * can be: SUPPLIER SWITCH, CUSTOMER SWITCH, COMBINED CUSTOMER SWITCH, MOVE IN
	   * @return accountNumber This returns account number of B2B contract
	   * 
	   * Following cases work: 
	   * 
	   * B2B Contract is "TC2" and use "NOT FAKE" address and switch type is "MOVE IN"
	   * B2B Contract is "UP" and use "FAKE" address and switch type is "MOVE IN"
	   * B2B Contract is "UP" and use "FAKE" address and switch type is "SUPPLIER SWITCH"
	   * B2B Contract is "UP" and use "FAKE" address and switch type is "COMBINED CUSTOMER SWITCH"
	   * B2B Contract is "TC2" and use "FAKE" address and switch type is "SUPPLIER SWITCH"
	   * 
	   * 
	   * Any combination for TC1 won't work due to missing tariffsheets I think.
	   * 
	   * Before running test for creation of B2B quote, prices should be uploaded already. We upload prices in SOAPUI tests or can use 
	   * class UploadB2BContractPrices, but fields locationOfPrivateKey and ftpUserName must be set. This is only relevant for B2B
	   */
	@Given("^B2B Contract is \"([^\"]*)\" product type and use \"([^\"]*)\" address and switch type is \"([^\"]*)\"$")
	public String createB2BContract(String productType, String isFakeAddress, String switchType) {

		accountNumber = StringUtils.EMPTY;

		B2BProductTypes b2bProductTypes = B2BProductTypes.valueOf(productType);
		try {
			switch (b2bProductTypes) {

				case UP: {
					CreateQuoteB2B createQuoteB2BUP = new CreateContractUPB2B(isFakeAddress, switchType);
					accountNumber = createQuoteB2BUP.createContractB2B();
	
					break;
				}
				case TC1: {
					CreateQuoteB2B createQuoteB2BTC1 = new CreateContractTC1B2B(isFakeAddress, switchType);
					accountNumber = createQuoteB2BTC1.createContractB2B();
					break;
				}
				case TC2: {
					CreateQuoteB2B createQuoteB2BTC2 = new CreateContractTC2B2B(isFakeAddress, switchType);
					accountNumber = createQuoteB2BTC2.createContractB2B();
					break;
				}
				default:
					throw new AssertionError("Not supported product type used " + productType);
			}

		} catch (Exception e) {			
			logger().error("Creation of B2B contract failed", e);
			Assert.fail("Creation of B2B contract failed: " + e.getMessage());
		}
		
		if(StringUtils.isEmpty(accountNumber)) {
			Assert.fail("Creation of B2B contract failed");
			logger().error("Something went wrong with creation of B2B contract");
		}
		logger().info("ACCOUNT NUMBER: " + accountNumber);
		return accountNumber;
	}

	/**
	   * This method is used to create B2B contract with checking is contract ACTIVE or not.
	   * @param productType This is product type and values of productType can be: UP, TC1, TC2
	   * @param isFakeAddress  This is used to select fake or real addresses during creation of B2B quote.
	   * Values can be: FAKE and any other value except FAKE. FAKE means we will use FAKE address to create B2B contract
	   * @param switchType This is switch type which we select during creation of B2B quote and values of switchType
	   * can be: SUPPLIER SWITCH, CUSTOMER SWITCH, COMBINED CUSTOMER SWITCH, MOVE IN
	   * @return accountNumber This returns account number of B2B contract
	   *  
	   *  Following cases work: 
	   * 
	   * B2B Contract is "TC2" and use "NOT FAKE" address and switch type is "MOVE IN"
	   * B2B Contract is "UP" and use "FAKE" address and switch type is "MOVE IN"
	   * B2B Contract is "UP" and use "FAKE" address and switch type is "SUPPLIER SWITCH"
	   * B2B Contract is "UP" and use "FAKE" address and switch type is "COMBINED CUSTOMER SWITCH"
	   * B2B Contract is "TC2" and use "FAKE" address and switch type is "SUPPLIER SWITCH"
	   * 
	   * Any combination for TC1 won't work due to missing tariffsheets I think.
	   * 
	   * Before running test for creation of B2B quote, prices should be uploaded already. We upload prices in SOAPUI tests or can use 
	   * class UploadB2BContractPrices, but fields locationOfPrivateKey and ftpUserName must be set. This is only relevant for B2B
	   */
	@Given("^B2B Active Contract is \"([^\"]*)\" product type and use \"([^\"]*)\" address and switch type is \"([^\"]*)\"$")
	public String createContractB2BAndCheckContractStatus(String productType, String isFakeAddress, String switchType) {

		accountNumber = "";

		B2BProductTypes b2bProductTypes = B2BProductTypes.valueOf(productType);
		try {
			switch (b2bProductTypes) {

				case UP: {
					CreateQuoteB2B createQuoteB2BUP = new CreateContractUPB2B(isFakeAddress, switchType);
					accountNumber = createQuoteB2BUP.createContractB2BAndCheckContractStatus();
	
					break;
				}
				case TC1: {
					CreateQuoteB2B createQuoteB2BTC1 = new CreateContractTC1B2B(isFakeAddress, switchType);
					accountNumber = createQuoteB2BTC1.createContractB2BAndCheckContractStatus();
					break;
				}
				case TC2: {
					CreateQuoteB2B createQuoteB2BTC2 = new CreateContractTC2B2B(isFakeAddress, switchType);
					accountNumber = createQuoteB2BTC2.createContractB2BAndCheckContractStatus();
					break;
				}
				default:
					throw new AssertionError("Not supported product type used " + productType);
			}

		} catch (Exception e) {			
			logger().error("B2B contract is not ACTIVE", e);
			Assert.fail("B2B contract is not ACTIVE: " + e.getMessage());
		}
		
		if(StringUtils.isEmpty(accountNumber)) {
			Assert.fail("B2B contract is not ACTIVE");
			logger().error("Something went wrong with creation of ACTIVE B2B contract");
		}
		
		logger().info("ACCOUNT NUMBER: " + accountNumber);
		return accountNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	@Override
	@After("@DWP, @CORE, @E2E, @REGRESSION, @SALES-MARKETING, @CONTRACTING-SWITCHING, @BUSINESS-DESK, @BILLING")
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
