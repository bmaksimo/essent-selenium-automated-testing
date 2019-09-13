package stepdefinitions.odoo.page;

import com.billinghouse.test_automation.util.dsl.DateExpressionsUtil;
import com.billinghouse.test_automation.util.dsl.EssentDateTimeFormat;
import com.essent.testing.odoo.pageobject.impl.page.AccountBlockPage;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.java.en.And;
import org.junit.Assert;

public class AccountBlockSteps extends OdooScenario {
    @And("Account Block Reason is E-plus")
    public void accountBlockReasonIsEPlus(){
        String pageReason = new AccountBlockPage().getAccountBlockReason();
        Assert.assertTrue("Account block reason is not E-plus", pageReason.equalsIgnoreCase("E-plus"));
    }

    @And("Account Block Start date is today")
    public void accountBlockStartDateIsToday(){
        String today = DateExpressionsUtil
            .getToday()
            .toString(EssentDateTimeFormat.ODOO_DATE_FORMAT.getFormat());
        String pageStartDate = new AccountBlockPage().getAccountBlockStartDate();
        Assert.assertTrue("Account block start date is not today", pageStartDate.equalsIgnoreCase(today));
    }

    @And("Account Block End date is \"([^\"]*)\" days from today")
    public void accountBlockEndDateIsSevenDaysFromToday(int num){
        String aWeekFromToday = DateExpressionsUtil
            .getNDaysFromToday(num)
            .toString(EssentDateTimeFormat.ODOO_DATE_FORMAT.getFormat());
        String pageStartDate = new AccountBlockPage().getAccountBlockEndDate();
        Assert.assertTrue("Account block end date is not 7 days from today", pageStartDate.equalsIgnoreCase(aWeekFromToday    ));
    }

}
