
package com.billinghouse.javascript.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SubMenu {

    @SerializedName("active")
    private String mActive;
    @SerializedName("class")
    private String mClass;
    @SerializedName("label")
    private String mLabel;
    @SerializedName("link-to")
    private String mLinkTo;
    @SerializedName("ng-repeat")
    private String mNgRepeat;
    @SerializedName("params")
    private String mParams;

    public String getActive() {
        return mActive;
    }

    public void setActive(String active) {
        mActive = active;
    }

    public String getClazz() {
        return mClass;
    }

    public void setClass(String clazz) {
        mClass = clazz;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public String getLinkTo() {
        return mLinkTo;
    }

    public void setLinkTo(String linkTo) {
        mLinkTo = linkTo;
    }

    public String getNgRepeat() {
        return mNgRepeat;
    }

    public void setNgRepeat(String ngRepeat) {
        mNgRepeat = ngRepeat;
    }

    public String getParams() {
        return mParams;
    }

    public void setParams(String params) {
        mParams = params;
    }

}
