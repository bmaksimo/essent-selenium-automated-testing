package stepdefinitions.dwp.contracts.b2b;

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.restassured.create_contract.QuoteCreator;
import com.essent.testing.restassured.create_contract.impl.b2b.ContractTC1B2BCreator;
import com.essent.testing.restassured.create_contract.impl.b2b.ContractTC2B2BCreator;
import com.essent.testing.restassured.create_contract.impl.b2b.ContractUPB2BCreator;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import stepdefinitions.dwp.contracts.product_types.ProductTypes;

public class ContractB2BScenario extends DwpScenario {

	private static final Logger logger = Logger.getLogger(ContractB2BScenario.class);
	private static final String EMPTY_STRING = "";

	private String accountNumber;

	@Before("@QUOTE, @MENU, @RENEWAL, @FILTER, @SMOKE, @B2B_REGRESSION, @HB1")
	public void setupTest(Scenario scenario) throws Throwable {
		registerActiveScenario(scenario);
	}

	@Given("^B2B Contract is \"([^\"]*)\"$")
	public String createB2BContract(String productType) {

		accountNumber = EMPTY_STRING;

		ProductTypes productTypes = ProductTypes.valueOf(productType);
		try {
			switch (productTypes) {

				case UP: {
					QuoteCreator quoteB2BUP = new ContractUPB2BCreator();

					accountNumber = quoteB2BUP.createContract();

					break;
				}
				case TC1: {
					QuoteCreator quoteB2BTC1 = new ContractTC1B2BCreator();
					accountNumber = quoteB2BTC1.createContract();
					break;
				}
				case TC2: {
					QuoteCreator quoteB2BTC2 = new ContractTC2B2BCreator();
					accountNumber = quoteB2BTC2.createContract();
					break;
				}
				default:
					throw new AssertionError("Not supported product type used " + productType);
			}

		} catch (Exception e) {
			logger.error("Creation of B2B contract failed", e);
			Assert.fail("Creation of B2B contract failed: " + e.getMessage());
		}

		if(accountNumber.equals(EMPTY_STRING)) {
			Assert.fail("Creation of B2B contract failed");
			logger.error("Something went wrong with creation of B2B contract");
		}

		logger.info("ACCOUNT NUMBER: " + accountNumber);

		return accountNumber;
	}

	@Given("^B2B Active Contract is \"([^\"]*)\"$")
	public String createContractB2BAndCheckContractStatus(String productType) {

		accountNumber = "";

		ProductTypes productTypes = ProductTypes.valueOf(productType);
		try {
			switch (productTypes) {

				case UP: {
					QuoteCreator quoteB2BUP = new ContractUPB2BCreator();

					accountNumber = quoteB2BUP.createContractAndCheckContractStatus();

					break;
				}
				case TC1: {
					QuoteCreator quoteB2BTC1 = new ContractTC1B2BCreator();
					accountNumber = quoteB2BTC1.createContractAndCheckContractStatus();
					break;
				}
				case TC2: {
					QuoteCreator quoteB2BTC2 = new ContractTC2B2BCreator();
					accountNumber = quoteB2BTC2.createContractAndCheckContractStatus();
					break;
				}
				default:
					throw new AssertionError("Not supported product type used " + productType);
			}

		} catch (Exception e) {
			logger.error("B2B contract is not ACTIVE", e);
			Assert.fail("B2B contract is not ACTIVE: " + e.getMessage());
		}

		if(accountNumber.equals(EMPTY_STRING)) {
			Assert.fail("B2B contract is not ACTIVE");
			logger.error("Something went wrong with creation of ACTIVE B2B contract");
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
