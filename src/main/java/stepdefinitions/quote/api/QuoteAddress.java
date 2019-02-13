package stepdefinitions.quote.api;

public class QuoteAddress {

    private Integer id;
    private QuoteAddressType addressType;
    private String addressNumber;
    private String addressAddition;
    private String addressBus;
    private String addressPostalCode;
    private String addressCity;
    private String addressCountry;
    private String road65StreetId;

    public QuoteAddress(Integer id, QuoteAddressType addressType, String addressNumber, String addressAddition,
                        String addressBuss, String addressPostalCode, String addressCity, String addressCountry,
                        String road65StreetId) {
        this.id = id;
        this.addressType = addressType;
        this.addressNumber = addressNumber;
        this.addressAddition = addressAddition;
        this.addressBus = addressBuss;
        this.addressPostalCode = addressPostalCode;
        this.addressCity = addressCity;
        this.addressCountry = addressCountry;
        this.road65StreetId = road65StreetId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public QuoteAddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(QuoteAddressType addressType) {
        this.addressType = addressType;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getAddressAddition() {
        return addressAddition;
    }

    public void setAddressAddition(String addressAddition) {
        this.addressAddition = addressAddition;
    }

    public String getAddressBus() {
        return addressBus;
    }

    public void setAddressBus(String addressBus) {
        this.addressBus = addressBus;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getRoad65StreetId() {
        return road65StreetId;
    }

    public void setRoad65StreetId(String road65StreetId) {
        this.road65StreetId = road65StreetId;
    }

}
