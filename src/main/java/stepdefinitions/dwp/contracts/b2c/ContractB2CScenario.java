package stepdefinitions.dwp.contracts.b2c;

import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.restassured.create_contract.QuoteCreator;
import com.essent.testing.restassured.create_contract.impl.b2b.ContractTC1B2BCreator;
import com.essent.testing.restassured.create_contract.impl.b2b.ContractTC2B2BCreator;
import com.essent.testing.restassured.create_contract.impl.b2b.ContractUPB2BCreator;
import com.essent.testing.restassured.create_contract.impl.b2c.ContractTC1B2CCreator;
import com.essent.testing.restassured.create_contract.impl.b2c.ContractTC2B2CCreator;
import com.essent.testing.restassured.create_contract.impl.b2c.ContractUPB2CCreator;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.apache.log4j.Logger;
import org.junit.Assert;
import stepdefinitions.dwp.contracts.product_types.ProductTypes;

public class ContractB2CScenario extends DwpScenario {

	private static final Logger logger = Logger.getLogger(ContractB2CScenario.class);
	private static final String EMPTY_STRING = "";

	private String accountNumber;

	@Before("@QUOTE, @MENU, @RENEWAL, @FILTER, @SMOKE, @B2B_REGRESSION, @HB1")
	public void setupTest(Scenario scenario) throws Throwable {
		registerActiveScenario(scenario);
	}

	@Given("^B2C Contract is \"([^\"]*)\"$")
	public String createB2CContract(String productType) {

		accountNumber = EMPTY_STRING;

		ProductTypes productTypes = ProductTypes.valueOf(productType);
		try {
			switch (productTypes) {

				case UP: {
					QuoteCreator quoteB2CUP = new ContractUPB2CCreator();
					accountNumber = quoteB2CUP.createContract();
					break;
				}
				case TC1: {
					QuoteCreator quoteB2CTC1 = new ContractTC1B2CCreator();
					accountNumber = quoteB2CTC1.createContract();
					break;
				}
				case TC2: {
					QuoteCreator quoteB2CTC2 = new ContractTC2B2CCreator();
					accountNumber = quoteB2CTC2.createContract();
					break;
				}
				default:
					throw new AssertionError("Not supported product type used " + productType);
			}

		} catch (Exception e) {
			logger.error("Creation of B2C contract failed", e);
			Assert.fail("Creation of B2C contract failed: " + e.getMessage());
		}

		if(accountNumber.equals(EMPTY_STRING)) {
			Assert.fail("Creation of B2C contract failed");
			logger.error("Something went wrong with creation of B2C contract");
		}

		logger.info("ACCOUNT NUMBER: " + accountNumber);

		return accountNumber;
	}

	@Given("^B2C Active Contract is \"([^\"]*)\"$")
	public String createContractB2CAndCheckContractStatus(String productType) {

		accountNumber = "";

		ProductTypes productTypes = ProductTypes.valueOf(productType);
		try {
			switch (productTypes) {

				case UP: {
					QuoteCreator quoteB2CUP = new ContractUPB2CCreator();
					accountNumber = quoteB2CUP.createContractAndCheckContractStatus();
					break;
				}
				case TC1: {
					QuoteCreator quoteB2CTC1 = new ContractTC1B2CCreator();
					accountNumber = quoteB2CTC1.createContractAndCheckContractStatus();
					break;
				}
				case TC2: {
					QuoteCreator quoteB2CTC2 = new ContractTC2B2CCreator();
					accountNumber = quoteB2CTC2.createContractAndCheckContractStatus();
					break;
				}
				default:
					throw new AssertionError("Not supported product type used " + productType);
			}

		} catch (Exception e) {
			logger.error("B2C contract is not ACTIVE", e);
			Assert.fail("B2C contract is not ACTIVE: " + e.getMessage());
		}

		if(accountNumber.equals(EMPTY_STRING)) {
			Assert.fail("B2C contract is not ACTIVE");
			logger.error("Something went wrong with creation of ACTIVE B2C contract");
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
