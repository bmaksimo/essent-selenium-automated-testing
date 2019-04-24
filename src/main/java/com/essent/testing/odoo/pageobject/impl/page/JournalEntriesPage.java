package com.essent.testing.odoo.pageobject.impl.page;

import com.essent.automation.util.Sleeper;
import com.essent.testing.odoo.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class JournalEntriesPage extends Component {
    private static String DATE_PATTERN = "MM/dd/yyyy";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);

    private String date = simpleDateFormat.format(new Date());

    public void clickOnCreateJournalEntry() {
        seleniumDriver.findElementWhenVisible(By.xpath("//button[@class='oe_button oe_list_add oe_highlight']")).click();
    }

    public void clickOnDropDownButtonJournal(){
        seleniumDriver.findElementWhenVisible(By.xpath("//span[@class='oe_m2o_drop_down_button']"));
    }

    public void chooseDiverseDagboekKlanten(String journal) {
        seleniumDriver.findElementWhenVisible(By.xpath("(//input[@class='ui-autocomplete-input'])[1]")).sendKeys(journal);
        seleniumDriver.findElementWhenVisible(By.xpath("//a[contains(text(),'Diverse dagboek klanten (EUR)')]")).click();
    }

    public WebElement findDataDocumentElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//input[@name='date_document']"));
    }

    public void dateDocumentIsToday(){
        findDataDocumentElement().click();
        findDataDocumentElement().clear();
        findDataDocumentElement().sendKeys(date);
    }

    public void clickOnAddAnItem() {
        WebElement addItemLink = seleniumDriver.findElementWhenVisible(By.xpath("//a [contains(text(),\"Add an item\")]"));
        seleniumDriver.moveToElementAndClick(addItemLink);
    }

    public void saveJournal(){
        WebElement saveButton = seleniumDriver.findElementWhenVisible(By.xpath("//button[@class='oe_button oe_form_button_save oe_highlight']"));
        seleniumDriver.moveToElementAndClick(saveButton);
    }

    public void setName(String name){
         seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='name']/input")).sendKeys(name);
    }

    public WebElement findPartnerElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='partner_id']/div/input"));
    }

    public void setPartner(String partner){
        findPartnerElement().sendKeys(partner);
        Sleeper.sleepTightInSeconds(7);
        WebElement partnerLink = seleniumDriver.findElementWhenVisible(By.xpath("//a[contains(text(),'B2B_UP')]"));
        seleniumDriver.moveToElementAndClick(partnerLink);
    }

    public WebElement findAccountElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='account_id']/div/input"));
    }

    public void setAccount(String account){

        findAccountElement().click();
        findAccountElement().clear();
        findAccountElement().sendKeys(account);
        WebElement accountLink = seleniumDriver.findElementWhenVisible(By.xpath("//a[contains(text(),'580100 B2C-B2B  OVERBOEKINGEN')]"));
        seleniumDriver.moveToElementAndClick(accountLink);
    }


    public WebElement findDebitElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='debit']/input"));
    }

    public void setDebit(String debit){
        findDebitElement().click();
        findDebitElement().clear();
        findDebitElement().sendKeys(debit);
    }


    public WebElement findCreditElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='credit']/input"));
    }

    public void setCredit(String credit){
        findCreditElement().click();
        Sleeper.sleepTightInSeconds(1);
        findCreditElement().sendKeys(credit);
    }

    public void createNewItem(List<List<String>> table, Integer row, String partnerNumber) {
        setName(table.get(row).get(0));
        setPartner(partnerNumber);
        Sleeper.sleepTightInSeconds(2);
        if (row == 1) {
            setCredit(table.get(row).get(3));
        }
    }

    public WebElement journalItemsCheckBox(Integer row){
        return  seleniumDriver.findElementWhenVisible(By.xpath(("(//tbody/tr["+row+"]/th/input)[2]")));
    }

    public void clickOnJournalItemsCheckBox(Integer row){
        journalItemsCheckBox(row).click();
    }


    public WebElement findMoreElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath("(//button[@class='oe_dropdown_toggle oe_dropdown_arrow'])[8]"));
    }

    public void clickOnMoreMenuItem(String item){
        findMoreElement().click();
        seleniumDriver.findElementWhenVisible(By.xpath("//a[contains(text(),'"+item+"')]")).click();
    }

    public void clickOnConfirm(){
        seleniumDriver.findElementWhenVisible(By.xpath("//button[@class='oe_button oe_form_button oe_highlight']")).click();
    }

    public WebElement reconcile(Integer row){
        return seleniumDriver.findElementWhenVisible(By.xpath("(//td[@data-field='reconcile_id'])["+row+"]/a"));
    }

    public String reconcileText(Integer row){
        return reconcile(row).getText();
    }


}
