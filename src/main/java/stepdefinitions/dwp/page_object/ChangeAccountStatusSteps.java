package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.impl.service_contracting.ChangeAccountStatusPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.util.resource.ResourceUtil;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import org.openqa.selenium.By;

import java.io.File;

public class ChangeAccountStatusSteps extends DwpScenario {
    @Before("@SMOKE, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @BILLING, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@SMOKE, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void tearDown(Scenario scenario) throws Exception {
        super.tearDown(scenario);
    }

    @And("^Update account status on \"([^\"]*)\"$")
    public void updateAccountStatusOn(String status) throws Throwable {
        ChangeAccountStatusPage changeAccountStatusPage = new ChangeAccountStatusPage(webDriver);
        changeAccountStatusPage.chooseAccountStatus(status);
    }

    @And("^Upload file$")
    public void uploadFile() throws Throwable {
        String filePath = ResourceUtil.toPath("data/dwp/customer-signature.pdf");
        final String jacobdllarch = System.getProperty("sun.arch.data.model")
            .contains("32") ? "jacob-1.18-x86.dll" : "jacob-1.18-x64.dll";
        String jacobdllpath = filePath + "\\" + jacobdllarch;
        File filejacob = new File(jacobdllpath);
        System.setProperty(LibraryLoader.JACOB_DLL_PATH,
            filejacob.getAbsolutePath());
    }
}
