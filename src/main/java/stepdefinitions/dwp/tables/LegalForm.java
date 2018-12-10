package stepdefinitions.dwp.tables;

public enum LegalForm {
    bvba("bvba"),
    cva("cva"),
    cvba("cvba"),
    ebvba("ebvba"),
    gcv("gcv"),
    ivzw("ivzw"),
    nv("nv"),
    vof("vof"),
    vzv("vzv");


    private String option;
    LegalForm(String option) {
        this.option = option;
    }
    public String getOption() {
        return option;
    }
}
