
package com.billinghouse.random;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Timezone {

    @Expose
    private String description;
    @Expose
    private String offset;

    public String getDescription() {
        return description;
    }

    public String getOffset() {
        return offset;
    }

    public static class Builder {

        private String description;
        private String offset;

        public Timezone.Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Timezone.Builder withOffset(String offset) {
            this.offset = offset;
            return this;
        }

        public Timezone build() {
            Timezone timezone = new Timezone();
            timezone.description = description;
            timezone.offset = offset;
            return timezone;
        }

    }

}
