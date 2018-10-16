#@B2B_REGRESSION
Feature: Dwp test for rejecting contract - nl_BE

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario:
        When Left menu is contracting-switching
        And Top menu item is Marktberichten
        And Top action is Filters
        And "Label" selection is "Move In"
        And "Status EC" selection is "Geweigerd"
        Then Plus action and "Herstuur marktbericht" of first customer from list

        When "Startdatum" date is "now"
        And "Testing" turn on
        And "Market mock" turn on
        And Save EAN code of customer
        Then Changes are confirmed

        And Top menu item is Klanten
        And Top action is Filters
        And "EAN-code" input is "541449611000044685"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column

        When Dashboard menu is Marktberichten
        Then Validate rejection status is "MOVE IN"
