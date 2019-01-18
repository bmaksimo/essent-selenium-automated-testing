package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class DwpAccountOverviewPage extends Component {

    public DwpAccountOverviewPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public WebElement overviewHeader() {
        return seleniumDriver.findElementWhenVisible(By.className("nav-header"));
    }

    public WebElement serviceIcon() {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//span[@class='icon-agent'])[1]"));
    }

    public void clickOnServiceIcon()  {
        seleniumDriver.waitAndClick(serviceIcon());
    }

    public void clickArrowUpButton()  {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.className("icon-arrow-up")));
    }

    public WebElement arrowUpButton()  {
        return seleniumDriver.findElementWhenVisible(By.className("icon-arrow-up"));
    }

    public WebElement workflowsicon() {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//span[@class='icon-flowchart'])[1]"));
    }

    public void clickOnWorkflowsIcon()  {
        seleniumDriver.waitAndClick(workflowsicon());
    }

    public WebElement detailsIcon()  {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//span[@class='icon-bedrijf'])[1]"));
    }

    public void clickOnDetailsIcon()  {
        seleniumDriver.waitAndClick(detailsIcon());
    }

    public WebElement contractIcon()  {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[@class='icon-contract']"));
    }

    public void clickOnContractIcon()  {
           seleniumDriver.waitAndClick(contractIcon());
    }

    public WebElement plusIcon() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//a[@name='Plus Menu']"));
    }

    public void clickOnplusIcon()  {
        seleniumDriver.waitAndClick(plusIcon());
    }

    public WebElement logAcaseForAccountOption(String key) {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'"+key+"')]"));
    }

    public void clickOnLogAcaseForAccountOption(String key)  {
         seleniumDriver.waitAndClick(logAcaseForAccountOption(key));
    }

    public WebElement subjectDropdownMenu() {

        return seleniumDriver.findElementWhenVisible(By.id("cases-name-field"));
    }

    public void clickOnSubjectDropdownMenu()  {
        seleniumDriver.waitAndClick(subjectDropdownMenu());
    }

    public WebElement settlementInvoiceDropdownOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//option[@label='Afrekeningsfactuur']"));
    }

    public void clickOnSettlementInvoiceDropdownOption()  {
        seleniumDriver.waitAndClick(settlementInvoiceDropdownOption());
    }

    public WebElement descriptionField() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-description-field"));
    }

    public void clickOnDescriptionField()  {
        seleniumDriver.waitAndClick(descriptionField());
    }

    public void enterComplaintText(String string)  {
        seleniumDriver.waitAndClick(descriptionField());
        seleniumDriver.waitAndSendKeys(descriptionField(), string);
    }

    public WebElement saveButton() {
        return seleniumDriver.findElementWhenClickable(By.xpath("//button[contains(text(),'Opslaan')]"));
    }

    public void clickOnSaveButton()  {
        seleniumDriver.waitAndClick(saveButton());
    }

    public WebElement serviceDropdownMenu() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Service')]"));
    }

    public void clickOnServiceDropdownMenu()  {
         seleniumDriver.waitAndClick(serviceDropdownMenu());
    }

    public WebElement accountChangesDropdownSubMenu() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Account changes')]"));
    }

    public void clickOnAccountChangesDropdownSubMenu()  {
           seleniumDriver.waitAndClick(accountChangesDropdownSubMenu());
    }

    public WebElement updateAccountDetailsOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Update account details')]"));
    }

    public void clickOnUpdateAccountDetailsOption()  {
        seleniumDriver.waitAndClick(updateAccountDetailsOption());
    }

    public WebElement saveButtonForFinanceAndLegalSection()  {
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[contains(text(),' Finance & legal ')]/preceding-sibling::*[1]"));
    }

    public void clickOnSaveButtonForFinanceAndLegalSection()  {
        seleniumDriver.waitAndClick(saveButtonForFinanceAndLegalSection());
    }


    public WebElement channelDropdownMenu() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-interaction-channel-c-field"));
    }

    public void clickOnChannelDropdownMenu()  {
        channelDropdownMenu();
    }

    public WebElement phoneDropdownOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//option[@label='Telefoon']"));
    }

    public void clickOnPhoneDropdownOption()  {
        seleniumDriver.waitAndClick(phoneDropdownOption());
    }

    public WebElement casePriorityField() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-priority-field"));
    }

    public void clickOnCasePriorityField()  {
         seleniumDriver.waitAndClick(casePriorityField());
    }

    public WebElement highDropdownOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//option[@label='Hoog']"));
    }

    public void clickOnHighDropdownOption() {
        highDropdownOption().click();
    }

    public WebElement solutionField() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-resolution-field"));
    }

    public void clickOnSolutionField()  {
        seleniumDriver.waitAndClick(solutionField());
    }

    public WebElement describeTheSolutionDropdown() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'DESCRIBE THE SOLUTION')]"));
    }

    public void clickOnDescribeTheSolutionDropdown()  {
        seleniumDriver.waitAndClick(describeTheSolutionDropdown());
    }


    public WebElement describeTheQuestionDropdown() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'DESCRIBE THE QUESTION')]"));
    }

    public void clickOnDescribeTheQuestionDropdown()  {
        seleniumDriver.waitAndClick(describeTheQuestionDropdown());
    }

    public void setAllForNewCaseForCustomer(String descriptiontext, String solutionText)  {
        clickOnSubjectDropdownMenu();
        clickOnSettlementInvoiceDropdownOption();
        clickOnChannelDropdownMenu();
        clickOnPhoneDropdownOption();
        clickOnCasePriorityField();
        clickOnHighDropdownOption();
        seleniumDriver.waitAndSendKeys(descriptionField(),descriptiontext);
        clickOnDescribeTheQuestionDropdown();
        seleniumDriver.waitAndSendKeys(solutionField(),solutionText);
        clickOnDescribeTheSolutionDropdown();
        clickOnSaveButton();

    }

    public WebElement complaintField() {

        return seleniumDriver.findElementWhenVisible(By.id("description-field"));
    }

    public String getComplaintText() {
        return seleniumDriver.findElementWhenVisible(By.id("description")).getText();
    }

    public WebElement firstCaseInTheList() {

        return seleniumDriver.findElementWhenVisible(By.xpath("(//h5)[1]"));
    }

    public void clickOnFirstCaseInTheList()  {
        seleniumDriver.waitAndClick(firstCaseInTheList());
    }

    public WebElement priorityField() {

        return seleniumDriver.findElementWhenVisible(By.id("priority-field"));
    }

    public boolean checkIfPriorityIsHigh() {
        return priorityField().getText().equalsIgnoreCase("Hoog");
    }

    public String getSolutionFieldText() {
        return seleniumDriver.findElementWhenVisible(By.id("resolution-field")).getText();

    }

    public void clickOnMarketTransactionLink()  {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("market-transactions-dashboard-link")));
    }

    public void clickOnFirstTransaction(String contractID)  {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("(//h6[contains(text(),'"+contractID+"')]/preceding-sibling::*[1])[1]")));
    }

    public void refreshMarketTransactions()  {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'REFRESH MARKET TRANSACTIONS')]")));
    }
}
