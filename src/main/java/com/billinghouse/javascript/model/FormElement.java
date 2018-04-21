
package com.billinghouse.javascript.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class FormElement {

    @SerializedName("id")
    private String mId;
    @SerializedName("label")
    private String mLabel;
    @SerializedName("options")
    private List<Option> mOptions;
    @SerializedName("type")
    private String mType;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public List<Option> getOptions() {
        return mOptions;
    }

    public void setOptions(List<Option> options) {
        mOptions = options;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
