@ALL
@DWP
@B2C
@REGRESSION
@UNSTABLE
Feature: NSTA-445 Passive renewal of contract TK1 - with communication through Invoice

    Background:
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"

    @NSTA-445
    Scenario: Sign in to default electricity product
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "35 days before now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Random           | 1       |            |     | 2550       | Kontich |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"
        And "Pakket" selection is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When Electricity market mock mode is switched On on "Elektriciteit Vast" card
        And  EAN code is generated
        And "Startdatum" date is "35 days before now"
        And "EAN-code" input is "parameter:EAN-code-generated"
        And Connection details are confirmed
        Then Form header is "Billing details"


        When "Betalingswijze" selection is "Overschrijving"
        And Billing details are confirmed
        Then Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in "Kontich"
        And "Datum ondertekening" date is "35 days before now"
        And Quote is confirmed
        Then View list header is "Offertes"
        Then "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"

        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds

        #"Start & Einddatum" is parsed, start is put to parameterProvider as "Start & Einddatum - start", end - as "Start & Einddatum - end"
        When End of interval from "1st" row of table "Contracten" at column "Start & Einddatum" is checked
        And Top arrow button is "Up"
        And Plus menu is "Contracting -> TK1 Hernieuwingen -> Hernieuwingsbatches"
        Then View list header is "TK1 - Hernieuwingsbatches" appears within 20 seconds
        
        When Click on "START NIEUWE HERNIEUWINGSBATCH" link
        Then Modal dialog is "Start passive renewal batch"
        When "Batchnaam" input is "parameter:suitecrm-customer-name"
        And  "Renewal date from" date is "parameter:Start & Einddatum - start"
        And "Renewal date to" date is "parameter:Start & Einddatum - end"
        And "EAN-code" input is "parameter:EAN-code-generated"
        Then Modal dialogue is confirmed



