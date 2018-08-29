package stepdefinitions.billing.generic;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.scenario.RegisteredScenario;


public class BillingScenario extends RegisteredScenario {

  protected String baseUrl = ConfigProvider.getProperty(ConfigKey.BILLING_BASE_URL);

  public String getBaseUrl()
  {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl)
  {
    this.baseUrl = baseUrl;
  }

}
