
package com.billinghouse.javascript.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Option {

    @SerializedName("value")
    private String mValue;

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

}
