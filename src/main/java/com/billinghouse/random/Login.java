package com.billinghouse.random;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Login {

    @Expose
    private String md5;
    @Expose
    private String password;
    @Expose
    private String salt;
    @Expose
    private String sha1;
    @Expose
    private String sha256;
    @Expose
    private String username;
    @Expose
    private String uuid;

    public String getMd5() {
        return md5;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getSha1() {
        return sha1;
    }

    public String getSha256() {
        return sha256;
    }

    public String getUsername() {
        return username;
    }

    public String getUuid() {
        return uuid;
    }

    public static class Builder {

        private String md5;
        private String password;
        private String salt;
        private String sha1;
        private String sha256;
        private String username;
        private String uuid;

        public Login.Builder withMd5(String md5) {
            this.md5 = md5;
            return this;
        }

        public Login.Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Login.Builder withSalt(String salt) {
            this.salt = salt;
            return this;
        }

        public Login.Builder withSha1(String sha1) {
            this.sha1 = sha1;
            return this;
        }

        public Login.Builder withSha256(String sha256) {
            this.sha256 = sha256;
            return this;
        }

        public Login.Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Login.Builder withUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Login build() {
            Login login = new Login();
            login.md5 = md5;
            login.password = password;
            login.salt = salt;
            login.sha1 = sha1;
            login.sha256 = sha256;
            login.username = username;
            login.uuid = uuid;
            return login;
        }

    }

}
