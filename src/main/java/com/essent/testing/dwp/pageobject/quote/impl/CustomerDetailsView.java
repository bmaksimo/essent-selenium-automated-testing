package com.essent.testing.dwp.pageobject.quote.impl;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Autocrat;
import com.essent.automation.autocrat.Model;
import com.essent.automation.core.WebDriverWait;
import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.dwp.pageobject.constant.Quote;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteStepView;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteView;
import com.essent.testing.selenium.SeleniumDriver;
import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.tables.CustomerTable;

import static com.essent.testing.dwp.DwpDateFormats.TIMESTAMP;
import static com.essent.testing.dwp.DwpTimingParameters.*;
import static com.essent.testing.dwp.elements.DwpBasicElements.NEXT_BUTTON;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.TITLE_SELECTOR_TEMPLATE;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.VIEW_SELECTOR;
import static com.essent.testing.dwp.quote.elements.B2CQuoteElements.*;

public class CustomerDetailsView extends Component implements CreateQuoteView, CreateQuoteStepView {

    private CustomerTable customer;

    public void setCustomer(CustomerTable customer) {
        this.customer = customer;
    }

    public CustomerDetailsView(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(By.xpath(VIEW_SELECTOR.getQuery())), seleniumDriver);
        WebElement title = new WebDriverWait(seleniumDriver.getDriver(), 5).withoutException().until(
            driver -> {
                logger().info("STEP:");
                logger().info(" - ACTION: SELENIUM_FIND_ELEMENT");
                By by = By.xpath(TITLE_SELECTOR_TEMPLATE.getQuery().replace("${value}", Quote.PERSONAL_DETAILS.getText()));
                logger().info(" - BY: " + by.toString());
                return driver.findElement(by);
            }
        );
        if(title == null) {
            throw new CucumberException("Personal Details view was not found.");
        }
        logger().info(" - RESULT: " + "element: <" + title.getTagName() + " class='" + title.getAttribute("class") + "'>" + title.getText() + "/<" + title.getTagName()+ ">");
    }



    private class HideAddressSuggestion implements Model.Callback {
        @Override
        public void onAccess(Autocrat.ExecutionContext context, Model.Step step, WebElement value) {
            JavascriptExecutor jsExec = (JavascriptExecutor) context.driver;
            String setProperty = "style = 'display:none'";
            logger().info("Executing javascript " + setProperty + " on target element");
            jsExec.executeScript("arguments[0]." + setProperty, value);
        }
    }

    @Override
    public CreateQuoteStepView next() {
        return new SelectPackageAndFuelTypeView(seleniumDriver);
    }

    @Override
    public boolean fillInInputValues() {
        Model.Execution initializeFields = newExecution();
        String lastName = customer.getLastName().replace("${TIMESTAMP}", TIMESTAMP.print());
        initializeFields.
            element(COPY_ADDRESS_CONNECTION_TO_BILLING.element()).
            element(SALUTATION.element()).
            element(FIRST_NAME.element()).
            element(BIRTHDAY.element()).
            element(LAST_NAME.element()).
            element(EMAIL.element()).
            element(MOBILE_NR.element()).
            element(WORK_PHONE_NR.element()).
            element(DELIVERY_ADDR_STREET.element()).
            element(DELIVERY_ADDR_STREET_SUGGESTION.element()).
            element(DELIVERY_ADDR_HOUSE_NR.element()).
            element(DELIVERY_ADDR_HOUSE_ADD.element()).
            element(DELIVERY_ADDR_BUS.element()).
            element(DELIVERY_ADDR_ZIPCODE.element()).
            element(DELIVERY_ADDR_CITY.element()).
            element(DELIVERY_ADDR_COUNTRY.element()).
            step(createStep(Action.ACCESS).element(COPY_ADDRESS_CONNECTION_TO_BILLING.name()).requireDisplayed(false).callback(new HideIconOverlays())).
            step(createStep(Action.CLICK).element(COPY_ADDRESS_CONNECTION_TO_BILLING.name()).requireDisplayed(false), INPUT.getSleepInMillis()).
            step(createStep(Action.SELECT).element(SALUTATION.name()).value(customer.getGender()), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(FIRST_NAME.name()).value(customer.getFirstName()), INPUT.getSleepInMillis()).
            step(createStep(Action.CLICK).element(BIRTHDAY.name()), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(BIRTHDAY.name()).value(customer.getBirthDate()).timeoutInSeconds(4), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(LAST_NAME.name()).value(lastName), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(EMAIL.name()).value(customer.getEmail()), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(MOBILE_NR.name()).value(customer.getMobile()), INPUT.getSleepInMillis());
        if (StringUtils.isNotEmpty(customer.getPhoneNumber())) {
            initializeFields.step(createStep(Action.TYPING).element(WORK_PHONE_NR.name()).value(customer.getPhoneNumber()), INPUT.getSleepInMillis());
        }
        initializeFields.
            step(createStep(Action.TYPING).element(DELIVERY_ADDR_STREET.name()).value(customer.getStreet()), INPUT.getSleepInMillis()).
            step(createStep(Action.ACCESS).element(DELIVERY_ADDR_STREET_SUGGESTION.name()).requireDisplayed(false).callback(new HideAddressSuggestion())).
            step(createStep(Action.TYPING).timeoutInSeconds(3).element(DELIVERY_ADDR_HOUSE_NR.name()).value(String.valueOf(customer.getHouseNr())), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(DELIVERY_ADDR_HOUSE_ADD.name()).value(customer.getHouseNrAdd() != null ? customer.getHouseNrAdd() : ""), INPUT.getSleepInMillis());
        if (StringUtils.isNotEmpty(customer.getBus())) {
            initializeFields.step(createStep(Action.TYPING).element(DELIVERY_ADDR_BUS.name()).value(customer.getBus() != null ? customer.getBus() : ""), INPUT.getSleepInMillis());
        }
        initializeFields.step(createStep(Action.TYPING).element(DELIVERY_ADDR_ZIPCODE.name()).value(customer.getPostalCode()), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(DELIVERY_ADDR_CITY.name()).value(customer.getCity()), INPUT.getSleepInMillis());
        if (StringUtils.isNotEmpty(customer.getCountry())) {
            initializeFields.step(createStep(Action.SELECT).element(DELIVERY_ADDR_COUNTRY.name()).value(customer.getCountry()));
        }
        boolean fieldsInitialized = execute(initializeFields);

        Model.Execution submitCustomer = newExecution()
            .element(NEXT_BUTTON.element())
            .element(SELECT_PACKAGE_ACTIVE.element())
            .element(COPY_ADDRESS_CONNECTION_TO_BILLING.element())
            .step(createStep(Action.SLEEP).sleepInMillis(3000))
            .step(createStep(Action.CLICK).element(COPY_ADDRESS_CONNECTION_TO_BILLING.name()).requireDisplayed(false), TOGGLE_CHECKBOX.getSleepInMillis())
            .step(createStep(Action.CLICK).timeoutInSeconds(NEXT_STEP.getWaitInSeconds()).element(NEXT_BUTTON.name()))
            .step(createStep(Action.REQUIRE).timeoutInSeconds(WAIT_NEXT_PAGE.getWaitInSeconds()).element(SELECT_PACKAGE_ACTIVE.name()), NEXT_STEP.getSleepInMillis());
        boolean customerSubmitted;
        customerSubmitted = execute(submitCustomer);

        Model.Execution discardSimilarCustomer = newExecution()
            .element(SIMILAR_CUSTOMER_ALERT.element())
            .step(createStep(Action.CLICK).element(SIMILAR_CUSTOMER_ALERT.name()).timeoutInSeconds(1.5), INPUT.getSleepInMillis());
        return execute(discardSimilarCustomer);
    }
}
