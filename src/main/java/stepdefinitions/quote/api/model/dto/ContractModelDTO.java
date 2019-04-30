package stepdefinitions.quote.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ContractModelDTO {

  @JsonProperty("dwp|signed_contract_docguid_c")
  private String signedcContractDocguid;

  @JsonProperty("dwp|alreadyContractedForDifferentClient")
  private boolean alreadyContractedForDifferentClient;

  @JsonProperty("baseModule")
  private String baseModule;

  @JsonProperty("signature_option_c")
  private String signatureOption;

  @JsonProperty("recordId")
  private String recordId;

  @JsonProperty("sign_date_c")
  private String signDate;

  @JsonProperty("stage")
  private String stage;

  @JsonProperty("do_auto_communication_c")
  private boolean doAutoCommunication;

  @JsonProperty("signed_contract_docguid_c")
  private List<String> signedContractDocguidC = new ArrayList<>();

  @JsonProperty("id")
  private String id;

  @JsonProperty("recordTypeOfRecordId")
  private String recordTypeOfRecordId;

  @JsonProperty("dwp|id")
  private String dwpId;

  @JsonProperty("complete")
  private boolean complete;

  @JsonProperty("dwp|recordType")
  private String recordType;

  @JsonProperty("dwp|alreadyContracted")
  private boolean dwpAlreadyContracted;

  public String getSignedcContractDocguid() {
    return signedcContractDocguid;
  }

  public void setSignedcContractDocguid(String signedcContractDocguid) {
    this.signedcContractDocguid = signedcContractDocguid;
  }

  public boolean getAlreadyContractedForDifferentClient() {
    return alreadyContractedForDifferentClient;
  }

  public void setAlreadyContractedForDifferentClient(boolean alreadyContractedForDifferentClient) {
    this.alreadyContractedForDifferentClient = alreadyContractedForDifferentClient;
  }

  public String getBaseModule() {
    return baseModule;
  }

  public void setBaseModule(String baseModule) {
    this.baseModule = baseModule;
  }

  public String getSignatureOption() {
    return signatureOption;
  }

  public void setSignatureOption(String signatureOption) {
    this.signatureOption = signatureOption;
  }

  public String getRecordId() {
    return recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

  public String getSignDate() {
    return signDate;
  }

  public void setSignDate(String signDate) {
    this.signDate = signDate;
  }

  public String getStage() {
    return stage;
  }

  public void setStage(String stage) {
    this.stage = stage;
  }

  public boolean getDoAutoCommunication() {
    return doAutoCommunication;
  }

  public void setDoAutoCommunication(boolean doAutoCommunication) {
    this.doAutoCommunication = doAutoCommunication;
  }

  public List<String> getSignedContractDocguidC() {
    return signedContractDocguidC;
  }

  public void setSignedContractDocguidC(List<String> signedContractDocguidC) {
    this.signedContractDocguidC = signedContractDocguidC;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRecordTypeOfRecordId() {
    return recordTypeOfRecordId;
  }

  public void setRecordTypeOfRecordId(String recordTypeOfRecordId) {
    this.recordTypeOfRecordId = recordTypeOfRecordId;
  }

  public String getDwpId() {
    return dwpId;
  }

  public void setDwpId(String dwpId) {
    this.dwpId = dwpId;
  }

  public boolean getComplete() {
    return complete;
  }

  public void setComplete(boolean complete) {
    this.complete = complete;
  }

  public String getRecordType() {
    return recordType;
  }

  public void setRecordType(String recordType) {
    this.recordType = recordType;
  }

  public boolean getDwpAlreadyContracted() {
    return dwpAlreadyContracted;
  }

  public void setDwpAlreadyContracted(boolean dwpAlreadyContracted) {
    this.dwpAlreadyContracted = dwpAlreadyContracted;
  }
}
