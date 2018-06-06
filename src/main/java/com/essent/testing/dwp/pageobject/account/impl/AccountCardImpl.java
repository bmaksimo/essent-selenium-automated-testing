package com.essent.testing.dwp.pageobject.account.impl;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.dwp.pageobject.account.AccountCard;
import com.essent.testing.selenium.SeleniumDriver;

import static com.essent.testing.dwp.DwpTimingParameters.SUBMIT_SUGNATURE;
import static com.essent.testing.dwp.account.elements.Elements.ACCOUNT_HEADER;

public class AccountCardImpl extends Component implements AccountCard {

    public AccountCardImpl(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    @Override
    public boolean containsFirstAndLastName(String firstName, String lastName) {
        Model.Execution execution = newExecution();
        execution
            .element(ACCOUNT_HEADER.element(new String[]{firstName, lastName}))
            .step(createStep(Action.REQUIRE).element(ACCOUNT_HEADER.name()).timeoutInSeconds(SUBMIT_SUGNATURE.getWaitInSeconds()));
        return execute(execution);
    }
}
