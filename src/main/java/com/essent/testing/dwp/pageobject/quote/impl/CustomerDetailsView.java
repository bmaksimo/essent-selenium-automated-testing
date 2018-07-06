package com.essent.testing.dwp.pageobject.quote.impl;

import com.billinghouse.random.Location;
import com.billinghouse.random.RandomUser;
import com.billinghouse.test_automation.util.gherkin.DateTimeFormatUtil;
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
import stepdefinitions.dwp.tables.CustomerTable;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.essent.testing.dwp.DwpTimingParameters.*;
import static com.essent.testing.dwp.elements.BasicElements.NEXT_BUTTON;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.TITLE_SELECTOR_TEMPLATE;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.VIEW_SELECTOR;
import static com.essent.testing.dwp.quote.elements.B2CQuoteElements.*;
import static org.junit.Assert.fail;

public class CustomerDetailsView extends Component implements CreateQuoteView, CreateQuoteStepView {

    private RandomUser    customer;

    private Location      address;

    @Deprecated
    public void setCustomer(CustomerTable customer) {

    }

    public void setRandomUser(RandomUser randomUser) {
        this.customer = randomUser;
    }

    public void setRandomAddress(Location randomAddress) {
        address = randomAddress;
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
        fillInCustomerName();
        fillInCustomerAddress();
        return true;
    }

    private boolean fillInCustomerName() {
        String[][] data = new String[][]{{"male", "Mr."}, {"female", "Ms."}};
        Map<String, String> titleMap = Stream.of(data).collect(Collectors.toMap(d -> d[0], d -> d[1]));

        String firstName = StringUtils.capitalize(customer.getName().getFirst());
        String lastName = StringUtils.capitalize(customer.getName().getLast());
        String salutation = titleMap.get(customer.getGender());
        String birthDate = DateTimeFormatUtil.getBirthDate(customer.getDob().getDate());
        String mobilePhone = "+3168" + (int)(Math.floor(Math.random()*9000000) + 1000000);

        Model.Execution initializeFields = newExecution();
        initializeFields.
            element(COPY_ADDRESS_CONNECTION_TO_BILLING.element()).
            element(SALUTATION.element()).
            element(FIRST_NAME.element()).
            element(BIRTHDAY.element()).
            element(LAST_NAME.element()).
            element(EMAIL.element()).
            element(MOBILE_NR.element()).
            element(WORK_PHONE_NR.element()).
            step(createStep(Action.ACCESS).element(COPY_ADDRESS_CONNECTION_TO_BILLING.name()).requireDisplayed(false).callback(new HideIconOverlays())).
            step(createStep(Action.SELECT).element(SALUTATION.name()).value(salutation), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(FIRST_NAME.name()).value(firstName), INPUT.getSleepInMillis()).
            step(createStep(Action.CLICK).element(BIRTHDAY.name()), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(BIRTHDAY.name()).value(birthDate).timeoutInSeconds(4), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(LAST_NAME.name()).value(lastName), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(EMAIL.name()).value(customer.getEmail()), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(MOBILE_NR.name()).value(mobilePhone), INPUT.getSleepInMillis());
        if(!execute(initializeFields)) {
            fail("Customer Name fields were not initialized");
        }
        return true;
    }


    private boolean fillInCustomerAddress() {

        String street = address.getStreet();
        String houseNr = address.getHouseNr();
        String houseNrAdd = address.getHouseNrAdd();
        String bus = address.getBus();
        String postcode = address.getPostcode();
        String city = address.getCity();
        String country = address.getState();

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
