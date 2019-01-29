package stepdefinitions.soctar;

import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import com.billinghouse.test_automation.util.soctar_file.SoctarFileUtil;
import com.billinghouse.test_automation.util.ssh.JSchUtil;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.scenario.RegisteredScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.FilenameUtils;

import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkAndConvertToSoctarFileDate;

public class SoctarSteps extends RegisteredScenario {


    @Before("@DWP, @CORE, @E2E, @REGRESSION, @DB-CORE")
    public void setUp(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }


    @OutputParameter(name ="soctar-file-name")
    private String soctarFileName;

    @Then("^Soctar file is uploaded to \"([^\"]*)\" remote directory$")
    public void uploadSoctarFile(String remoteDirectory) throws Throwable {
        String custId = parameterProvider.getValueOrParameterAsString("parameter:cust_id");
        String eanId =  parameterProvider.getValueOrParameterAsString("parameter:ean_id");
        String soctarDate = parameterProvider.getValueOrParameterAsString("parameter:start-end-date");
        String soctarFilePath = SoctarFileUtil.getSoctarFileFromTemplate(custId, eanId, soctarDate);
        soctarFileName = FilenameUtils.getName(soctarFilePath);
        final String sftpHost = ConfigProvider.getProperty(ConfigKey.SSH_NOVA_SFTP_HOST);
        JSchUtil.get().sftpPut(sftpHost, remoteDirectory, soctarFilePath);
    }

    @When("^Soctar EAN is \"([^\"]*)\"$")
    public void setValue(final String value) throws Throwable {
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        parameterProvider.put("ean_id", inputValue);
    }

    @And("^Soctar start date is \"([^\"]*)\"$")
    public void setSoctarDate(final String value) throws Throwable {
        String dateValue = checkAndConvertToSoctarFileDate(parameterProvider.getValueOrParameterAsString(value));
        parameterProvider.put("start-end-date", dateValue);
    }

    @And("^Soctar customer Id is \"([^\"]*)\"$")
    public void soctarCustomerIdIs(String value) throws Throwable {
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        parameterProvider.put("cust_id", inputValue);
    }
}
