package stepdefinitions.quote.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ModelSignatureDTO {

    @JsonProperty("valid_until_c")
    private String validUntil;
    @JsonProperty("stage")
    private String stage;
    @JsonProperty("signature_received_date_c")
    private String signatureReceivedDate;
    @JsonProperty("baseModule")
    private String baseModule;
    @JsonProperty("dwp|create_task")
    private String createTask;
    @JsonProperty("pricing_date_c")
    private String pricing_Date;
    @JsonProperty("id")
    private String id;
    @JsonProperty("recordTypeOfRecordId")
    private String recordTypeOfRecordId;
    @JsonProperty("assigned_user_id")
    private List<UserSignatureDTO> assignedUserId = new ArrayList<UserSignatureDTO>();
    @JsonProperty("accounts|id")
    private String accountsId;

    public String getValidUntil() {
        return validUntil;
    }
    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }
    public String getStage() {
        return stage;
    }
    public void setStage(String stage) {
        this.stage = stage;
    }
    public String getSignatureReceivedDate() {
        return signatureReceivedDate;
    }
    public void setSignatureReceivedDate(String signatureReceivedDate) {
        this.signatureReceivedDate = signatureReceivedDate;
    }
    public String getBaseModule() {
        return baseModule;
    }
    public void setBaseModule(String baseModule) {
        this.baseModule = baseModule;
    }
    public String getCreateTask() {
        return createTask;
    }
    public void setCreateTask(String createTask) {
        this.createTask = createTask;
    }
    public String getPricing_Date() {
        return pricing_Date;
    }
    public void setPricing_Date(String pricing_Date) {
        this.pricing_Date = pricing_Date;
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
    public List<UserSignatureDTO> getAssignedUserId() {
        return assignedUserId;
    }
    public void setAssignedUserId(List<UserSignatureDTO> assignedUserId) {
        this.assignedUserId = assignedUserId;
    }
    public String getAccountsId() {
        return accountsId;
    }
    public void setAccountsId(String accountsId) {
        this.accountsId = accountsId;
    }

}
