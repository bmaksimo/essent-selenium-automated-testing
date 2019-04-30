package stepdefinitions.quote.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuoteDetailsDTO {

  @JsonProperty("action")
  private ActionDTO action;

  @JsonProperty("model")
  private ModelDTO model;

  public ActionDTO getAction() {
    return action;
  }

  public void setAction(ActionDTO action) {
    this.action = action;
  }

  public ModelDTO getModel() {
    return model;
  }

  public void setModel(ModelDTO model) {
    this.model = model;
  }
}
