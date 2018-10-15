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

	@Given("^B2B Contract is \"([^\"]*)\"$")
	public String createB2BContract(String productType) {

		accountNumber = StringUtils.EMPTY;

		B2BProductTypes b2bProductTypes = B2BProductTypes.valueOf(productType);
		try {
			switch (b2bProductTypes) {

				case UP: {
					CreateQuoteB2B createQuoteB2BUP = new CreateContractUPB2B();
	
					accountNumber = createQuoteB2BUP.createContractB2B();
	
					break;
				}
				case TC1: {
					CreateQuoteB2B createQuoteB2BTC1 = new CreateContractTC1B2B();
					accountNumber = createQuoteB2BTC1.createContractB2B();
					break;
				}
				case TC2: {
					CreateQuoteB2B createQuoteB2BTC2 = new CreateContractTC2B2B();
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
	
	@Given("^B2B Active Contract is \"([^\"]*)\"$")

	public String createContractB2BAndCheckContractStatus(String productType) {

		accountNumber = "";

		B2BProductTypes b2bProductTypes = B2BProductTypes.valueOf(productType);
		try {
			switch (b2bProductTypes) {

				case UP: {
					CreateQuoteB2B createQuoteB2BUP = new CreateContractUPB2B();
	
					accountNumber = createQuoteB2BUP.createContractB2BAndCheckContractStatus();
	
					break;
				}
				case TC1: {
					CreateQuoteB2B createQuoteB2BTC1 = new CreateContractTC1B2B();
					accountNumber = createQuoteB2BTC1.createContractB2BAndCheckContractStatus();
					break;
				}
				case TC2: {
					CreateQuoteB2B createQuoteB2BTC2 = new CreateContractTC2B2B();
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
