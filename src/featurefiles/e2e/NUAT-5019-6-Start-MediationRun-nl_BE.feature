@DWP
@E2E
@CREDIT-AND-CONTROL
@NUAT-5019
@MEDIATION-RUN
Feature: Billing - Triggering mediation run via DWP

    Background:

        Given I logged in to DWP as billing.testautomation@essent.be

    Scenario: Trigger Mediation run process

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

        When Plus menu is "Billing -> Start mediationrun"
        And Modal dialog is Start mediationrun
        And "Naam job" selection is "Voorschot"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "EAN-code" input is "parameter:EAN-code"
        And "Datum afrekeningsfactuur" date is "now"
        Then Form is submitted



