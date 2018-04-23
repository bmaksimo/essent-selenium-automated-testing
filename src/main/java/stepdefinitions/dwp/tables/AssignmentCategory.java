package stepdefinitions.dwp.tables;

public enum AssignmentCategory {
    Apparatuur("Apparatuur"),
    Elektra("Elektra"),
    InternetEnComputeronderhoud("Internet en computeronderhoud"),
    RamenDeurenEnSloten("Ramen, deuren en sloten"),
    Verwarming("Verwarming"),
    WaterEnRiolering("Water en riolering");

    private String label;

    private AssignmentCategory(String label) { this.label = label;}

    public String getLabel() { return label; }
}
