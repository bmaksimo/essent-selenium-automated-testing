package stepdefinitions.quote.api.model;

public class ContractsOnAccount {

    private String recordId;
    private int page;
    private String contractStartDate;
    private String contractEndDate;

    public String getRecordId() {
        return recordId;
    }
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public String getContractStartDate(){return contractStartDate;}
    public void setContractStartDate(){this.contractStartDate = contractStartDate;}
    public String getContractEndDate(){return contractEndDate;}
}
