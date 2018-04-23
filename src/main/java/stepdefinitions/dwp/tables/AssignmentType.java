package stepdefinitions.dwp.tables;

public enum AssignmentType {
    Slimme_Rookmelders_Aansluiten("Apparatuur - Slimme rookmelders aansluiten"),
    Verlichting_Ophangen("Elektra - Verlichting Ophangen");

    private String label;

    private AssignmentType(String label) { this.label = label;}

    public String getLabel() { return label; }
}
