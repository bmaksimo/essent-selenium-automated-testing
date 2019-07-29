package stepdefinitions.billing.test;

public enum ConsumptionMeterType {
    LOW_HIGH("LOW-HIGH"),
    LOW_HIGH_NIGHT_EXCLUSIVE("LOW-HIGH-NIGHT_EXCLUSIVE"),
    TOTAL_HOUR_NIGHT_EXCLUSIVE("TOTAL_HOUR,NIGHT_EXCLUSIVE"),
    TOTAL_HOUR("TOTAL_HOUR"),
    LOW_HIGH_TOTAL_HOUR("LOW-HIGH-TOTAL_HOUR");

    String value;

    ConsumptionMeterType(String value) {
        this.value = value;
    }
}
