@DWP
@REGRESSION
@BUSINESS-DESK
@ALL

Feature: NUAT-446: Check The Usage Of A Customer - nl_BE

    Background:
        Given   I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"


    @NUAT-446
    Scenario: Checking usage of a customer
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "1 month before now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Mechelsesteenweg | 2       |            |     | 2550       | Kontich |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        When "Pakket" selection is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Kortingen is "50_part"
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When EAN code is generated
        And "Startdatum" date is "5 day before now"
        And "EAN-code" input is "parameter:EAN-code-generated"
        And "Meternummer" input is "1000"
        And Option "test" is On
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And  Billing details are confirmed
        Then  Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in "Kontich"
        And "Datum ondertekening" date is "now"
        And Quote is confirmed

        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And  "1st" List element with value at column "EAN-code" is checked
        And Get Account Number
        Then  "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds

        When Dashboard menu is "Contracten"
        And View list header is "Actieve en toekomstige connecties"
        And "1st" List element with value at column "EAN-code" is checked
        Then Consumption at deliverypointid "parameter:EAN-code" is generated until "now"

        When I renew login to DWP as "billing.testautomation@essent.be"
        And Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds

        When Plus menu is "Billing -> Verbruiken voor klant"
        And View list header is "Verbruiken"
        And View List is not empty
