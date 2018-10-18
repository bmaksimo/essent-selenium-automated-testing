@DWP
@E2E
Feature: Billing - Invoices 03 - Afrekeningsfactuur

    Background:

        Given I logged in to DWP as billing.testautomation@essent.be

    Scenario: B2C E2E 2 Start Invoicerun
        When Plus menu is "Billing -> Start mediationrun"
        And Modal dialog is Start invoicerun
        And "Naam job" selection is "Voorschot"
        And "ID Billing customer" input is "parameter:id-billing-customer"

        And "EAN-code" input is "parameter:ean-code"
        And "Datum afrekeningsfactuur" date is "now"
        And Form is submitted

        And Top action is Plus Menu
        And Plus menu is "Start facturatierun"
        And "Naam job" selection is "Eenmalig"
        And "ID Billing customer" input is "parameter:id-billing-customer"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "now"
        And Form is submitted

        And Left menu is billing
        And Top menu item is Klanten
        And Top action is Filters
        And "Klantnummer" input is "parameter:id-billing-customer"
        And Click on link in View List at 1st row and "Klantnummer & Naam" column polling 5 seconds
        And Dashboard menu is Contracten

        And Click on link in View List at 1st row and "EAN-code" column polling 5 seconds
        Then Afrekeningsfacturen list is not empty
