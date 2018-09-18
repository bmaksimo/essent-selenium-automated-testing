package stepdefinitions.dwp.contracts.b2b;

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.restassured.create_b2b_contract.CreateQuoteB2B;
import com.essent.testing.restassured.create_b2b_contract.impl.CreateContractTC1B2B;
import com.essent.testing.restassured.create_b2b_contract.impl.CreateContractTC2B2B;
import com.essent.testing.restassured.create_b2b_contract.impl.CreateContractUPB2B;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import stepdefinitions.dwp.contracts.b2b.product_types.B2BProductTypes;

public class ContractB2BScenario extends DwpScenario {
	
	private static final Logger logger = Logger.getLogger(ContractB2BScenario.class);

	private String accountNumber;

	@Before("@QUOTE, @MENU, @RENEWAL, @FILTER, @SMOKE, @B2B_REGRESSION")
	public void setupTest(Scenario scenario) throws Throwable {
		registerActiveScenario(scenario);
	}

	@Given("^B2B Contract is \"([^\"]*)\"$")
	public String createB2BContract(String productType) {

		accountNumber = "";

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
			logger.error("Creation of B2B contract failed", e);
			Assert.fail("Creation of B2B contract failed: " + e.getMessage());
		}
		
		logger.info("ACCOUNT NUMBER: " + accountNumber);

		return accountNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	@Override
	@After("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL, @B2B_REGRESSION")
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
