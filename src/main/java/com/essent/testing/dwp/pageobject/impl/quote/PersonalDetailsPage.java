package com.essent.testing.dwp.pageobject.impl.quote;

import com.billinghouse.random.RandomUser;
import com.billinghouse.test_automation.util.gherkin.DateTimeFormatUtil;
import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.selenium.SeleniumDriver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.html5.WebStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.essent.testing.dwp.autocrat.element.quote.B2CQuoteElements.*;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.INPUT;
import static com.essent.testing.selenium.helper.dwp.LocalStorage.fetchPreferredLanguage;

public class PersonalDetailsPage extends CreateQuoteGuidedStep {

    private RandomUser    customer;

    public void setRandomUser(RandomUser randomUser) {
        this.customer = randomUser;
    }

    public PersonalDetailsPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    @Override
    public boolean fillInFormData() {
        return fillInCustomerDetails();
    }

    public boolean fillInCustomerDetails() {
        Map<String, String[][]> titles = new HashMap<>();
        String[][] en_Titles = new String[][]{{"male", "Mr."}, {"female", "Ms."}};
        String[][] nl_Titles = new String[][]{{"male", "Meneer"}, {"female", "Mevr."}};
        titles.put("en_BE", en_Titles);
        titles.put("nl_BE", nl_Titles);
        String[][] salutations = titles.get(fetchPreferredLanguage((WebStorage) seleniumDriver.getDriver()));
        Map<String, String> titleMap = Stream.of(salutations).collect(Collectors.toMap(d -> d[0], d -> d[1]));

        String firstName = StringUtils.capitalize(customer.getName().getFirst());
        String lastName = StringUtils.capitalize(customer.getName().getLast());
        String salutation = titleMap.get(customer.getGender());
        String birthDate = DateTimeFormatUtil.getBirthDate(customer.getDob().getDate());
        String mobilePhone = "+3168" + (int) (Math.floor(Math.random() * 9000000) + 1000000);

        Model.Execution initializeFields = createExecutuin();
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
        return execute(initializeFields);
    }
}
