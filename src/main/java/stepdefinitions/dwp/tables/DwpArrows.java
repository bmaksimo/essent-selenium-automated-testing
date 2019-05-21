package stepdefinitions.dwp.tables;

public enum DwpArrows {
    Up("Up"),
    Back("Back");
    private String arrow;

    public String getArrow() {
        return arrow;
    }

    DwpArrows(String arrow) {
        this.arrow = arrow;
    }
}
