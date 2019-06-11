package stepdefinitions.dwp.contracts.b2c;

import com.essent.testing.restassured.create_contract.QuoteCreator;
import com.essent.testing.restassured.create_contract.impl.b2c.ContractTC1B2CCreator;
import com.essent.testing.scenario.RegisteredScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.apache.log4j.Logger;
import org.junit.Assert;

public class ContractB2CScenario extends RegisteredScenario {

	private static final Logger logger = Logger.getLogger(ContractB2CScenario.class);
	private static final String EMPTY_STRING = "";

	private String accountNumber;

    @Before("@DWP, @E2E, @REGRESSION, @B2C")
	public void setupTest(Scenario scenario) throws Throwable {
		registerActiveScenario(scenario);
	}

    @Given("^B2C TC1 Contract uses \"([^\"]*)\" address and switch type is \"([^\"]*)\"$")
	public String createB2CContract(String isFakeAddress, String switchType) {
        this.setAccountNumber(EMPTY_STRING);

		try {
		    QuoteCreator quoteB2CTC1 = new ContractTC1B2CCreator(isFakeAddress, switchType);
            this.setAccountNumber(quoteB2CTC1.createContract());
		} catch (Exception e) {
			logger.error("Creation of B2C contract failed", e);
			Assert.fail("Creation of B2C contract failed: " + e.getMessage());
		}

		if(EMPTY_STRING.equals(this.getAccountNumber())) {
			Assert.fail("Creation of B2C contract failed");
			logger.error("Something went wrong with creation of B2C contract");
		}

		logger.debug("ACCOUNT NUMBER: " + this.getAccountNumber());

		return this.getAccountNumber();
	}

    @Given("^B2C TC1 Active Contract uses \"([^\"]*)\" address and switch type is \"([^\"]*)\"$")
	public String createContractB2CAndCheckContractStatus(String isFakeAddress, String switchType) {

		this.setAccountNumber(EMPTY_STRING);

		try {
		    QuoteCreator quoteB2CTC1 = new ContractTC1B2CCreator(isFakeAddress, switchType);
			this.setAccountNumber(quoteB2CTC1.createContractAndCheckContractStatus());
		} catch (Exception e) {
			logger.error("B2C contract is not ACTIVE", e);
			Assert.fail("B2C contract is not ACTIVE: " + e.getMessage());
		}

		if(EMPTY_STRING.equals(this.getAccountNumber())) {
			Assert.fail("B2C contract is not ACTIVE");
			logger.error("Something went wrong with creation of ACTIVE B2C contract");
		}

		logger.debug("ACCOUNT NUMBER: " + this.getAccountNumber());

		parameterProvider.put("accountNumber", this.getAccountNumber());

		return this.getAccountNumber();
	}

	private String getAccountNumber() {
		return accountNumber;
	}

    private void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
