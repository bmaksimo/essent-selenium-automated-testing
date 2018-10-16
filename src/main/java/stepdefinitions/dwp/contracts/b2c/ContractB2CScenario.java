package stepdefinitions.dwp.contracts.b2c;

import com.essent.testing.restassured.create_contract.QuoteCreator;
import com.essent.testing.restassured.create_contract.impl.b2c.ContractTC1B2CCreator;
import com.essent.testing.restassured.create_contract.impl.b2c.ContractTC2B2CCreator;
import com.essent.testing.restassured.create_contract.impl.b2c.ContractUPB2CCreator;
import com.essent.testing.scenario.RegisteredScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.apache.log4j.Logger;
import org.junit.Assert;
import stepdefinitions.dwp.contracts.product_types.ProductTypes;

public class ContractB2CScenario extends RegisteredScenario {

	private static final Logger logger = Logger.getLogger(ContractB2CScenario.class);
	private static final String EMPTY_STRING = "";

	private String accountNumber;

	@Before("@QUOTE, @MENU, @RENEWAL, @FILTER, @SMOKE, @B2B_REGRESSION, @HB1")
	public void setupTest(Scenario scenario) throws Throwable {
		registerActiveScenario(scenario);
	}

    @Given("^B2C Contract is \"([^\"]*)\" product type and use \"([^\"]*)\" address and switch type is \"([^\"]*)\"$")
	public String createB2CContract(String productType, String isFakeAddress, String switchType) {

		accountNumber = EMPTY_STRING;

		ProductTypes productTypes = ProductTypes.valueOf(productType);
		try {
			switch (productTypes) {
				case UP: {
					QuoteCreator quoteB2CUP = new ContractUPB2CCreator(isFakeAddress, switchType);
					accountNumber = quoteB2CUP.createContract();
					break;
				}
				case TC1: {
					QuoteCreator quoteB2CTC1 = new ContractTC1B2CCreator(isFakeAddress, switchType);
					accountNumber = quoteB2CTC1.createContract();
					break;
				}
				case TC2: {
					QuoteCreator quoteB2CTC2 = new ContractTC2B2CCreator(isFakeAddress, switchType);
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

    @Given("^B2C Active Contract is \"([^\"]*)\" product type and use \"([^\"]*)\" address and switch type is \"([^\"]*)\"$")
	public String createContractB2CAndCheckContractStatus(String productType, String isFakeAddress, String switchType) {

		accountNumber = "";

		ProductTypes productTypes = ProductTypes.valueOf(productType);
		try {
			switch (productTypes) {

				case UP: {
					QuoteCreator quoteB2CUP = new ContractUPB2CCreator(isFakeAddress, switchType);
					accountNumber = quoteB2CUP.createContractAndCheckContractStatus();
					break;
				}
				case TC1: {
					QuoteCreator quoteB2CTC1 = new ContractTC1B2CCreator(isFakeAddress, switchType);
					accountNumber = quoteB2CTC1.createContractAndCheckContractStatus();
					break;
				}
				case TC2: {
					QuoteCreator quoteB2CTC2 = new ContractTC2B2CCreator(isFakeAddress, switchType);
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
}
