@SCENARIO_INTEGRATION
Feature: Essent Web Automation testing with Cucumber-JVM  and Selenium. Framework, integration of the scenarios.

    Scenario: 1 Retrieve test input parameter, populated by the other (external) scenarios.
    https://emagine-reality.atlassian.net/browse/NSTA-220

        When  Period of tenure is printed
        And   Contractor "parameter:contractor-name" has value "Sjaak van Vliet"
        #And   Fail
