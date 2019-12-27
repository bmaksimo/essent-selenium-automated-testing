package stepdefinitions.quote.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignContractDTO {

  @JsonProperty("action")
  private ContractActionDTO contractActionDTO;

  @JsonProperty("model")
  private ContractModelDTO contractModeDTO;

  public ContractActionDTO getContractActionDTO() {
    return contractActionDTO;
  }

  public void setContractActionDTO(ContractActionDTO contractActionDTO) {
    this.contractActionDTO = contractActionDTO;
  }

  public ContractModelDTO getContractModeDTO() {
    return contractModeDTO;
  }

  public void setContractModeDTO(ContractModelDTO contractModeDTO) {
    this.contractModeDTO = contractModeDTO;
  }
}
