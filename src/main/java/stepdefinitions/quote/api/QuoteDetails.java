package stepdefinitions.quote.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuoteDetails {

    @JsonProperty("action")
    private Action action;

    @JsonProperty("model")
    private Model model;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }


}
