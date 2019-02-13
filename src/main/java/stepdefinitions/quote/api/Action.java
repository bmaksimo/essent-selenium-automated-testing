package stepdefinitions.quote.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Action {

    @JsonProperty("currentStep")
    private String currentStep;

    @JsonProperty("event")
    private String event;


    // Getter Methods

    public String getCurrentStep() {
     return currentStep;
    }

    public String getEvent() {
     return event;
    }

    // Setter Methods

    public void setCurrentStep(String currentStep) {
     this.currentStep = currentStep;
    }

    public void setEvent(String event) {
     this.event = event;
    }
}
