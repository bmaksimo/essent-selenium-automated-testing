@DWP
@B2B
@REGRESSION
@BUSINESS-DESK
Feature: NUAT-3884: Copy contract

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-3884
    Scenario: Copy Contract
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds

        When Dashboard menu is "Contracten"

        Then Find "Actief" contract
