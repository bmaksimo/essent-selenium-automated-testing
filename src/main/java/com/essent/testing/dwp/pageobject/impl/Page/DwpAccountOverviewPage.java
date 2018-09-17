package com.essent.testing.dwp.pageobject.impl.Page;

import com.essent.testing.dwp.pageobject.Component;
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

    public void clickOnServiceIcon() throws InterruptedException {
        seleniumDriver.waitAndClick(serviceIcon());
    }

    public void clickArrowUpButton() throws InterruptedException {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.className("icon-arrow-up")));
    }

    public WebElement arrowUpButton() throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.className("icon-arrow-up"));
    }

    public WebElement workflowsicon() {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//span[@class='icon-flowchart'])[1]"));
    }

    public void clickOnWorkflowsIcon() throws InterruptedException {
        seleniumDriver.waitAndClick(workflowsicon());
    }

    public WebElement detailsIcon() throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//span[@class='icon-bedrijf'])[1]"));
    }

    public void clickOnDetailsIcon() throws InterruptedException {
        seleniumDriver.waitAndClick(detailsIcon());
    }

    public WebElement contractIcon() throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[@class='icon-contract']"));
    }

    public void clickOnContractIcon() throws InterruptedException {
           seleniumDriver.waitAndClick(contractIcon());
    }

    public WebElement plusIcon() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//a[@name='Plus Menu']"));
    }

    public void clickOnplusIcon() throws InterruptedException {
        seleniumDriver.waitAndClick(plusIcon());
    }

    public WebElement logAcaseForAccountOption(String key) {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'"+key+"')]"));
    }

    public void clickOnLogAcaseForAccountOption(String key) throws InterruptedException {
         seleniumDriver.waitAndClick(logAcaseForAccountOption(key));
    }

    public WebElement subjectDropdownMenu() {

        return seleniumDriver.findElementWhenVisible(By.id("cases-name-field"));
    }

    public void clickOnSubjectDropdownMenu() throws InterruptedException {
        seleniumDriver.waitAndClick(subjectDropdownMenu());
    }

    public WebElement settlementInvoiceDropdownOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//option[@label='Afrekeningsfactuur']"));
    }

    public void clickOnSettlementInvoiceDropdownOption() throws InterruptedException {
        seleniumDriver.waitAndClick(settlementInvoiceDropdownOption());
    }

    public WebElement descriptionField() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-description-field"));
    }

    public void clickOnDescriptionField() throws InterruptedException {
        seleniumDriver.waitAndClick(descriptionField());
    }

    public void enterComplaintText(String string) throws InterruptedException {
        seleniumDriver.waitAndClick(descriptionField());
        seleniumDriver.waitAndSendKeys(descriptionField(), string);
    }

    public WebElement saveButton() {
        return seleniumDriver.findElementWhenClickable(By.xpath("//button[contains(text(),'Opslaan')]"));
    }

    public void clickOnSaveButton() throws InterruptedException {
        seleniumDriver.waitAndClick(saveButton());
    }

    public WebElement serviceDropdownMenu() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Service')]"));
    }

    public void clickOnServiceDropdownMenu() throws InterruptedException {
         seleniumDriver.waitAndClick(serviceDropdownMenu());
    }

    public WebElement accountChangesDropdownSubMenu() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Account changes')]"));
    }

    public void clickOnAccountChangesDropdownSubMenu() throws InterruptedException {
           seleniumDriver.waitAndClick(accountChangesDropdownSubMenu());
    }

    public WebElement updateAccountDetailsOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Update account details')]"));
    }

    public void clickOnUpdateAccountDetailsOption() throws InterruptedException {
        seleniumDriver.waitAndClick(updateAccountDetailsOption());
    }

    public WebElement duningStopCheckbox() {
        return seleniumDriver.findElementWhenVisible(By.id("dunning-stop-c-field"));
    }

    public void clickOnDuningStopCheckbox() throws InterruptedException {
        if (checkIfDunningStopCheckboxIsChecked()) {
            seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("dunning_stop_c")));
            seleniumDriver.waitAndClick(duningStopCheckbox());
        } else {
            seleniumDriver.waitAndClick(duningStopCheckbox());
        }
    }

    public WebElement saveButtonForFinanceAndLegalSection() throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[contains(text(),' Finance & legal ')]/preceding-sibling::*[1]"));
    }

    public void clickOnSaveButtonForFinanceAndLegalSection() throws InterruptedException {
        seleniumDriver.waitAndClick(saveButtonForFinanceAndLegalSection());
    }

    public boolean checkIfDunningStopCheckboxIsChecked() throws InterruptedException {
        String classValue = duningStopCheckbox().getAttribute("class");
       return classValue.contains("not-empty");
    }

    public void putDuningStopBackToOff() throws InterruptedException {
        clickOnplusIcon();
        clickOnServiceDropdownMenu();
        clickOnAccountChangesDropdownSubMenu();
        clickOnUpdateAccountDetailsOption();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("dunning_stop_c")));//seleniumDriver.findElementWhenVisible(By.id("dunning_stop_c")).click();
        clickOnSaveButtonForFinanceAndLegalSection();

    }

    public WebElement channelDropdownMenu() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-interaction-channel-c-field"));

    }

    public void clickOnChannelDropdownMenu() throws InterruptedException {
        channelDropdownMenu();
    }

    public WebElement phoneDropdownOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//option[@label='Telefoon']"));
    }

    public void clickOnPhoneDropdownOption() throws InterruptedException {
        seleniumDriver.waitAndClick(phoneDropdownOption());
    }

    public WebElement casePriorityField() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-priority-field"));
    }

    public void clickOnCasePriorityField() throws InterruptedException {
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

    public void clickOnSolutionField() throws InterruptedException {
        seleniumDriver.waitAndClick(solutionField());
    }

    public WebElement describeTheSolutionDropdown() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'DESCRIBE THE SOLUTION')]"));
    }

    public void clickOnDescribeTheSolutionDropdown() throws InterruptedException {
        seleniumDriver.waitAndClick(describeTheSolutionDropdown());
    }


    public WebElement describeTheQuestionDropdown() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'DESCRIBE THE QUESTION')]"));
    }

    public void clickOnDescribeTheQuestionDropdown() throws InterruptedException {
        seleniumDriver.waitAndClick(describeTheQuestionDropdown());
    }

    public void setAllForNewCaseForCustomer(String descriptiontext, String solutionText) throws InterruptedException {
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
        return seleniumDriver.findElementWhenVisible(By.id("description-field")).getText();
    }

    public WebElement firstCaseInTheList() {

        return seleniumDriver.findElementWhenVisible(By.xpath("(//h5)[1]"));
    }

    public void clickOnFirstCaseInTheList() throws InterruptedException {
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

    public void clickOnMarketTransactionLink() throws InterruptedException {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("market-transactions-dashboard-link")));
    }

    public void clickOnFirstTransaction(String contractID) throws InterruptedException {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("(//h6[contains(text(),'"+contractID+"')]/preceding-sibling::*[1])[1]")));
    }

    public void refreshMarketTransactions() throws InterruptedException {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'REFRESH MARKET TRANSACTIONS')]")));
    }
}
