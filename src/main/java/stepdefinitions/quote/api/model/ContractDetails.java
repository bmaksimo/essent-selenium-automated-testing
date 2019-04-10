package stepdefinitions.quote.api.model;

public class ContractDetails {

    private String contractRecordId;
    private String contractNumber;
    private String aosProductsId;
    private String jbillingId;
    private String contractStartDate;
    private String contractEndDate;

    public String getContractRecordId() {
        return contractRecordId;
    }
    public void setContractRecordId(String contractRecordId) {
        this.contractRecordId = contractRecordId;
    }
    public String getContractNumber() {
        return contractNumber;
    }
    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }
    public String getAosProductsId() {
        return aosProductsId;
    }
    public void setAosProductsId(String aosProductsId) {
        this.aosProductsId = aosProductsId;
    }
    public String getjBillingId() {
        return jbillingId;
    }
    public void setjBillingId(String jbillingId) {
        this.jbillingId = jbillingId;
    }
    public String getContractStartDate(){return contractStartDate;}
    public void setContractStartDate(String contractStartDate){this.contractStartDate = contractStartDate;}
    public String getContractEndDate(){return contractEndDate;}
    public void setContractEndDate(){this.contractEndDate = contractEndDate;}

}
