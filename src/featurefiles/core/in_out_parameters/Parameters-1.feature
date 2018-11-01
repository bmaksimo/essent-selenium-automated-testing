@SCENARIO_INTEGRATION
Feature: Essent Web Automation testing with Cucumber-JVM  and Selenium. Framework, integration of the scenarios.

    Scenario: Populate test output parameter for the other (external) scenarios.
    https://emagine-reality.atlassian.net/browse/NSTA-220

        When  Contractor "Sjaak van Vliet" is put as "contractor-name"
        And   Start of tenure "10 years before now" is put as "start-of-tenure"
        #And   Fail
