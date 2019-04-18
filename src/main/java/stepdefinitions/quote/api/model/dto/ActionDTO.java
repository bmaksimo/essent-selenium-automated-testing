package stepdefinitions.quote.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionDTO {

    @JsonProperty("currentStep")
    private String currentStep;
    @JsonProperty("event")
    private String event;

    public String getCurrentStep() {
        return currentStep;
    }
    public String getEvent() {
        return event;
    }
    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }
    public void setEvent(String event) {
        this.event = event;
    }
}
