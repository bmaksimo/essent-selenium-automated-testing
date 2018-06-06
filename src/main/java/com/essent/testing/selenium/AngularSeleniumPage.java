package com.essent.testing.selenium;

import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;

public class AngularSeleniumPage extends SeleniumPage {

    private int timeout = 15;

    NgWebDriver ngWebDriver = new NgWebDriver((JavascriptExecutor) driver);

    private final static Logger LOG = LoggerFactory.getLogger(AngularSeleniumPage.class);

    public AngularSeleniumPage(WebDriver driver){
        super(driver);
        getDriver().manage().timeouts().setScriptTimeout(120, SECONDS);
        getDriver().manage().timeouts().implicitlyWait(10, SECONDS);
    }

    protected void pressButton(final String id) {
        WebElement e = driver.findElement(By.id(id));
        e.click();
    }

    public void fillInValuesById(final HashMap<String, String> parameters) {
        for(Map.Entry<String, String> entry : parameters.entrySet()) {
            String id = entry.getKey();
            String value = entry.getValue();

            fillInValue(id,value);
        }
    }

    protected void fillTextInFieldWithNgModel(final String text, final String ngModel) {
        waitUntilAngularPageIsLoaded();
        WebElement element = driver.findElement(ByAngular.model(ngModel));
        element.clear();
        element.sendKeys(text);
        waitUntilAngularPageIsLoaded();
    }

    public void waitUntilAngularPageIsLoaded() {
        super.waitForReady();
        LOG.info("waiting for all angular requests to finish on page at url: " + getDriver().getCurrentUrl());
        ngWebDriver.waitForAngularRequestsToFinish();
        LOG.info("all angular requests finished! " + getDriver().getCurrentUrl());
    }


    protected void fillInValue(final String id, final String value){
        waitUntilAngularPageIsLoaded();
        if(id.startsWith("select")){
            selectValueFromDropDown(value, id);
        }else if (id.startsWith("input") || id.startsWith("datepicker")){
            fillTextInFieldWithId(value, id);
        }else{
            dealWithElement(id,value);
        }
    }

    protected void selectValueFromDropDown(final String value, final String id) {
        Select select = new Select(driver.findElement(By.xpath("//*[contains(@id, '" + id + "')]")));
        select.selectByValue(value);
    }

    protected void fillTextInFieldWithId(final String text, final String id) {
        WebElement element = driver.findElement(By.xpath("//*[contains(@id, '" + id + "')]"));
        element.clear();
        element.sendKeys(text);
    }

    protected void clickAngular(WebElement e) throws WebDriverException{
        waitUntilAngularPageIsLoaded();
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(e));
            e.click();
        }catch (WebDriverException wde){
            waitForReady();
            Actions actions = new Actions(driver);
            actions.moveToElement(e);
            actions.click();
            actions.build().perform();
        }
        waitUntilAngularPageIsLoaded();
    }

    public boolean elementExists(WebElement e) {
        waitUntilAngularPageIsLoaded();
        try {
            new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOf(e));
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public void clickJS(WebElement e){
        waitUntilAngularPageIsLoaded();
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", e);
        waitUntilAngularPageIsLoaded();
    }

    public void sendKeysAngular(WebElement e, String value){
        waitUntilAngularPageIsLoaded();
        e.clear();
        e.sendKeys(value);
        waitUntilAngularPageIsLoaded();
    }

    public void setCheckbox(WebElement e, String value)
    {
        waitUntilAngularPageIsLoaded();
        if (value.equalsIgnoreCase("true")){
            if (!e.isSelected()) {
                clickAngular(e);
            }
        }
        else{
            if (e.isSelected()) {
                clickAngular(e);
            }
        }
        waitUntilAngularPageIsLoaded();
    }

    public void verifyPageLoaded(String pageLoadedText) {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                waitUntilAngularPageIsLoaded();
                return d.getPageSource().contains(pageLoadedText);
            }
        });
    }

    /**
     * Helper method that outputs all IDs on the page you are currently on to system out.
     * Greatly speeds up looking up all IDs for a page.
     *
     */
    public void displayAllIdsOnPage(){
        List<WebElement> elements = driver.findElements(By.xpath("//*[@id]"));

        for(WebElement ele:elements)
        {
            String id = ele.getAttribute("id");
            if(!id.contains("hidden")) {
                // remove the formly
                id = id.replaceFirst("formly_[0-9]+_","");
                // remove the trailing number
                id = id.replaceAll("_[0-9]$","");
            }

        }
    }

}
