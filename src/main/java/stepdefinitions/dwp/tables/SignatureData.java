package stepdefinitions.dwp.tables;

import com.essent.testing.dwp.DwpDateFormats;

public class SignatureData {

    private String firstName;
    private String lastName;
    private DwpDateFormats date;
    private String place;
    private String filePath;

    public SignatureData() {

    }

    public SignatureData(String firstName, String lastName, DwpDateFormats date, String place, String pathToSingatureFile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.place = place;
        this.filePath = pathToSingatureFile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public DwpDateFormats getDate() {
        return date;
    }

    public void setDate(DwpDateFormats date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
