package com.essent.testing.dwp.pageobject.quote.impl;

import com.billinghouse.random.RandomUser;
import com.billinghouse.test_automation.util.gherkin.DateTimeFormatUtil;
import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.automation.core.WebDriverWait;
import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.dwp.pageobject.constant.Quote;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteStepView;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteView;
import com.essent.testing.selenium.SeleniumDriver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.essent.testing.dwp.DwpTimingParameters.INPUT;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.TITLE_SELECTOR_TEMPLATE;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.VIEW_SELECTOR;
import static com.essent.testing.dwp.quote.elements.B2CQuoteElements.*;
import static org.junit.Assert.fail;

public class CustomerDetailsView extends Component implements CreateQuoteView, CreateQuoteStepView {

    private RandomUser customerDetails;

    public void setRandomUser(RandomUser randomUser) {
        this.customerDetails = randomUser;
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
    @Override
    public CreateQuoteStepView next() {
        return new CustomerAddressView(seleniumDriver);
    }

    @Override
    public boolean fillInFormData() {
        return fillInCustomerDetails();
    }

    public boolean fillInCustomerDetails() {
        String[][] data = new String[][]{{"male", "Mr."}, {"female", "Ms."}};
        Map<String, String> titleMap = Stream.of(data).collect(Collectors.toMap(d -> d[0], d -> d[1]));

        String firstName = StringUtils.capitalize(customerDetails.getName().getFirst());
        String lastName = StringUtils.capitalize(customerDetails.getName().getLast());
        String salutation = titleMap.get(customerDetails.getGender());
        String birthDate = DateTimeFormatUtil.getBirthDate(customerDetails.getDob().getDate());
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
            step(createStep(Action.TYPING).element(EMAIL.name()).value(customerDetails.getEmail()), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(MOBILE_NR.name()).value(mobilePhone), INPUT.getSleepInMillis());
        if(!execute(initializeFields)) {
            fail("Customer Name fields were not initialized");
        }
        return true;
    }
}
