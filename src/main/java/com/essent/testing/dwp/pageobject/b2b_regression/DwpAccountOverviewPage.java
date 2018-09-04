package com.essent.testing.dwp.pageobject.b2b_regression;

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
       // Thread.sleep(4000);
        serviceIcon().click();
    }

    public void clickArrowUpButton() throws InterruptedException {
       // Thread.sleep(4000);
        seleniumDriver.findElementWhenVisible(By.className("icon-arrow-up")).click();
    }

    public WebElement arrowUpButton() throws InterruptedException {
       // Thread.sleep(3000);
        return seleniumDriver.findElementWhenVisible(By.className("icon-arrow-up"));
    }

    public WebElement workflowsicon() {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//span[@class='icon-flowchart'])[1]"));
    }

    public void clickOnWorkflowsIcon() throws InterruptedException {
       // Thread.sleep(4000);
        workflowsicon().click();
    }

    public WebElement detailsIcon() throws InterruptedException {
       // Thread.sleep(5000);
        return seleniumDriver.findElementWhenVisible(By.xpath("(//span[@class='icon-bedrijf'])[1]"));
    }

    public void clickOnDetailsIcon() throws InterruptedException {
        detailsIcon().click();
       // Thread.sleep(2000);

    }

    public WebElement contractIcon() throws InterruptedException {
       // Thread.sleep(6000);
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[@class='icon-contract']"));
    }

    public void clickOnContractIcon() throws InterruptedException {
        contractIcon().click();
    }

    public WebElement plusIcon() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//a[@name='Plus Menu']"));
    }

    public void clickOnplusIcon() throws InterruptedException {
       // Thread.sleep(5000);
        plusIcon().click();
    }

    public WebElement logAcaseForAccountOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Log a case for account')]"));
    }

    public void clickOnLogAcaseForAccountOption() throws InterruptedException {
       // Thread.sleep(500);
        logAcaseForAccountOption().click();
    }

    public WebElement subjectDropdownMenu() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-name-field"));
    }

    public void clickOnSubjectDropdownMenu() throws InterruptedException {
       // Thread.sleep(1000);
        subjectDropdownMenu().click();
    }

    public WebElement settlementInvoiceDropdownOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//option[@label='Settlement invoice']"));
    }

    public void clickOnSettlementInvoiceDropdownOption() throws InterruptedException {
       // Thread.sleep(500);
        settlementInvoiceDropdownOption().click();
    }

    public WebElement descriptionField() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-description-field"));
    }

    public void clickOnDescriptionField() throws InterruptedException {
       // Thread.sleep(1000);
        descriptionField();
    }

    public void enterComplaintText(String string) throws InterruptedException {
       // Thread.sleep(200);
        descriptionField().click();
        descriptionField().clear();
        descriptionField().sendKeys(string);
    }

    public WebElement saveButton() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//button[contains(text(),'Save')]"));
    }

    public void clickOnSaveButton() throws InterruptedException {
       // Thread.sleep(1000);
        saveButton().click();
    }

    public WebElement serviceDropdownMenu() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Service')]"));
    }

    public void clickOnServiceDropdownMenu() throws InterruptedException {
       // Thread.sleep(2000);
        serviceDropdownMenu().click();
    }

    public WebElement accountChangesDropdownSubMenu() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Account changes')]"));
    }

    public void clickOnAccountChangesDropdownSubMenu() throws InterruptedException {
       // Thread.sleep(1000);
        accountChangesDropdownSubMenu().click();
    }

    public WebElement updateAccountDetailsOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Update account details')]"));
    }

    public void clickOnUpdateAccountDetailsOption() throws InterruptedException {
       // Thread.sleep(1000);
        updateAccountDetailsOption().click();
    }

    public WebElement duningStopCheckbox() {
        return seleniumDriver.findElementWhenVisible(By.id("dunning-stop-c-field"));
    }

    public void clickOnDuningStopCheckbox() throws InterruptedException {
       // Thread.sleep(4000);
        if (checkIfDunningStopCheckboxIsChecked()) {
            System.out.println("DUNNING STOP WAS ALREADY ACTIVE");
            seleniumDriver.findElementWhenVisible(By.id("dunning_stop_c")).click();
           // Thread.sleep(1000);
            duningStopCheckbox().click();
           // Thread.sleep(2000);
        } else {
            duningStopCheckbox().click();
           // Thread.sleep(2000);
        }
    }

    public WebElement saveButtonForFinanceAndLegalSection() throws InterruptedException {
       // Thread.sleep(2000);
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[contains(text(),' Finance & legal ')]/preceding-sibling::*[1]"));
    }

    public void clickOnSaveButtonForFinanceAndLegalSection() throws InterruptedException {
       // Thread.sleep(1000);
        saveButtonForFinanceAndLegalSection().click();
       // Thread.sleep(2000);

    }

    public boolean checkIfDunningStopCheckboxIsChecked() throws InterruptedException {
       // Thread.sleep(5000);
        String classValue = duningStopCheckbox().getAttribute("class");
        if (classValue.contains("not-empty")) {
            return true;
        } else {
            return false;
        }
    }

    public void putDuningStopBackToOff() throws InterruptedException {
       // Thread.sleep(2000);
        clickOnplusIcon();
        clickOnServiceDropdownMenu();
        clickOnAccountChangesDropdownSubMenu();
        clickOnUpdateAccountDetailsOption();
       // Thread.sleep(7000);
        seleniumDriver.findElementWhenVisible(By.id("dunning_stop_c")).click();
       // Thread.sleep(3000);
        clickOnSaveButtonForFinanceAndLegalSection();

    }

    public WebElement channelDropdownMenu() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-interaction-channel-c-field"));

    }

    public void clickOnChannelDropdownMenu() throws InterruptedException {
       // Thread.sleep(1000);
        channelDropdownMenu();
    }

    public WebElement phoneDropdownOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//option[@label='Phone']"));
    }

    public void clickOnPhoneDropdownOption() throws InterruptedException {
       // Thread.sleep(500);
        phoneDropdownOption().click();
    }

    public WebElement casePriorityField() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-priority-field"));
    }

    public void clickOnCasePriorityField() throws InterruptedException {
       // Thread.sleep(1000);
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
       // Thread.sleep(1000);
        solutionField().click();

    }

    public WebElement describeTheSolutionDropdown() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'DESCRIBE THE SOLUTION')]"));
    }

    public void clickOnDescribeTheSolutionDropdown() throws InterruptedException {
       // Thread.sleep(300);
        describeTheSolutionDropdown().click();
    }


    public WebElement describeTheQuestionDropdown() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'DESCRIBE THE QUESTION')]"));
    }

    public void clickOnDescribeTheQuestionDropdown() throws InterruptedException {
       // Thread.sleep(300);
        describeTheQuestionDropdown().click();
    }

    public void setAllForNewCaseForCustomer(String descriptiontext, String solutionText) throws InterruptedException {
       //// Thread.sleep(2000);
        clickOnSubjectDropdownMenu();
        ////Thread.sleep(500);
        clickOnSettlementInvoiceDropdownOption();
        clickOnChannelDropdownMenu();
        ////Thread.sleep(500);
        clickOnPhoneDropdownOption();
        ////Thread.sleep(500);
        clickOnCasePriorityField();
        ////Thread.sleep(500);
        clickOnHighDropdownOption();
        ////Thread.sleep(500);


        clickOnDescriptionField();
        ////Thread.sleep(500);
        descriptionField().sendKeys(descriptiontext);
        ////Thread.sleep(1000);

        clickOnDescribeTheQuestionDropdown();
        ////Thread.sleep(500);

        clickOnSolutionField();
        ////Thread.sleep(500);
        solutionField().sendKeys(solutionText);
        ////Thread.sleep(1000);
        clickOnDescribeTheSolutionDropdown();


        ////Thread.sleep(500);
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
       // Thread.sleep(4000);
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
       // Thread.sleep(2000);
        seleniumDriver.findElementWhenVisible(By.id("market-transactions-dashboard-link")).click();
    }

    public void clickOnFirstTransaction(String contractID) throws InterruptedException {
       // Thread.sleep(10000);
        seleniumDriver.findElementWhenVisible(By.xpath("(//h6[contains(text(),'"+contractID+"')]/preceding-sibling::*[1])[1]"))
            .click();
    }

    public void refreshMarketTransactions() throws InterruptedException {
       // Thread.sleep(10000);
        seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'REFRESH MARKET TRANSACTIONS')]")).click();
       // Thread.sleep(4000);
    }
}
