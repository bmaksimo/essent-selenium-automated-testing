package stepdefinitions.dwp.tables;

public class CustomerAddress
{
    private String street;
    private int houseNr;
    private String houseNrAdd;
    private String bus;
    private String postalCode;
    private String city;
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(int houseNr) {
        this.houseNr = houseNr;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouseNrAdd() { return houseNrAdd;  }

    public void setHouseNrAdd(String houseNrAdd) {
        this.houseNrAdd = houseNrAdd;
    }

    public String getBus() { return bus; }

    public void setBus(String bus) { this.bus = bus; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }
}
