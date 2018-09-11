@B2B_REGRESSION
Feature: Create a case with complaint

    Background:
        Given   I logged in to DWP as businessdesk.testautomation.b2b@essent.be
        And b2b Left menu is "Sales-marketing"
        And b2b Top menu is "Klanten"
        And b2b Account is selected by using "Klantnummer" in filter
    @Case_from_plus
    Scenario:
        When b2b Plus menu is "Service"
        And b2b "Case aanmaken voor de klant" is selected in Service
        And b2b New case for account is created
        Then b2b Case details are visible when case is opened

    @Case_from_service
    Scenario:
        When b2b Left button menu is "Service"
        And b2b "CASE TOEVOEGEN" is clicked
        And b2b New case for account is created
        Then b2b Case details are visible when case is opened
