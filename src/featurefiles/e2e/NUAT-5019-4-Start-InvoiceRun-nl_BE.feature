@DWP
@E2E
@CREDIT-AND-CONTROL
@NUAT-5019
@INVOICE-RUN
Feature: Billing - Advanec Invoice

    Background:

        Given I logged in to DWP as billing.testautomation@essent.be

    Scenario: Trigger Mediation run process
        When Plus menu is "Billing -> Start mediationrun"
        And Modal dialog is Start invoicerun
        And "Naam job" selection is "Voorschot"
        And "ID Billing customer" input is "parameter:id-billing-customer"

        And "EAN-code" input is "parameter:EAN-code"
        And "Datum afrekeningsfactuur" date is "now"
        Then Form is submitted

        When Plus menu is "Billing -> Start facturatierun"
        And Modal dialog is Start invoicerun
        And "Naam job" selection is "recurrent"
        And "ID Billing customer" input is "parameter:id-billing-customer"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "now"
        Then Invoice run is scheduled

    #Scenario: Check Advance Invoice
    #    When Left tab is contracting-switching
    #    And Top menu item is Contracten

