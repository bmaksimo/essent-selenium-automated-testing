@B2B_REGRESSION
Feature: Log questions of customers

    Background:
        Given   I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario:
        When b2b Left menu is "Sales-marketing"
        When b2b Top menu is "Klanten"
        When b2b Account is selected by using "Klantnummer" in filter
        And b2b Plus menu is "Service"
        And b2b "Case aanmaken voor de klant" is selected in Service
        And b2b New case for account is created
        Then b2b Case details are visible when case is opened
