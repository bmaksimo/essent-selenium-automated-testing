package stepdefinitions.dwp.contracts.b2b;

import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import com.essent.testing.restassured.create_contract.QuoteCreator;
import com.essent.testing.restassured.create_contract.impl.b2b.ContractTC1B2BCreator;
import com.essent.testing.restassured.create_contract.impl.b2b.ContractTC2B2BCreator;
import com.essent.testing.restassured.create_contract.impl.b2b.ContractUPB2BCreator;
import com.essent.testing.scenario.RegisteredScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import stepdefinitions.dwp.contracts.product_types.ProductTypes;

public class ContractB2BScenario extends RegisteredScenario {

    private static final Logger logger = Logger.getLogger(ContractB2BScenario.class);
    private static final String EMPTY_STRING = "";

        @Before("@DWP, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @OutputParameter(name="account-nr")
    private String accountNumber;

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

            ProductTypes productTypes = ProductTypes.valueOf(productType);
            try {
                switch (productTypes) {

                    case UP: {
                        QuoteCreator quoteB2BUP = new ContractUPB2BCreator(isFakeAddress, switchType);

                        accountNumber = quoteB2BUP.createContract();

                        break;
                    }
                    case TC1: {
                        QuoteCreator quoteB2BTC1 = new ContractTC1B2BCreator(isFakeAddress, switchType);
                        accountNumber = quoteB2BTC1.createContract();
                        break;
                    }
                    case TC2: {
                        QuoteCreator quoteB2BTC2 = new ContractTC2B2BCreator(isFakeAddress, switchType);
                        accountNumber = quoteB2BTC2.createContract();
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
            parameterProvider.put("accountNumber",accountNumber);
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
            ProductTypes productTypes = ProductTypes.valueOf(productType);

            try {
                switch (productTypes) {
                    case UP: {
                        QuoteCreator quoteB2BUP = new ContractUPB2BCreator(isFakeAddress, switchType);
                        accountNumber = quoteB2BUP.createContractAndCheckContractStatus();
                        break;
                    }
                    case TC1: {
                        QuoteCreator quoteB2BTC1 = new ContractTC1B2BCreator(isFakeAddress, switchType);
                        accountNumber = quoteB2BTC1.createContractAndCheckContractStatus();
                        break;
                    }
                    case TC2: {
                        QuoteCreator quoteB2BTC2 = new ContractTC2B2BCreator(isFakeAddress, switchType);
                        accountNumber = quoteB2BTC2.createContractAndCheckContractStatus();
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
            parameterProvider.put("accountNumber", accountNumber);
            return accountNumber;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

    @Given("^B2B signed quote by customer \"([^\"]*)\" product type and use \"([^\"]*)\" address and switch type is \"([^\"]*)\"$")
    public String createB2BQueteSignedByCustomer(String productType, String isFakeAddress, String switchType) {

        accountNumber = StringUtils.EMPTY;

        ProductTypes productTypes = ProductTypes.valueOf(productType);
        try {
            switch (productTypes) {

                case UP: {
                    QuoteCreator quoteB2BUP = new ContractUPB2BCreator(isFakeAddress, switchType);

                    accountNumber = quoteB2BUP.createQuoteWithoutSignature();

                    break;
                }
                case TC1: {
                    QuoteCreator quoteB2BTC1 = new ContractTC1B2BCreator(isFakeAddress, switchType);
                    accountNumber = quoteB2BTC1.createQuoteWithoutSignature();
                    break;
                }
                case TC2: {
                    QuoteCreator quoteB2BTC2 = new ContractTC2B2BCreator(isFakeAddress, switchType);
                    accountNumber = quoteB2BTC2.createQuoteWithoutSignature();
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
        parameterProvider.put("accountNumber",accountNumber);
        return accountNumber;
    }
}
