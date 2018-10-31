@DWP
@REGRESSION
@B2B
@BUSINESS-DESK
Feature: NUAT-432: Create A Task For An Other Team - nl_BE

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: Create a task for an other team
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column
        
        When Plus menu is "Service -> Een taak aanmaken voor de klant"
        And "Type" selection is "invoicing"
        And "Subtype" input is "settlement"
        And "Subtype" selection is "settlement"
        And "Onderwerp" input is "Test"
        And "Test Nuat - 432" input in omschrijving
        Then Changes are confirmed

        When Dashboard menu is Service
        Then "Test" is created
