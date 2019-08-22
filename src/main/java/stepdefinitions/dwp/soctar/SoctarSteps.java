package stepdefinitions.dwp.soctar;

import com.billinghouse.test_automation.util.dsl.DateExpressionsUtil;
import com.billinghouse.test_automation.util.file.FileUtil;
import com.billinghouse.test_automation.util.ssh.JSchUtil;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.FilenameUtils;

import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkAndConvertToDwpContractStartEndDate;
import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkAndConvertToSoctarFileDate;

public class SoctarSteps extends DwpScenario {


    @Before("@DWP or @CORE or @E2E or @REGRESSION or @DB-CORE")
    public void setUp(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Then("^Soctar file is uploaded to \"([^\"]*)\" remote directory$")
    public void uploadSoctarFile(String remoteDirectory) throws Throwable {
        String custId = parameterProvider.getValueOrParameterAsString("parameter:cust_id");
        String eanId =  parameterProvider.getValueOrParameterAsString("parameter:ean_id");
        String soctarDate = parameterProvider.getValueOrParameterAsString("parameter:start-end-date");
        String soctarFilePath = FileUtil.getSoctarFileFromTemplate(custId, eanId, soctarDate);
        parameterProvider.put("soctar-file-name", FilenameUtils.getName(soctarFilePath));
        final String sftpHost = ConfigProvider.getProperty(ConfigKey.SSH_NOVA_SFTP_HOST);
        JSchUtil.get().sftpPut(sftpHost, remoteDirectory, soctarFilePath);
    }

    @When("^Soctar EAN is \"([^\"]*)\"$")
    public void setValue(final String value){
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        parameterProvider.put("ean_id", inputValue);
    }

    @And("^Soctar start date is \"([^\"]*)\"$")
    public void setSoctarDate(final String value){
        String dateValue = checkAndConvertToSoctarFileDate(parameterProvider.getValueOrParameterAsString(value));
        parameterProvider.put("start-end-date", dateValue);
        String startEndDates = checkAndConvertToDwpContractStartEndDate(value);
        parameterProvider.put("start-en-einddatum", startEndDates);
        String[] dates = startEndDates.split(DateExpressionsUtil.DATE_SEPARATOR);
        parameterProvider.put("start-date", dates[0]);
        parameterProvider.put("end-date", dates[1]);

    }

    @And("^Soctar customer Id is \"([^\"]*)\"$")
    public void soctarCustomerIdIs(String value) {
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        parameterProvider.put("cust_id", inputValue);
    }
}
