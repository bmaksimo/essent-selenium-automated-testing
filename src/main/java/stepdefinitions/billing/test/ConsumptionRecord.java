package stepdefinitions.billing.test;

public class ConsumptionRecord {

    private String ean;
    private String type;
    private String meterType;
    private String startDate;
    private String endDate;

    public ConsumptionRecord(String ean, String type, String meterType, String startDate, String endDate) {
        this.ean = ean;
        this.type = type;
        this.meterType = meterType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getEan() {
        return ean;
    }

    public String getType() {
        return type;
    }

    public String getMeterType() {
        return meterType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "ConsumptionRecord{" + "ean=" + ean + ", type=" + type + ", meterType=" + meterType
            + ", startDate=" + startDate + ", endDate=" + endDate + "}";
    }
}
