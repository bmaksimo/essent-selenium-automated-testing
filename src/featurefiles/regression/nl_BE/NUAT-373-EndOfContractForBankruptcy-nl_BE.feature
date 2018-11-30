@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
Feature: NUAT-373: End Of Contract For Bankruptcy - nl_BE

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: End of contract for bankruptcy
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
        And "Klantnummer" input is "151005192"
        And Click on link in View List at 1st row and "Klantnummer & Naam" column polling 15 seconds
        Then Dashboard menu is Contracten

        When Find "Actief" contract
        And Dashboard menu is Marktberichten
        And Click on Start nieuw marktbericht
        And Click Select Contractline
        And Search for ean code
        Then Changes are confirmed

        When Input in Module is "INITIATE STOP ACCESS"
        And Input in Label is "Non-Residential End-of-Contract"
        And "Effective Date" date is "now"
        And "Testing" turn on
        And "Market mock" turn on
        And Changes are confirmed
        Then Confirm task was "Non-Residential End-of-Contract"

        When Refresh "REFRESH MARKTBERICHTEN" till "Geaccepteerd" is visible
        Then Confirm status is "Geaccepteerd"
