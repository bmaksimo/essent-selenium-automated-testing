package com.essent.testing.dwp.pageobject.impl.servicecontracting;

import com.essent.automation.autocrat.Model;
import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.Form;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.servicecontracting.LogCasePage;
import org.apache.commons.lang3.StringUtils;
import org.awaitility.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.Callable;

import static com.essent.automation.autocrat.Action.*;
import static com.essent.testing.selenium.helper.autocrat.AutocratExecutionAdapter.newExecution;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.awaitility.Duration.FIVE_HUNDRED_MILLISECONDS;
import static org.awaitility.Duration.TWO_SECONDS;

public class LogCasePageImpl extends Component implements Form, LogCasePage {

    private static final String BUTTON_SELECTOR_TEMPLATE = "//div[@class='form__footer']/button[normalize-space(text()) = '${text}']";
    private String subject;
    private String description;
    private String solution;
    private String priority;

    @Override
    public boolean fillInFormData() {
        seleniumDriver.waitForRequestsToFinish();
        String specificationsSubjectElement = "element.specifications.subject";
        String specificationsPriorityElement = "element.specifications.priority";

        String questionQuestionElement =  "element.question.question";
        String solutionSolutionElement =  "element.solution.solution";

        Model.Execution execution = newExecution();
        execution
            .element(specificationsSubjectElement, createElement("SELECTOR", "#cases-name-field"))
            .element(specificationsPriorityElement, createElement("SELECTOR", "#cases-priority-field"))
            .element(questionQuestionElement, createElement("SELECTOR", "#cases-description-field"))
            .element(solutionSolutionElement, createElement("SELECTOR", "#cases-resolution-field"))

            .flow()
            .step(createStep(SELECT).element(specificationsSubjectElement).value(subject).timeoutInSeconds(4))
            .step(createStep(SELECT).element(specificationsPriorityElement).value(priority).timeoutInSeconds(4))
            .step(createStep(CLICK).element(questionQuestionElement).timeoutInSeconds(4))
            .step(createStep(TYPING).element(questionQuestionElement).value(description).timeoutInSeconds(4))
            .step(createStep(ACCESS).element(solutionSolutionElement).callback(scrollToView()).timeoutInSeconds(4))
            .step(createStep(TYPING).element(solutionSolutionElement).value(solution));
        return execute(execution);
    }

    @Override
    public void setSubjectSelection(String subject) {
        seleniumDriver.waitForRequestsToFinish();
        this.subject = subject;
    }

    @Override
    public void setDescription(String description) {
        seleniumDriver.waitForRequestsToFinish();
        this.description = description;
    }

    @Override
    public void setSolution(String solution) {
        seleniumDriver.waitForRequestsToFinish();
        this.solution = solution;
    }

    @Override
    public void setPriority(String priority) {
        seleniumDriver.waitForRequestsToFinish();
        this.priority = priority;
    }

    @Override
    public void save(String buttonText) {
        seleniumDriver.waitForRequestsToFinish();
        String query = createQuery(BUTTON_SELECTOR_TEMPLATE, "text", buttonText);
        findAndClickButton(query);
    }

    private void findAndClickButton(String query) {
        seleniumDriver.waitForRequestsToFinish();
        waitUntil(FIVE_HUNDRED_MILLISECONDS, TWO_SECONDS, () -> isEnabled(query));
        seleniumDriver.findElementWhenPresent(By.xpath(query));
        waitUntil(FIVE_HUNDRED_MILLISECONDS, TWO_SECONDS, () -> isEnabled(query));
        WebElement button = seleniumDriver.findElementWhenPresent(By.xpath(query));
        Sleeper.sleepTightInSeconds(2);
        button.click();
    }

    private void waitUntil(Duration pollInterval, Duration pollDelay, Callable<Boolean> findElement) {
        given().await()
            .pollInterval(pollInterval)
            .pollDelay(pollDelay)
            .atMost(new Duration(30, SECONDS)).until(findElement);
    }

    private Boolean isEnabled(String query) {
        seleniumDriver.waitForRequestsToFinish();
        WebElement button = seleniumDriver.findElementWhenPresent(By.xpath(query));
        String disabled = button.getAttribute("disabled");
        return StringUtils.isEmpty(disabled) || !StringUtils.equals(disabled, "disabled");
    }
}
