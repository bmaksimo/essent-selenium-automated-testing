@DWP
@BASIC
@_SMOKE
@REGRESSION
Feature: Billing - Invoices

    Background:

        Given I logged in to DWP as billing.testautomation@essent.be

    Scenario: Navigate to "account_cockpit_billing", verify B2C Advance Invoice
        When Left Menu Item is billing
        And Top Menu Item is Contracten
        And Top Action is Filters
        And "Contracttype" selection is "RENEWAL_PASSIVE"
        And Multiple product input selected is "Electricity Fix B2C (TC1)"
        Then View List Header is "Contracten"

        When Click on link in View List at 1st row and "Nummer & Aanmaakdatum" column
        Then Form Header is "Contract Details"

        When Dashboard menu is Documenten
        Then View List Header is "Documenten"
        And Document with document type Tussentijdse is available

