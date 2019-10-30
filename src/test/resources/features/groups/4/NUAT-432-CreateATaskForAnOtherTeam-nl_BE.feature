@DWP
@B2B
@REGRESSION
@BUSINESS-DESK
Feature: NUAT-432: Create A Task For An Other Team - nl_BE

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-432
    Scenario: Create a task for an other team
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And B2C TC1 Contract uses "FAKE" address and switch type is "MOVE IN"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds

        When Click on top menu button PLUS and navigate to "Service -> Een taak aanmaken voor de klant"
        And "Type" selection is "invoicing"
        And "Subtype" selection is "settlement"
        And "Onderwerp" input is "Test"
        And "Test Nuat - 432" input in omschrijving
        Then Changes are confirmed

        When Dashboard menu is "Service"
        Then Table "Taken" has matching value "Test" at column "Naam & Type & Subtype"