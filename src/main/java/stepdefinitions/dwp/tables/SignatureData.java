package stepdefinitions.dwp.tables;

import stepdefinitions.dwp.quote.DwpDateFormats;

public class SignatureData {

  private DwpDateFormats date;
  private String place;
  private String filePath;

  public SignatureData(DwpDateFormats date, String place, String pathToSingatureFile) {

    this(date, pathToSingatureFile);
    this.place = place;
  }

  public SignatureData(DwpDateFormats date, String pathToSingatureFile) {
    this.date = date;
    this.filePath = pathToSingatureFile;
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
