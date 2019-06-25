package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.automation.autocrat.Model;
import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.navigation.TopActionsPage;

import static com.essent.automation.autocrat.Action.CLICK;
import static com.essent.automation.autocrat.Action.SLEEP;
import static com.essent.testing.selenium.helper.autocrat.AutocratExecutionAdapter.newExecution;

public class TopActionsPageImpl extends Component implements TopActionsPage {

    private static final String BUTTON_ELEMENT = "button.element.name";
    private static final String BUTTON_ELEMENT_QUERY_TEMPLATE = ".top-actions > a[name='${name}']";

    @Override
    public boolean executeTopAction(String name) {
        String query = createQuery(BUTTON_ELEMENT_QUERY_TEMPLATE, "name", name);
        Model.Execution execution = newExecution().element(BUTTON_ELEMENT, createElement("SELECTOR", query));
        execution
            .flow()
            .step(createStep(CLICK).element(BUTTON_ELEMENT).requireDisplayed(false));
        return execute(execution);
    }

    @Override
    public boolean executeTopActionWithFixedWait(String name, int waitingTime) {
        Sleeper.sleepTightInSeconds(waitingTime);
        String query = createQuery(BUTTON_ELEMENT_QUERY_TEMPLATE, "name", name);
        Model.Execution execution = newExecution().element(BUTTON_ELEMENT, createElement("SELECTOR", query));
        execution
            .flow()
            .step(createStep(CLICK).element(BUTTON_ELEMENT).requireDisplayed(false))
            .step(createStep(SLEEP).sleepInMillis(2500));
        return executeNow(execution);
    }
}
