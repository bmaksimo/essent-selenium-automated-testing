@B2B_REGRESSION
Feature: Log questions of customers

    Background:
        Given   I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario:
        When Left menu is Sales-marketing
        And  Top menu item is Klanten
        Then View list header is "Klanten" appears within 25 seconds

        When b2b Account is selected by using "Klantnummer" in filter
        And b2b Plus menu is "Service"
        And b2b "Case aanmaken voor de klant" is selected in Service
        And b2b New case for account is created
        Then b2b Case details are visible when case is opened
