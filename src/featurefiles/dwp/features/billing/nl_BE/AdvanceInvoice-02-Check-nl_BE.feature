@DWP
@SERVICE-DESK
Feature: Billing - Invoices - Check - InBetween Document

    Background:

        Given I logged in to DWP as billing.testautomation@essent.be

    Scenario: Navigate to "account_cockpit_billing", verify B2C Advance Invoice
        When Left menu is billing
        And Top menu item is Contracten
        And Top action is Filters
        And "Contracttype" selection is "Passieve hernieuwing"
        And Multiple product input selected is "Electricity Fix B2C (TC1)"
        Then View list header is "Contracten"

        When Click on link in View List at 1st row and "Nummer & Aanmaakdatum" column
        Then Form header is "Contract Details"

        When Dashboard menu is Documenten
        Then View list header is "Documenten"
        And Document with document type Tussentijdse is available

