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
        And  Top Menu Item is Klanten
        And Top Action is Filters
        And "Type klant" input is "Klant"
        And Click on link in View List at 1st row and "Klantnummer & Naam" column
        Then Overview is Billing
