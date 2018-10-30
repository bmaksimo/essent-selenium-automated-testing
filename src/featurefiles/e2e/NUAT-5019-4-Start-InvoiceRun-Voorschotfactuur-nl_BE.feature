@DWP
@E2E
@CREDIT-AND-CONTROL
@NUAT-5019
@INVOICE-RUN-VF
Feature: Billing - Triggering Advanced Invoice via DWP

    Background:

        Given I logged in to DWP as billing.testautomation@essent.be

    Scenario: Trigger Invoice run process

        When Left menu is billing
        And Top menu item is Klanten
        And Top action is Filters
        And "Naam" input is "parameter:suitecrm-customer-name"

        And 1st List element with value at column "Id Billing customer & persoon/familie sleutel" is checked
        And Click on link in View List at 1st row and "Klantnummer & Naam" column
        And Dashboard menu is Contracten
        Then View list header is "Actieve en toekomstige connecties"
        And 1st List element with value at column "EAN-code" is checked
        And Top arrow button is Up

        When Plus menu is "Billing -> Start facturatierun"
        And Modal dialog is Start invoicerun
        And "Naam job" selection is "recurrent"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "1 month from now"
        Then Invoice run is scheduled

    #Scenario: Check Advance Invoice
    #    When Left tab is contracting-switching
    #    And Top menu item is Contracten

