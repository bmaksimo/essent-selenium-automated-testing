package com.billinghouse.random;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Id {

  @Expose private String name;
  @Expose private String value;

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public static class Builder {

    private String name;
    private String value;

    public Id.Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Id.Builder withValue(String value) {
      this.value = value;
      return this;
    }

    public Id build() {
      Id id = new Id();
      id.name = name;
      id.value = value;
      return id;
    }
  }
}
