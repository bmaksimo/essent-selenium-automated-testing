package stepdefinitions.quote.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuoteSignatureDTO {

  @JsonProperty("action")
  private ActionSignatureDTO action;

  @JsonProperty("model")
  private ModelSignatureDTO model;

  public ActionSignatureDTO getAction() {
    return action;
  }

  public void setAction(ActionSignatureDTO action) {
    this.action = action;
  }

  public ModelSignatureDTO getModel() {
    return model;
  }

  public void setModel(ModelSignatureDTO model) {
    this.model = model;
  }
}
