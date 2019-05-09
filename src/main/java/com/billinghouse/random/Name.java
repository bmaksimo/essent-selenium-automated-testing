package com.billinghouse.random;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Name {

    @Expose
    private String first;
    @Expose
    private String last;
    @Expose
    private String title;

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getTitle() {
        return title;
    }

    public static class Builder {

        private String first;
        private String last;
        private String title;

        public Name.Builder withFirst(String first) {
            this.first = first;
            return this;
        }

        public Name.Builder withLast(String last) {
            this.last = last;
            return this;
        }

        public Name.Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Name build() {
            Name name = new Name();
            name.first = first;
            name.last = last;
            name.title = title;
            return name;
        }

    }

    @Override
    public String toString() {
        return "Name{" +
            "first='" + first + '\'' +
            ", last='" + last + '\'' +
            ", title='" + title + '\'' +
            '}';
    }
}
