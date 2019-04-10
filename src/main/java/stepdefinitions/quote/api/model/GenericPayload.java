package stepdefinitions.quote.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenericPayload {

    @JsonProperty("model")
    private Model model;

    public Model getModel() {
        return model;
    }
    public void setModel(Model model) {
        this.model = model;
    }

    public static class Model {}


}
