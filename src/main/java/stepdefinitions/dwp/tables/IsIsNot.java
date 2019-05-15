package stepdefinitions.dwp.tables;

public enum IsIsNot {
    IS("is"),
    IS_NOT("is not");

    IsIsNot(String verb) {
        this.verb = verb.toUpperCase();
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    private String verb;

}
