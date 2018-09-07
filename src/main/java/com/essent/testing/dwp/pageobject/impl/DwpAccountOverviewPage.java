package com.essent.testing.dwp.pageobject.impl;

import com.essent.testing.selenium.SeleniumDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class DwpAccountOverviewPage  {


    private SeleniumDriver seleniumDriver;

    public DwpAccountOverviewPage(SeleniumDriver seleniumDriver) {

        this.seleniumDriver = seleniumDriver;
    }

    public WebElement overviewHeader() {

        return seleniumDriver.findElementWhenVisible(By.className("nav-header"));
    }

    public WebElement serviceIcon() {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//span[@class='icon-agent'])[1]"));
    }

    public void clickOnServiceIcon() throws InterruptedException {
        serviceIcon().click();
    }

    public void clickArrowUpButton() throws InterruptedException {
        seleniumDriver.findElementWhenVisible(By.className("icon-arrow-up")).click();
    }

    public WebElement arrowUpButton() throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.className("icon-arrow-up"));
    }

    public WebElement workflowsicon() {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//span[@class='icon-flowchart'])[1]"));
    }

    public void clickOnWorkflowsIcon() throws InterruptedException {
        workflowsicon().click();
    }

    public WebElement detailsIcon() throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//span[@class='icon-bedrijf'])[1]"));
    }

    public void clickOnDetailsIcon() throws InterruptedException {
        detailsIcon().click();
    }

    public WebElement contractIcon() throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[@class='icon-contract']"));
    }

    public void clickOnContractIcon() throws InterruptedException {
        contractIcon().click();
    }

    public WebElement plusIcon() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//a[@name='Plus Menu']"));
    }

    public void clickOnplusIcon() throws InterruptedException {
        plusIcon().click();
    }

    public WebElement logAcaseForAccountOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Log a case for account')]"));
    }

    public void clickOnLogAcaseForAccountOption() throws InterruptedException {
        logAcaseForAccountOption().click();
    }

    public WebElement subjectDropdownMenu() {

        return seleniumDriver.findElementWhenVisible(By.id("cases-name-field"));
    }

    public void clickOnSubjectDropdownMenu() throws InterruptedException {
        subjectDropdownMenu().click();
    }

    public WebElement settlementInvoiceDropdownOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//option[@label='Settlement invoice']"));
    }

    public void clickOnSettlementInvoiceDropdownOption() throws InterruptedException {
        settlementInvoiceDropdownOption().click();
    }

    public WebElement descriptionField() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-description-field"));
    }

    public void clickOnDescriptionField() throws InterruptedException {
        descriptionField();
    }

    public void enterComplaintText(String string) throws InterruptedException {
        descriptionField().click();
        descriptionField().clear();
        descriptionField().sendKeys(string);
    }

    public WebElement saveButton() {
        return seleniumDriver.findElementWhenClickable(By.xpath("//button[contains(text(),'Save')]"));
    }

    public void clickOnSaveButton() throws InterruptedException {
        saveButton().click();
    }

    public WebElement serviceDropdownMenu() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Service')]"));
    }

    public void clickOnServiceDropdownMenu() throws InterruptedException {
        serviceDropdownMenu().click();
    }

    public WebElement accountChangesDropdownSubMenu() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Account changes')]"));
    }

    public void clickOnAccountChangesDropdownSubMenu() throws InterruptedException {
        accountChangesDropdownSubMenu().click();
    }

    public WebElement updateAccountDetailsOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Update account details')]"));
    }

    public void clickOnUpdateAccountDetailsOption() throws InterruptedException {
        updateAccountDetailsOption().click();
    }

    public WebElement duningStopCheckbox() {
        return seleniumDriver.findElementWhenVisible(By.id("dunning-stop-c-field"));
    }

    public void clickOnDuningStopCheckbox() throws InterruptedException {
        if (checkIfDunningStopCheckboxIsChecked()) {
            System.out.println("DUNNING STOP WAS ALREADY ACTIVE");
            seleniumDriver.findElementWhenVisible(By.id("dunning_stop_c")).click();
            duningStopCheckbox().click();
        } else {
            duningStopCheckbox().click();
        }
    }

    public WebElement saveButtonForFinanceAndLegalSection() throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[contains(text(),' Finance & legal ')]/preceding-sibling::*[1]"));
    }

    public void clickOnSaveButtonForFinanceAndLegalSection() throws InterruptedException {
        saveButtonForFinanceAndLegalSection().click();
    }

    public boolean checkIfDunningStopCheckboxIsChecked() throws InterruptedException {
        String classValue = duningStopCheckbox().getAttribute("class");
        if (classValue.contains("not-empty")) {
            return true;
        } else {
            return false;
        }
    }

    public void putDuningStopBackToOff() throws InterruptedException {
        clickOnplusIcon();
        clickOnServiceDropdownMenu();
        clickOnAccountChangesDropdownSubMenu();
        clickOnUpdateAccountDetailsOption();
        seleniumDriver.findElementWhenVisible(By.id("dunning_stop_c")).click();
        clickOnSaveButtonForFinanceAndLegalSection();

    }

    public WebElement channelDropdownMenu() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-interaction-channel-c-field"));

    }

    public void clickOnChannelDropdownMenu() throws InterruptedException {
        channelDropdownMenu();
    }

    public WebElement phoneDropdownOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//option[@label='Phone']"));
    }

    public void clickOnPhoneDropdownOption() throws InterruptedException {
        phoneDropdownOption().click();
    }

    public WebElement casePriorityField() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-priority-field"));
    }

    public void clickOnCasePriorityField() throws InterruptedException {
        casePriorityField().click();
    }

    public WebElement highDropdownOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//option[@label='High']"));
    }

    public void clickOnHighDropdownOption() {
        highDropdownOption().click();
    }

    public WebElement solutionField() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-resolution-field")); //cases-resolution-field
    }

    public void clickOnSolutionField() throws InterruptedException {
        solutionField().click();

    }

    public WebElement describeTheSolutionDropdown() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'DESCRIBE THE SOLUTION')]"));
    }

    public void clickOnDescribeTheSolutionDropdown() throws InterruptedException {
        describeTheSolutionDropdown().click();
    }


    public WebElement describeTheQuestionDropdown() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'DESCRIBE THE QUESTION')]"));
    }

    public void clickOnDescribeTheQuestionDropdown() throws InterruptedException {
        describeTheQuestionDropdown().click();
    }

    public void setAllForNewCaseForCustomer(String descriptiontext, String solutionText) throws InterruptedException {
        clickOnSubjectDropdownMenu();
        clickOnSettlementInvoiceDropdownOption();
        clickOnChannelDropdownMenu();
        clickOnPhoneDropdownOption();
        clickOnCasePriorityField();
        clickOnHighDropdownOption();
        clickOnDescriptionField();
        descriptionField().sendKeys(descriptiontext);
        clickOnDescribeTheQuestionDropdown();
        clickOnSolutionField();
        solutionField().sendKeys(solutionText);
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
        firstCaseInTheList().click();
    }

    public WebElement priorityField() {

        return seleniumDriver.findElementWhenVisible(By.id("priority-field"));
    }

    public boolean checkIfPriorityIsHigh() {
        if (priorityField().getText().equalsIgnoreCase("High")) {
            return true;
        }

        else {
            return false;
        }
    }

    public String getSolutionFieldText() {
        return seleniumDriver.findElementWhenVisible(By.id("resolution-field")).getText();

    }

    public void clickOnMarketTransactionLink() throws InterruptedException {
        seleniumDriver.findElementWhenVisible(By.id("market-transactions-dashboard-link")).click();
    }

    public void clickOnFirstTransaction(String contractID) throws InterruptedException {
        seleniumDriver.findElementWhenVisible(By.xpath("(//h6[contains(text(),'"+contractID+"')]/preceding-sibling::*[1])[1]"))
            .click();
    }

    public void refreshMarketTransactions() throws InterruptedException {
        seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'REFRESH MARKET TRANSACTIONS')]")).click();
    }
}
