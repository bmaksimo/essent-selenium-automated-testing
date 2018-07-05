
package com.billinghouse.random;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Location {

    @Expose
    private String bus;
    @Expose
    private String city;
    @Expose
    private Coordinates coordinates;
    @Expose
    private String houseNr;
    @Expose
    private String houseNrAdd;
    @Expose
    private String postcode;
    @Expose
    private String state;
    @Expose
    private String street;
    @Expose
    private Timezone timezone;

    public String getBus() {
        return bus;
    }

    public String getCity() {
        return city;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getHouseNr() {
        return houseNr;
    }

    public String getHouseNrAdd() {
        return houseNrAdd;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getState() {
        return state;
    }

    public String getStreet() {
        return street;
    }

    public Timezone getTimezone() {
        return timezone;
    }

    public static class Builder {

        private String bus;
        private String city;
        private Coordinates coordinates;
        private String houseNr;
        private String houseNrAdd;
        private String postcode;
        private String state;
        private String street;
        private Timezone timezone;

        public Location.Builder withBus(String bus) {
            this.bus = bus;
            return this;
        }

        public Location.Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Location.Builder withCoordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public Location.Builder withHouseNr(String houseNr) {
            this.houseNr = houseNr;
            return this;
        }

        public Location.Builder withHouseNrAdd(String houseNrAdd) {
            this.houseNrAdd = houseNrAdd;
            return this;
        }

        public Location.Builder withPostcode(String postcode) {
            this.postcode = postcode;
            return this;
        }

        public Location.Builder withState(String state) {
            this.state = state;
            return this;
        }

        public Location.Builder withStreet(String street) {
            this.street = street;
            return this;
        }

        public Location.Builder withTimezone(Timezone timezone) {
            this.timezone = timezone;
            return this;
        }

        public Location build() {
            Location location = new Location();
            location.bus = bus;
            location.city = city;
            location.coordinates = coordinates;
            location.houseNr = houseNr;
            location.houseNrAdd = houseNrAdd;
            location.postcode = postcode;
            location.state = state;
            location.street = street;
            location.timezone = timezone;
            return location;
        }

    }

}
