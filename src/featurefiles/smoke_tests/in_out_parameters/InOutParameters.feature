@SMOKE
@REGRESSION
Feature: DWP Tool Framework: Annotations, Zephyre

    Scenario: Support global parameters that could keep the values through the scenario execution cycle,
               even if scenario steps are defined in the different Java classes.

        When  Contractor is Sjaak van Vliet
        And   Start of tenure is 10-02-2000
        Then  Print contractor tenure date
