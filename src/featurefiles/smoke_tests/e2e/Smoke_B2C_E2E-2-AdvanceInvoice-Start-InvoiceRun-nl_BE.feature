@DWP
@E2E_B2C
@_SMOKE
@REGRESSION
Feature: Billing - Invoices 01 - Start invoiceRun

    Background:

        Given I logged in to DWP as billing.testautomation@essent.be

    Scenario: B2C E2E 2 Start Invoicerun
        When Plus menu is "Billing -> Start facturatierun"
        And Modal dialog is Start invoicerun
        And "Naam job" selection is "recurrent"
        And "ID Billing customer" input is "@id-billing-customer"
        Then Invoice run is scheduled

    #Scenario: Check Advance Invoice
    #    When Left menu is contracting-switching
    #    And Top menu item is Contracten

