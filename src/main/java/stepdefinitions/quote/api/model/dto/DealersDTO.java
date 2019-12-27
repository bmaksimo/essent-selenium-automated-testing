package stepdefinitions.quote.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DealersDTO {

  @JsonProperty("key")
  private String key;

  @JsonProperty("label")
  private String label;

  public String getKey() {
    return key;
  }

  public void setKey(String addressType) {
    this.key = key;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }
}
