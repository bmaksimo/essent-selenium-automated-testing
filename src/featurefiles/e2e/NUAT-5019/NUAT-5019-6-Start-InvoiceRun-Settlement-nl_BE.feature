@DWP
@E2E
@CREDIT-AND-CONTROL
@NUAT-5019
Feature: Billing - Triggering End Invoice via DWP

    Background:

        Given I logged in to DWP as billing.testautomation@essent.be

    @INVOICE-RUN-SETTLMENT
    Scenario: Trigger Invoice run process

        When Left menu is billing
        And Top menu item is Klanten
        And Top action is Filters
        And "Naam" input is "parameter:suitecrm-customer-name"
        #And "Naam" input is "Ale van puffelen"

        And 1st List element with value at column "Id Billing customer & persoon/familie sleutel" is checked
        And Click on link in View List at 1st row and "Klantnummer & Naam" column
        And Dashboard menu is Contracten
        Then View list header is "Actieve en toekomstige connecties"
        And 1st List element with value at column "EAN-code" is checked
        And Top arrow button is Up

        When Plus menu is "Billing -> Start facturatierun"
        And Modal dialog is Start invoicerun
        And "Naam job" selection is "Eenmalig"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "parameter:billrun-date"
        Then Invoice run is scheduled

        And Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
        And Dashboard menu is Contracten

        And Click on link in View List at 1st row and "EAN-code" column polling 5 seconds
        Then Afrekeningsfacturen list is not empty
