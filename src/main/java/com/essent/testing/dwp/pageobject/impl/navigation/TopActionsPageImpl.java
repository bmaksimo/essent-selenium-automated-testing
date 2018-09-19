package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.navigation.TopActionsPage;
import com.essent.testing.selenium.SeleniumDriver;

import static com.essent.automation.autocrat.Action.CLICK;
import static com.essent.automation.autocrat.Action.SLEEP;
import static com.essent.testing.selenium.helper.autocrat.AutocratExecutionAdapter.newExecution;

public class TopActionsPageImpl extends Component implements TopActionsPage {

    private static final String BUTTON_ELEMENT                = "button.element.name";
    private static final String BUTTON_ELEMENT_QUERY_TEMPLATE = ".top-actions > a[name='${name}']";

    public TopActionsPageImpl(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    @Override
    public boolean executeTopAction(String name) {
        String query = createQuery(BUTTON_ELEMENT_QUERY_TEMPLATE, "name", name);
        Model.Execution execution = newExecution().element(BUTTON_ELEMENT, createElement("SELECTOR", query));
        execution
            .flow()
            .step(createStep(CLICK).element(BUTTON_ELEMENT))
            .step(createStep(SLEEP).sleepInMillis(2500));
        return execute(execution);
    }
}
