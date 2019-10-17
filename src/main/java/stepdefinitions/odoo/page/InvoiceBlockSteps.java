package stepdefinitions.odoo.page;

import com.billinghouse.test_automation.util.dsl.DateExpressionsUtil;
import com.billinghouse.test_automation.util.dsl.EssentDateTimeFormat;
import com.essent.testing.odoo.pageobject.impl.page.InvoiceBlockPage;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class InvoiceBlockSteps extends OdooScenario {
    @And("Reason is \"([^\"]*)\" on Invoice Blocks page")
    public void invoiceBlockReason(String reason){
        String pageReason = new InvoiceBlockPage().getInvoiceBlockReason();
        Assert.assertTrue("Invoice block reason is not " + reason, pageReason.equalsIgnoreCase(reason));
    }

    @And("Start date is today on Invoice Blocks page")
    public void invoiceBlockStartDateIsToday(){
        String startDate = DateExpressionsUtil
            .getToday()
            .toString(EssentDateTimeFormat.ODOO_DATE_FORMAT.getFormat());
        String pageStartDate = new InvoiceBlockPage().getInvoiceBlockStartDate();
        Assert.assertTrue("Invoice block start date is not today", pageStartDate.equalsIgnoreCase(startDate));
    }

    @And("^End date is \"([^\"]*)\" days from today on Invoice Blocks page$")
    public void invoiceBlockEndDate(int amountOfDays){
        String endDate = DateExpressionsUtil
            .getNDaysFromToday(amountOfDays)
            .toString(EssentDateTimeFormat.ODOO_DATE_FORMAT.getFormat());
        String pageEndDate = new InvoiceBlockPage().getInvoiceBlockEndDate();
        Assert.assertTrue("Invoice block end date is not " + amountOfDays + " days from today", pageEndDate.equalsIgnoreCase(endDate));
    }

    @And("^End date is empty on Invoice Blocks page$")
    public void invoiceBlockEndDateIsEmpty() {
        String pageEndDate = new InvoiceBlockPage().getInvoiceBlockEndDateIsEmpty();
        Assert.assertNull("Invoice block end date is not empty"+ pageEndDate, pageEndDate);
    }

    @And("^\"([^\"]*)\" is open$")
    public void isOpen(String text){
        new InvoiceBlockPage().expand(text);
    }

    @And("^Manage invoice block is clicked$")
    public void manageInvoiceBlockIsClicked(){
        new InvoiceBlockPage().ClickManageInvoiceBlock();
    }

    @And("^Crete new invoice block button is clicked$")
    public void creteNewInvoiceBlockButtonIsClicked(){
        new InvoiceBlockPage().ClickCreteNewInvoiceBlockButton();
    }

    @And("^Invoice block reason is \"([^\"]*)\"$")
    public void invoiceBlockReasonIs(String reason){
       new InvoiceBlockPage().SelectInvoiceBlockReason(reason);
    }

//    public String toOdooDateDaysFromToday(int amountOfDays){
//        return DateExpressionsUtil
//            .getNDaysFromToday(amountOfDays)
//            .toString(EssentDateTimeFormat.ODOO_DATE_FORMAT.getFormat());
//    }

    //    @And("^End date is \"([^\"]*)\" days from today$")
    @And("^End date is \"([^\"]*)\"$")
    public void endDateIs(String date){
//        String endDate = toOdooDateDaysFromToday(date);
        String endDate=toOdooDate(date);
        new InvoiceBlockPage().selectEndDateInInvoiceBlock(endDate);
    }

    @Then("^Save invoice block button is clicked$")
    public void saveInvoiceBlockButtonIsClicked(){
        new InvoiceBlockPage().ClickSaveInvoiceBlockButton();
    }


}
