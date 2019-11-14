package stepdefinitions.odoo.page;

import com.billinghouse.testautomation.util.dsl.DateExpressionsUtil;
import com.billinghouse.testautomation.util.dsl.EssentDateTimeFormat;
import com.essent.testing.odoo.pageobject.impl.page.AccountBlockPage;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.java.en.And;
import org.junit.Assert;

public class AccountBlockSteps extends OdooScenario {
    @And("Reason is \"([^\"]*)\" on Account Blocks page")
    public void accountBlockReasonIsEPlus(String reason){
        String pageReason = new AccountBlockPage().getAccountBlockReason();
        Assert.assertTrue("Account block reason is not " + reason, pageReason.equalsIgnoreCase(reason));
    }

    @And("Start date is today on Account Blocks page")
    public void accountBlockStartDateIsToday(){
        String startDate = DateExpressionsUtil
            .getToday()
            .toString(EssentDateTimeFormat.ODOO_DATE_FORMAT.getFormat());
        String pageStartDate = new AccountBlockPage().getAccountBlockStartDate();
        Assert.assertTrue("Account block start date is not today", pageStartDate.equalsIgnoreCase(startDate));
    }

    @And("^End date is \"([^\"]*)\" days from today on Account Blocks page$")
    public void accountBlockEndDateIsSevenDaysFromToday(int amountOfDays){
        String endDate = DateExpressionsUtil
            .getNDaysFromToday(amountOfDays)
            .toString(EssentDateTimeFormat.ODOO_DATE_FORMAT.getFormat());
        String pageEndDate = new AccountBlockPage().getAccountBlockEndDate();
        Assert.assertTrue("Account block end date is not " + amountOfDays + " days from today", pageEndDate.equalsIgnoreCase(endDate));
    }

    @And("^End date is empty on Account Blocks page$")
    public void accountBlockEndDateIsEmpty() {
        String pageEndDate = new AccountBlockPage().getAccountBlockEndDateIsEmpty();
        Assert.assertNull("Account block end date is not empty"+ pageEndDate, pageEndDate);
    }

}
