@B2B_REGRESSION
Feature: Create a task for an other team

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario:
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
        And "Omschrijving" input is "Test Nuat - 432"
        Then Changes are confirmed

        When Dashboard menu is Service
        Then "Test" is created
