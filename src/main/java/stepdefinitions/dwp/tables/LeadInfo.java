package stepdefinitions.dwp.tables;

import java.util.Objects;

public class LeadInfo {

    String companyName;
    String firstName;
    String secondName;
    String telephone;
    String mobile;
    String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeadInfo)) return false;
        LeadInfo leadInfo = (LeadInfo) o;
        return Objects.equals(companyName, leadInfo.companyName) &&
            Objects.equals(firstName, leadInfo.firstName) &&
            Objects.equals(secondName, leadInfo.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, firstName, secondName);
    }

    @Override
    public String toString() {
        return "LeadInfo{" +
            "companyName='" + companyName + '\'' +
            ", firstName='" + firstName + '\'' +
            ", secondName='" + secondName + '\'' +
            ", telephone='" + telephone + '\'' +
            ", mobile='" + mobile + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
