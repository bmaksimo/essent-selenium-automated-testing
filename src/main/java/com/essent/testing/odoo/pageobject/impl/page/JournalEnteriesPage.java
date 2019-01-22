package com.essent.testing.odoo.pageobject.impl.page;

import com.essent.automation.util.Sleeper;

import com.essent.testing.odoo.pageobject.impl.Component;
import com.essent.testing.selenium.webdriver.odoo.SeleniumDriverOdooImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class JournalEnteriesPage extends Component {
    private static String DATE_PATTERN = "MM/dd/yyyy";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);

    private String date = simpleDateFormat.format(new Date());

    public JournalEnteriesPage(SeleniumDriverOdooImpl seleniumDriver) {
        super(seleniumDriver);
    }

    public void clickOnCreateJournalEntery(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//button[@class='oe_button oe_list_add oe_highlight']")));
    }

    public void clickOnDropDownButtonJournal(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//span[@class='oe_m2o_drop_down_button']")));
    }

    public void chooseDiverseDagboekKlanten(String journal){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("oe-field-input-8")), journal);
    }

    public void dateDocumentIsToday(){
        Sleeper.sleepTightInSeconds(3);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//input[@name='date_document']")), date);
    }

    public void clickOnAddAnItem(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//a [contains(text(),\"Add an item\")]")));
    }

    public void saveJournal(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//button[@class='oe_button oe_form_button_save oe_highlight']")));
    }
    public void postJournal(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//button[@class='oe_button oe_form_button oe_highlight']")));
    }

    public void setName(String name){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='name']/input")), name);
    }

    public void setPartner(String partner){
        Sleeper.sleepTightInSeconds(3);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='partner_id']/div/input")), partner);
        Sleeper.sleepTightInSeconds(3);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("(//li[@class='ui-menu-item']/a)[2]")));
    }

    public WebElement findAccountElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='account_id']/div/input"));
    }

    public void setAccout(String account){
        Sleeper.sleepTightInSeconds(5);
        seleniumDriver.waitAndClick(findAccountElement());
        findAccountElement().clear();
        seleniumDriver.waitAndSendKeys(findAccountElement(), account);
        Sleeper.sleepTightInSeconds(3);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("(//li[@class='ui-menu-item']/a)[4]")));
    }

    public WebElement findDebitElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='debit']/input"));
    }

    public void setDebit(String debit){
        Sleeper.sleepTightInSeconds(3);
        seleniumDriver.waitAndClick(findDebitElement());
        findDebitElement().clear();
        seleniumDriver.waitAndSendKeys(findDebitElement(), debit);
    }

    public WebElement findCreditElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='credit']/input"));
    }

    public void setCredit(String credit){
        Sleeper.sleepTightInSeconds(3);
        seleniumDriver.waitAndClick(findCreditElement());
        findCreditElement().clear();
        seleniumDriver.waitAndSendKeys(findCreditElement(), credit);
    }

    public void createNewItem(List<List<String>> table, Integer row, String partnerNumber) {
        if (row == 2){
            setPartner(partnerNumber);
            setAccout(table.get(row).get(2));
        }else {
            setName(table.get(row).get(0));
            setPartner(partnerNumber);
            setAccout(table.get(row).get(2));
            setDebit(table.get(row).get(3));
            setCredit(table.get(row).get(4));
        }
    }

    public WebElement journalItemsCheckBox(Integer row){
        return  seleniumDriver.findElementWhenVisible(By.xpath(("(//tbody/tr["+row+"]/th/input)[2]")));
    }

    public void clickOnJournalItemsCheckBox(Integer row){
        seleniumDriver.waitAndClick(journalItemsCheckBox(row));
    }

    public String getJournalItemsCredit(Integer row){
        return journalItemsCheckBox(row).findElement(By.xpath("//td[@data-field='credit']")).getText();
    }
    public String getJournalItemsDebit(Integer row){
        return journalItemsCheckBox(row).findElement(By.xpath("//td[@data-field='debit']")).getText();
    }

    public WebElement findMoreElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath("(//button[@class='oe_dropdown_toggle oe_dropdown_arrow'])[8]"));
    }

    public void clickOnMoreMenuItem(String item){
        seleniumDriver.waitAndClick(findMoreElement());
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//a[contains(text(),'"+item+"')]")));
    }

    public void clickOnConfirm(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//button[@class='oe_button oe_form_button oe_highlight']")));
    }

    public WebElement reconcile(Integer row){
        return seleniumDriver.findElementWhenVisible(By.xpath("(//td[@data-field='reconcile_id'])["+row+"]/a"));
    }

    public String reconcileText(Integer row){
        return reconcile(row).getText();
    }

}
