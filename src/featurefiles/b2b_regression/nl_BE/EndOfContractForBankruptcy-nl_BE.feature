@B2B_REGRESSION
Feature: End of contract for bankruptcy - nl_BE version

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario:
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And "B2C/B2B" selection is "B2B"
        And "Klantnummer" input is "151004631"
        When Click on link in View List at 1st row and "Klantnummer & Naam" column
        Then Dashboard menu is Marktberichten
        When Click on Start nieuw marktbericht
        And Click Select Contractline
        And EAN check box
        Then Changes are confirmed
        And "Module" selection is "INITIATE STOP ACCESS"
        And "Label" selection is "Non-Residential End-of-Contract"
        And "Label" selection is "Non-Residential End-of-Contract"
        And "Effective Date" date is "now"
        Then Changes are confirmed
