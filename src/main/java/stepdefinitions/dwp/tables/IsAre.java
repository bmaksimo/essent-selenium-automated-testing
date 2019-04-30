package stepdefinitions.dwp.tables;

public enum IsAre {
  Is("is"),
  Are("are");

  IsAre(String verb) {
    this.verb = verb;
  }

  public String getVerb() {
    return verb;
  }

  public void setVerb(String verb) {
    this.verb = verb;
  }

  private String verb;
}
