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
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
        
        When Plus menu is "Service -> Een taak aanmaken voor de klant"
        And "Type" selection is "invoicing"
        And "Subtype" input is "settlement"
        And "Subtype" selection is "settlement"
        And "Onderwerp" input is "Test"
        And "Test Nuat - 432" input in omschrijving
        Then Changes are confirmed

        When Dashboard menu is Service
        Then "Test" is created
