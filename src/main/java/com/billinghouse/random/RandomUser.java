package com.billinghouse.random;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RandomUser {

    @Expose
    private String cell;
    @Expose
    private Dob dob;
    @Expose
    private String email;
    @Expose
    private String gender;
    @Expose
    private Id id;
    @Expose
    private Location location;
    @Expose
    private Login login;
    @Expose
    private Name name;
    @Expose
    private String nat;
    @Expose
    private String phone;
    @Expose
    private Picture picture;
    @Expose
    private Registered registered;

    public String getCell() {
        return cell;
    }

    public Dob getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public Id getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public Login getLogin() {
        return login;
    }

    public Name getName() {
        return name;
    }

    public String getNat() {
        return nat;
    }

    public String getPhone() {
        return phone;
    }

    public Picture getPicture() {
        return picture;
    }

    public Registered getRegistered() {
        return registered;
    }

    public static class Builder {

        private String cell;
        private Dob dob;
        private String email;
        private String gender;
        private Id id;
        private Location location;
        private Login login;
        private Name name;
        private String nat;
        private String phone;
        private Picture picture;
        private Registered registered;

        public RandomUser.Builder withCell(String cell) {
            this.cell = cell;
            return this;
        }

        public RandomUser.Builder withDob(Dob dob) {
            this.dob = dob;
            return this;
        }

        public RandomUser.Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public RandomUser.Builder withGender(String gender) {
            this.gender = gender;
            return this;
        }

        public RandomUser.Builder withId(Id id) {
            this.id = id;
            return this;
        }

        public RandomUser.Builder withLocation(Location location) {
            this.location = location;
            return this;
        }

        public RandomUser.Builder withLogin(Login login) {
            this.login = login;
            return this;
        }

        public RandomUser.Builder withName(Name name) {
            this.name = name;
            return this;
        }

        public RandomUser.Builder withNat(String nat) {
            this.nat = nat;
            return this;
        }

        public RandomUser.Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public RandomUser.Builder withPicture(Picture picture) {
            this.picture = picture;
            return this;
        }

        public RandomUser.Builder withRegistered(Registered registered) {
            this.registered = registered;
            return this;
        }

        public RandomUser build() {
            RandomUser randomUser = new RandomUser();
            randomUser.cell = cell;
            randomUser.dob = dob;
            randomUser.email = email;
            randomUser.gender = gender;
            randomUser.id = id;
            randomUser.location = location;
            randomUser.login = login;
            randomUser.name = name;
            randomUser.nat = nat;
            randomUser.phone = phone;
            randomUser.picture = picture;
            randomUser.registered = registered;
            return randomUser;
        }

        @Override
        public String toString() {
            return "RandomUser{" +
                "cell='" + cell + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", name=" + name +
                '}';
        }
    }

}
