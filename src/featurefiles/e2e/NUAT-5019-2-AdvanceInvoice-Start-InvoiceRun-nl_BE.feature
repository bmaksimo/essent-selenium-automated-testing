@DWP
@E2E
@SMOKE
@REGRESSION
Feature: Billing - Advanec Invoice

    Background:

        Given I logged in to DWP as billing.testautomation@essent.be

    Scenario: Start Invoicerun will generate advance invoide
        When Plus menu is "Billing -> Start facturatierun"
        And Modal dialog is Start invoicerun
        And "Naam job" selection is "recurrent"
        And "ID Billing customer" input is "parameter:id-billing-customer"
        Then Invoice run is scheduled

    #Scenario: Check Advance Invoice
    #    When Left tab is contracting-switching
    #    And Top menu item is Contracten

