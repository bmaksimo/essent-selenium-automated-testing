@DWP
@E2E_B2C
@SMOKE
@AFREKENINGSFACTUUR
Feature: Billing - Invoices 03 - Afrekeningsfactuur

    Background:

        Given I logged in to DWP as billing.testautomation@essent.be

    Scenario: B2C E2E 2 Start Invoicerun
        When Plus menu is "Billing -> Start mediationrun"
        And Modal dialog is Start invoicerun
        And "Naam job" selection is "Voorschot"
        And "ID Billing customer" input is "1000927215"
        And "EAN-code" input is "1000000843"
        And "Datum afrekeningsfactuur" date is "now"
        And Form is submitted

        And Plus menu is "Billing -> Start facturatierun"
        And "Naam job" selection is "Eenmalig"
        And "ID Billing customer" input is "1000927215"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "now"
        And Form is submitted

        And Left menu is billing
        And Top menu item is Klanten
        And Top action is Filters
        And "Klantnummer" input is "1000000843"
        And Click on link in View List at 1st row and "Id Billing customer & persoon/familie sleutel" column
        //TODO
        Then

