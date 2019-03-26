package stepdefinitions.quote.api.model;

public class GetOrderDetailsRequest
{
    private String billingId;
    private String ean;
    private String settlementStatus;
    private String askDate;
    private String includeSettlement;

    public String getBillingId ()
    {
        return billingId;
    }

    public void setBillingId (String billingId)
    {
        this.billingId = billingId;
    }

    public String getEan ()
    {
        return ean;
    }

    public void setEan (String ean)
    {
        this.ean = ean;
    }

    public String getSettlementStatus ()
    {
        return settlementStatus;
    }

    public void setSettlementStatus (String settlementStatus)
    {
        this.settlementStatus = settlementStatus;
    }

    public String getAskDate ()
    {
        return askDate;
    }

    public void setAskDate (String askDate)
    {
        this.askDate = askDate;
    }

    public String getIncludeSettlement ()
    {
        return includeSettlement;
    }

    public void setIncludeSettlement (String includeSettlement)
    {
        this.includeSettlement = includeSettlement;
    }

}
