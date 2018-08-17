@DWP
@BASIC
@BILLING
@INVOICE
@REGRESSION
Feature: Billing - Invoices

    Background:
        Given I logged in to DWP as billing.testautomation@essent.be

    Scenario: Navigate to "account_cockpit_billing", verify B2C Advance Invoice
        When Left Menu Item is billing
        And Top Menu Item is Klanten
        And Top Action is Filters
        And "B2C/B2B" selection is "B2C"
        And "Type klant" selection is "CUSTOMER"
#        And "Klantnummer" input is "150633291"
        Then View List Header is "Klanten"
        When Click on link in View List at 1st row and "Klantnummer & Naam" column
        And Click on Documenten dashboard menu
        Then View List Header is "Documenten"
        And Document with document type B2BCollectionLetter is available
#        And Document with document type Tussentijdse is available

