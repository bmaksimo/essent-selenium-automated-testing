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
        And "Type klant" selection is "CUSTOMER"
        Then View List Header is "Klanten"
        When Click on link in View List at 1st row and "Klantnummer & Naam" column
        And Overview is Billing
        And Click on link in View List at 1st row and "ID & Type" column
        Then Advanced Invoice is available

