package stepdefinitions.odoo.navigation.search;

public class AdvancedSearch {
    private String field;
    private AdvancedSearchOperator advancedSearchOperator;
    private String searchTerm;

    public AdvancedSearch(String field, AdvancedSearchOperator advancedSearchOperator, String searchTerm) {
        this.field = field;
        this.advancedSearchOperator = advancedSearchOperator;
        this.searchTerm = searchTerm;
    }

    public AdvancedSearch(String field, AdvancedSearchOperator advancedSearchOperator) {
        this(field, advancedSearchOperator, null);
    }

    public AdvancedSearch(String field, String operator, String searchTerm) {
        AdvancedSearchOperator advancedSearchOperator = AdvancedSearchOperator.fromOperator(operator);
        this.field = field;
        this.advancedSearchOperator = advancedSearchOperator;
        this.searchTerm = searchTerm;
    }

    public AdvancedSearch(String field, String operator) {
        this(field, operator, null);
    }

    public String getField() {
        return field;
    }

    public AdvancedSearchOperator getAdvancedSearchOperator() {
        return advancedSearchOperator;
    }

    public String getSearchTerm() {
        return searchTerm;
    }
}
