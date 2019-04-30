package com.billinghouse.random;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Dob {

  @Expose private Long age;
  @Expose private String date;

  public Long getAge() {
    return age;
  }

  public String getDate() {
    return date;
  }

  public static class Builder {

    private Long age;
    private String date;

    public Dob.Builder withAge(Long age) {
      this.age = age;
      return this;
    }

    public Dob.Builder withDate(String date) {
      this.date = date;
      return this;
    }

    public Dob build() {
      Dob dob = new Dob();
      dob.age = age;
      dob.date = date;
      return dob;
    }
  }
}
