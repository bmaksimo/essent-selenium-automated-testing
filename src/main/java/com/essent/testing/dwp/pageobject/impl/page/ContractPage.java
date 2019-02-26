package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ContractPage extends Component {

    public WebElement startData(){
        return seleniumDriver.findElementWhenVisible(By.id("contract-start-date-field"));
    }
    public String pattern = "dd/MM/yyyy";
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public String date = simpleDateFormat.format(new Date());

    public void startDateIsToday() {
        seleniumDriver.waitAndSendKeys(startData(),"date");
    }

    public void saveButtton(){
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("primaryButton")));
    }

    public String getClientNumber() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//blue-sidebar//h4")).getText();
    }

    public void selectAccount(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"account-id-field\"]//span[2]")));
    }

    public void searchByClientNuiber(String nubmer){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("search-input")),nubmer);
    }

    public void clickOnPlusMeniInTable(String row, String table) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementOrNull(By.xpath("(//list[@list-key='"+table+"']//tbody[@id='rows']//list-plus-cell//a[@class='show-actions icon-plus'])["+row+"]")));
    }
    public String getActiveContractEAN(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"rows\"]//list-link-bold-top-two-liner-cell//a/h5")).getText();
    }

    public String status(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"rows\"]/tr[1]/td[2]/list-simple-two-liner-cell/p/span[1]")).getText();
    }

    public void clickOnX() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//guidance-modal//div[@class = 'modal__header']/a")));

    }

    public void selectItemLegalForm() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"legal-form-c-field\"]/option[2]")));
    }

    public void selectGender() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"gender-c-field\"]/option[2]")));
    }

    public void getEmail(String emailContract) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"leads-contact-details-contact-details-type-email-contact-details-value-field\"]")), emailContract);
    }

    public void clickNaceCode() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"nace-code-c-field\"]")));
    }

    public void clickOnSearch() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//div[@class = 'input__with-button']/input")));
    }

    public void checkNaceCode() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//select-with-search-modal/section//span")));
    }

    public void saveSelectedItem() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//select-with-search-modal/section//div[@class = 'modal__header']/a")));
    }

    public void setAddress(String address, String houseNumber, String postalCode, String City) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-street-field\"]")), address);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-number-field\"]")), houseNumber);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-postalcode-field\"]")), postalCode);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-city-field\"]")), City);

    }

    public void setTelephone(String telephone) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"leads-contact-details-contact-details-phone-type-work-phone-contact-details-type-phone-contact-details-value-field\"]")), telephone);
    }

    public void setName (String fname, String lname) throws Throwable {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"first-name-field\"]")), fname);
        Thread.sleep(2000);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"last-name-field\"]")), lname);

    }

    public void setCompanyName(String cname) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"company-name-c-field\"]")), cname);

    }

    public void setEanCode(String eanCode) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"ean-c-accounts-aos-quotes-aos-products-quotes-7-cc-91145-f-43-f-4800-d-705-58-ffa-7899-fbd-field\"]")), eanCode);

    }

    public void saveInitialQuote() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"primaryButton\"]/span[2]")));

    }

    public void checkInvoiceOpenBalance(String key) {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list-checkbox-cell[@list-key='"+key+"']")));
    }

    public String getActiveContractEndDate(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"rows\"]/tr[1]/td[5]/list-simple-two-liner-cell/p/span[2]")).getText();
    }

    public String getActiveContractStartDate(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"rows\"]/tr[1]/td[5]/list-simple-two-liner-cell/p/span[1]")).getText();
    }

    public void setNewMoveAddress(String address, String houseNumber, String postalCode, String City) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-street-field\"]")), address);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenClickable(By.xpath("//*[@id=\"aos-products-quotes-addresses-aos-products-quotes-field-container\"]//ul/li[1]/a/b")));
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-number-field\"]")), houseNumber);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-postalcode-field\"]")), postalCode);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenClickable(By.xpath("//*[@id=\"aos-products-quotes-addresses-aos-products-quotes-field-container\"]//ul/li/a/b")));

    }

    public String getEanFromContract(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("(//list-link-bold-top-two-liner-cell/div/a/h5)[1]")).getText();
    }

    public String getStatusFromContract(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("(//list-link-bold-top-two-liner-cell/div/h6)[2]")).getText();
    }

    public String getContractType(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key='ContractsOnAccount']//tr[1]/td[3]//span[1]")).getText();
    }

    public String getTypeProduct(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("aos-products-price-type-field")).getText();
    }

    public String getEnergieprijsEnkelvoudigInclBtw(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("dwp-selling-price-th-incl-vat-fixed-field")).getText();
    }

    public String getEnergieprijsDagInclBtw(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("dwp-selling-price-high-incl-vat-fixed-field")).getText();
    }

    public String getEnergieprijsNachtInclBtw(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("dwp-selling-price-low-incl-vat-fixed-field")).getText();
    }

    public String getEnergieprijsExclusiefNachtInclBtw(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("dwp-selling-price-exclnight-incl-vat-fixed-field")).getText();
    }

    public String getVasteVergoedingInclBtw(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("dwp-selling-price-fixedfee-incl-vat-field")).getText();
    }

    public String getEnergieprijsEnkelvoudigExclBtw(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("dwp-selling-price-th-fixed-field")).getText();
    }

    public String getEnergieprijsDagExclBtw(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("dwp-selling-price-high-fixed-field")).getText();
    }

    public String getEnergieprijsNachExclBtw(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("dwp-selling-price-low-fixed-field")).getText();
    }

    public String getEnergieprijsExclusiefNachtExclBtw(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("dwp-selling-price-exclnight-fixed-field")).getText();
    }

    public String getVasteVergoedingExclBtw(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("dwp-selling-price-fixedfee-field")).getText();
    }

    public String getKortingenOpContractKortingscode(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key='DiscountsOnContract']//tr[1]/td[1]//span[1]")).getText();
    }

    public String getKortingenOpContractProducttype(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key='DiscountsOnContract']//tr[1]/td[2]//span[1]")).getText();
    }

    public void clickOnContractenNummer(){
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key='ContractsOnAccount']//tr[1]/td[4]//h5")));
    }

    public void clickOnBekijkPrijzenTariefkaatFromPlus(){
        Sleeper.sleepTightInSeconds(2);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list-plus-cell[@list-key='ContractlinesOnContract']/div/a")));
        Sleeper.sleepTightInSeconds(2);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list-row-action[@label='Bekijk prijzen tariefkaart']/a")));
    }

    public String getProductName(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("(//list[@list-key='ContractlinesOnContract']//td[@class='list__cell cell__text']//p/span[2])[1]")).getText();
    }

    public String getCaseOnderwerp(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key=\"InteractionsOnAccount\"]//td[@class=\"list__cell cell__text\"][6]/list-link-bold-top-two-liner-cell/div/h6")).getText();
    }
    public String getCaseNumber(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key=\"InteractionsOnAccount\"]//td[7]//div//h5[1]")).getText();
    }

    public String getInteractionType(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//list-simple-two-liner-cell[@line-1='Document']/p/span[1]")).getText();
    }

    public String getInteractionOnderwerp() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//list-simple-two-liner-cell[@line-1='Document']/p/span[2]")).getText();
    }

    public String getInteractionVerwanteCase() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id='rows']/tr[1]/td[7]/list-link-bold-top-two-liner-cell/div/a")).getText();
    }

    public void closeBekijkPrijsdetailsTK1(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//a[@class=\"button icon-close\"]")));
    }

    public void chooseKortigen(String discount){
        Sleeper.sleepTightInSeconds(2);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("dwp|discount_id")));
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id='dwp-discount-id-field']/option[@label='"+discount+"']")));
    }

    public void confirmTheSign(String place){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"accounts|aos_quotes|sign_location_c\"]/div[1]/input")),place);
    }

    public String getCompanyNumber(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//*//*[@id=\"company-number-c-field\"]")).getText();
    }

    public String getContractNumber(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"account_number_c\"]/div")).getText();
    }


}
