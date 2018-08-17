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
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.tables.CustomerAddress;

import static com.essent.testing.dwp.DwpTimingParameters.*;
import static com.essent.testing.dwp.elements.BasicElements.NEXT_BUTTON;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.TITLE_SELECTOR_TEMPLATE;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.VIEW_SELECTOR;
import static com.essent.testing.dwp.quote.elements.B2CQuoteElements.*;
import static org.junit.Assert.fail;

public class CustomerAddressView extends Component implements CreateQuoteView, CreateQuoteStepView {


    private CustomerAddress      address;

    public void setCustometAddress(CustomerAddress customerAddress) {
        address = customerAddress;
    }

    public CustomerAddressView(SeleniumDriver seleniumDriver) {
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
            fail("Personal Details view was not found.");
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
    public boolean fillInFormData() {
        fillInCustomerAddress();
        return true;
    }


    public boolean fillInCustomerAddress() {

        String street = address.getStreet();
        String houseNr = Integer.toString(address.getHouseNr());
        String houseNrAdd = address.getHouseNrAdd();
        String bus = address.getBus();
        String postcode = address.getPostalCode();
        String city = address.getCity();
        String country = address.getCountry();

        Model.Execution initializeAddress = newExecution();
        initializeAddress.
            element(DELIVERY_ADDR_STREET.element()).
            element(DELIVERY_ADDR_STREET_SUGGESTION.element()).
            element(DELIVERY_ADDR_HOUSE_NR.element()).
            element(DELIVERY_ADDR_HOUSE_ADD.element()).
            element(DELIVERY_ADDR_BUS.element()).
            element(DELIVERY_ADDR_ZIPCODE.element()).
            element(DELIVERY_ADDR_CITY.element()).
            element(DELIVERY_ADDR_COUNTRY.element()).
            element(COPY_ADDRESS_CONNECTION_TO_BILLING.element()).
            step(createStep(Action.ACCESS).element(COPY_ADDRESS_CONNECTION_TO_BILLING.name()).requireDisplayed(false).callback(new HideIconOverlays())).
            step(createStep(Action.CLICK).element(COPY_ADDRESS_CONNECTION_TO_BILLING.name()).requireDisplayed(false), INPUT.getSleepInMillis());

        initializeAddress.
            step(createStep(Action.TYPING).element(DELIVERY_ADDR_STREET.name()).value(street), INPUT.getSleepInMillis()).
            step(createStep(Action.ACCESS).element(DELIVERY_ADDR_STREET_SUGGESTION.name()).requireDisplayed(false).callback(new HideAddressSuggestion())).
            step(createStep(Action.TYPING).timeoutInSeconds(3).element(DELIVERY_ADDR_HOUSE_NR.name()).value(houseNr), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(DELIVERY_ADDR_HOUSE_ADD.name()).value(houseNrAdd), INPUT.getSleepInMillis());
        if (StringUtils.isNotEmpty(bus)) {
            initializeAddress.step(createStep(Action.TYPING).element(DELIVERY_ADDR_BUS.name()).value(bus), INPUT.getSleepInMillis());
        }
        initializeAddress.step(createStep(Action.TYPING).element(DELIVERY_ADDR_ZIPCODE.name()).value(postcode), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(DELIVERY_ADDR_CITY.name()).value(city), INPUT.getSleepInMillis());
        if (StringUtils.isNotEmpty(country)) {
            initializeAddress.step(createStep(Action.SELECT).element(DELIVERY_ADDR_COUNTRY.name()).value(country));
        }

        if(!execute(initializeAddress)) {
            fail("Customer Address fields were not initialized");
        }

        Model.Execution copyAddress = newExecution()
            .element(NEXT_BUTTON.element())
            .element(SELECT_PACKAGE_ACTIVE.element())
            .element(COPY_ADDRESS_CONNECTION_TO_BILLING.element())
            .step(createStep(Action.SLEEP).sleepInMillis(3000))
            .step(createStep(Action.CLICK).element(COPY_ADDRESS_CONNECTION_TO_BILLING.name()).requireDisplayed(false), TOGGLE_CHECKBOX.getSleepInMillis())
            .step(createStep(Action.CLICK).timeoutInSeconds(NEXT_STEP.getWaitInSeconds()).element(NEXT_BUTTON.name()))
            .step(createStep(Action.REQUIRE).timeoutInSeconds(WAIT_NEXT_PAGE.getWaitInSeconds()).element(SELECT_PACKAGE_ACTIVE.name()), NEXT_STEP.getSleepInMillis());
        if(!execute(copyAddress)) {
            fail("Copy Address switch was not initialized");
        }
        Model.Execution discardSimilarCustomer = newExecution()
            .element(SIMILAR_CUSTOMER_ALERT.element())
            .step(createStep(Action.CLICK).element(SIMILAR_CUSTOMER_ALERT.name()).timeoutInSeconds(1.5), INPUT.getSleepInMillis());
        execute(discardSimilarCustomer);
        return true;
    }
}
