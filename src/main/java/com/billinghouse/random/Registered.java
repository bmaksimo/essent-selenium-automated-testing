package com.billinghouse.random;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Registered {

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

    public Registered.Builder withAge(Long age) {
      this.age = age;
      return this;
    }

    public Registered.Builder withDate(String date) {
      this.date = date;
      return this;
    }

    public Registered build() {
      Registered registered = new Registered();
      registered.age = age;
      registered.date = date;
      return registered;
    }
  }
}
