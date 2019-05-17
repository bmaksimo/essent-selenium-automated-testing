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

        And Find "Actief" contract
        And Plus action of "1" element from "ContractsOnAccount" and click on "Kopie contract"
        And "Testing" turn on
        And Input in "Mystery switch" is "Hijacked"
        And "EAN-code" input is "parameter:contractEanCode"
        And "Module" selection is "START ACCESS"
        And "Label" selection is "Supplier Switch after Mystery Switch"
        And Save changes
        Then View list header is "Contractlijnen"
        And Table "Contractlijnen" contains value "parameter:contractEanCode" at column "EAN-code & Metertype & Configuratie"

