@DWP
@E2E
@ONBOARDING
@CREDIT-AND-CONTROL
@NUAT-5019
Feature: Creating a B2C Quote TC1 with move in, Dutch language version with form headers in English language
    Background:
        Given I logged in to DWP as salesmarketing.testautomation.b2c@essent.be

    Scenario: Create a B2C Quote with move in https://emagine-reality.atlassian.net/browse/NUAT-5019

        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        #Then Form header is "Details van de offerte"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
        And B2C sales channel is Inbound
        And Quote details are confirmed
        #Then Form header is "Persoonsgegevens"
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
            | street          | houseNr | houseNrAdd |  bus | postalCode | city     | country |
            | Mechelsesteenweg| 154     |            |      | 2550       | Kontich  |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        When Package is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When "Startdatum" date is "now"
        And Electricity EAN code is selected
        And Electricity EAN code is put as output parameter "EAN-code"
        And Electricity meter is Closed
        And Electricity market mock test is Open
        And Connection details are confirmed
        Then Form header is "Billing details"


        When Payment details are: method Overschrijving, IBAN "NL57ABNA0874253356", bic "ABNANL2A"
        And Billing details are confirmed
        Then  Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And Quote is signed in Kontich
        And "Datum ondertekening" date is "now"
        And Quote is confirmed
        Then View list header is "Offertes"

        When Plus actions at 1st list row having cell value "Sales Handtekening ontvangen - Geaccepteerd" at column "Type & status" are open
        And List plus action is Bevestig
        And Modal dialog is Sign quote
        And  Contract signature is confirmed
        Then 1st list element has cell value Sales Getekend - Geaccepteerd at column Type & status
        And  Cell value from "1st" row and "Billing klant & Tariefdatum" column is put to parameter "id-billing-customer"

        When Dashboard menu is Contracten
        Then View list header is "Actieve en toekomstige connecties"
        And  1st list element has cell value Actief at column "Contractnummer" polling 450 seconds



