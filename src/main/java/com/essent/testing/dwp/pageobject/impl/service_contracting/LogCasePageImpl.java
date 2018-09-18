package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.pageobject.Form;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.service_contracting.LogCasePage;
import com.essent.testing.selenium.SeleniumDriver;
import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.StringUtils;
import org.awaitility.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.essent.automation.autocrat.Action.*;
import static com.essent.testing.selenium.helper.autocrat.AutocratExecutionAdapter.newExecution;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.awaitility.Duration.FIVE_HUNDRED_MILLISECONDS;
import static org.awaitility.Duration.ONE_SECOND;

public class LogCasePageImpl extends Component implements Form, LogCasePage {


    String BUTTON_SELECTOR_TEMPLATE                  = "//div[@class='form__footer']/button[normalize-space(text()) = '${text}']";

    private String subject;

    private String description;

    private String solution;

    private String priority;

    public LogCasePageImpl(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    @Override
    public boolean fillInFormData() {

        String specificationsSubjectElement = "element.specifications.subject";
        String specificationsPriorityElement = "element.specifications.priority";

        String questionQuestionElement =  "element.question.question";
        String solutionSolutionElement =  "element.solution.solution";

        Model.Execution execution = newExecution().element(specificationsSubjectElement, new Model.Element()
             .search("SELECTOR")
             .query("#cases-name-field"));

        execution.element(specificationsPriorityElement, new Model.Element()
            .search("SELECTOR")
            .query("#cases-priority-field"));


        execution.element(questionQuestionElement, new Model.Element()
            .search("SELECTOR")
            .query("#cases-description-field"));



        execution.element(solutionSolutionElement, new Model.Element()
            .search("SELECTOR")
            .query("#cases-resolution-field"));

        execution.flow()
            .step(((new Model.Step().action(SELECT).element(specificationsSubjectElement))).value(subject))
            .step(new Model.Step().action(Action.SLEEP).sleepInMillis(500))

            .step(((new Model.Step().action(SELECT).element(specificationsPriorityElement))).value(priority))
            .step(new Model.Step().action(Action.SLEEP).sleepInMillis(500))

            .step((new Model.Step().action(CLICK).element(questionQuestionElement)))
            .step(new Model.Step().action(Action.SLEEP).sleepInMillis(500))
            .step(((new Model.Step().action(TYPING).element(questionQuestionElement))).value(description))
            .step(new Model.Step().action(Action.SLEEP).sleepInMillis(500))
            .step((new Model.Step().action(ACCESS).element(solutionSolutionElement).callback(
                srollToView()
            )))
            .step(new Model.Step().action(Action.SLEEP).sleepInMillis(500))
            .step(((new Model.Step().action(TYPING).element(solutionSolutionElement))).value(solution))
            .step(new Model.Step().action(Action.SLEEP).sleepInMillis(500));

        return execute(execution);
    }

    @Override
    public void setSubjectSelection(String subject) {
        this.subject = subject;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setSolution(String solution) {
        this.solution = solution;
    }

    @Override
    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public void cancel() {
        throw new CucumberException("Not yet implemented.");
    }

    @Override
    public void save(String buttonText) {
        String query = createQuery(BUTTON_SELECTOR_TEMPLATE, "text", buttonText);
        WebElement button = seleniumDriver.findElementOrNull(By.xpath(query));
        if (button == null) {
            return;
        }
        given().await()
            .pollInterval(FIVE_HUNDRED_MILLISECONDS)
            .pollDelay(ONE_SECOND)
            .atMost(new Duration(30, SECONDS)).until(() -> isEnabled(query));
        button = seleniumDriver.findElementOrNull(By.xpath(query));
        button.click();
    }

    private Boolean isEnabled(String query) {
        WebElement button = seleniumDriver.findElementOrNull(By.xpath(query));
        String disabled = button.getAttribute("disabled");
        return StringUtils.isEmpty(disabled) || !StringUtils.equals(disabled, "disabled");
    }
}
