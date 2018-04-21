
package com.billinghouse.javascript.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Data {

    @SerializedName("data")
    private Data mData;
    @SerializedName("formElements")
    private List<FormElement> mFormElements;
    @SerializedName("id")
    private String mId;
    @SerializedName("state")
    private State mState;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public List<FormElement> getFormElements() {
        return mFormElements;
    }

    public void setFormElements(List<FormElement> formElements) {
        mFormElements = formElements;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        mState = state;
    }

}
