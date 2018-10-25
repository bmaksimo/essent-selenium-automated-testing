@DWP
Feature: Dwp for handling task for rejection - nl_BE

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: Handle task for rejection
        When Left menu is Werkbakken
        And Top action is Filters
        And "Status" selection is "Open"
        And Save task ID of first customer in list
        And Plus action and "Mark as done" of first customer from list
        And Resolution input is "Mark as rejected for testing"
        Then Changes are confirmed

        When Left menu is sales-marketing
        And Search for task id
        And Click on link in View List at 1st row and "Klantnaam & nummer" column polling 20 seconds
        And Dashboard menu is Service
        Then "Marktberichten - Rejection" was rejection reason

