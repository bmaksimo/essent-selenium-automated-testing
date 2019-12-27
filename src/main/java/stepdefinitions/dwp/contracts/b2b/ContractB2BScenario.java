package stepdefinitions.dwp.contracts.b2b;

import com.essent.testing.restassured.create_contract.QuoteCreator;
import com.essent.testing.restassured.create_contract.impl.b2b.ContractTC1B2BCreator;
import com.essent.testing.restassured.create_contract.impl.b2b.ContractTC2B2BCreator;
import com.essent.testing.restassured.create_contract.impl.b2b.ContractUPB2BCreator;
import com.essent.testing.scenario.RegisteredScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import io.cucumber.datatable.DataTable;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import stepdefinitions.dwp.contracts.producttypes.ProductTypes;

public class ContractB2BScenario extends RegisteredScenario {

  @Before("@DWP or @E2E or @REGRESSION")
  public void setupTest(Scenario scenario) {
    registerActiveScenario(scenario);
  }

  private String accountNumber;
  private String companyNumber;
  private String billingId;
  private String ean;

  /**
   * This method is used to create B2B contract without checking is contract ACTIVE or not.
   *
   * @param productType This is product type and values of productType can be: UP, TC1, TC2
   * @param isFakeAddress This is used to select fake or real addresses during creation of B2B
   *     quote. Values can be: FAKE and any other value except FAKE. FAKE means we will use FAKE
   *     address to create B2B contract
   * @param switchType This is switch type which we select during creation of B2B quote and values
   *     of switchType can be: SUPPLIER SWITCH, CUSTOMER SWITCH, COMBINED CUSTOMER SWITCH, MOVE IN
   * @return accountNumber This returns account number of B2B contract
   *     <p>Following cases work:
   *     <p>B2B Contract is "TC2" product type and use "NOT FAKE" address and switch type is "MOVE
   *     IN" B2B Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
   *     B2B Contract is "UP" product type and use "FAKE" address and switch type is "SUPPLIER
   *     SWITCH" B2B Contract is "UP" product type and use "FAKE" address and switch type is
   *     "COMBINED CUSTOMER SWITCH" B2B Contract is "TC2" product type and use "FAKE" address and
   *     switch type is "SUPPLIER SWITCH"
   *     <p>
   *     <p>Any combination for TC1 won't work due to missing tariffsheets I think.
   *     <p>Before running test for creation of B2B quote, prices should be uploaded already. We
   *     upload prices in SOAPUI tests or can use class UploadB2BContractPrices, but fields
   *     locationOfPrivateKey and ftpUserName must be set. This is only relevant for B2B
   */
  @Given(
      "^B2B Contract is \"([^\"]*)\" product type and use \"([^\"]*)\" address and switch type is \"([^\"]*)\"$")
  public String createB2BContract(String productType, String isFakeAddress, String switchType) {

    accountNumber = StringUtils.EMPTY;

    ProductTypes productTypes = ProductTypes.valueOf(productType);
    try {
      switch (productTypes) {
        case UP:
          {
            QuoteCreator quoteB2BUP = new ContractUPB2BCreator(isFakeAddress, switchType);
            accountNumber = quoteB2BUP.createContract();
            break;
          }
        case TC1:
          {
            QuoteCreator quoteB2BTC1 = new ContractTC1B2BCreator(isFakeAddress, switchType);
            accountNumber = quoteB2BTC1.createContract();
            break;
          }
        case TC2:
          {
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

    if (StringUtils.isEmpty(accountNumber)) {
      Assert.fail("Creation of B2B contract failed");
      logger().error("Something went wrong with creation of B2B contract");
    }

    logger().debug("ACCOUNT NUMBER: " + accountNumber);
    parameterProvider.put("accountNumber", accountNumber);
    return accountNumber;
  }

  /**
   * This method is used to create B2B contract with checking is contract ACTIVE or not.
   *
   * @param productType This is product type and values of productType can be: UP, TC1, TC2
   * @param isFakeAddress This is used to select fake or real addresses during creation of B2B
   *     quote. Values can be: FAKE and any other value except FAKE. FAKE means we will use FAKE
   *     address to create B2B contract
   * @param switchType This is switch type which we select during creation of B2B quote and values
   *     of switchType can be: SUPPLIER SWITCH, CUSTOMER SWITCH, COMBINED CUSTOMER SWITCH, MOVE IN
   * @return accountNumber This returns account number of B2B contract
   *     <p>Following cases work:
   *     <p>B2B Active Contract is "TC2" product type and use "NOT FAKE" address and switch type is
   *     "MOVE IN" B2B Active Contract is "UP" product type and use "FAKE" address and switch type
   *     is "MOVE IN" B2B Active Contract is "UP" product type and use "FAKE" address and switch
   *     type is "SUPPLIER SWITCH" B2B Active Contract is "UP" product type and use "FAKE" address
   *     and switch type is "COMBINED CUSTOMER SWITCH" B2B Active Contract is "TC2" product type and
   *     use "FAKE" address and switch type is "SUPPLIER SWITCH"
   *     <p>Any combination for TC1 won't work due to missing tariffsheets I think.
   *     <p>Before running test for creation of B2B quote, prices should be uploaded already. We
   *     upload prices in SOAPUI tests or can use class UploadB2BContractPrices, but fields
   *     locationOfPrivateKey and ftpUserName must be set. This is only relevant for B2B
   */
  @Given(
      "^B2B Active Contract is \"([^\"]*)\" product type and use \"([^\"]*)\" address and switch type is \"([^\"]*)\"$")
  public String createContractB2BAndCheckContractStatus(
      String productType, String isFakeAddress, String switchType) {
    accountNumber = StringUtils.EMPTY;
    companyNumber = StringUtils.EMPTY;
    ean = StringUtils.EMPTY;
    ProductTypes productTypes = ProductTypes.valueOf(productType);

    try {
      switch (productTypes) {
        case UP:
          {
            QuoteCreator quoteB2BUP = new ContractUPB2BCreator(isFakeAddress, switchType);
            accountNumber = quoteB2BUP.createContractAndCheckContractStatus();
            companyNumber = ((ContractUPB2BCreator) quoteB2BUP).getCompanyNumber();
            ean = ((ContractUPB2BCreator) quoteB2BUP).getEan();
            break;
          }
        case TC1:
          {
            QuoteCreator quoteB2BTC1 = new ContractTC1B2BCreator(isFakeAddress, switchType);
            accountNumber = quoteB2BTC1.createContractAndCheckContractStatus();
            companyNumber = ((ContractTC1B2BCreator) quoteB2BTC1).getCompanyNumber();
            ean = ((ContractTC1B2BCreator) quoteB2BTC1).getEan();
            break;
          }
        case TC2:
          {
            QuoteCreator quoteB2BTC2 = new ContractTC2B2BCreator(isFakeAddress, switchType);
            accountNumber = quoteB2BTC2.createContractAndCheckContractStatus();
            companyNumber = ((ContractTC2B2BCreator) quoteB2BTC2).getCompanyNumber();
            ean = ((ContractTC2B2BCreator) quoteB2BTC2).getEan();
            break;
          }
        default:
          throw new AssertionError("Not supported product type used " + productType);
      }
    } catch (Exception e) {
      logger().error("B2B contract is not ACTIVE", e);
      Assert.fail("B2B contract is not ACTIVE: " + e.getMessage());
    }

    if (StringUtils.isEmpty(accountNumber)) {
      Assert.fail("B2B contract is not ACTIVE");
      logger().error("Something went wrong with creation of ACTIVE B2B contract");
    }

    if (StringUtils.isEmpty(companyNumber)) {
      Assert.fail("Failed to obtain company number");
      logger().error("Something went wrong with creation of ACTIVE B2B contract");
    }

    if (StringUtils.isEmpty(ean)) {
      Assert.fail("Failed to obtain EAN code");
      logger().error("Something went wrong with creation of ACTIVE B2B contract");
    }

    logger().debug("ACCOUNT NUMBER: " + accountNumber);
    logger().debug("COMPANY NUMBER: " + companyNumber);
    logger().debug("EAN CODE: " + ean);
    parameterProvider.put("accountNumber", accountNumber);
    parameterProvider.put("EAN-code", ean);
    parameterProvider.put("companyNumber", companyNumber);

    return accountNumber;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  @Given(
      "^B2B signed quote by customer \"([^\"]*)\" product type and use \"([^\"]*)\" address and switch type is \"([^\"]*)\"$")
  public String createB2BQueteSignedByCustomer(
      String productType, String isFakeAddress, String switchType) {

    accountNumber = StringUtils.EMPTY;

    ProductTypes productTypes = ProductTypes.valueOf(productType);
    try {
      switch (productTypes) {
        case UP:
          {
            QuoteCreator quoteB2BUP = new ContractUPB2BCreator(isFakeAddress, switchType);

            accountNumber = quoteB2BUP.createQuoteWithoutSignature();

            break;
          }
        case TC1:
          {
            QuoteCreator quoteB2BTC1 = new ContractTC1B2BCreator(isFakeAddress, switchType);
            accountNumber = quoteB2BTC1.createQuoteWithoutSignature();
            break;
          }
        case TC2:
          {
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

    if (StringUtils.isEmpty(accountNumber)) {
      Assert.fail("Creation of B2B contract failed");
      logger().error("Something went wrong with creation of B2B contract");
    }

    logger().debug("ACCOUNT NUMBER: " + accountNumber);
    parameterProvider.put("accountNumber", accountNumber);
    return accountNumber;
  }

  @Given("^B2B Active Contract is$")
  public String createContractB2B(final DataTable quote) {
    accountNumber = StringUtils.EMPTY;
    companyNumber = StringUtils.EMPTY;
    billingId = StringUtils.EMPTY;
    List<List<String>> list = quote.asLists(String.class);

    QuoteB2B quoteB2B = new QuoteB2B();
    quoteB2B.setProductType(list.get(1).get(0));
    quoteB2B.setIsFakeAddress(list.get(1).get(1));
    quoteB2B.setSwitchType(list.get(1).get(2));
    quoteB2B.setMeterType(list.get(1).get(3));
    quoteB2B.setKwMax(list.get(1).get(4));

    ProductTypes productTypes = ProductTypes.valueOf(quoteB2B.getProductType());

    try {
      switch (productTypes) {
        case UP:
          {
            QuoteCreator quoteB2BUP = new ContractUPB2BCreator(quoteB2B);
            accountNumber = quoteB2BUP.createContractAndCheckContractStatus();
            companyNumber = ((ContractUPB2BCreator) quoteB2BUP).getCompanyNumber();
            billingId = ((ContractUPB2BCreator) quoteB2BUP).getBillingId();
            break;
          }
        case TC1:
          {
            QuoteCreator quoteB2BTC1 = new ContractTC1B2BCreator(quoteB2B);
            accountNumber = quoteB2BTC1.createContractAndCheckContractStatus();
            companyNumber = ((ContractTC1B2BCreator) quoteB2BTC1).getCompanyNumber();
            billingId = ((ContractTC1B2BCreator) quoteB2BTC1).getBillingId();
            break;
          }
        case TC2:
          {
            QuoteCreator quoteB2BTC2 = new ContractTC2B2BCreator(quoteB2B);
            accountNumber = quoteB2BTC2.createContractAndCheckContractStatus();
            companyNumber = ((ContractTC2B2BCreator) quoteB2BTC2).getCompanyNumber();
            billingId = ((ContractTC2B2BCreator) quoteB2BTC2).getBillingId();
            break;
          }
        default:
          throw new AssertionError("Not supported product type used " + quoteB2B.getProductType());
      }
    } catch (Exception e) {
      logger().error("B2B contract is not ACTIVE", e);
      Assert.fail("B2B contract is not ACTIVE: " + e.getMessage());
    }

    if (StringUtils.isEmpty(accountNumber)) {
      Assert.fail("B2B contract is not ACTIVE");
      logger().error("Something went wrong with creation of ACTIVE B2B contract");
    }

    if (StringUtils.isEmpty(companyNumber)) {
      Assert.fail("Failed to obtain company number");
      logger().error("Something went wrong with creation of ACTIVE B2B contract");
    }

    if (StringUtils.isEmpty(billingId)) {
      Assert.fail("Failed to obtain billing number");
      logger().error("Something went wrong with creation of ACTIVE B2B contract");
    }

    logger().debug("ACCOUNT NUMBER: " + accountNumber);
    logger().debug("COMPANY NUMBER: " + companyNumber);
    logger().debug("BILLING NUMBER: " + billingId);
    parameterProvider.put("accountNumber", accountNumber);
    parameterProvider.put("companyNumber", companyNumber);
    parameterProvider.put("billingId", billingId);

    return accountNumber;
  }

  @Given(
      "^B2B Contract without signature is \"([^\"]*)\" product type and use \"([^\"]*)\" address and switch type is \"([^\"]*)\"$")
  public String createB2BContractWithoutSignature(
      String productType, String isFakeAddress, String switchType) {

    accountNumber = StringUtils.EMPTY;

    ProductTypes productTypes = ProductTypes.valueOf(productType);
    try {
      switch (productTypes) {
        case UP:
          {
            QuoteCreator quoteB2BUP = new ContractUPB2BCreator(isFakeAddress, switchType);

            accountNumber = quoteB2BUP.createQuoteWithoutSignature();

            break;
          }
        case TC1:
          {
            QuoteCreator quoteB2BTC1 = new ContractTC1B2BCreator(isFakeAddress, switchType);
            accountNumber = quoteB2BTC1.createQuoteWithoutSignature();
            break;
          }
        case TC2:
          {
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

    if (StringUtils.isEmpty(accountNumber)) {
      Assert.fail("Creation of B2B contract failed");
      logger().error("Something went wrong with creation of B2B contract");
    }

    parameterProvider.put("accountNumber", accountNumber);
    return accountNumber;
  }
}
