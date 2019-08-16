package stepdefinitions.report;

import com.billinghouse.test_automation.util.msg.EmailService;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.scenario.RegisteredScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EmailReporter extends RegisteredScenario {

    @Before("@DWP or @CORE or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @When("^Send email to SMEs$")
    public void sendEmailToSMEs() {
        String environment = ConfigProvider.getProperty(ConfigKey.ENVIRONMENT);
        String crmName = parameterProvider.getValueOrParameterAsString("parameter:suitecrm-customer-name");
        String crmId = parameterProvider.getValueOrParameterAsString("parameter:accountNumber");
        String billingId = parameterProvider.getValueOrParameterAsString("parameter:billingCustomerId");
        String ean = parameterProvider.getValueOrParameterAsString("parameter:EAN-code");

        Map<String, String> input = new HashMap<>();
        input.put("env", environment);
        input.put("crm-name", crmName);
        input.put("crm-id", crmId);
        input.put("billing-id", billingId);
        input.put("ean", ean);

        EmailService emailService = new EmailService();
        boolean success = emailService.createTestReport(input);

        assertThat("Email has not been sent due to a messaging error.", success, is(true));

    }
}
