package stepdefinitions.quote.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountsAsQuotesDTO {

    @JsonProperty("address_type")
    private String addressType;
    @JsonProperty("address_postalcode")
    private String addressPostalcode;
    @JsonProperty("address_region")
    private String addressRegion;
    @JsonProperty("address_street")
    private String addressStreet;
    @JsonProperty("address_country")
    private String addressCountry;
    @JsonProperty("address_addition")
    private String addressAddition;
    @JsonProperty("address_bus")
    private String addressBus;
    @JsonProperty("road65_street_id")
    private String road65StreetId;
    @JsonProperty("id")
    private String id;
    @JsonProperty("address_number")
    private String addressNumber;
    @JsonProperty("address_city")
    private String addressCity;

    public String getAddressType() {
        return addressType;
    }
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
    public String getAddressPostalcode() {
        return addressPostalcode;
    }
    public void setAddressPostalcode(String addressPostalcode) {
        this.addressPostalcode = addressPostalcode;
    }
    public String getAddressRegion() {
        return addressRegion;
    }
    public void setAddressRegion(String addressRegion) {
        this.addressRegion = addressRegion;
    }
    public String getAddressStreet() {
        return addressStreet;
    }
    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }
    public String getAddressCountry() {
        return addressCountry;
    }
    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
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
    public String getRoad65StreetId() {
        return road65StreetId;
    }
    public void setRoad65StreetId(String road65StreetId) {
        this.road65StreetId = road65StreetId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAddressNumber() {
        return addressNumber;
    }
    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }
    public String getAddressCity() {
        return addressCity;
    }
    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

}
