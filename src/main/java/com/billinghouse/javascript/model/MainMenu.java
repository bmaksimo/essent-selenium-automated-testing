
package com.billinghouse.javascript.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class MainMenu {

    @SerializedName("active")
    private String mActive;
    @SerializedName("class")
    private String mClass;
    @SerializedName("icon")
    private String mIcon;
    @SerializedName("link-to")
    private String mLinkTo;
    @SerializedName("name")
    private String mName;
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

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getLinkTo() {
        return mLinkTo;
    }

    public void setLinkTo(String linkTo) {
        mLinkTo = linkTo;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
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
