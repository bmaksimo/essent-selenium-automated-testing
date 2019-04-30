package stepdefinitions.quote.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayloadWrapperDTO {

  @JsonProperty("c0f94c2f-72e0-51b9-ce93-58930799ecf1")
  private PayloadDTO payload;

  public PayloadDTO getPayload() {
    return payload;
  }

  public void setPayload(PayloadDTO payload) {
    this.payload = payload;
  }
}
