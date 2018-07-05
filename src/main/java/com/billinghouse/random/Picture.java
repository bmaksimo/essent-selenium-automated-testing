
package com.billinghouse.random;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Picture {

    @Expose
    private String large;
    @Expose
    private String medium;
    @Expose
    private String thumbnail;

    public String getLarge() {
        return large;
    }

    public String getMedium() {
        return medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public static class Builder {

        private String large;
        private String medium;
        private String thumbnail;

        public Picture.Builder withLarge(String large) {
            this.large = large;
            return this;
        }

        public Picture.Builder withMedium(String medium) {
            this.medium = medium;
            return this;
        }

        public Picture.Builder withThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public Picture build() {
            Picture picture = new Picture();
            picture.large = large;
            picture.medium = medium;
            picture.thumbnail = thumbnail;
            return picture;
        }

    }

}
