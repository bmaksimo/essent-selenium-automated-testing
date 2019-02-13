package stepdefinitions.quote.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountsAddress {

        @JsonProperty("address_addition")
        private String addressAddition;
        @JsonProperty("address_buss")
        private String addressBuss;
        @JsonProperty("address_city")
        private String addressCity;
        @JsonProperty("address_country")
        private String addressCountry;
        @JsonProperty("address_number")
        private String addressNumber;
        @JsonProperty("address_postalcode")
        private String addressPostalcode;
        @JsonProperty("address_street")
        private String addressStreet;
        @JsonProperty("address_type")
        private String addressType;
        @JsonProperty("id")
        private String id;
        public String getAddressAddition() {
            return addressAddition;
        }
        public void setAddressAddition(String addressAddition) {
            this.addressAddition = addressAddition;
        }
        public String getAddressBuss() {
            return addressBuss;
        }
        public void setAddressBuss(String addressBuss) {
            this.addressBuss = addressBuss;
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
        public String getAddressNumber() {
            return addressNumber;
        }
        public void setAddressNumber(String addressNumber) {
            this.addressNumber = addressNumber;
        }
        public String getAddressPostalcode() {
            return addressPostalcode;
        }
        public void setAddressPostalcode(String addressPostalcode) {
            this.addressPostalcode = addressPostalcode;
        }
        public String getAddressStreet() {
            return addressStreet;
        }
        public void setAddressStreet(String addressStreet) {
            this.addressStreet = addressStreet;
        }
        public String getAddressType() {
            return addressType;
        }
        public void setAddressType(String addressType) {
            this.addressType = addressType;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }



}
