package com.billinghouse.random;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Coordinates {

    @Expose
    private String latitude;
    @Expose
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public static class Builder {

        private String latitude;
        private String longitude;

        public Coordinates.Builder withLatitude(String latitude) {
            this.latitude = latitude;
            return this;
        }

        public Coordinates.Builder withLongitude(String longitude) {
            this.longitude = longitude;
            return this;
        }

        public Coordinates build() {
            Coordinates coordinates = new Coordinates();
            coordinates.latitude = latitude;
            coordinates.longitude = longitude;
            return coordinates;
        }

    }

}
